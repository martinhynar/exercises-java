package martinhynar.seamcarving.client;

import martinhynar.seamcarving.SeamCarver;
import stanford.stdlib.Picture;

import java.util.Arrays;

/**
 * **********************************************************************
 * Compilation: javac PrintSeams.java Execution: java PrintSeams input.png
 * Dependencies: SeamCarver.java SCUtility.java Picture.java StdDraw.java
 * <p/>
 * <p/>
 * Read image from file specified as command line argument. Print energy of each
 * image, as well as both seams, and the total energy of each seam.
 * <p/>
 * ***********************************************************************
 */

public class PrintSeams {

    private static void printHorizontalSeam(SeamCarver sc) {
        double totalSeamEnergy = 0;

        int[] horizontalSeam = sc.findHorizontalSeam();
        System.out.printf("Horizontal seam: %s%n", Arrays.toString(horizontalSeam));
        for (int j = 0; j < sc.height(); j++) {
            for (int i = 0; i < sc.width(); i++) {
                char lMarker = ' ';
                char rMarker = ' ';
                if (j == horizontalSeam[i]) {
                    lMarker = '[';
                    rMarker = ']';
                    totalSeamEnergy += sc.energy(i, j);
                }

                System.out.printf("%c%6.0f%c ", lMarker, sc.energy(i, j), rMarker);
            }
            System.out.println();
        }

        System.out.printf("\nTotal energy: %.0f\n\n", totalSeamEnergy);
    }

    private static void printVerticalSeam(SeamCarver sc) {
        double totalSeamEnergy = 0;

        int[] verticalSeam = sc.findVerticalSeam();
        System.out.printf("Vertical seam: %s%n", Arrays.toString(verticalSeam));
        for (int j = 0; j < sc.height(); j++) {
            for (int i = 0; i < sc.width(); i++) {
                char lMarker = ' ';
                char rMarker = ' ';
                if (i == verticalSeam[j]) {
                    lMarker = '[';
                    rMarker = ']';
                    totalSeamEnergy += sc.energy(i, j);
                }

                System.out.printf("%c%6.0f%c ", lMarker, sc.energy(i, j), rMarker);
            }

            System.out.println();
        }

        System.out.printf("\nTotal energy: %.0f\n\n", totalSeamEnergy);
    }

    public static void main(String[] args) {
        // Picture inputImg = new Picture(args[0]);
        Picture inputImg = new Picture("src/test/resources/pa2/6x5.png");
        System.out.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
        // inputImg.show();
        SeamCarver sc = new SeamCarver(inputImg);

        System.out.printf("Displaying horizontal seam calculated.\n");
        printHorizontalSeam(sc);

        System.out.printf("Displaying vertical seam calculated.\n");
        printVerticalSeam(sc);

    }

}
