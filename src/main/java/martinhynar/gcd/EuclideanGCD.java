package martinhynar.gcd;

/**
 * @author mhynar
 * @since 2013-Oct-10
 */
public class EuclideanGCD {
    long compute(long a, long b) {
        long remainder = 0;
        // Need to divide greater number with lesser
        long g = Math.max(a, b);
        long l = Math.min(a, b);

        do {
            remainder = g % l;
            // The original lesser number becomes new greater
            g = l;
            // The new lesser number is the remainder
            l = remainder;

        } while (remainder != 0);

        // Lets take absolute value to tackle negative numbers:
        // for all a, b: gcd (a, b) = gcd (-a, b) = gcd(a, -b) = gcd(-a, -b)
        return Math.abs(g);
    }
}
