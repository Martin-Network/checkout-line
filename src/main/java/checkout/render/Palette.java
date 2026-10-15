package checkout.render;

/**
 * Every colour of the channel in one place, so all recordings look alike.
 * Colours are ARGB ints, the format Processing uses.
 */
public final class Palette {

    public static final int BACKGROUND = 0xFF14161B;
    public static final int SURFACE = 0xFF242833;    // checkouts, panels
    public static final int TEXT = 0xFFE8EAF0;
    public static final int TEXT_DIM = 0xFF8B91A1;
    public static final int ACCENT = 0xFF5AA9FF;     // "the customer we are watching"
    public static final int LETTERBOX = 0xFF000000;  // window area outside the video

    // How long someone has been waiting
    public static final int CALM = 0xFF3DD68C;
    public static final int TENSE = 0xFFF5C451;
    public static final int ANGRY = 0xFFF0605D;

    private Palette() {
    }

    /** Colour of a waiting customer: 0 = just arrived, 1 = waited far too long. */
    public static int waitColor(float t) {
        t = Math.max(0, Math.min(1, t));
        return t < 0.5f
                ? lerp(CALM, TENSE, t * 2)
                : lerp(TENSE, ANGRY, (t - 0.5f) * 2);
    }

    static int lerp(int from, int to, float t) {
        return channel(from, to, t, 24)
                | channel(from, to, t, 16)
                | channel(from, to, t, 8)
                | channel(from, to, t, 0);
    }

    private static int channel(int from, int to, float t, int shift) {
        int a = (from >>> shift) & 0xFF;
        int b = (to >>> shift) & 0xFF;
        return Math.round(a + (b - a) * t) << shift;
    }
}
