package martinhynar.gcd;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author mhynar
 * @since 2013-Oct-10
 */
public class EuclideanGCDTest {
    @Test
    public void testCompute() throws Exception {
        EuclideanGCD gcd = new EuclideanGCD();
        assertThat(gcd.compute(2, 2), is(2L));
        assertThat(gcd.compute(1, 2), is(1L));
        assertThat(gcd.compute(10, 5), is(5L));
        assertThat(gcd.compute(4, 20), is(4L));
        assertThat(gcd.compute(21, 14), is(7L));
        assertThat(gcd.compute(18, 24), is(6L));
        assertThat(gcd.compute(13, 12), is(1L));
        assertThat(gcd.compute(-25, 15), is(5L));
        assertThat(gcd.compute(24, -36), is(12L));
    }
}
