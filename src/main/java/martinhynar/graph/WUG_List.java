package martinhynar.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mhynar
 * @since 2013-Oct-10
 */
public class WUG_List implements WeightedUndirectedGraph {
    private int vertices;
    private int edges;
    List<WeightedEdge>[] edge;

    public WUG_List(int vertices, int edges) {
        this.vertices = vertices;
        this.edges = edges;
        edge = new List[vertices + 1];
        for (int i = 1; i < edge.length; i++) {
            edge[i] = new ArrayList<>();
        }
    }

    @Override
    public int vertices() {
        return vertices;
    }

    @Override
    public int edges() {
        return edges;
    }

    @Override
    public WeightedEdge getEdge(int u, int v) {
        List<WeightedEdge> edges = edge[u];
        for (WeightedEdge e : edges) {
            if (e.other(u) == v) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void addEdge(int u, int v, double weight) {
        ExplicitWeightedEdge e = new ExplicitWeightedEdge(u, v, weight);
        List<WeightedEdge> edges = edge[u];
        edges.add(e);
        edges = edge[v];
        edges.add(e);
    }
}
