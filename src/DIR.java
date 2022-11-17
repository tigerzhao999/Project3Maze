public enum DIR {
    //direction(bit, column move, row move)
    //bit 1 is North, 2 is South, 4 is East and 8 is West
    //example North N(1,0,-1).
    N(1, -1, 0), S(2, 1, 0), E(4, 0, 1), W(8, 0, -1);
    protected final int bit;
    protected final int dx;
    protected final int dy;
    protected DIR opposite;

    // use the static initializer to resolve forward references
    static {
        N.opposite = S;
        S.opposite = N;
        E.opposite = W;
        W.opposite = E;
    }

    DIR(int bit, int dx, int dy) {
        this.bit = bit;
        this.dx = dx;
        this.dy = dy;
    }
}