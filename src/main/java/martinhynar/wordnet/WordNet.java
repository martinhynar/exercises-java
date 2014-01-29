package martinhynar.wordnet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import stanford.algs4.BreadthFirstDirectedPaths;
import stanford.algs4.Digraph;
import stanford.algs4.Stack;
import stanford.stdlib.In;

/**
 * @author Martin Hynar
 * 
 */
public class WordNet {

    private Map<String, List<Integer>> nounToId;
    private Map<Integer, String> idToNoun;
    private String preNounA;
    private String preNounB;
    private String ancestor;
    private int distance;
    private Digraph digraph;
    private BreadthFirstDirectedPaths vbfs;
    private BreadthFirstDirectedPaths wbfs;

    private static class CycleOrMultiRoot {
        private boolean[] marked; // marked[v] = has vertex v been marked?
        private int[] edgeTo; // edgeTo[v] = previous vertex on path to v
        private boolean[] onStack; // onStack[v] = is vertex on the stack?
        private Stack<Integer> cycle; // directed cycle (or null if no such
                                      // cycle)
        private int zeroOutDegreeVertices;

        public CycleOrMultiRoot(Digraph G) {
            marked = new boolean[G.V()];
            onStack = new boolean[G.V()];
            edgeTo = new int[G.V()];
            zeroOutDegreeVertices = G.V();
            for (int v = 0; v < G.V(); v++)
                if (!marked[v])
                    dfs(G, v);

        }

        // check that algorithm computes either the topological order or finds a
        // directed cycle
        private void dfs(Digraph G, int v) {
            int out = zeroOutDegreeVertices;
            onStack[v] = true;
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (out == zeroOutDegreeVertices) {
                    zeroOutDegreeVertices--;
                }
                // short circuit if directed cycle found
                if (cycle != null)
                    return;

                // found new vertex, so recur
                else if (!marked[w]) {
                    edgeTo[w] = v;
                    dfs(G, w);
                }

                // trace back directed cycle
                else if (onStack[w]) {
                    cycle = new Stack<Integer>();
                    for (int x = v; x != w; x = edgeTo[x]) {
                        cycle.push(x);
                    }
                    cycle.push(w);
                    cycle.push(v);
                }
            }

            onStack[v] = false;
        }

        public boolean hasCycle() {
            return cycle != null;
        }

        /**
         * @return
         */
        public boolean isMultiRoot() {
            return zeroOutDegreeVertices > 1;
        }

    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        nounToId = new TreeMap<String, List<Integer>>();
        idToNoun = new TreeMap<Integer, String>();
        List<Integer> idsOfNoun;
        In sin = new In(synsets);
        In hin = new In(hypernyms);
        String line;
        String[] synset;

        int id = 0;
        int tail, head;

        while (sin.hasNextLine()) {
            line = sin.readLine();
            synset = line.split(",");
            // Read id
            id = Integer.parseInt(synset[0]);
            // Read nouns and store them
            String nounss = synset[1];
            idToNoun.put(id, nounss);
            synset = nounss.split(" ");
            for (String noun : synset) {
                idsOfNoun = nounToId.get(noun);
                if (idsOfNoun == null) {
                    idsOfNoun = new ArrayList<Integer>();
                    nounToId.put(noun, idsOfNoun);
                }
                idsOfNoun.add(id);
            }
            // Read gloss and forget
        }
        digraph = new Digraph(id + 1);
        String[] th;
        while (hin.hasNextLine()) {
            line = hin.readLine();
            th = line.split(",");
            tail = Integer.parseInt(th[0]);
            for (int i = 1; i < th.length; i++) {
                head = Integer.parseInt(th[i]);
                digraph.addEdge(tail, head);
            }
        }
        CycleOrMultiRoot dcmr = new CycleOrMultiRoot(digraph);
        if (dcmr.hasCycle() || dcmr.isMultiRoot()) {
            throw new IllegalArgumentException();
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToId.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounToId.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA)) {
            throw new IllegalArgumentException();
        }
        if (!isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        if (!nounA.equals(this.preNounA) || !nounB.equals(this.preNounB)) {
            this.preNounA = nounA;
            this.preNounB = nounB;
            calculate(nounA, nounB);
        }
        return this.distance;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of
    // nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA)) {
            throw new IllegalArgumentException(nounA + " is not noun");
        }
        if (!isNoun(nounB)) {
            throw new IllegalArgumentException(nounB + " is not noun");
        }
        if (!nounA.equals(this.preNounA) || !nounB.equals(this.preNounB)) {
            this.preNounA = nounA;
            this.preNounB = nounB;
            calculate(nounA, nounB);
        }
        return this.ancestor;
    }

    private void calculate(String nounA, String nounB) {
        int dist = Integer.MAX_VALUE;
        int cdist = 0;
        List<Integer> sourcesA = nounToId.get(nounA);
        List<Integer> sourcesB = nounToId.get(nounB);
        vbfs = new BreadthFirstDirectedPaths(digraph, sourcesA);
        wbfs = new BreadthFirstDirectedPaths(digraph, sourcesB);

        for (int i = 0; i < digraph.V(); i++) {
            if (vbfs.hasPathTo(i) && wbfs.hasPathTo(i)) {
                cdist = vbfs.distTo(i) + wbfs.distTo(i);
                if (cdist < dist) {
                    dist = cdist;
                    this.ancestor = idToNoun.get(i);
                }

            } else {
                continue;
            }
        }
        if (dist == Integer.MAX_VALUE) {
            this.distance = -1;
            this.ancestor = null;
        } else {
            this.distance = dist;
        }
    }

    // for unit testing of this class
    public static void main(String[] args) {
    }
}
