package martinhynar.graph;

/**
 * @author mhynar
 * @since 2013-Oct-10
 */
public class WUG_EuclideanDistance implements WeightedUndirectedGraph {
    private double[][] coordinates;
    int vertex = 0;
    private int vertices;

    public WUG_EuclideanDistance(int vertices) {
        this.vertices = vertices;
        coordinates = new double[vertices][2];
    }

    public void addVertex(double x, double y) {
        coordinates[vertex][0] = x;
        coordinates[vertex][1] = y;
        vertex++;
    }

    public int vertices() {
        return vertices;
    }

    @Override
    public int edges() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public WeightedEdge getEdge(int tail, int head) {
        return new EuclideanEdge(tail, head, euclideanDistance(tail, head));
    }

    @Override
    public void addEdge(int u, int v, double weight) {
        // no-op method
    }

    public double euclideanDistance(int i, int j) {
        // Vertices are numbered from 1
        double xd = (coordinates[i - 1][0] - coordinates[j - 1][0]);
        double yd = (coordinates[i - 1][1] - coordinates[j - 1][1]);
        return Math.sqrt((xd * xd) + (yd * yd));
    }


    private static final class EuclideanEdge implements WeightedEdge {

        private final int u;
        private final int v;
        private final double weight;

        private EuclideanEdge(int u, int v, double weight) {
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
            return v;
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
    }

}
