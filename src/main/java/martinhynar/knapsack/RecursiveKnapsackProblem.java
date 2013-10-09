package martinhynar.knapsack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This problem also asks you to solve a knapsack instance, but a much bigger one.
 * This input describes a knapsack instance, and it has the following format:
 * <p/>
 * [knapsack_size][number_of_items]
 * [value_1] [weight_1]
 * [value_2] [weight_2]
 * <p/>
 * For example, the third line of the file is "50074 834558", indicating that the second item has value 50074 and
 * size 834558, respectively. As before, you should assume that item weights and the knapsack capacity are integers.
 * <p/>
 * This instance is so big that the straightforward iterative implemetation uses an infeasible amount of time and space.
 * So you will have to be creative to compute an optimal solution. One idea is to go back to a recursive implementation,
 * solving subproblems --- and, of course, caching the results to avoid redundant work --- only on an "as needed" basis.
 * Also, be sure to think about appropriate data structures for storing and looking up solutions to subproblems.
 */
public class RecursiveKnapsackProblem {
    public static final int UNKNOWN = -1;
    private String filename;
    private int knapsackSize;
    private Element[] elements;
    private int numberOfItems;
    private boolean printSteps = false;
    private long[] maxFit;
    private int[] index;
    private Map<String, Long> subproblems;
    double maxProfit = 0.0d;
    PriorityQueue<Item> q = new PriorityQueue<>();

    public RecursiveKnapsackProblem() {
    }

    public RecursiveKnapsackProblem useSourceFile(String filename) {
        this.filename = filename;
        return this;
    }

    public RecursiveKnapsackProblem withSteps() {
        this.printSteps = true;
        return this;
    }

    private float bound(Item item) {

        int j, k;
        int weight;
        float value;

        if (item.weight >= knapsackSize) {
            return 0;
        } else {
            value = item.profit;
            j = item.level + 1;
            weight = item.weight;
            while (j <= numberOfItems && weight + elements[j].weight <= knapsackSize) {
                weight += elements[j].weight;
                value += elements[j].value;
                j++;
            }
            k = j;
            if (k <= numberOfItems) {
                value += (knapsackSize - weight) * elements[k].value / elements[k].weight;
            }

        }
        System.out.printf("Bound value calculated to %f for %s%n", value, item);
        return value;
    }

    public double agetOptimum() throws IOException, CloneNotSupportedException {
        readFile();
//        Arrays.sort(elements);

        return branchAndBound(elements, knapsackSize);

    }


    public double getOptimum() throws IOException, CloneNotSupportedException {
        readFile();
        Arrays.sort(elements);
        Item u = new Item();
        // ROOT
        Item v = new Item();
        v.weight = 0;
        v.profit = 0;
        v.level = 0;
        maxProfit = 0;
        v.bound = bound(v);
        q.add(v);

        while (!q.isEmpty()) {
            v = (Item) q.remove();
            printSolution();
            if (v.bound > maxProfit) {

                u.level = v.level + 1;
                u.weight = v.weight + elements[u.level].weight;
                u.profit = v.profit + elements[u.level].value;

                if (u.weight <= knapsackSize && u.profit > maxProfit) {
                    maxProfit = u.profit;
                }
                u.bound = bound(u);
                if (bound(u) > maxProfit) {
                    q.add(u.clone());
                }

                u.weight = v.weight;
                u.profit = v.profit;
                u.bound = bound(u);
                if (u.bound > maxProfit) {
                    q.add(u.clone());
                }
            }
        }
        return maxProfit;
    }

    private void readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        String[] split;
        // First line is: [knapsack_size][number_of_items]
        line = reader.readLine();
        split = line.split("\\p{Blank}+");
        knapsackSize = Integer.parseInt(split[0]);
        numberOfItems = Integer.parseInt(split[1]);
        System.out.printf("Expecting %d elements%n", numberOfItems);
        Element[] allElements = new Element[numberOfItems + 1];
        int i = 1;
        // Rest of lines are: [value_x] [weight_x]
        allElements[0] = new Element(0, 0);
        // allElements[0] = new Element(0, 0);
        while ((line = reader.readLine()) != null) {
            split = line.split("\\p{Blank}+");
            int value = Integer.parseInt(split[0]);
            int weight = Integer.parseInt(split[1]);
            if (weight <= knapsackSize) {
                allElements[i++] = new Element(value, weight);
            } else {
                numberOfItems--;
            }
        }
        elements = new Element[numberOfItems+1];
        for (int j = 0, k = 0; j < allElements.length; j++) {
            if (allElements[j] != null) {
                elements[k++] = allElements[j];
            }
        }
        System.out.printf("Read %d elements%n", i - 1);
    }


    int branchAndBound(Element[] items, int W) {

        int n = numberOfItems;

        int[] p = new int[n];
        int[] w = new int[n];

        for (int i = 0; i < n; i++) {

            p[i] = (int) items[i].value;
            w[i] = (int) items[i].weight;

        }

        Item u = new Item();
        Item v = new Item(); // tree root

        int maxProfit = 0;


        v.level = -1;
        v.profit = 0;
        v.weight = 0; // v initialized to -1, dummy root

        q.offer(v); // place the dummy at the root

        while (!q.isEmpty()) {

            v = q.remove();

            u = new Item();
            if (v.level == -1) {
                u.level = 0;
            } else if (v.level != (n - 1)) {
                u.level = v.level + 1; // set u to be a child of v
            }

            u.weight = v.weight + w[u.level];// set u to the child
            u.profit = v.profit + p[u.level]; // that includes the
            //next item
            float bound = boundX(u, W, n, w, p);
            u.bound = bound;


            if (u.weight <= W && u.profit > maxProfit) {
                maxProfit = u.profit;
            }

            if (bound > maxProfit) {
                q.add(u);
            }

            u = new Item();
            if (v.level == -1) {
                u.level = 0;
            } else if (v.level != (n - 1)) {
                u.level = v.level + 1; // set u to be a child of v
            }

            u.weight = v.weight; // set u to the child that
            u.profit = v.profit;// does NOT include the next item

            bound = boundX(u, W, n, w, p);
            u.bound = bound;

            if (bound > maxProfit) {
                q.add(u);
            }
        }


        return maxProfit;
    }

    public static float boundX(Item u, int W, int n, int[] w, int[] p) {

        int j = 0;
        int k = 0;
        int totWeight = 0;
        float result = 0;

        if (u.weight >= W)
            return 0;
        else {
            result = u.profit;
            j = u.level + 1;
            totWeight = u.weight;

            while ((j < n) && (totWeight + w[j] <= W)) {
                totWeight = totWeight + w[j]; // grab as many items as possible
                result = result + p[j];
                j++;
            }
            k = j; // use k for consistency with formula in text
            if (k < n)
                result = result + (W - totWeight) * p[k] / w[k];// grab fraction of kth item

            return result;
        }

    }


    private void printSolution() {
        if (printSteps) {
//            System.out.printf("%s%n", elements[0]);
        }
    }

    final private static class Element implements Comparable<Element> {
        public final int value;
        public final int weight;

        private Element(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        @Override
        public String toString() {
            float ratio = -1;
            if (weight != 0) {
                ratio = ((float) value) / weight;
            }
            return String.format("(%d, %d, %f)", value, weight, ratio);
        }

        @Override
        public int compareTo(Element that) {
            float thisRatio = ((float) value) / weight;
            float thatRatio = ((float) that.value) / that.weight;
            if (thisRatio < thatRatio) {
                return 1;
            } else if (thisRatio > thatRatio) {
                return -1;
            }
            return 0;
        }
    }


    final private static class Item implements Comparable<Item>, Cloneable {
        int profit, weight, level;
        float bound;

        @Override
        protected Item clone() throws CloneNotSupportedException {
            Item item = new Item();
            item.profit = this.profit;
            item.weight = this.weight;
            item.level = this.level;
            item.bound = this.bound;
            return item;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Item{");
            sb.append("profit=").append(profit);
            sb.append(", weight=").append(weight);
            sb.append(", level=").append(level);
            sb.append(", bound=").append(bound);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public int compareTo(Item that) {
            if (this.bound < that.bound) {
                return 1;
            } else if (this.bound > that.bound) {
                return -1;
            }
            return 0;
        }
    }

}
