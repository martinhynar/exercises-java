package martinhynar.graph;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mhynar
 * Date: 10/6/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WeightedDirectedGraph {

    public void addEdge(int tail, int head, int weight);

    public List<Edge> getEdges(int vertex);

    public Edge getEdge(int tail, int head);

    public int vertices();

    public int edges();
}
