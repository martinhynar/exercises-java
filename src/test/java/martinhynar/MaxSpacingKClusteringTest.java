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

public class MaxSpacingKClusteringTest {

    @Test
    public void cluster_4_of_9() throws Exception {
        MaxSpacingKClustering clustering = new MaxSpacingKClustering()
                .useSourceFile("src/main/resources/maxspacingkclustering/4-of-9.txt")
                .useTargetClusters(4);
        long maximumSpacing = clustering.getMaximumSpacing();
        assertThat(maximumSpacing, is(7L));
    }

    @Test
    public void cluster_4_of_8() throws Exception {
        MaxSpacingKClustering clustering = new MaxSpacingKClustering()
                .useSourceFile("src/main/resources/maxspacingkclustering/4-of-8.txt")
                .useTargetClusters(4);
        long maximumSpacing = clustering.getMaximumSpacing();
        assertThat(maximumSpacing, is(28L));
    }

    @Test
    public void assignment() throws Exception {
        MaxSpacingKClustering clustering = new MaxSpacingKClustering()
                .useSourceFile("src/main/resources/maxspacingkclustering/clustering1.txt")
                .useTargetClusters(4);
        long maximumSpacing = clustering.getMaximumSpacing();
        System.out.printf("Maximum spacing: %d%n", maximumSpacing);
        assertThat(maximumSpacing, is(106L));
    }


}
