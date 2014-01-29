package martinhynar.wordnet;

import org.fest.assertions.Assertions;
import org.junit.Test;

/**
 * @author Martin Hynar
 * 
 */
public class OutcastTest {
    @Test
    public void testOutcast5() throws Exception {
        // WordNet wordnet = null;
        WordNet wordnet = new WordNet("src/test/resources/wordnet/wordnet/synsets.txt",
                "src/test/resources/wordnet/wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        String outcastWord = outcast.outcast(new String[] { "horse", "zebra", "cat", "bear", "table" });
        Assertions.assertThat(outcastWord).isEqualTo("table");
    }

    @Test
    public void testOutcast8() throws Exception {
        // WordNet wordnet = null;
        WordNet wordnet = new WordNet("src/test/resources/wordnet/wordnet/synsets.txt",
                "src/test/resources/wordnet/wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        String outcastWord = outcast.outcast(new String[] { "water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea",
                "coffee" });
        Assertions.assertThat(outcastWord).isEqualTo("bed");
    }

    @Test
    public void testOutcast11() throws Exception {
        // WordNet wordnet = null;
        WordNet wordnet = new WordNet("src/test/resources/wordnet/wordnet/synsets.txt",
                "src/test/resources/wordnet/wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        String outcastWord = outcast.outcast(new String[] { "apple", "pear", "peach", "banana", "lime", "lemon", "blueberry",
                "strawberry", "mango", "watermelon", "potato" });
        Assertions.assertThat(outcastWord).isEqualTo("potato");
    }
}
