package martinhynar.wordnet;

import org.junit.Before;
import org.junit.Test;
import stanford.algs4.Digraph;
import stanford.stdlib.In;

import java.util.Arrays;

/**
 * @author Martin Hynar
 */
public class SAPInvalidInputsTest {
    private static final String PATH_PREFIX = "src/test/resources/wordnet/sap";
    private Digraph digraph;
    private SAP sap;
    private int lessThanMin = -1;
    private int moreThanMax;
    private int valid;

    @Before
    public void setup() {
        In in = new In(String.format("%s/digraph1.txt", PATH_PREFIX));
        digraph = new Digraph(in);
        sap = new SAP(digraph);
        moreThanMax = digraph.V() + 1;
        valid = digraph.V() - 1;
    }

    // Length
    @Test(expected = IndexOutOfBoundsException.class)
    public void testLength_FirstVertexLessThanZero() throws Exception {
        sap.length(lessThanMin, valid);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLength_FirstVertexGreaterThanMax() throws Exception {
        sap.length(moreThanMax, valid);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLength_SecondVertexLessThanZero() throws Exception {
        sap.length(valid, lessThanMin);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLength_SecondVertexGreaterThanMax() throws Exception {
        sap.length(valid, moreThanMax);
    }

    // Length Set
    @Test(expected = IndexOutOfBoundsException.class)
    public void testLengthSet_FirstVertexLessThanZero() throws Exception {
        Integer[] i = new Integer[]{valid, lessThanMin};
        Integer[] ii = new Integer[]{valid, 0};
        sap.length(Arrays.asList(i), Arrays.asList(ii));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLengthSet_FirstVertexGreaterThanMax() throws Exception {
        Integer[] i = new Integer[]{valid, moreThanMax};
        Integer[] ii = new Integer[]{valid, 0};
        sap.length(Arrays.asList(i), Arrays.asList(ii));
    }

    public void testLengthSet_SecondVertexLessThanZero() throws Exception {
        Integer[] i = new Integer[]{valid, 0};
        Integer[] ii = new Integer[]{valid, lessThanMin};
        sap.length(Arrays.asList(i), Arrays.asList(ii));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLengthSet_SecondVertexGreaterThanMax() throws Exception {
        Integer[] i = new Integer[]{valid, 0};
        Integer[] ii = new Integer[]{valid, moreThanMax};
        sap.length(Arrays.asList(i), Arrays.asList(ii));
    }

    // Ancestors
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAncestor_FirstVertexLessThanZero() throws Exception {
        sap.ancestor(-1, digraph.V() - 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAncestor_FirstVertexGreaterThanMax() throws Exception {
        sap.ancestor(digraph.V() + 1, digraph.V() - 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAncestor_SecondVertexLessThanZero() throws Exception {
        sap.ancestor(digraph.V() - 1, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAncestor_SecondVertexGreaterThanMax() throws Exception {
        sap.ancestor(digraph.V() - 1, digraph.V() + 1);
    }
}
