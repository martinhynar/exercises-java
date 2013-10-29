package martinhynar;


import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JobSchedulerTest {
    @Test
    public void subtractionSchedulingJobsWithEqualLengths() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jobscheduling/equallength.txt");
        JobScheduler scheduler = new JobScheduler()
                .useSource(new InputStreamReader(in))
                .useSubtractionScoring();
        long cost = scheduler.getCost();
        // 5 + 8 + 9 + 8 + 5
        assertThat(cost, is(35l));
    }

    @Test
    public void subtractionSchedulingJobsWithEqualWeights() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jobscheduling/equalweight.txt");
        JobScheduler scheduler = new JobScheduler()
                .useSource(new InputStreamReader(in))
                .useSubtractionScoring();
        long cost = scheduler.getCost();
        // 1 + 3 + 6 + 10 + 15
        assertThat(cost, is(35l));
    }

    @Test
    public void subtractionSchedulingJobsWithEqualScores() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jobscheduling/equalsubtractionscore.txt");
        JobScheduler scheduler = new JobScheduler()
                .useSource(new InputStreamReader(in))
                .useSubtractionScoring();
        long cost = scheduler.getCost();
        // 25 + 36 + 36 + 28 + 15
        assertThat(cost, is(140l));
    }

    @Test
    public void ratioSchedulingJobsWithEqualLengths() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jobscheduling/equallength.txt");
        JobScheduler scheduler = new JobScheduler()
                .useSource(new InputStreamReader(in))
                .useRatioScoring();
        long cost = scheduler.getCost();
        // 5 + 8 + 9 + 8 + 5
        assertThat(cost, is(35l));
    }

    @Test
    public void ratioSchedulingJobsWithEqualWeights() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jobscheduling/equalweight.txt");
        JobScheduler scheduler = new JobScheduler()
                .useSource(new InputStreamReader(in))
                .useRatioScoring();
        long cost = scheduler.getCost();
        // 1 + 3 + 6 + 10 + 15
        assertThat(cost, is(35l));
    }

    @Test
    public void ratioSchedulingJobsWithEqualScores() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jobscheduling/equalsubtractionscore.txt");
        JobScheduler scheduler = new JobScheduler()
                .useSource(new InputStreamReader(in))
                .useRatioScoring();
        long cost = scheduler.getCost();
        // 25 + 36 + 36 + 28 + 15
        assertThat(cost, is(140l));
    }


    @Test
    public void assignment_Subtraction() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jobscheduling/jobs.txt");
        JobScheduler scheduler = new JobScheduler()
                .useSource(new InputStreamReader(in))
                .useSubtractionScoring();
        long cost = scheduler.getCost();
        System.out.printf("Cost with subtraction scoring: %d%n", cost);
        assertThat(cost, is(69119377652L));
    }

    @Test
    public void assignment() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jobscheduling/jobs.txt");
        JobScheduler scheduler = new JobScheduler()
                .useSource(new InputStreamReader(in))
                .useRatioScoring();
        long cost = scheduler.getCost();
        System.out.printf("Cost with subtraction scoring: %d%n", cost);
        assertThat(cost, is(67311454237L));
    }


}
