package martinhynar.graph;

/**
 * @author mhynar
 * @since 2013-Oct-10
 */
public interface WeightedUndirectedGraph {

    public int vertices();

    public int edges();

    public WeightedEdge getEdge(int u, int v);

    public void addEdge(int u, int v, double weight);
}
