package martinhynar.graph;

/**
 * @author mhynar
 * @since 2013-Oct-10
 */

public interface WeightedEdge extends Comparable<WeightedEdge> {

    public double weight();

    public int other(int u);
}
