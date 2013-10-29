package martinhynar;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * In this programming problem you'll code up Prim's minimum spanning tree algorithm. Download the text file
 * http://spark-public.s3.amazonaws.com/algo2/datasets/edges.txt
 * This file describes an undirected graph with integer edge costs. It has the format
 * [number_of_nodes] [number_of_edges]
 * [one_node_of_edge_1] [other_node_of_edge_1] [edge_1_cost]
 * [one_node_of_edge_2] [other_node_of_edge_2] [edge_2_cost]
 * ...
 * For example, the third line of the file is "2 3 -8874", indicating that there is an edge connecting vertex #2 and
 * vertex #3 that has cost -8874. You should NOT assume that edge costs are positive, nor should you assume that they
 * are distinct.
 * Your task is to run Prim's minimum spanning tree algorithm on this graph. You should report the overall cost of
 * a minimum spanning tree --- an integer, which may or may not be negative --- in the box below.
 * <p/>
 * IMPLEMENTATION NOTES: This graph is small enough that the straightforward O(mn) time implementation
 * of Prim's algorithm should work fine.
 * <p/>
 * OPTIONAL: For those of you seeking an additional challenge, try implementing a heap-based version. The simpler
 * approach, which should already give you a healthy speed-up, is to maintain relevant edges in a heap
 * (with keys = edge costs). The superior approach stores the unprocessed vertices in the heap, as described in lecture.
 * Note this requires a heap that supports deletions, and you'll probably need to maintain some kind of mapping between
 * vertices and their positions in the heap.
 *
 * @author Martin Hynar
 */
public class PrimMST {
    private Strategy strategy;
    private WeightedUndirectedGraph graph;
    private Reader source;
    private BufferedReader reader;

    public PrimMST() {
    }

    public long getCost() throws IOException {
        readFile();
        switch (strategy) {
            case VERTEX_HEAP:
                throw new UnsupportedOperationException("Vertex heap strategy not implemented");
//                break;
            case EDGE_HEAP:
                throw new UnsupportedOperationException("Edge heap strategy not implemented");
//                break;
            case BRUTE_FORCE:
            default:
                return graph.prim();
        }
    }

    public PrimMST useBruteForceMinCostEdgeSearch() {
        strategy = Strategy.BRUTE_FORCE;
        return this;
    }


    public PrimMST useSource(Reader source) {
        this.source = source;
        return this;
    }

    private void readFile() throws IOException {
        reader = new BufferedReader(source);
        String line;
        // First line is: [number_of_nodes] [number_of_edges]
        line = reader.readLine();
        String[] split = line.split("\\p{Blank}+");
        int totalNodes = Integer.parseInt(split[0]);
        int totalEdges = Integer.parseInt(split[1]);
        this.graph = new WeightedUndirectedGraph(totalNodes);
        // Rest of lines are: [one_node_of_edge_1] [other_node_of_edge_1] [edge_1_cost]
        for (int i = 0; i < totalEdges; i++) {
            line = reader.readLine();
            split = line.split("\\p{Blank}+");
            int vertexU = Integer.parseInt(split[0]);
            int vertexV = Integer.parseInt(split[1]);
            int cost = Integer.parseInt(split[2]);
            graph.add(new Edge(vertexU, vertexV, cost));
        }

    }

    class Edge {
        public final int u;
        public final int v;
        public final int cost;

        public Edge(int u, int v, int cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }

        public int other(int vertex) {
            if (u == vertex) {
                return v;
            } else {
                return u;
            }
        }
    }

    class WeightedUndirectedGraph {
        // Index 0 is unused
        List<Edge>[] adjacency;

        WeightedUndirectedGraph(int vertices) {
            adjacency = new ArrayList[vertices + 1];
            for (int i = 1; i <= vertices; i++) {
                adjacency[i] = new ArrayList<>();
            }
        }

        public void add(Edge edge) {
            // Put the edge into both positions
            adjacency[edge.u].add(edge);
            adjacency[edge.v].add(edge);
        }

        public long prim() {
            // Start with vertex 1
            Set<Integer> visited = new HashSet<>();
            visited.add(1);
            long totalMstCost = 0;
            int minCost = Integer.MAX_VALUE;
            Edge winner = null;
            while (visited.size() != adjacency.length - 1) {
                // Take all vertices from visited
                for (Integer v : visited) {
                    for (Edge edge : adjacency[v]) {
                        if (visited.contains(edge.other(v))) {
                            // Skip edges that do not cross
                            continue;
                        }
                        if (edge.cost < minCost) {
                            // Use '<' to skip same edge coming backwards
                            winner = edge;
                            minCost = winner.cost;
                        }
                    }
                }
                // We have winner here
                assert winner != null;
                visited.add(winner.u);
                visited.add(winner.v);
                totalMstCost += winner.cost;
                // Reset values before next iteration
                winner = null;
                minCost = Integer.MAX_VALUE;

            }
            return totalMstCost;
        }
    }

    enum Strategy {
        BRUTE_FORCE,
        VERTEX_HEAP,
        EDGE_HEAP
    }

}
