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

public class JobSchedulerTest {
    @Test
    public void subtractionSchedulingJobsWithEqualLengths() throws Exception {
        JobScheduler scheduler = new JobScheduler("src/main/resources/pa1/jobscheduling/equallength.txt")
                .useSubtractionScoring();
        long cost = scheduler.getCost();
        // 5 + 8 + 9 + 8 + 5
        assertThat(cost, is(35l));
    }

    @Test
    public void subtractionSchedulingJobsWithEqualWeights() throws Exception {
        JobScheduler scheduler = new JobScheduler("src/main/resources/pa1/jobscheduling/equalweight.txt")
                .useSubtractionScoring();
        long cost = scheduler.getCost();
        // 1 + 3 + 6 + 10 + 15
        assertThat(cost, is(35l));
    }

    @Test
    public void subtractionSchedulingJobsWithEqualScores() throws Exception {
        JobScheduler scheduler = new JobScheduler("src/main/resources/pa1/jobscheduling/equalsubtractionscore.txt")
                .useSubtractionScoring();
        long cost = scheduler.getCost();
        // 25 + 36 + 36 + 28 + 15
        assertThat(cost, is(140l));
    }

    @Test
    public void ratioSchedulingJobsWithEqualLengths() throws Exception {
        JobScheduler scheduler = new JobScheduler("src/main/resources/pa1/jobscheduling/equallength.txt")
                .useRatioScoring();
        long cost = scheduler.getCost();
        // 5 + 8 + 9 + 8 + 5
        assertThat(cost, is(35l));
    }

    @Test
    public void ratioSchedulingJobsWithEqualWeights() throws Exception {
        JobScheduler scheduler = new JobScheduler("src/main/resources/pa1/jobscheduling/equalweight.txt")
                .useRatioScoring();
        long cost = scheduler.getCost();
        // 1 + 3 + 6 + 10 + 15
        assertThat(cost, is(35l));
    }

    @Test
    public void ratioSchedulingJobsWithEqualScores() throws Exception {
        JobScheduler scheduler = new JobScheduler("src/main/resources/pa1/jobscheduling/equalsubtractionscore.txt")
                .useRatioScoring();
        long cost = scheduler.getCost();
        // 25 + 36 + 36 + 28 + 15
        assertThat(cost, is(140l));
    }


    @Test
    public void assignment() throws Exception {
        JobScheduler scheduler = new JobScheduler("src/main/resources/pa1/jobscheduling/jobs.txt")
                .useSubtractionScoring();
        long cost = scheduler.getCost();
        System.out.printf("Cost with subtraction scoring: %d%n", cost);
        assertThat(cost, is(69119377652L));

        scheduler.useRatioScoring();
        cost = scheduler.getCost();
        System.out.printf("Cost with ratio scoring: %d%n", cost);
        assertThat(cost, is(67311454237L));
    }


}
