package martinhynar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Problem 1
 * -----------------------------------------
 * In this programming problem and the next you'll code up the greedy algorithms from lecture for minimizing the
 * weighted sum of completion times.. Download the text file
 * http://spark-public.s3.amazonaws.com/algo2/datasets/jobs.txt
 * <p/>
 * This file describes a set of jobs with positive and integral weights and lengths. It has the format
 * [number_of_jobs]
 * [job_1_weight] [job_1_length]
 * [job_2_weight] [job_2_length]
 * ...
 * For example, the third line of the file is "74 59", indicating that the second job has weight 74 and length 59.
 * You should NOT assume that edge weights or lengths are distinct.
 * <p/>
 * Your task in this problem is to run the greedy algorithm that schedules jobs in decreasing order of the difference
 * (weight - length). Recall from lecture that this algorithm is not always optimal. IMPORTANT: if two jobs have equal
 * difference (weight - length), you should schedule the job with higher weight first. Beware: if you break ties in a
 * different way, you are likely to get the wrong answer. You should report the sum of weighted completion times of the
 * resulting schedule --- a positive integer --- in the box below.
 * <p/>
 * ADVICE: If you get the wrong answer, try out some small test cases to debug your algorithm (and post your test
 * cases to the discussion forum)!
 * <p/>
 * Problem 2
 * -----------------------------------------
 * For this problem, use the same data set as in the previous problem. Your task now is to run the greedy algorithm
 * that schedules jobs (optimally) in decreasing order of the ratio (weight/length). In this algorithm, it does not
 * matter how you break ties. You should report the sum of weighted completion times of the resulting
 * schedule --- a positive integer --- in the box below.
 *
 * @author Martin Hynar
 */
public class JobScheduler {
    private boolean useSubtractionScoring;
    private ArrayList<Job> jobs;
    private Comparator<Job> scoring;
    private Reader source;
    private BufferedReader reader;

    private class Job {
        public final int weight;
        public final int length;

        private Job(String weight, String length) {
            this.weight = Integer.parseInt(weight);
            this.length = Integer.parseInt(length);
        }

        @Override
        public String toString() {
            if (JobScheduler.this.useSubtractionScoring) {
                return String.format("[%d, %d, %f]", weight, length, JobScheduler.this.subtractionScore(this));
            } else {
                return String.format("[%d, %d, %f]", weight, length, JobScheduler.this.ratioScore(this));
            }
        }
    }

    public JobScheduler() throws Exception {
        // by default set scoring to ratio-based
        this.useSubtractionScoring = false;
    }

    public JobScheduler useSource(Reader source) {
        this.source = source;
        return this;
    }

    private void readFile() throws IOException {
        reader = new BufferedReader(source);
        String line;
        // First line is number of jobs
        line = reader.readLine();
        int totalJobs = Integer.parseInt(line);
        jobs = new ArrayList<>(totalJobs);
        // Rest of lines are: [job_1_weight] [job_1_length]
        for (int i = 0; i < totalJobs; i++) {
            line = reader.readLine();
            String[] split = line.split("\\p{Blank}+");
            jobs.add(new Job(split[0], split[1]));
        }
    }

    private void setupScoring() {

        scoring = new Comparator<Job>() {
            @Override
            public int compare(Job j1, Job j2) {
                float j1score;
                float j2score;
                if (JobScheduler.this.useSubtractionScoring) {
                    j1score = subtractionScore(j1);
                    j2score = subtractionScore(j2);
                } else {
                    j1score = ratioScore(j1);
                    j2score = ratioScore(j2);
                }
                if (j1score < j2score) {
                    return 1;
                } else if (j1score > j2score) {
                    return -1;
                } else {
                    //equal scores, compare weights
                    if (j1.weight < j2.weight) {
                        return 1;
                    }
                }
                return -1;
            }
        };
    }

    private float subtractionScore(Job job) {
        return job.weight - job.length;
    }

    private float ratioScore(Job job) {
        return (float) job.weight / (float) job.length;
    }

    public JobScheduler useSubtractionScoring() {
        this.useSubtractionScoring = true;
        return this;
    }

    public JobScheduler useRatioScoring() {
        this.useSubtractionScoring = false;
        return this;
    }

    public long getCost() throws IOException {
        readFile();
        // Init scoring
        setupScoring();
        // Sort jobs
        Collections.sort(jobs, scoring);
        long jobCost = 0, cost = 0;
        for (Job job : jobs) {
            jobCost += job.length;
            cost += jobCost * job.weight;
        }
        return cost;
    }
}
