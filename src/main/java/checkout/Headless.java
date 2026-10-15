package checkout;

import checkout.sim.Simulation;

/**
 * Runs the simulation without a window, as fast as possible.
 *
 * <pre>./gradlew runHeadless --args="42 43200"</pre>
 * (seed, number of ticks)
 */
public final class Headless {

    public static void main(String[] args) {
        long seed = args.length > 0 ? Long.parseLong(args[0]) : Config.DEFAULT_SEED;
        long ticks = args.length > 1 ? Long.parseLong(args[1]) : 12 * 60 * 60; // one 12-hour day

        Simulation sim = new Simulation(seed);
        long start = System.nanoTime();
        for (long i = 0; i < ticks; i++) {
            sim.step();
        }
        double millis = (System.nanoTime() - start) / 1e6;

        System.out.printf("seed %d: simulated %d ticks in %.1f ms%n", seed, sim.tick(), millis);
    }
}
