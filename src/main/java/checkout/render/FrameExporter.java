package checkout.render;

import processing.core.PGraphics;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Saves every frame of the canvas as a numbered PNG (00000.png, 00001.png, ...).
 * One folder per recording. The README shows how to turn a folder into a video with ffmpeg.
 */
public final class FrameExporter {

    private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private final Path root;
    private Path take;
    private int written;

    public FrameExporter(Path root) {
        this.root = root.toAbsolutePath();
    }

    public void start(String sceneName) {
        take = root.resolve(sceneName + "_" + LocalDateTime.now().format(STAMP));
        written = 0;
        try {
            Files.createDirectories(take);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void stop() {
        take = null;
    }

    public boolean isRecording() {
        return take != null;
    }

    public int framesWritten() {
        return written;
    }

    public Path currentTake() {
        return take;
    }

    /** Call once per frame, after the canvas has been drawn. */
    public void capture(PGraphics canvas) {
        if (take == null) {
            return;
        }
        canvas.save(take.resolve(String.format("%05d.png", written++)).toString());
    }
}
