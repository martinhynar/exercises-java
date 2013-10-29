package martinhynar;

import martinhynar.unionfind.UnionFind;
import martinhynar.unionfind.WeightedUnionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * In this question your task is again to run the clustering algorithm from lecture, but on a MUCH bigger graph.
 * So big, in fact, that the distances (i.e., edge costs) are only defined implicitly, rather than being
 * provided as an explicit list.
 * The data set is here. The format is:
 * [# of nodes] [# of bits for each node's label]
 * [first bit of node 1] ... [last bit of node 1]
 * [first bit of node 2] ... [last bit of node 2]
 * ...
 * For example, the third line of the file "0 1 1 0 0 1 1 0 0 1 0 1 1 1 1 1 1 0 1 0 1 1 0 1"
 * denotes the 24 bits associated with node #2.
 * <p/>
 * The distance between two nodes u and v in this problem is defined as the
 * Hamming distance - the number of differing bits - between the two nodes' labels. For example, the Hamming distance
 * between the 24-bit label of node #2 above and the label "0 1 0 0 0 1 0 0 0 1 0 1 1 1 1 1 1 0 1 0 0 1 0 1" is 3
 * (since they differ in the 3rd, 7th, and 21st bits).
 * <p/>
 * The question is: what is the largest value of k such that there is a k-clustering with spacing at least 3? That is,
 * how many clusters are needed to ensure that no pair of nodes with all but 2 bits in common get split into
 * different clusters?
 * <p/>
 * NOTE: The graph implicitly defined by the data file is so big that you probably can't write it out explicitly,
 * let alone sort the edges by cost. So you will have to be a little creative to complete this part of the question.
 * For example, is there some way you can identify the smallest distances without explicitly looking at every
 * pair of nodes?
 *
 * @author Martin Hynar
 */
public class MaxSpacingKClusteringBinary {
    private int limit;
    private int bits;
    private UnionFind clusters;
    private Map<Integer, Node> nodes;
    private Reader source;
    private BufferedReader reader;

    public MaxSpacingKClusteringBinary useSource(Reader source) {
        this.source = source;
        return this;
    }

    public MaxSpacingKClusteringBinary useLimitSpacing(int limit) {
        this.limit = limit;
        return this;
    }

    public long getMaxK() throws IOException {
        readFile();

        return clusters.getComponents();
    }

    private void readFile() throws IOException {
        reader = new BufferedReader(source);
        String line;
        String[] split = null;
        // First line is: [# of nodes] [# of bits for each node's label]
        line = reader.readLine();
        split = line.split("\\p{Blank}+");
        int totalNodes = Integer.parseInt(split[0]);
        bits = Integer.parseInt(split[1]);

        clusters = new WeightedUnionFind(totalNodes);
//        System.out.printf("Nodes: %d%n", totalNodes);
        int node = 1;
        nodes = new HashMap<>();
        // Rest of lines are: [first bit of node 1] ... [last bit of node 1]
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{Blank}+", "");
            int pos = hash(line);

//            System.out.printf("position of %02d: %s: %d%n", node, Arrays.toString(split), pos);
            Node nd = nodes.get(pos);
            if (nd != null) {
                // Same bit sequence already seen
                hammingCheck(nd.b, line);
                clusters.union(node++, nd.name);
            } else {
                nodes.put(pos, new Node(line, node++));
            }
        }
        for (Map.Entry<Integer, Node> n : nodes.entrySet()) {
            walk(n.getKey(), n.getValue());
        }


    }

    private void walk(int nodeHash, Node node) {
//        System.out.printf("Checking node: %d (%d)%n", node.name, nodeHash);
        int n = 0;

        // single swap
        for (int i = 0; i < bits; i++) {
            String s = swap(node.b, i);
            n = hash(s);
            Node match = nodes.get(n);
            if (match != null) {
                hammingCheck(node.b, s);
                clusters.union(node.name, match.name);
            }
        }
        // double swap
        for (int i = 0; i < bits - 1; i++) {
            for (int j = i + 1; j < bits; j++) {
                String s = swap(node.b, i, j);
                n = hash(s);
                Node match = nodes.get(n);
                if (match != null) {
                    hammingCheck(node.b, s);
                    clusters.union(node.name, match.name);
                }
            }
        }

//        System.out.println("");
    }


    private int hash(String bits) {
        BitSet b = new BitSet(bits.length());
        for (int i = 0; i < bits.length(); i++) {
            b.set(i, bits.charAt(i) == '1');
        }
        return b.hashCode();
    }

    private String swap(String original, int i) {
        int obit = Integer.parseInt(Character.toString(original.charAt(i)));
        StringBuilder copy = new StringBuilder(original);
        if (obit == 1) {
            copy.setCharAt(i, '0');
        } else {
            copy.setCharAt(i, '1');
        }
        return copy.toString();
    }

    private String swap(String original, int i, int j) {
        int obiti = Integer.parseInt(Character.toString(original.charAt(i)));
        int obitj = Integer.parseInt(Character.toString(original.charAt(j)));
        StringBuilder copy = new StringBuilder(original);
        if (obiti == 1) {
            copy.setCharAt(i, '0');
        } else {
            copy.setCharAt(i, '1');
        }
        if (obitj == 1) {
            copy.setCharAt(j, '0');
        } else {
            copy.setCharAt(j, '1');
        }
        return copy.toString();
    }

    private int inc(int i) {
        return (int) Math.pow(7, i);
    }


    private void hammingCheck(String s1, String s2) {
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        int dist = 0;
        for (int i = 0; i < c1.length; i++) {
            if (c1[i] != c2[i]) {
                dist++;
            }
        }
        if (dist > 2) {
            System.out.printf("WARNING:%n%s (%d)%n%s (%d)%n", s1, hash(s1), s2, hash(s2));
        }
    }

    private class Node {
        public final String b;
        public final int name;

        private Node(String b, int name) {
            this.b = b;
            this.name = name;
        }
    }
}
