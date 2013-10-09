package martinhynar.shortestpath.allpairsshortestpath;

import junit.framework.Assert;
import martinhynar.graph.ShortestPath;
import martinhynar.graph.ShortestPaths;
import martinhynar.graph.WeightedDirectedGraphList;
import martinhynar.reader.CourseraFormatReader;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

public class FloydWarshallTest {

    @Test
    public void testTuto() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("graphs/clr-pg690.txt");
        WeightedDirectedGraphList weightedDirectedGraph = new CourseraFormatReader()
                .useSource(new InputStreamReader(in))
                .buildWeightedDirectedGraph(true);

        FloydWarshall fw = new FloydWarshall()
                .useGraph(weightedDirectedGraph)
                .withSteps(!false);

        ShortestPaths shortestPaths = fw.getShortestPaths();
        Assert.assertNotNull(shortestPaths);

        ShortestPaths expected = new ShortestPaths(weightedDirectedGraph.vertices());
        expected.add(1, 1, new ShortestPath(0, new int[]{}));
        expected.add(1, 2, new ShortestPath(1, new int[]{}));
        expected.add(1, 3, new ShortestPath(-3, new int[]{}));
        expected.add(1, 4, new ShortestPath(2, new int[]{}));
        expected.add(1, 5, new ShortestPath(-4, new int[]{}));
        expected.add(2, 1, new ShortestPath(3, new int[]{}));
        expected.add(2, 2, new ShortestPath(0, new int[]{}));
        expected.add(2, 3, new ShortestPath(-4, new int[]{}));
        expected.add(2, 4, new ShortestPath(1, new int[]{}));
        expected.add(2, 5, new ShortestPath(-1, new int[]{}));
        expected.add(3, 1, new ShortestPath(7, new int[]{}));
        expected.add(3, 2, new ShortestPath(4, new int[]{}));
        expected.add(3, 3, new ShortestPath(0, new int[]{}));
        expected.add(3, 4, new ShortestPath(5, new int[]{}));
        expected.add(3, 5, new ShortestPath(3, new int[]{}));
        expected.add(4, 1, new ShortestPath(2, new int[]{}));
        expected.add(4, 2, new ShortestPath(-1, new int[]{}));
        expected.add(4, 3, new ShortestPath(-5, new int[]{}));
        expected.add(4, 4, new ShortestPath(0, new int[]{}));
        expected.add(4, 5, new ShortestPath(-2, new int[]{}));
        expected.add(5, 1, new ShortestPath(8, new int[]{}));
        expected.add(5, 2, new ShortestPath(5, new int[]{}));
        expected.add(5, 3, new ShortestPath(1, new int[]{}));
        expected.add(5, 4, new ShortestPath(6, new int[]{}));
        expected.add(5, 5, new ShortestPath(0, new int[]{}));

        for (int i = 1; i <= weightedDirectedGraph.vertices(); i++) {
            for (int j = 1; j <= weightedDirectedGraph.vertices(); j++) {
                Assert.assertEquals(expected.get(i, j).weight(), shortestPaths.get(i, j).weight());
            }
        }
    }
}
