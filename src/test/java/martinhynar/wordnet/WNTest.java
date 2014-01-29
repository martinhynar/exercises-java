package martinhynar.wordnet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Martin Hynar
 */
public class WNTest {
    WordNet wordNet = new WordNet("src/test/resources/wordnet/wordnet/synsets15.txt",
            "src/test/resources/wordnet/wordnet/hypernymsPath15.txt");

    WordNet wordNetLarge = new WordNet("src/test/resources/wordnet/wordnet/synsets.txt",
            "src/test/resources/wordnet/wordnet/hypernyms.txt");

    @Test
    public void testConstructor() {
        assertTrue("a is a noun", wordNet.isNoun("a"));
    }

    @Test
    public void testAllNouns() {
        assertEquals("f", wordNet.nouns().iterator().next());
    }

    @Test
    public void testLarge() {
        assertEquals(7, wordNetLarge.distance("horse", "cat"));
    }

    @Test
    public void testLargeIsNoun() {
        assertTrue(wordNetLarge.isNoun("'s_Gravenhage"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDistanceInvalid() {
        assertEquals(7, wordNetLarge.distance("horse", "eleventeen"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSapInvalid() {
        try {
            wordNetLarge.sap("horse", "eleventeen");
        } catch (IllegalArgumentException e) {
            throw e;
        }
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

}
