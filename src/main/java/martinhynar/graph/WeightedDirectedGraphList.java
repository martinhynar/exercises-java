package martinhynar.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mhynar
 * @since 2013-Oct-01
 */
public class WeightedDirectedGraphList implements WeightedDirectedGraph {
    List<Edge>[] adjacency;
    private boolean inAdjacency;
    private int edges = 0;

    public WeightedDirectedGraphList(int vertices, boolean inAdjacency) {
        this.inAdjacency = inAdjacency;
        adjacency = new List[vertices + 1];
        for (int i = 1; i < adjacency.length; i++) {
            adjacency[i] = new ArrayList<Edge>();
        }
    }


    public void addEdge(int tail, int head, int weight) {
        edges++;
        if (inAdjacency) {
            addEdgeInAdjacent(tail, head, weight);
        } else {
            addEdgeOutAdjacent(tail, head, weight);
        }
    }

    public List<Edge> getEdges(int vertex) {
        return adjacency[vertex];
    }

    @Override
    public Edge getEdge(int tail, int head) {
        for (Edge edge : getEdges(tail)) {
            if (edge.u == head) {
                return edge;
            }
        }
        return null;
    }

    public int vertices() {
        return adjacency.length;
    }

    public int edges() {
        return edges;
    }

    private void addEdgeInAdjacent(int tail, int head, int weight) {
        List<Edge> edges = adjacency[head];
        edges.add(new Edge(tail, head, weight));
    }

    private void addEdgeOutAdjacent(int tail, int head, int weight) {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 1; i < adjacency.length; i++) {
            sb.append(String.format("%3d : %s%n", i, adjacency[i]));
        }
        return sb.toString();
    }
}
