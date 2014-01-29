package martinhynar.wordnet;

import org.fest.assertions.Assertions;
import org.junit.Test;
import stanford.algs4.Digraph;
import stanford.stdlib.In;
import stanford.stdlib.StdOut;

/**
 * @author Martin Hynar
 */
public class SAPTest {
    @Test
    public void testTwoDisconnectedVertices() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/TwoDisconnectedVertices.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 0;
        int w = 1;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("(%d, %d): length = %d, ancestor = %d\n", v, w, length, ancestor);
        Assertions.assertThat(length).isEqualTo(-1);
        Assertions.assertThat(ancestor).isEqualTo(-1);
    }

    @Test
    public void testTwoVertices_AncestorOfTheSameNodeTwice() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/TwoVertices.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 0;
        int w = 0;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("(%d, %d): length = %d, ancestor = %d\n", v, w, length, ancestor);
        Assertions.assertThat(length).isEqualTo(0);
        Assertions.assertThat(ancestor).isEqualTo(0);
    }

    @Test
    public void testTwoVertices_AncestorOfNodeAndItsParent() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/TwoVertices.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 0;
        int w = 1;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("(%d, %d): length = %d, ancestor = %d\n", v, w, length, ancestor);
        Assertions.assertThat(ancestor).isEqualTo(0);
        Assertions.assertThat(length).isEqualTo(1);

        v = 1;
        w = 0;
        length = sap.length(v, w);
        ancestor = sap.ancestor(v, w);
        StdOut.printf("(%d, %d): length = %d, ancestor = %d\n", v, w, length, ancestor);
        Assertions.assertThat(length).isEqualTo(1);
        Assertions.assertThat(ancestor).isEqualTo(0);
    }

    @Test
    public void testCherryTwin() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/CherryTwin.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 1;
        int w = 2;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(2);
        Assertions.assertThat(ancestor).isEqualTo(0);
    }

    @Test
    public void testFourOnLine_LeafAndRoot() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/FourOnLine.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 0;
        int w = 3;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(3);
        Assertions.assertThat(ancestor).isEqualTo(0);
    }

    @Test
    public void testFourOnLine_LeafAndMiddle() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/FourOnLine.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 1;
        int w = 3;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(2);
        Assertions.assertThat(ancestor).isEqualTo(1);
    }

    @Test
    public void testTwoPathsInTriangle() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/TwoPathsInTriangle.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 1;
        int w = 2;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(2);
        Assertions.assertThat(ancestor).isEqualTo(0);

    }

    @Test
    public void testArrow_TopWings() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/DoubleArrow.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 1;
        int w = 3;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(2);
        Assertions.assertThat(ancestor).isEqualTo(0);
    }

    @Test
    public void testArrow_BottomWings() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/DoubleArrow.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 5;
        int w = 7;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(2);
        Assertions.assertThat(ancestor).isEqualTo(4);
    }

    @Test
    public void testArrow_TopWingBottomWing() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/DoubleArrow.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 1;
        int w = 7;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(4);
        Assertions.assertThat(ancestor).isEqualTo(0);
    }

    @Test
    public void testDigraph1() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 12;
        int w = 7;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(5);
        Assertions.assertThat(ancestor).isEqualTo(1);
    }

    @Test
    public void testDigraph2_2_5() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph2.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 2;
        int w = 5;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(3);
    }

    @Test
    public void testDigraph3_13_14() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 13;
        int w = 14;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(1);
    }

    @Test
    public void testDigraph4_9_9() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph4.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 9;
        int w = 9;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(0);
        Assertions.assertThat(ancestor).isEqualTo(9);
    }

    @Test
    public void testDigraph5_16_21() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph5.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 16;
        int w = 21;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(6);
    }

    @Test
    public void testDigraph6_7_6() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph6.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 7;
        int w = 6;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(1);
    }

    @Test
    public void testDigraph1_12_12() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 12;
        int w = 12;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(0);
        Assertions.assertThat(ancestor).isEqualTo(12);
    }

    @Test
    public void testDigraph2() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph2.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 1;
        int w = 5;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(2);
        Assertions.assertThat(ancestor).isEqualTo(0);
        v = 1;
        w = 3;
        length = sap.length(v, w);
        ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(2);
        Assertions.assertThat(ancestor).isEqualTo(3);
    }

    @Test
    public void testDigraph3() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 12;
        int w = 7;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(2);
        Assertions.assertThat(ancestor).isEqualTo(8);

        v = 8;
        w = 13;
        length = sap.length(v, w);
        ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(5);
        Assertions.assertThat(ancestor).isEqualTo(8);
    }

    @Test
    public void testDigraphAmbiguousAncestor() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph-ambiguous-ancestor.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 1;
        int w = 5;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(3);
        Assertions.assertThat(ancestor).isEqualTo(2);
        v = 1;
        w = 3;
        length = sap.length(v, w);
        ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(2);
        Assertions.assertThat(ancestor).isEqualTo(3);
        v = 0;
        w = 6;
        length = sap.length(v, w);
        ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(5);
        // There is SAP of the same length to 7
        Assertions.assertThat(ancestor).isEqualTo(2);
        v = 10;
        w = 10;
        length = sap.length(v, w);
        ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(0);
        Assertions.assertThat(ancestor).isEqualTo(10);
    }

    @Test
    public void testDigraphWordNet() throws Exception {
        In in = new In("src/test/resources/wordnet/sap/digraph-wordnet.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 18;
        int w = 45550;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(1);
        Assertions.assertThat(ancestor).isEqualTo(45550);

        v = 4;
        w = 4;
        length = sap.length(v, w);
        ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        Assertions.assertThat(length).isEqualTo(0);
        Assertions.assertThat(ancestor).isEqualTo(4);
    }

}
