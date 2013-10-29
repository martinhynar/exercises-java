package martinhynar.shortestpath.singleshortestpath;

import martinhynar.graph.WeightedDirectedGraphList;
import martinhynar.reader.CourseraFormatReader;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author mhynar
 * @since 2013-Oct-01
 */
public class BellmanFordTest {
    @Test
    public void diamond() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("graphs/67.txt");
        WeightedDirectedGraphList weightedDirectedGraph = new CourseraFormatReader()
                .useSource(new InputStreamReader(in))
                .buildWeightedDirectedGraph(true);
        int[] sp = new int[weightedDirectedGraph.vertices()];
        for (int i = 1; i < weightedDirectedGraph.vertices(); i++) {

            BellmanFord bf = new BellmanFord()
                    .useGraph(weightedDirectedGraph)
                    .useStartingVertex(i)
                    .withSteps(false);
            sp[i] = bf.getShortestPathWeight();
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < sp.length; i++) {
            if (min > sp[i]) {
                min = sp[i];
            }
        }
        System.out.println(min);
    }

    @Test(expected = RuntimeException.class)
    public void negativecycle() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("graphs/negativecycle.txt");
        WeightedDirectedGraphList weightedDirectedGraph = new CourseraFormatReader()
                .useSource(new InputStreamReader(in))
                .buildWeightedDirectedGraph(true);
        BellmanFord bf = new BellmanFord()
                .useGraph(weightedDirectedGraph)
                .useStartingVertex(1)
                .withSteps(false);
        bf.getShortestPathWeight();
    }

    @Test(expected = RuntimeException.class)
    public void assignment_g1() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("graphs/g1.txt");
        WeightedDirectedGraphList weightedDirectedGraph = new CourseraFormatReader()
                .useSource(new InputStreamReader(in))
                .buildWeightedDirectedGraph(true);
        BellmanFord bf = new BellmanFord()
                .useGraph(weightedDirectedGraph)
                .useStartingVertex(2)
                .withSteps(false);
        bf.getShortestPathWeight();
    }

    @Test(expected = RuntimeException.class)
    public void assignment_g2() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("graphs/g2.txt");
        WeightedDirectedGraphList weightedDirectedGraph = new CourseraFormatReader()
                .useSource(new InputStreamReader(in))
                .buildWeightedDirectedGraph(true);
        BellmanFord bf = new BellmanFord()
                .useGraph(weightedDirectedGraph)
                .useStartingVertex(2)
                .withSteps(false);
        bf.getShortestPathWeight();
    }

//    @Test
    public void assignment_g3() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("graphs/g3.txt");
        WeightedDirectedGraphList weightedDirectedGraph = new CourseraFormatReader()
                .useSource(new InputStreamReader(in))
                .buildWeightedDirectedGraph(true);
        int[] sp = new int[weightedDirectedGraph.vertices()];
        for (int i = 1; i < weightedDirectedGraph.vertices(); i++) {

            BellmanFord bf = new BellmanFord()
                    .useGraph(weightedDirectedGraph)
                    .useStartingVertex(i)
                    .withSteps(false);
            sp[i] = bf.getShortestPathWeight();
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < sp.length; i++) {
            if (min > sp[i]) {
                min = sp[i];
            }
        }
        System.out.println(min);
    }

    @Test
    public void assignment_large() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("graphs/large.txt");
        WeightedDirectedGraphList weightedDirectedGraph = new CourseraFormatReader()
                .useSource(new InputStreamReader(in))
                .buildWeightedDirectedGraph(true);
        BellmanFord bf = new BellmanFord()
                .useGraph(weightedDirectedGraph)
                .useStartingVertex(2)
                .withSteps(false);
        bf.getShortestPathWeight();
    }

}
