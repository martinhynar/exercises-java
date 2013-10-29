package martinhynar.graph;

/**
 * @author mhynar
 * @since 2013-Oct-06
 */
public class WeightedDirectedGraphMatrix {
    private int[][] adjacencyMatrix;

    public WeightedDirectedGraphMatrix(int vertices) {
        adjacencyMatrix = new int[vertices][vertices];
    }
}
