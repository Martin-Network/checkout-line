package checkout.render;

import checkout.sim.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldViewTest {

    private final WorldView view = new WorldView(80);

    @Test
    void convertsMetersToPixels() {
        assertEquals(80f, view.px(1));
        assertEquals(40f, view.px(0.5));
    }

    @Test
    void convertsPointsToCanvasPositions() {
        Point p = new Point(2, 3);
        assertEquals(160f, view.x(p));
        assertEquals(240f, view.y(p));
    }
}
