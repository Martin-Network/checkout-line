package checkout.sim;

import java.util.Random;

/**
 * The supermarket, without any graphics.
 * Everything that happens is decided here, one tick at a time.
 * This package never imports Processing, so it can also run without a window.
 */
public final class Simulation {

    /** One tick is one simulated second. */
    public static final int SECONDS_PER_TICK = 1;

    private final long seed;

    // Two separate sources of randomness:
    // "arrivals" decides who walks into the shop and when,
    // "decisions" is used by the customers when they choose a line.
    // Keeping them apart means every strategy gets exactly the same customers,
    // no matter how many dice the strategy itself rolls.
    private final Random arrivals;
    private final Random decisions;

    private long tick;

    public Simulation(long seed) {
        this.seed = seed;
        this.arrivals = new Random(seed);
        this.decisions = new Random(mix(seed));
    }

    /** Moves the world forward by one tick. */
    public void step() {
        tick++;
    }

    public long seed() {
        return seed;
    }

    public long tick() {
        return tick;
    }

    Random arrivals() {
        return arrivals;
    }

    Random decisions() {
        return decisions;
    }

    /** Turns one seed into a second, unrelated-looking seed (SplitMix64 finaliser). */
    private static long mix(long seed) {
        long z = seed + 0x9E3779B97F4A7C15L;
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        return z ^ (z >>> 31);
    }
}
