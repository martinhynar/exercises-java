package martinhynar.shortestpath.singleshortestpath;

import martinhynar.graph.Edge;
import martinhynar.graph.WeightedDirectedGraphList;

import java.util.List;

/**
 * @author mhynar
 * @since 2013-Oct-01
 */
public class BellmanFord {

    private int[][] pathWeights;
    private WeightedDirectedGraphList graph;
    private boolean steps;
    private int start;


    public BellmanFord useGraph(WeightedDirectedGraphList graph) {
        this.graph = graph;
        this.pathWeights = new int[graph.vertices()][graph.vertices()];
        return this;
    }

    public BellmanFord useStartingVertex(int start) {
        this.start = start;
        return this;
    }

    public BellmanFord withSteps(boolean steps) {
        // Initialize
        for (int i = 0; i < graph.vertices(); i++) {
            if (i == start) {
                pathWeights[0][i] = 0;
            } else {
                pathWeights[0][i] = Integer.MAX_VALUE / 2;
            }
        }
        this.steps = steps;
        return this;
    }


    public int getShortestPathWeight() {
        step();
        for (int i = 1; i < pathWeights.length; i++) {
            for (int v = 1; v < pathWeights[i].length; v++) {
                int previous = pathWeights[i - 1][v];
                int minIncrement = getMinIncrement(i, v);
                int min = Math.min(previous, minIncrement);
                pathWeights[i][v] = min;
            }
            step();
        }

        int shortestShortestPath = Integer.MAX_VALUE;
        // Check for negative cycle
        for (int v = 1; v < pathWeights[0].length; v++) {
            if (v != start && pathWeights[pathWeights.length - 1][v] < shortestShortestPath) {
                shortestShortestPath = pathWeights[pathWeights.length - 1][v];
            }
            if (pathWeights[pathWeights.length - 1][v] != pathWeights[pathWeights.length - 2][v]) {
                throw new RuntimeException("Negative cycle detected");
            }
        }


        return shortestShortestPath;
    }

    private int getMinIncrement(int i, int v) {
        List<Edge> edges = graph.getEdges(v);
        int minIncrement = Integer.MAX_VALUE / 2;
        for (Edge edge : edges) {
            int increment = pathWeights[i - 1][edge.u] + edge.weight;
            if (increment < minIncrement) {
                minIncrement = increment;
            }
        }
        return minIncrement;
    }

    private void step() {
        if (steps) {
            for (int i = 0; i < pathWeights.length; i++) {
                System.out.printf("i = %3d : ", i);
                for (int v = 1; v < pathWeights[i].length; v++) {
                    System.out.printf("%12d", pathWeights[i][v]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
