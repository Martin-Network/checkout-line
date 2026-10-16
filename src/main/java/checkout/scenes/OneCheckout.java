package checkout.scenes;

import checkout.Config;
import checkout.render.Palette;
import checkout.render.WorldView;
import checkout.sim.Checkout;
import checkout.sim.Simulation;
import processing.core.PGraphics;

import static processing.core.PConstants.CENTER;

/** A shop with a single checkout. */
public class OneCheckout implements Scene {

    // Size of a counter in meters
    private static final double COUNTER_WIDTH = 1.8;
    private static final double COUNTER_DEPTH = 0.9;

    // The whole shop fills the canvas: 1920 px / 24 m = 80 px per meter
    private final WorldView view = new WorldView((float) (Config.VIDEO_WIDTH / Simulation.SHOP_WIDTH));

    @Override
    public String name() {
        return "one-checkout";
    }

    @Override
    public void draw(PGraphics g, Simulation sim) {
        g.background(Palette.BACKGROUND);

        for (Checkout checkout : sim.checkouts()) {
            drawCheckout(g, checkout);
        }
    }

    private void drawCheckout(PGraphics g, Checkout checkout) {
        float x = view.x(checkout.register());
        float y = view.y(checkout.register());

        g.push();
        g.rectMode(CENTER);
        g.noStroke();

        // Counter
        g.fill(Palette.SURFACE);
        g.rect(x, y, view.px(COUNTER_WIDTH), view.px(COUNTER_DEPTH), view.px(0.12));

        // Conveyor belt along the counter
        g.fill(Palette.BACKGROUND);
        g.rect(x - view.px(0.2), y, view.px(1.1), view.px(0.35), view.px(0.06));

        // Register at the end of the belt
        g.fill(Palette.TEXT_DIM);
        g.rect(x + view.px(0.6), y, view.px(0.3), view.px(0.3), view.px(0.05));

        g.pop();
    }
}
