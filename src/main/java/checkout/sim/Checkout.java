package checkout.sim;

/** A checkout counter with its register. */
public final class Checkout {

    private final int id;
    private final Point register;

    public Checkout(int id, Point register) {
        this.id = id;
        this.register = register;
    }

    public int id() {
        return id;
    }

    /** Centre of the counter. */
    public Point register() {
        return register;
    }
}
