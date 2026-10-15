package checkout;

import checkout.render.FrameExporter;
import checkout.render.Palette;
import checkout.scenes.FirstPixel;
import checkout.scenes.Scene;
import checkout.sim.Simulation;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The window.
 * Runs the simulation, draws the current scene into the video canvas (1920×1080)
 * and shows that canvas scaled to the window. The debug overlay is drawn on the
 * window only, so it never ends up in exported frames.
 *
 * <pre>
 * ./gradlew run
 * ./gradlew run --args="--seed=7 --speed=2"
 * ./gradlew run --args="--export=600"     (record 600 frames, then quit)
 * </pre>
 */
public class App extends PApplet {

    private PGraphics canvas;
    private PFont hudFont;
    private FrameExporter exporter;

    private Simulation sim;
    private final Scene scene = new FirstPixel();

    private long seed = Config.DEFAULT_SEED;
    private float speed = 1;            // ticks per frame
    private float pendingTicks;         // leftover fractions of a tick (slow motion)
    private boolean paused;
    private boolean showHud = true;
    private int exportFrameLimit = -1;  // --export=N

    public static void main(String[] args) {
        PApplet.main(App.class, args);
    }

    @Override
    public void settings() {
        size(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        pixelDensity(displayDensity());
    }

    @Override
    public void setup() {
        surface.setTitle("Checkout Line");
        surface.setResizable(true);
        frameRate(Config.FPS);

        canvas = createGraphics(Config.VIDEO_WIDTH * Config.VIDEO_SCALE, Config.VIDEO_HEIGHT * Config.VIDEO_SCALE);
        canvas.beginDraw();
        canvas.hint(DISABLE_ASYNC_SAVEFRAME); // write frames one after another, none lost on exit
        canvas.endDraw();

        hudFont = createFont("Monospaced", 14);
        exporter = new FrameExporter(Path.of("frames"));

        parseArgs();
        reset(seed);
        if (exportFrameLimit > 0) {
            exporter.start(scene.name());
        }
    }

    @Override
    public void draw() {
        // 1. Move the world forward. Time is counted in frames, not in seconds,
        //    so an export looks the same no matter how fast this computer is.
        if (!paused) {
            pendingTicks += speed;
            while (pendingTicks >= 1) {
                sim.step();
                pendingTicks -= 1;
            }
        }

        // 2. Draw the world into the video canvas.
        canvas.beginDraw();
        canvas.pushMatrix();
        canvas.scale(Config.VIDEO_SCALE);
        scene.draw(canvas, sim);
        canvas.popMatrix();
        canvas.endDraw();

        // 3. Save exactly what the video will show.
        exporter.capture(canvas);
        if (exportFrameLimit > 0 && exporter.framesWritten() >= exportFrameLimit) {
            println("Exported " + exporter.framesWritten() + " frames to " + exporter.currentTake());
            exporter.stop();
            exit();
            return;
        }

        // 4. Show the canvas in the window, debug overlay on top.
        background(Palette.LETTERBOX);
        drawCanvasFitted();
        if (showHud) {
            drawHud();
        }
    }

    @Override
    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == UP) {
                speed = min(speed * 2, 256);
            } else if (keyCode == DOWN) {
                speed = max(speed / 2, 1 / 16f);
            } else if (keyCode == RIGHT && paused) {
                sim.step();
            }
            return;
        }
        switch (Character.toLowerCase(key)) {
            case ' ' -> paused = !paused;
            case 'r' -> reset(seed);
            case 'n' -> reset(ThreadLocalRandom.current().nextLong(1_000_000));
            case 'e' -> toggleExport();
            case 's' -> saveScreenshot();
            case 'h' -> showHud = !showHud;
            default -> {
            }
        }
    }

    private void reset(long newSeed) {
        seed = newSeed;
        sim = new Simulation(seed);
        pendingTicks = 0;
    }

    private void toggleExport() {
        if (exporter.isRecording()) {
            println("Saved " + exporter.framesWritten() + " frames to " + exporter.currentTake());
            exporter.stop();
        } else {
            exporter.start(scene.name());
        }
    }

    private void saveScreenshot() {
        Path file = Path.of("screenshots", scene.name() + "_seed" + seed + "_tick" + sim.tick() + ".png")
                .toAbsolutePath();
        try {
            Files.createDirectories(file.getParent());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        canvas.save(file.toString());
        println("Screenshot: " + file);
    }

    private void drawCanvasFitted() {
        float scale = min((float) width / canvas.width, (float) height / canvas.height);
        float w = canvas.width * scale;
        float h = canvas.height * scale;
        image(canvas, (width - w) / 2, (height - h) / 2, w, h);
    }

    private void drawHud() {
        String[] lines = {
                "seed   " + seed,
                "time   " + clock(sim.tick()) + "   (tick " + sim.tick() + ")",
                "speed  " + speedLabel() + " ticks/frame" + (paused ? "   PAUSED" : ""),
                "fps    " + round(frameRate),
                "",
                "space pause  r reset  n new seed  up/down speed  right step",
                "e record frames  s screenshot  h hide overlay",
        };
        float lineHeight = 18;
        int rows = lines.length + (exporter.isRecording() ? 1 : 0);

        textFont(hudFont);
        textAlign(LEFT, TOP);
        float boxWidth = 0;
        for (String line : lines) {
            boxWidth = max(boxWidth, textWidth(line));
        }

        noStroke();
        fill(0, 170);
        rect(10, 10, boxWidth + 24, rows * lineHeight + 16, 6);

        fill(Palette.TEXT);
        for (int i = 0; i < lines.length; i++) {
            text(lines[i], 22, 18 + i * lineHeight);
        }
        if (exporter.isRecording()) {
            float y = 18 + lines.length * lineHeight;
            fill(Palette.ANGRY);
            circle(27, y + 8, 10);
            text("REC  frame " + exporter.framesWritten(), 38, y);
        }
    }

    private String speedLabel() {
        return speed >= 1 ? String.valueOf((int) speed) : "1/" + Math.round(1 / speed);
    }

    /** Simulated time of day. */
    private static String clock(long tick) {
        long s = Config.OPENING_SECOND + tick * Simulation.SECONDS_PER_TICK;
        return String.format("%02d:%02d:%02d", (s / 3600) % 24, (s / 60) % 60, s % 60);
    }

    private void parseArgs() {
        if (args == null) {
            return;
        }
        for (String arg : args) {
            if (arg.startsWith("--seed=")) {
                seed = Long.parseLong(arg.substring("--seed=".length()));
            } else if (arg.startsWith("--speed=")) {
                speed = Float.parseFloat(arg.substring("--speed=".length()));
            } else if (arg.startsWith("--export=")) {
                exportFrameLimit = Integer.parseInt(arg.substring("--export=".length()));
            }
        }
    }
}
