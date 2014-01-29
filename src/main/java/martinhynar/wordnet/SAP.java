package martinhynar.wordnet;

import java.util.Arrays;
import java.util.List;

import stanford.algs4.BreadthFirstDirectedPaths;
import stanford.algs4.Digraph;

/**
 * @author Martin Hynar
 * 
 */
public class SAP {
    private static final int UNINITIALIZED = Integer.MIN_VALUE;
    private Digraph digraph;
    private int vertices;
    private int ancestor;
    private int distance;

    /**
     * constructor takes a digraph (not necessarily a DAG)
     */
    public SAP(Digraph digraph) {
        this.digraph = new Digraph(digraph);
        this.vertices = digraph.V();
        this.distance = UNINITIALIZED;
        this.ancestor = UNINITIALIZED;
    }

    /**
     * length of shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        // Sanity checking
        verifyVertexBounds(v);
        verifyVertexBounds(w);
        List<Integer> vl = Arrays.asList(v);
        List<Integer> wl = Arrays.asList(w);
        lengthAndAncestor(vl, wl);
        return this.distance;
    }

    /**
     * a common ancestor of v and w that participates in a shortest ancestral
     * path; -1 if no such path
     */
    public int ancestor(int v, int w) {
        // Sanity checking
        verifyVertexBounds(v);
        verifyVertexBounds(w);
        List<Integer> vl = Arrays.asList(v);
        List<Integer> wl = Arrays.asList(w);
        lengthAndAncestor(vl, wl);
        return this.ancestor;
    }

    /**
     * length of shortest ancestral path between any vertex in v and any vertex
     * in w; -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        for (int vi : v) {
            verifyVertexBounds(vi);
            for (int wi : w) {
                verifyVertexBounds(wi);
            }
        }
        lengthAndAncestor(v, w);
        return this.distance;
    }

    /**
     * a common ancestor that participates in shortest ancestral path; -1 if no
     * such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        for (int vi : v) {
            verifyVertexBounds(vi);
            for (int wi : w) {
                verifyVertexBounds(wi);
            }
        }
        lengthAndAncestor(v, w);
        return this.ancestor;
    }

    /**
     * Checks vertex numbers if they are within limit 0 and digraph.V()-1
     */
    private void verifyVertexBounds(int vertex) {
        if (vertex < 0 || vertex >= vertices) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void lengthAndAncestor(Iterable<Integer> v, Iterable<Integer> w) {
        int dist = Integer.MAX_VALUE;
        int cdist = 0;
        BreadthFirstDirectedPaths vbfs;
        vbfs = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wbfs;
        wbfs = new BreadthFirstDirectedPaths(digraph, w);

        int vc = digraph.V();
        for (int i = 0; i < vc; i++) {
            if (vbfs.hasPathTo(i) && wbfs.hasPathTo(i)) {
                cdist = vbfs.distTo(i) + wbfs.distTo(i);
                if (cdist < dist) {
                    dist = cdist;
                    this.ancestor = i;
                }

            } else {
                continue;
            }
        }
        if (dist == Integer.MAX_VALUE) {
            this.distance = -1;
            this.ancestor = -1;
        } else {
            this.distance = dist;
        }
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {

    }
}
