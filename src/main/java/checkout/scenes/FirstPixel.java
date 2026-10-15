package checkout.scenes;

import checkout.Config;
import checkout.render.Palette;
import checkout.sim.Simulation;
import processing.core.PGraphics;

/** An empty supermarket: one breathing dot. */
public final class FirstPixel implements Scene {

    @Override
    public String name() {
        return "first-pixel";
    }

    @Override
    public void draw(PGraphics g, Simulation sim) {
        g.background(Palette.BACKGROUND);

        float breathe = 1 + 0.1f * (float) Math.sin(sim.tick() * 0.05);
        g.noStroke();
        g.fill(Palette.ACCENT);
        g.circle(Config.VIDEO_WIDTH / 2f, Config.VIDEO_HEIGHT / 2f, 40 * breathe);
    }
}
