/*
 * CONFIDENTIAL COMPUTER CODE AND INFORMATION
 * COPYRIGHT (C) 2000-2010 VENDAVO, INC. ALL RIGHTS RESERVED.
 * REPRODUCTION BY ANY MEANS EXPRESSLY FORBIDDEN WITHOUT THE WRITTEN
 * PERMISSION OF THE OWNER.
 */
package martinhynar;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author mhynar
 * @since 2013-Sep-22
 */
public class MaxSpacingKClusteringBinaryTest {
    @Test
    public void cluster_4_of_7_k3() throws Exception {
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSourceFile("src/main/resources/maxspacingkclustering/4-of-7-k3-binary.txt")
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        Assert.assertThat(maxK, CoreMatchers.is(4L));
    }

    @Test
    public void cluster_6_of_8_k3() throws Exception {
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSourceFile("src/main/resources/maxspacingkclustering/6-of-8-k3-binary.txt")
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        Assert.assertThat(maxK, CoreMatchers.is(6L));
    }

    @Test
    public void cluster_45_of_48_k3() throws Exception {
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSourceFile("src/main/resources/maxspacingkclustering/45-of-48-k3-binary.txt")
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        Assert.assertThat(maxK, CoreMatchers.is(45L));
    }

    @Test
    public void cluster_1_of_5_k3() throws Exception {
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSourceFile("src/main/resources/maxspacingkclustering/1-of-5-k3-binary.txt")
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        Assert.assertThat(maxK, CoreMatchers.is(1L));
    }

    @Test
    public void cluster_big_100000_32() throws Exception {
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSourceFile("src/main/resources/maxspacingkclustering/cluster_big_100000_32.txt")
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        Assert.assertThat(maxK, CoreMatchers.is(99398L));
    }

    @Test
    public void assignment() throws Exception {
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSourceFile("src/main/resources/maxspacingkclustering/clustering_big.txt")
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        System.out.println("Asigmentn rs: " + maxK);
        Assert.assertThat(maxK, CoreMatchers.is(6118L));
    }
}
