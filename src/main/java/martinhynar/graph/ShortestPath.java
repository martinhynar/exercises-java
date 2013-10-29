package martinhynar.graph;

/**
 * @author mhynar
 * @since 2013-Oct-06
 */
public class ShortestPath {
    private final double weight;
    private final int[] path;

    public ShortestPath(double weight, int[] path) {
        this.weight = weight;
        this.path = path;
    }

    public double weight() {
        return weight;
    }
}
