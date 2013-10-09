/*
 * CONFIDENTIAL COMPUTER CODE AND INFORMATION
 * COPYRIGHT (C) 2000-2010 VENDAVO, INC. ALL RIGHTS RESERVED.
 * REPRODUCTION BY ANY MEANS EXPRESSLY FORBIDDEN WITHOUT THE WRITTEN
 * PERMISSION OF THE OWNER.
 */
package martinhynar.graph;

/**
 * @author mhynar
 * @since 2013-Oct-06
 */
public class WeightedDirectedGraphMatrix {
    private int[][] adjacencyMatrix;

    public WeightedDirectedGraphMatrix(int vertices) {
        adjacencyMatrix = new int[vertices][vertices];
    }
}
