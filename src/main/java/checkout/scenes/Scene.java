package checkout.scenes;

import checkout.sim.Simulation;
import processing.core.PGraphics;

/**
 * Decides what is shown on screen and how.
 * A scene only reads the simulation and draws it. It never changes it,
 * so the same seed always produces exactly the same pictures.
 */
public interface Scene {

    /** Short name, used for the folder of recorded frames. */
    String name();

    /**
     * Draws the current state of the world. Everything drawn here ends up in recorded frames.
     * Coordinates are in scene units: always 1920×1080, see {@link checkout.Config}.
     */
    void draw(PGraphics g, Simulation sim);
}
