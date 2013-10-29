package martinhynar.tsp;

import martinhynar.graph.WeightedEdge;
import martinhynar.graph.WeightedUndirectedGraph;

import java.io.IOException;
import java.util.*;

/**
 * In this assignment you will implement one or more algorithms for the traveling salesman problem, such as the dynamic
 * programming algorithm covered in the video lectures. Here is a data file describing a TSP instance. The first line
 * indicates the number of cities. Each city is a point in the plane, and each subsequent line indicates the
 * x- and y-coordinates of a single city.
 * The distance between two cities is defined as the Euclidean distance --- that is, two cities at locations (x,y)
 * and (z,w) have distance sqrt((x−z)^2+(y−w)^2) between them.
 * <p/>
 * The result shall be rounded down to the nearest integer.
 * <p/>
 * OPTIONAL: If you want bigger data sets to play with, check out the TSP instances from around the world here:
 * http://www.tsp.gatech.edu/world/countries.html
 * The smallest data set (Western Sahara) has 29 cities, and most of the data sets are much bigger than that. What's the
 * largest of these data sets that you're able to solve --- using dynamic programming or, if you like, a completely
 * different method?
 * <p/>
 * HINT: You might experiment with ways to reduce the data set size. For example, trying plotting the points.
 * Can you infer any structure of the optimal solution? Can you use that structure to speed up your algorithm?
 *
 * @author mhynar
 */
public class TravelingSalesmanProblemDP {

    private boolean steps;
    private WeightedUndirectedGraph graph;

    private double[][] subProblems;
    private Map<Integer, List<SubSet>> allSubSets;
    private Map<SubSet, Integer> indexes;
    private Map<Integer, SubSet> rindexes;
    private int noSubSets = 0;
    private int index = 0;

    public TravelingSalesmanProblemDP useGraph(WeightedUndirectedGraph graph) {
        this.graph = graph;
        return this;
    }


    public TravelingSalesmanProblemDP withSteps(boolean steps) {
        this.steps = steps;
        if (steps) {
            rindexes = new HashMap<>();
        }
        return this;
    }

    public double getMinTour() throws IOException {
        generateInitialSubSets();
        System.out.println("Subsets generated.");
//        org.apache.commons.math3.util.ArithmeticUtils s;
        subProblems = new double[40000000][graph.vertices()];
        List<SubSet> subsets;
        // Initialize
        subProblems[0][0] = 0;
        for (int i = 1; i < subProblems.length; i++) {
            subProblems[i][0] = Double.POSITIVE_INFINITY;
        }
//        for (int probSize = 1; probSize <= graph.vertices(); probSize++) {
//            subsets = allSubSets.get(probSize);
//            for (SubSet subset : subsets) {
//                if (subset.isStarting()) {
//                    subProblems[indexes.get(subset)][0] = 0;
//                } else {
//                    subProblems[indexes.get(subset)][0] = Double.POSITIVE_INFINITY;
//                }
//            }
//        }
//        print();

        for (int probSize = 2; probSize <= graph.vertices(); probSize++) {
            generateSubSets(probSize);
            subsets = allSubSets.get(probSize);
            for (SubSet subset : subsets) {
                double minimum = Double.POSITIVE_INFINITY;
                for (int v : subset.vertices()) {
                    if (v == 1) continue;
                    SubSet nonv = subset.subtract(v);
                    for (int k : nonv.vertices()) {
                        WeightedEdge edge = graph.getEdge(k, v);
//                        System.out.printf("(%d, %d) = %s%n", k, v, edge);

                        int index = indexes.get(nonv);
                        double weight = subProblems[index][k - 1] + edge.weight();
                        minimum = Math.min(minimum, weight);
                    }
                    subProblems[indexes.get(subset)][v - 1] = minimum;
                }
            }
            print();
            System.out.printf("Problem size %d done.%n", probSize);
        }

        SubSet all = allSubSets.get(graph.vertices()).get(0);
        double minTour = Double.POSITIVE_INFINITY;
        for (int j = 2; j < graph.vertices(); j++) {
            double weight = subProblems[indexes.get(all)][j] + graph.getEdge(j, 1).weight();
//            System.out.println(weight);
            minTour = Math.min(minTour, weight);
        }
        return minTour;
    }

    private void generateInitialSubSets() {
        allSubSets = new HashMap<>();
        indexes = new HashMap<>();
        SubSet subset;
        // Size 1 sets
        List<SubSet> subSets = new ArrayList<>();
        for (int v = 1; v <= graph.vertices(); v++) {
            subset = new SubSet(new int[]{v});
            subSets.add(subset);
            setIndex(subset, index++);
        }
        allSubSets.put(1, subSets);
        noSubSets += subSets.size();
        System.out.printf("Initial sets generated.%n");
    }

    private void generateSubSets(int size) {
        int toRemove = size - 2;
        if (toRemove >= 1) {
            allSubSets.remove(toRemove);
        }
        SubSet subset;

        List<SubSet> previousSubsets = allSubSets.get(size - 1);
        List<SubSet> subSets = new ArrayList<>();
        for (SubSet ps : previousSubsets) {
            int mv = ps.maxVertex();
            for (int v = mv + 1; v <= graph.vertices(); v++) {
                subset = ps.add(v);
                subSets.add(subset);
                setIndex(subset, index++);
            }
        }
        allSubSets.put(size, subSets);
        noSubSets += subSets.size();
        System.out.printf("Subset of size %3d done - %d subsets.%n", size, subSets.size());
    }

    private void setIndex(SubSet subset, int index) {
        indexes.put(subset, index);
        if (steps) {
            rindexes.put(index, subset);
        }
    }

    private void print() {
        steps = false;
        if (steps) {
            int maxlenght = allSubSets.get(graph.vertices()).toString().length();
            String format = "%" + maxlenght + "s";
            for (int i = 0; i < subProblems.length; i++) {
                System.out.printf(format, rindexes.get(i));
                for (int v = 0; v < subProblems[i].length; v++) {
                    System.out.printf("%12f", subProblems[i][v]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static class SubSet {

        private int[] vertices;

        private SubSet(int[] vertices) {
            this.vertices = vertices;

        }

        public int[] vertices() {
            return vertices;
        }


        public boolean isStarting() {
            return vertices.length == 1 && vertices[0] == 1;
        }

        @Override
        public String toString() {
            return String.format("%s", Arrays.toString(vertices));
        }

        public SubSet subtract(int v) {
            int[] ver = new int[this.vertices.length - 1];
            for (int j = 0, k = 0; j < this.vertices.length; j++) {
                if (vertices[j] != v) {
                    ver[k] = vertices[j];
                    k++;
                }
            }
            SubSet subtracted = new SubSet(ver);
            return subtracted;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            if (!this.toString().equals(o.toString())) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        public int maxVertex() {
            return vertices[vertices.length - 1];
        }

        public SubSet add(int v) {
            int[] ver = new int[this.vertices.length + 1];
            System.arraycopy(this.vertices, 0, ver, 0, vertices.length);
            ver[vertices.length] = v;
            return new SubSet(ver);
        }
    }
}
