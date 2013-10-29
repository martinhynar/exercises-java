package martinhynar;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author mhynar
 * @since 2013-Sep-22
 */
public class MaxSpacingKClusteringBinaryTest {
    @Test
    public void cluster_4_of_7_k3() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("maxspacingkclustering/4-of-7-k3-binary.txt");
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSource(new InputStreamReader(in))
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        Assert.assertThat(maxK, is(4L));
    }

    @Test
    public void cluster_6_of_8_k3() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("maxspacingkclustering/6-of-8-k3-binary.txt");
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSource(new InputStreamReader(in))
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        Assert.assertThat(maxK, is(6L));
    }

    @Test
    public void cluster_45_of_48_k3() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("maxspacingkclustering/45-of-48-k3-binary.txt");
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSource(new InputStreamReader(in))
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        Assert.assertThat(maxK, is(45L));
    }

    @Test
    public void cluster_1_of_5_k3() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("maxspacingkclustering/1-of-5-k3-binary.txt");
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSource(new InputStreamReader(in))
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        Assert.assertThat(maxK, is(1L));
    }

    @Test
    public void cluster_big_100000_32() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("maxspacingkclustering/cluster_big_100000_32.txt");
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSource(new InputStreamReader(in))
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        Assert.assertThat(maxK, is(99398L));
    }

    @Test
    public void assignment() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("maxspacingkclustering/clustering_big.txt");
        MaxSpacingKClusteringBinary clustering = new MaxSpacingKClusteringBinary()
                .useSource(new InputStreamReader(in))
                .useLimitSpacing(3);
        long maxK = clustering.getMaxK();
        System.out.println("Asigmentn rs: " + maxK);
        Assert.assertThat(maxK, is(6118L));
    }
}
