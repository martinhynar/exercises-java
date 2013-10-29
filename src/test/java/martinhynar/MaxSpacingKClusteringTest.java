package martinhynar;

import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MaxSpacingKClusteringTest {

    @Test
    public void cluster_4_of_9() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("maxspacingkclustering/4-of-9.txt");
        MaxSpacingKClustering clustering = new MaxSpacingKClustering()
                .useSource(new InputStreamReader(in))
                .useTargetClusters(4);
        long maximumSpacing = clustering.getMaximumSpacing();
        assertThat(maximumSpacing, is(7L));
    }

    @Test
    public void cluster_4_of_8() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("maxspacingkclustering/4-of-8.txt");
        MaxSpacingKClustering clustering = new MaxSpacingKClustering()
                .useSource(new InputStreamReader(in))
                .useTargetClusters(4);
        long maximumSpacing = clustering.getMaximumSpacing();
        assertThat(maximumSpacing, is(28L));
    }

    @Test
    public void assignment() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("maxspacingkclustering/clustering1.txt");
        MaxSpacingKClustering clustering = new MaxSpacingKClustering()
                .useSource(new InputStreamReader(in))
                .useTargetClusters(4);
        long maximumSpacing = clustering.getMaximumSpacing();
        System.out.printf("Maximum spacing: %d%n", maximumSpacing);
        assertThat(maximumSpacing, is(106L));
    }


}
