package martinhynar.reader;

import martinhynar.graph.WUG_EuclideanDistance;
import martinhynar.graph.WUG_List;
import martinhynar.graph.WeightedDirectedGraphList;

import java.io.BufferedReader;
import java.io.Reader;

/**
 * @author mhynar
 * @since 2013-Oct-01
 */
public class CourseraFormatReader {
    public static final String BLANK = "\\p{Blank}+";
    public static final String COMMENT_PREFIX = "#";
    private BufferedReader reader;
    private Reader source;

    public CourseraFormatReader useSource(Reader source) {
        this.source = source;
        return this;
    }

    public WeightedDirectedGraphList buildWeightedDirectedGraph(boolean inAdjacency) throws Exception {
        WeightedDirectedGraphList wdg = null;
        reader = new BufferedReader(source);
        String line;
        String[] split = null;
        //number_of_vertices number_of_edges
        line = readNextLine();
        split = line.split(BLANK);
        int vertices = Integer.parseInt(split[0]);
        int edges = Integer.parseInt(split[1]);
        wdg = new WeightedDirectedGraphList(vertices, inAdjacency);
        // tail head weight
        while ((line = readNextLine()) != null) {
            split = line.split(BLANK);
            int tail = Integer.parseInt(split[0]);
            int head = Integer.parseInt(split[1]);
            int weight = Integer.parseInt(split[2]);
            wdg.addEdge(tail, head, weight);
        }
        return wdg;
    }

    public WUG_EuclideanDistance buildWUG_EuclideanDistance() throws Exception {
        WUG_EuclideanDistance wdg = null;
        reader = new BufferedReader(source);
        String line;
        String[] split = null;
        //number_of_vertices
        line = readNextLine();
        int vertices = Integer.parseInt(line);
        wdg = new WUG_EuclideanDistance(vertices);
        // xcoord ycoord
        while ((line = readNextLine()) != null) {
            split = line.split(BLANK);
            double x = Double.parseDouble(split[0]);
            double y = Double.parseDouble(split[1]);
            wdg.addVertex(x, y);
        }
        return wdg;
    }

    public WUG_List buildWUG_List() throws Exception {
        WUG_List wug = null;
        reader = new BufferedReader(source);
        String line;
        String[] split = null;
        //number_of_vertices number_of_edges
        line = readNextLine();
        split = line.split(BLANK);
        int vertices = Integer.parseInt(split[0]);
        int edges = Integer.parseInt(split[1]);
        wug = new WUG_List(vertices, edges);
        // tail head weight
        while ((line = readNextLine()) != null) {
            split = line.split(BLANK);
            int u = Integer.parseInt(split[0]);
            int v = Integer.parseInt(split[1]);
            int weight = Integer.parseInt(split[2]);
            wug.addEdge(u, v, weight);
        }
        return wug;
    }


    private String readNextLine() throws Exception {
        String line;
        do {
            line = reader.readLine();
        }
        while (line != null && line.startsWith(COMMENT_PREFIX));
        return line;
    }

}
