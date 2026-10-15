package checkout.sim;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationTest {

    @Test
    void sameSeedGivesTheSameWorld() {
        Simulation a = new Simulation(7);
        Simulation b = new Simulation(7);
        for (int i = 0; i < 1_000; i++) {
            assertEquals(a.arrivals().nextInt(), b.arrivals().nextInt());
        }
    }

    @Test
    void decisionsCannotChangeWhoWalksIn() {
        Simulation calm = new Simulation(7);
        Simulation busy = new Simulation(7);

        // Customers who roll a lot of dice while deciding...
        for (int i = 0; i < 500; i++) {
            busy.decisions().nextInt();
        }

        // ...must not change which customers arrive.
        for (int i = 0; i < 1_000; i++) {
            assertEquals(calm.arrivals().nextInt(), busy.arrivals().nextInt());
        }
    }

    @Test
    void stepMovesTimeForwardByOneTick() {
        Simulation sim = new Simulation(1);
        sim.step();
        sim.step();
        assertEquals(2, sim.tick());
    }
}
