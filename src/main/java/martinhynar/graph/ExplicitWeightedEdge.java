package martinhynar.graph;

/**
 * @author mhynar
 * @since 2013-Oct-10
 */
public class ExplicitWeightedEdge implements WeightedEdge {
    private final int u;
    private final int v;
    private final double weight;

    public ExplicitWeightedEdge(int u, int v, double weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public double weight() {
        return weight;
    }

    @Override
    public int other(int vertex) {
        if (vertex == u) {
            return v;
        }
        return u;
    }

    @Override
    public int compareTo(WeightedEdge that) {
        if (this.weight() == that.weight()) {
            return 0;
        } else if (this.weight() < that.weight()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %f)", u, v, weight);
    }
}
