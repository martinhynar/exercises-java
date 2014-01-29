package martinhynar.wordnet;

import stanford.stdlib.In;
import stanford.stdlib.StdOut;

/**
 * @author Martin Hynar
 * 
 */
public class Outcast {
    private WordNet wordnet;
    private int[] distances;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        distances = new int[nouns.length];
        int max = nouns.length - 1;
        String nounA;
        String nounB;
        int dist;
        for (int i = 0; i < max; i++) {
            for (int j = i + 1; j < max + 1; j++) {
                nounA = nouns[i];
                nounB = nouns[j];
                dist = wordnet.distance(nounA, nounB);
                distances[i] += dist;
                distances[j] += dist;
            }
        }
        int outcastIndex = 0;
        int maxDist = Integer.MIN_VALUE;
        for (int i = 0; i < distances.length; i++) {
            if (maxDist < distances[i]) {
                maxDist = distances[i];
                outcastIndex = i;
            }
        }
        return nouns[outcastIndex];
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            String[] nouns = In.readStrings(args[t]);
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
