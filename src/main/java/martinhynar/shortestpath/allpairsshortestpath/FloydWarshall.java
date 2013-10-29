package martinhynar.shortestpath.allpairsshortestpath;

import martinhynar.graph.Edge;
import martinhynar.graph.ShortestPath;
import martinhynar.graph.ShortestPaths;
import martinhynar.graph.WeightedDirectedGraphList;

/**
 */
public class FloydWarshall {
    private double[][][] pathWeights;
    private int[][][] paths;
    private boolean steps;
    private WeightedDirectedGraphList graph;

    public FloydWarshall() {
    }

    public FloydWarshall useGraph(WeightedDirectedGraphList graph) {
        this.graph = graph;
        return this;
    }

    public FloydWarshall withSteps(boolean steps) {
        this.steps = steps;
        return this;
    }

    public ShortestPaths getShortestPaths() {
        // Initialize level 0 of the weights
        pathWeights = new double[graph.vertices()][graph.vertices()][graph.vertices()];
        for (int i = 0; i < graph.vertices() - 1; i++) {
            for (int j = 0; j < graph.vertices() - 1; j++) {
                if (i == j) {
                    pathWeights[i][j][0] = 0;
                    continue;
                }
                Edge edge = graph.getEdge(i + 1, j + 1);
                if (edge != null) {
                    pathWeights[i][j][0] = edge.weight;
                } else {
                    pathWeights[i][j][0] = Double.POSITIVE_INFINITY;
                }
            }
        }
        step(0);
        // Calculate weights until k-1
        for (int k = 1; k < graph.vertices() - 1; k++) {
            for (int i = 0; i < graph.vertices() - 1; i++) {
                for (int j = 0; j < graph.vertices() - 1; j++) {
                    double previousWeight = pathWeights[i][j][k - 1];
                    double x = pathWeights[i][k][k - 1] + pathWeights[k][j][k - 1];
                    double winner = Math.min(previousWeight, x);
                    pathWeights[i][j][k] = winner;
                }
            }
            step(k);
        }
        int k = graph.vertices() - 1;
        ShortestPaths shortestPaths = new ShortestPaths(graph.vertices());
        // Last round with filling result
        for (int i = 0; i < graph.vertices(); i++) {
            for (int j = 0; j < graph.vertices(); j++) {
                double previousWeight = pathWeights[i][j][k - 1];
                double x = pathWeights[i][k][k - 1] + pathWeights[k][j][k - 1];
                double winner = Math.min(previousWeight, x);
                pathWeights[i][j][k] = winner;
                shortestPaths.add(i + 1, j + 1, new ShortestPath(winner, new int[]{}));
            }
        }
        step(k);
        return shortestPaths;
    }

    private void step(int k) {
        if (steps) {
            System.out.printf("k = %d%n", k);
            for (int i = 0; i < pathWeights.length - 1; i++) {
                for (int v = 0; v < pathWeights[i].length - 1; v++) {
                    System.out.printf("%12f", pathWeights[i][v][k]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}
