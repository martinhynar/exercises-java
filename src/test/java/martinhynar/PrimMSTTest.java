package martinhynar;

import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author mhynar
 * @since 2013-Sep-05
 */
public class PrimMSTTest {
    @Test
    public void squareWithEqualEdges_BF() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("prim/squarewithequaledges.txt");
        long cost = new PrimMST()
                .useSource(new InputStreamReader(in))
                .useBruteForceMinCostEdgeSearch()
                .getCost();

        assertThat(cost, is(3L));
    }

    @Test
    public void squareWithVaryingEdges_BF() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("prim/squarewithvaryingedges.txt");
        long cost = new PrimMST()
                .useSource(new InputStreamReader(in))
                .useBruteForceMinCostEdgeSearch()
                .getCost();

        assertThat(cost, is(6L));
    }

    @Test
    public void line_BF() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("prim/squarewithequaledges.txt");
        long cost = new PrimMST()
                .useSource(new InputStreamReader(in))
                .useBruteForceMinCostEdgeSearch()
                .getCost();
        assertThat(cost, is(3L));
    }

    @Test
    public void graph5_BF() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("prim/grapgwith5vertexes.txt");
        long cost = new PrimMST()
                .useSource(new InputStreamReader(in))
                .useBruteForceMinCostEdgeSearch()
                .getCost();
        assertThat(cost, is(4L));
    }

    @Test
    public void assignment() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("prim/edges.txt");
        long cost = new PrimMST()
                .useSource(new InputStreamReader(in))
                .useBruteForceMinCostEdgeSearch()
                .getCost();
        assertThat(cost, is(-3612829L));
    }
}
