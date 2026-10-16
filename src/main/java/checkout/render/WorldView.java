package checkout.render;

import checkout.sim.Point;

/** Turns positions in the shop (meters) into positions on the canvas (pixels). */
public final class WorldView {

    private final float pixelsPerMeter;

    public WorldView(float pixelsPerMeter) {
        this.pixelsPerMeter = pixelsPerMeter;
    }

    /** A length in meters as a length in pixels. */
    public float px(double meters) {
        return (float) (meters * pixelsPerMeter);
    }

    public float x(Point p) {
        return px(p.x());
    }

    public float y(Point p) {
        return px(p.y());
    }
}
