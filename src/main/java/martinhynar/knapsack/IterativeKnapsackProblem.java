package martinhynar.knapsack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * In this programming problem and the next you'll code up the knapsack algorithm from lecture.
 * Let's start with a warm-up. This input describes a knapsack instance, and it has the following format:
 * <p/>
 * [knapsack_size][number_of_items]
 * [value_1] [weight_1]
 * [value_2] [weight_2]
 * <p/>
 * For example, the third line is "50074 659", indicating that the second item has value 50074 and size 659,
 * respectively. You can assume that all numbers are positive. You should assume that item weights and the knapsack
 * capacity are integers.
 */
public class IterativeKnapsackProblem {
    private Reader source;
    private BufferedReader reader;
    private int knapsackSize;
    List<Item> items;
    int[][] solution;
    private int numberOfItems;
    private boolean printSteps = false;

    public IterativeKnapsackProblem() {
    }

    public IterativeKnapsackProblem useSource(Reader source) {
        this.source = source;
        return this;
    }

    public IterativeKnapsackProblem withSteps() {
        this.printSteps = true;
        return this;
    }

    public long getOptimum() throws IOException {
        reader = new BufferedReader(source);
        String line;
        String[] split = null;
        // First line is: [knapsack_size][number_of_items]
        line = reader.readLine();
        split = line.split("\\p{Blank}+");
        knapsackSize = Integer.parseInt(split[0]);
        numberOfItems = Integer.parseInt(split[1]);
        solution = new int[numberOfItems + 1][knapsackSize + 1];
        items = new ArrayList<>(numberOfItems);
        // Rest of lines are: [value_x] [weight_x]
        while ((line = reader.readLine()) != null) {
            split = line.split("\\p{Blank}+");
            int value = Integer.parseInt(split[0]);
            int weight = Integer.parseInt(split[1]);
            items.add(new Item(value, weight));
        }


        // Initial step: for no item, fill solution line with 0
        // The rest, for better visualization, with -1
        for (int w = 1; w < numberOfItems + 1; w++) {
            Arrays.fill(solution[w], -1);
        }
        printSolution();

        // Go item after item and maximize value of items in knapsack while fitting weight limit
        int x = 1;

        for (int it = 1; it < numberOfItems + 1; it++) {
            // Does current item fit into knapsack?
            Item item = items.get(it - 1);
            for (int w = 0; w < knapsackSize + 1; w++) {
                if (item.weight > w) {
                    // It does not fit, use value of solution above
                    solution[it][w] = solution[it - 1][w];
                } else {
                    // It fits. Shall we use it or is the previous solution better?
                    int notAdding = solution[it - 1][w];

                    int adding = Integer.MIN_VALUE;
                    if ((it - 1 >= 0) && (w - item.weight >= 0)) {
                        adding = item.value + solution[it - 1][w - item.weight];
                    }
                    if (notAdding > adding) {
                        solution[it][w] = notAdding;
                    } else {
                        solution[it][w] = adding;
                    }

                }
                printSolution();
            }
        }

        return solution[numberOfItems][knapsackSize];
    }

    private void printSolution() {
        if (printSteps) {
            System.out.printf("%29s", "Capacity");
            for (int j = -1; j < numberOfItems + 1; j++) {
                if (j == 0) {
                    System.out.printf("%29s", "None");
                    for (int i = 0; i <= knapsackSize; i++) {
                        System.out.printf("%4d", solution[j][i]);
                    }
                } else if (j > 0) {
                    System.out.printf("%s", items.get(j - 1));
                    for (int i = 0; i <= knapsackSize; i++) {
                        System.out.printf("%4d", solution[j][i]);
                    }
                } else {
                    for (int i = 0; i <= knapsackSize; i++) {
                        System.out.printf("%4d", i);
                    }
                    System.out.printf("%n----------------------------------------------------------------------------");
                }
                System.out.printf("%n");
            }
            System.out.printf("%n");
        }
    }

    final private class Item {
        public final int value;
        public final int weight;

        private Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return String.format("[value = %4d, weight = %4d]", value, weight);
        }
    }

}
