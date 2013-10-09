/*
 * CONFIDENTIAL COMPUTER CODE AND INFORMATION
 * COPYRIGHT (C) 2000-2010 VENDAVO, INC. ALL RIGHTS RESERVED.
 * REPRODUCTION BY ANY MEANS EXPRESSLY FORBIDDEN WITHOUT THE WRITTEN
 * PERMISSION OF THE OWNER.
 */
package martinhynar.reader;

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

    private String readNextLine() throws Exception {
        String line;
        do {
            line = reader.readLine();
        }
        while (line != null && line.startsWith(COMMENT_PREFIX));
        return line;
    }

}
