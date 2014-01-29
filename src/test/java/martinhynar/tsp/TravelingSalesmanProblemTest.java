package martinhynar.tsp;

import martinhynar.reader.CourseraFormatReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author mhynar
 * @since 2013-Oct-09
 */
public class TravelingSalesmanProblemTest {
    //    @Test
    public void assignment() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("tsp/tsp.txt");

        CourseraFormatReader reader = new CourseraFormatReader()
                .useSource(new InputStreamReader(in));
        TravelingSalesmanProblemDP tsp = new TravelingSalesmanProblemDP()
                .useGraph(reader.buildWUG_EuclideanDistance());

        double tour = tsp.getMinTour();
        System.out.printf("Tour: %f%n", tour);

    }

    //    @Test
    public void tsp18() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("tsp/tsp-18.txt");

        CourseraFormatReader reader = new CourseraFormatReader()
                .useSource(new InputStreamReader(in));
        TravelingSalesmanProblemDP tsp = new TravelingSalesmanProblemDP()
                .useGraph(reader.buildWUG_EuclideanDistance());

        double tour = tsp.getMinTour();
        assertThat(tour, equalTo(84d));
    }

    //    @Test
    public void tsp8() throws Exception {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("tsp/tsp-8.txt");

        CourseraFormatReader reader = new CourseraFormatReader()
                .useSource(new InputStreamReader(in));
        TravelingSalesmanProblemDP tsp = new TravelingSalesmanProblemDP()
                .useGraph(reader.buildWUG_EuclideanDistance());

        double tour = tsp.getMinTour();
        assertThat(tour, equalTo(37.83));
    }

    //    @Test
    public void tsp5() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("tsp/tsp-8.txt");

        CourseraFormatReader reader = new CourseraFormatReader()
                .useSource(new InputStreamReader(in));
        TravelingSalesmanProblemDP tsp = new TravelingSalesmanProblemDP()
                .useGraph(reader.buildWUG_EuclideanDistance());

        double tour = tsp.getMinTour();
        assertThat(tour, equalTo(4d));
    }

    //    @Test
    public void complete_4() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("graphs/weighted/complete-4.txt");

        CourseraFormatReader reader = new CourseraFormatReader()
                .useSource(new InputStreamReader(in));

        TravelingSalesmanProblemDP tsp = new TravelingSalesmanProblemDP()
                .withSteps(!true)
                .useGraph(reader.buildWUG_List());
        double tour = tsp.getMinTour();
        assertThat(tour, equalTo(4d));
    }

    //    @Test
    public void complete_5() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("graphs/weighted/complete-5.txt");

        CourseraFormatReader reader = new CourseraFormatReader()
                .useSource(new InputStreamReader(in));

        TravelingSalesmanProblemDP tsp = new TravelingSalesmanProblemDP()
                .withSteps(!true)
                .useGraph(reader.buildWUG_List());
        double tour = tsp.getMinTour();
        assertThat(tour, equalTo(5d));
    }
}
