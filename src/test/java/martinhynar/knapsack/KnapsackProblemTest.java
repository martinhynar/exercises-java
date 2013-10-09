/*
 * CONFIDENTIAL COMPUTER CODE AND INFORMATION
 * COPYRIGHT (C) 2000-2010 VENDAVO, INC. ALL RIGHTS RESERVED.
 * REPRODUCTION BY ANY MEANS EXPRESSLY FORBIDDEN WITHOUT THE WRITTEN
 * PERMISSION OF THE OWNER.
 */
package martinhynar.knapsack;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class KnapsackProblemTest {
    @Test
    public void knapsack6_items4_optimum5_Iterative() throws Exception {
        IterativeKnapsackProblem knapsack = new IterativeKnapsackProblem()
//                .withSteps()
                .useSourceFile("src/main/resources/knapsack/knapsack6-items4-optimum5.txt");
        long optimum = knapsack.getOptimum();
        assertThat(optimum, is(5L));
    }

    @Test
    public void knapsack6_items4_optimum5_Recursive() throws Exception {
        TwoQueues knapsack = new TwoQueues()
                .withSteps()
                .useSourceFile("src/main/resources/knapsack/knapsack6-items4-optimum5.txt");
        long optimum = knapsack.getOptimum();
        assertThat(optimum, is(5L));
    }

    @Test
    public void knapsack6_items4_optimum4_Recursive() throws Exception {
        TwoQueues knapsack = new TwoQueues()
                .withSteps()
                .useSourceFile("src/main/resources/knapsack/knapsack6-items4-optimum4.txt");
        long optimum = knapsack.getOptimum();
        assertThat(optimum, is(4L));
    }


    @Test
    public void knapsack10_items7_optimum29_Recursive() throws Exception {
        TwoQueues knapsack = new TwoQueues()
                .withSteps()
                .useSourceFile("src/main/resources/knapsack/knapsack10-items7-optimum29.txt");
        double optimum = knapsack.getOptimum();
        assertThat(optimum, is(29d));
    }

    //    @Test
    public void assignment_1_Iterative() throws Exception {
        IterativeKnapsackProblem knapsack = new IterativeKnapsackProblem()
                .useSourceFile("src/main/resources/knapsack/knapsack1.txt");
        long optimum = knapsack.getOptimum();
        assertThat(optimum, is(2493893L));
    }

    @Test
    public void assignment_1_Recursive() throws Exception {
        TwoQueues knapsack = new TwoQueues()
                .withSteps()
                .useSourceFile("src/main/resources/knapsack/knapsack1.txt");
        long optimum = knapsack.getOptimum();
        assertThat(optimum, is(2493893L));
    }

    @Test
    public void assignment_2() throws Exception {
        TwoQueues knapsack = new TwoQueues()
                .useSourceFile("src/main/resources/knapsack/knapsack_big.txt");
        long optimum = knapsack.getOptimum();
        System.out.println("Optimum for Problem 2: " + optimum);
        assertThat(optimum, is(4243395L));
    }

    @Test
    public void some() throws Exception {
        TwoQueues knapsack = new TwoQueues()
//                .withSteps()
                .useSourceFile("src/main/resources/knapsack/some.txt");
        long optimum = knapsack.getOptimum();
        System.out.println("Optimum for Problem 2: " + optimum);
        assertThat(optimum, is(142156L));
    }
}
