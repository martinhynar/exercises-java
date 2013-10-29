package martinhynar;

import martinhynar.graph.Edge;
import martinhynar.unionfind.UnionFind;
import martinhynar.unionfind.WeightedUnionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.PriorityQueue;

/**
 * In this programming problem and the next you'll code up the clustering algorithm from lecture for computing
 * a max-spacing k-clustering. This file describes a distance function (equivalently, a complete graph with edge costs).
 * It has the following format:
 * <p/>
 * [number_of_nodes]
 * [edge 1 node 1] [edge 1 node 2] [edge 1 cost]
 * [edge 2 node 1] [edge 2 node 2] [edge 2 cost]
 * ...
 * <p/>
 * There is one edge (i,j) for each choice of 1<=i<j<=n, where n is the number of nodes. For example, the third line of
 * the file is "1 3 5250", indicating that the distance between nodes 1 and 3 (equivalently, the cost of the
 * edge (1,3)) is 5250. You can assume that distances are positive, but you should NOT assume that they are distinct.
 * <p/>
 * Your task in this problem is to run the clustering algorithm from lecture on this data set, where the target
 * number k of clusters is set to 4. What is the maximum spacing of a 4-clustering?
 *
 * @author Martin Hynar
 */
public class MaxSpacingKClustering {
    private int targetClusters;
    private int actualClusters;
    private PriorityQueue<Edge> edges;
    private UnionFind clusters;
    private Reader source;
    private BufferedReader reader;

    public MaxSpacingKClustering() {

    }

    public MaxSpacingKClustering useTargetClusters(int targetClusters) {
        this.targetClusters = targetClusters;
        return this;
    }

    public MaxSpacingKClustering useSource(Reader source) {
        this.source = source;
        return this;
    }

    public long getMaximumSpacing() throws IOException {
        readFile();
        Edge min;
        while (actualClusters != targetClusters) {
            // take edge with minimum cost
            min = edges.remove();
            // Are components of the edge's ends connected?
            if (!clusters.isConnected(min.u, min.v)) {
                clusters.union(min.u, min.v);
                actualClusters--;
            }
        }
        // Take rest of the edges that belong to same components
        do {
            min = edges.remove();
        } while (clusters.isConnected(min.u, min.v));

        return min.weight;
    }

    private void readFile() throws IOException {
        BufferedReader reader = new BufferedReader(source);
        String line;
        // First line is: number of nodes
        line = reader.readLine();
        int totalNodes = Integer.parseInt(line);
        actualClusters = totalNodes;
        edges = new PriorityQueue<>(totalNodes);
        clusters = new WeightedUnionFind(totalNodes);
        String[] split = null;
        // Rest of lines are: [edge x node 1] [edge x node 2] [edge x cost]
        while ((line = reader.readLine()) != null) {
            split = line.split("\\p{Blank}+");
            int u = Integer.parseInt(split[0]);
            int v = Integer.parseInt(split[1]);
            int cost = Integer.parseInt(split[2]);
            edges.add(new Edge(u, v, cost));
        }

    }
}
