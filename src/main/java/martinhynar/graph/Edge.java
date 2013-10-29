package martinhynar.graph;

/**
 * @author mhynar
 * @since 2013-Sep-21
 */
public class Edge implements Comparable<Edge> {
    public final int u;
    public final int v;
    public final int weight;

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge that) {
        return this.weight - that.weight;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", u, v, weight);
    }
}
