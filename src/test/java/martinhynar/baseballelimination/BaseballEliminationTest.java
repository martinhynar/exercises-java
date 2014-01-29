package martinhynar.baseballelimination;

import org.fest.assertions.Assertions;
import org.junit.Test;
import stanford.stdlib.StdOut;

/**
 * @author Martin Hynar
 */
public class BaseballEliminationTest {
    @Test
    public void testConstruction() throws Exception {
        BaseballElimination division = new BaseballElimination("src/test/resources/baseballelimination/teams5.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

    @Test
    public void testAgainst() throws Exception {
        BaseballElimination division = new BaseballElimination("src/test/resources/baseballelimination/teams4.txt");
        int against = division.against("Atlanta", "New_York");
        Assertions.assertThat(against).isEqualTo(6);
        against = division.against("New_York", "Atlanta");
        Assertions.assertThat(against).isEqualTo(6);
        against = division.against("Atlanta", "Atlanta");
        Assertions.assertThat(against).isEqualTo(0);
    }

}
