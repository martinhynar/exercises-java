package martinhynar.knapsack;

import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class KnapsackProblemTest {
    @Test
    public void knapsack6_items4_optimum5_Iterative() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("knapsack/knapsack6-items4-optimum5.txt");
        IterativeKnapsackProblem knapsack = new IterativeKnapsackProblem()
//                .withSteps()
                .useSource(new InputStreamReader(in));
        long optimum = knapsack.getOptimum();
        assertThat(optimum, is(5L));
    }

    @Test
    public void knapsack6_items4_optimum5_Recursive() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("knapsack/knapsack6-items4-optimum5.txt");
        TwoQueues knapsack = new TwoQueues()
                .withSteps()
                .useSource(new InputStreamReader(in));
        long optimum = knapsack.getOptimum();
        assertThat(optimum, is(5L));
    }

    @Test
    public void knapsack6_items4_optimum4_Recursive() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("knapsack/knapsack6-items4-optimum4.txt");
        TwoQueues knapsack = new TwoQueues()
                .withSteps()
                .useSource(new InputStreamReader(in));
        long optimum = knapsack.getOptimum();
        assertThat(optimum, is(4L));
    }


    @Test
    public void knapsack10_items7_optimum29_Recursive() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("knapsack/knapsack10-items7-optimum29.txt");
        TwoQueues knapsack = new TwoQueues()
                .withSteps()
                .useSource(new InputStreamReader(in));
        double optimum = knapsack.getOptimum();
        assertThat(optimum, is(29d));
    }

    @Test
    public void assignment_1_Iterative() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("knapsack/knapsack1.txt");
        IterativeKnapsackProblem knapsack = new IterativeKnapsackProblem()
                .useSource(new InputStreamReader(in));
        long optimum = knapsack.getOptimum();
        assertThat(optimum, is(2493893L));
    }

    @Test
    public void assignment_1_Recursive() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("knapsack/knapsack1.txt");
        TwoQueues knapsack = new TwoQueues()
                .withSteps()
                .useSource(new InputStreamReader(in));
        long optimum = knapsack.getOptimum();
        assertThat(optimum, is(2493893L));
    }

    @Test
    public void assignment_2() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("knapsack/knapsack_big.txt");
        TwoQueues knapsack = new TwoQueues()
                .useSource(new InputStreamReader(in));
        long optimum = knapsack.getOptimum();
        System.out.println("Optimum for Problem 2: " + optimum);
        assertThat(optimum, is(4243395L));
    }

    @Test
    public void some() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("knapsack/some.txt");
        TwoQueues knapsack = new TwoQueues()
//                .withSteps()
                .useSource(new InputStreamReader(in));
        long optimum = knapsack.getOptimum();
        System.out.println("Optimum for Problem 2: " + optimum);
        assertThat(optimum, is(142156L));
    }
}
