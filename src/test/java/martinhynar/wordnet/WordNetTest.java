package martinhynar.wordnet;

import org.fest.assertions.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Martin Hynar
 */
public class WordNetTest {
    @Test
    public void testWordNet6_BF() throws Exception {
        WordNet wn = new WordNet("src/test/resources/wordnet/wordnet/synsets6.txt",
                "src/test/resources/wordnet/wordnet/hypernyms6TwoAncestors.txt");
        String nounA = "b";
        String nounB = "f";
        List<String> nouns = Arrays.asList(new String[]{"a", "b", "c", "d", "e", "f"});
        for (String noun : nouns) {
            Assertions.assertThat(wn.isNoun(noun)).isTrue();
        }
        Assertions.assertThat(wn.isNoun("g")).isFalse();
        Iterable<String> wordNetNouns = wn.nouns();
        int size = nouns.size();
        List<String> nounsCopy = new ArrayList<String>(nouns);
        for (String noun : wordNetNouns) {
            nounsCopy.remove(noun);
            size--;
        }
        Assertions.assertThat(size).isEqualTo(0);
        Assertions.assertThat(nounsCopy.size()).isEqualTo(0);

        int distance = wn.distance(nounA, nounB);
        String sap = wn.sap(nounA, nounB);
        Assertions.assertThat(sap).isEqualTo("a");
        Assertions.assertThat(distance).isEqualTo(2);
    }

    @Test
    public void testWordNet6_Multinoun() throws Exception {
        WordNet wn = new WordNet("src/test/resources/wordnet/wordnet/synsets6_Multinoun.txt",
                "src/test/resources/wordnet/wordnet/hypernyms6TwoAncestors.txt");
        String nounA = "cc";
        String nounB = "cc";

        int distance = wn.distance(nounA, nounB);
        String sap = wn.sap(nounA, nounB);
        Assertions.assertThat(sap).isEqualTo("cccc cc");
        Assertions.assertThat(distance).isEqualTo(0);
    }

    @Test
    public void testWordNet6_DF() throws Exception {
        WordNet wn = new WordNet("src/test/resources/wordnet/wordnet/synsets6.txt",
                "src/test/resources/wordnet/wordnet/hypernyms6TwoAncestors.txt");
        String nounA = "f";
        String nounB = "d";

        int distance = wn.distance(nounA, nounB);
        String sap = wn.sap(nounA, nounB);
        Assertions.assertThat(sap).isEqualTo("f");
        Assertions.assertThat(distance).isEqualTo(2);
    }

    @Test
    public void testWordNetComplete_LongDistance() throws Exception {
        WordNet wn = new WordNet("src/test/resources/wordnet/wordnet/synsets.txt", "src/test/resources/wordnet/wordnet/hypernyms.txt");
        String nounA = "Black_Plague";
        String nounB = "black_marlin";
        int distance = wn.distance(nounA, nounB);
        Assertions.assertThat(distance).isEqualTo(33);

        nounA = "American_water_spaniel";
        nounB = "histology";
        distance = wn.distance(nounA, nounB);
        Assertions.assertThat(distance).isEqualTo(27);

        nounA = "Brown_Swiss";
        nounB = "barrel_roll";
        distance = wn.distance(nounA, nounB);
        Assertions.assertThat(distance).isEqualTo(29);
    }

    @Test
    public void testWordNetComplete_NounOrder() throws Exception {
        WordNet wn = new WordNet("src/test/resources/wordnet/wordnet/synsets.txt", "src/test/resources/wordnet/wordnet/hypernyms.txt");
        String nounA = "beefsteak_geranium";
        String nounB = "coil";
        int distance = wn.distance(nounA, nounB);
        String nouns = wn.sap(nounA, nounB);
        Assertions.assertThat(distance).isEqualTo(12);
        Assertions.assertThat(nouns).isEqualTo("whole unit");

    }

    @Test
    public void testWordNetComplete_Hypernym100K() throws Exception {
        WordNet wn = new WordNet("src/test/resources/wordnet/wordnet/synsets.txt", "src/test/resources/wordnet/wordnet/hypernyms100K.txt");
        String nounA = "brachium";
        String nounB = "silver_sagebrush";
        int distance = wn.distance(nounA, nounB);
        String nouns = wn.sap(nounA, nounB);
        System.out.println(nouns);
        Assertions.assertThat(distance).isEqualTo(17);
    }

    @Test
    public void testWordNetComplete_Hypernym200K() throws Exception {
        WordNet wn = new WordNet("src/test/resources/wordnet/wordnet/synsets.txt", "src/test/resources/wordnet/wordnet/hypernyms200K.txt");
        String nounA = "brachium";
        String nounB = "silver_sagebrush";
        int distance = wn.distance(nounA, nounB);
        String nouns = wn.sap(nounA, nounB);
        System.out.println(nouns);
        Assertions.assertThat(distance).isEqualTo(14);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCyclesInvalid() {
        WordNet wordNetInvalidCycle = new WordNet("src/test/resources/wordnet/wordnet/synsets3.txt",
                "src/test/resources/wordnet/wordnet/hypernymsInvalidCycle.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTwoRootsInvalid() {
        WordNet wordNetTwoRoots = new WordNet("src/test/resources/wordnet/wordnet/synsets3.txt",
                "src/test/resources/wordnet/wordnet/hypernymsInvalidTwoRoots.txt");
    }

    @Test
    public void testWordNetComplete_Hypernym300K() throws Exception {
        WordNet wn = new WordNet("src/test/resources/wordnet/wordnet/synsets.txt", "src/test/resources/wordnet/wordnet/hypernyms300K.txt");
        String nounA = "brachium";
        String nounB = "silver_sagebrush";
        int distance = wn.distance(nounA, nounB);
        String nouns = wn.sap(nounA, nounB);
        System.out.println(nouns);
        Assertions.assertThat(distance).isEqualTo(9);

    }
}
