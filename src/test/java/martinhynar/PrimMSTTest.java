/*
 * CONFIDENTIAL COMPUTER CODE AND INFORMATION
 * COPYRIGHT (C) 2000-2010 VENDAVO, INC. ALL RIGHTS RESERVED.
 * REPRODUCTION BY ANY MEANS EXPRESSLY FORBIDDEN WITHOUT THE WRITTEN
 * PERMISSION OF THE OWNER.
 */
package martinhynar;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author mhynar
 * @since 2013-Sep-05
 */
public class PrimMSTTest {
    @Test
    public void squareWithEqualEdges_BF() throws Exception {
        long cost = new PrimMST()
                .readGraphFromFile("src/main/resources/pa1/prim/squarewithequaledges.txt")
                .useBruteForceMinCostEdgeSearch()
                .getCost();

        assertThat(cost, is(3L));
    }

    @Test
    public void squareWithVaryingEdges_BF() throws Exception {
        long cost = new PrimMST()
                .readGraphFromFile("src/main/resources/pa1/prim/squarewithvaryingedges.txt")
                .useBruteForceMinCostEdgeSearch()
                .getCost();

        assertThat(cost, is(6L));
    }

    @Test
    public void line_BF() throws Exception {
        long cost = new PrimMST()
                .readGraphFromFile("src/main/resources/pa1/prim/squarewithequaledges.txt")
                .useBruteForceMinCostEdgeSearch()
                .getCost();
        assertThat(cost, is(3L));
    }

    @Test
    public void graph5_BF() throws Exception {
        long cost = new PrimMST()
                .readGraphFromFile("src/main/resources/pa1/prim/grapgwith5vertexes.txt")
                .useBruteForceMinCostEdgeSearch()
                .getCost();
        assertThat(cost, is(4L));
    }

    @Test
    public void assignment() throws Exception {
        long cost = new PrimMST()
                .readGraphFromFile("src/main/resources/pa1/prim/edges.txt")
                .useBruteForceMinCostEdgeSearch()
                .getCost();
        assertThat(cost, is(-3612829L));
    }
}
