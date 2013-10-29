package martinhynar.knapsack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * @author mhynar
 * @since 2013-Sep-29
 */
public class TwoQueues {

    private Reader source;
    private BufferedReader reader;
    private int knapsackSize;
    private boolean printSteps = false;
    private int numberOfItems;

    private PriorityQueue<Element> a, b;
    private List<Element> elements;

    private Map<Integer, Integer> choices = new HashMap<>();
    private Map<Integer, Integer> betterChoices = new HashMap<>();

    public TwoQueues() {
    }

    public TwoQueues useSource(Reader source) {
        this.source = source;
        return this;
    }

    public TwoQueues withSteps() {
        this.printSteps = true;
        return this;
    }


    public long getOptimum() throws IOException {
        readFile();

        a = new PriorityQueue<>();

        choices.put(0, 0);
        for (Element element : elements) {
            Set<Map.Entry<Integer, Integer>> entries = choices.entrySet();
            for (Map.Entry<Integer, Integer> entry : entries) {
                int newWeight = entry.getKey() + element.weight;
                int newValue = entry.getValue() + element.value;
                if (newWeight > knapsackSize) {
                    continue;
                }
                if (choices.get(newWeight) == null || choices.get(newWeight) < newValue) {
                    if (betterChoices.get(newWeight) == null || betterChoices.get(newWeight) < newValue) {
                        betterChoices.put(newWeight, newValue);
                        a.add(new Element(newValue, newWeight));
                    }
                }
            }
            entries = betterChoices.entrySet();
            for (Map.Entry<Integer, Integer> entry : entries) {
                choices.put(entry.getKey(), entry.getValue());
            }
            betterChoices.clear();
        }

        Element winner = a.peek();
        ;
        System.out.printf("Winner: %s%n", winner);
        return winner.value;
    }

    public long qgetOptimum() throws IOException {
        readFile();

        a = new PriorityQueue<>();
        b = new PriorityQueue<>();

//        a.add(elements.get(0));
        Element j = elements.get(0).join(elements.get(1));
        b.add(elements.get(0));
        if (j.weight <= knapsackSize) {
            b.add(j);
        }
        b.add(elements.get(1));

        printSolution();
        for (int i = 2; i < elements.size(); i++) {
            for (Element element : b) {
                a.add(element);
            }
            b.clear();
            Element next = elements.get(i);
            b.add(next);
            for (Element element : a) {
                Element join = element.join(next);
                if (join.weight <= knapsackSize) {
                    b.add(join);
                } else {
                    b.add(element);
                }
            }
            printSolution();
            a.clear();
        }

        return b.peek().value;
    }

    private void printSolution() {
        if (printSteps) {
            System.out.printf("A: %s%nB: %s%n", a, b);
        }
    }


    private void readFile() throws IOException {
        reader = new BufferedReader(source);
        String line;
        String[] split;
        // First line is: [knapsack_size][number_of_items]
        line = reader.readLine();
        split = line.split("\\p{Blank}+");
        knapsackSize = Integer.parseInt(split[0]);
        numberOfItems = Integer.parseInt(split[1]);
        System.out.printf("Expecting %d elements%n", numberOfItems);
        elements = new ArrayList<>();
        int i = 0;
        // Rest of lines are: [value_x] [weight_x]
        while ((line = reader.readLine()) != null) {
            split = line.split("\\p{Blank}+");
            int value = Integer.parseInt(split[0]);
            int weight = Integer.parseInt(split[1]);
            if (weight <= knapsackSize) {
                elements.add(new Element(value, weight));
            } else {
                numberOfItems--;
            }
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
            return String.format("(%d, %d)", value, weight);
        }

        @Override
        public int compareTo(Element that) {
            if (this.value < that.value) {
                return 1;
            } else if (this.value > that.value) {
                return -1;
            }
            return 0;
        }

        public Element join(Element next) {
            return new Element(this.value + next.value, this.weight + next.weight);
        }
    }


}
