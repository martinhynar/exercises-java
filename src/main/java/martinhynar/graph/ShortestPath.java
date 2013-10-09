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
public class ShortestPath {
    private final double weight;
    private final int[] path;

    public ShortestPath(double weight, int[] path) {
        this.weight = weight;
        this.path = path;
    }

    public double weight() {
        return weight;
    }
}
