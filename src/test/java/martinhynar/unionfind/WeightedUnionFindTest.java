package martinhynar.unionfind;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WeightedUnionFindTest {
    @Test
    public void twoItemsAreConnectedAfterUnion() throws Exception {
        UnionFind uf = new WeightedUnionFind(2);
        int itemA = 1;
        int itemB = 2;
        uf.union(itemA, itemB);
        assertTrue(uf.isConnected(itemA, itemB));
        assertEquals(uf.find(itemA), uf.find(itemB));
    }

    @Test
    public void threeItemsAreConnectedAfterUnion() throws Exception {
        UnionFind uf = new WeightedUnionFind(3);
        int itemA = 1;
        int itemB = 2;
        int itemC = 3;
        uf.union(itemA, itemB);
        uf.union(itemA, itemC);
        assertTrue(uf.isConnected(itemA, itemB));
        assertTrue(uf.isConnected(itemB, itemC));
        assertTrue(uf.isConnected(itemC, itemA));
        assertEquals(uf.find(itemA), uf.find(itemB));
        assertEquals(uf.find(itemA), uf.find(itemC));
        assertEquals(uf.find(itemB), uf.find(itemC));
    }
}
