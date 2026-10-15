package checkout;

/** Settings that are shared by the window, the exporter and the scenes. */
public final class Config {

    /**
     * Size of the video in "scene units". Scenes always draw as if the screen
     * were 1920×1080, no matter how big the exported frames really are.
     */
    public static final int VIDEO_WIDTH = 1920;
    public static final int VIDEO_HEIGHT = 1080;

    /** 1 = exported frames are 1920×1080, 2 = 3840×2160 (4K). */
    public static final int VIDEO_SCALE = 1;

    /** Starting size of the preview window. It can be resized freely. */
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    public static final int FPS = 60;

    public static final long DEFAULT_SEED = 42;

    /** The shop opens at 08:00. */
    public static final int OPENING_SECOND = 8 * 60 * 60;

    private Config() {
    }
}
