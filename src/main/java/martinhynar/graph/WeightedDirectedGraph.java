package martinhynar.graph;

import java.util.List;

/**
 * @author mhynar
 * @since 2013-Oct-01
 */
public interface WeightedDirectedGraph {

    public void addEdge(int tail, int head, int weight);

    public List<Edge> getEdges(int vertex);

    public Edge getEdge(int tail, int head);

    public int vertices();

    public int edges();
}
