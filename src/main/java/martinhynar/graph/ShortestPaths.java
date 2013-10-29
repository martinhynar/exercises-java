package martinhynar.graph;

/**
 * @author mhynar
 * @since 2013-Oct-06
 */
public class ShortestPaths {

    private ShortestPath[][] shortestPaths;

    public ShortestPaths(int vertices) {
        shortestPaths = new ShortestPath[vertices][vertices];
    }

    public void add(int start, int end, ShortestPath shortestPath) {
        shortestPaths[start - 1][end - 1] = shortestPath;
    }

    public ShortestPath get(int start, int end) {
        return shortestPaths[start - 1][end - 1];
    }

}
