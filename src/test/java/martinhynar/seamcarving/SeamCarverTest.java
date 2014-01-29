package martinhynar.seamcarving;

import org.fest.assertions.Assertions;
import org.junit.Test;
import stanford.stdlib.Picture;

/**
 * @author Martin Hynar
 */
public class SeamCarverTest {
    private Picture picture;
    private SeamCarver seamCarver;
    double[][] energyIn65 = new double[][]{{195075.0, 195075.0, 195075.0, 195075.0, 195075.0, 195075.0},
            {195075.0, 23346.0, 51304.0, 31519.0, 55112.0, 195075.0},
            {195075.0, 47908.0, 61346.0, 35919.0, 38887.0, 195075.0},
            {195075.0, 31400.0, 37927.0, 14437.0, 63076.0, 195075.0},
            {195075.0, 195075.0, 195075.0, 195075.0, 195075.0, 195075.0},

    };

    @Test
    public void everythingOn6_5Picture() throws Exception {
        picture = new Picture("src/test/resources/seamcarving/6x5.png");
        int expectedHeight = picture.height();
        int expectedWidth = picture.width();
        seamCarver = new SeamCarver(picture);
        int height = seamCarver.height();
        int width = seamCarver.width();
        Assertions.assertThat(height).isEqualTo(expectedHeight);
        Assertions.assertThat(width).isEqualTo(expectedWidth);
        for (int y = 0; y < expectedWidth; y++) {
            for (int x = 0; x < expectedHeight; x++) {
                double energy = seamCarver.energy(y, x);
                // Array is transposed
                double expectedEnergy = energyIn65[x][y];
                Assertions.assertThat(energy).isEqualTo(expectedEnergy);
            }
        }
        seamCarver.findVerticalSeam();
    }

    @Test
    public void borderPixelsHasConstantEnergy() throws Exception {
        picture = new Picture("src/test/resources/seamcarving/6x5.png");
        int expectedHeight = picture.height();
        int expectedWidth = picture.width();
        seamCarver = new SeamCarver(picture);
        for (int x = 0; x < expectedWidth; x++) {
            double energyB = seamCarver.energy(x, 0);
            double energyT = seamCarver.energy(x, expectedHeight - 1);
            Assertions.assertThat(energyB).isEqualTo(195075);
            Assertions.assertThat(energyT).isEqualTo(195075);
        }
        for (int y = 0; y < expectedHeight; y++) {
            double energyB = seamCarver.energy(0, y);
            double energyT = seamCarver.energy(expectedWidth - 1, y);
            Assertions.assertThat(energyB).isEqualTo(195075);
            Assertions.assertThat(energyT).isEqualTo(195075);
        }
    }

    @Test
    public void unchangedPicturePreservesSize() throws Exception {
        picture = new Picture("src/test/resources/seamcarving/6x5.png");
        int expectedHeight = picture.height();
        int expectedWidth = picture.width();
        seamCarver = new SeamCarver(picture);
        Picture tPicture = seamCarver.picture();
        Assertions.assertThat(tPicture.height()).isEqualTo(expectedHeight);
        Assertions.assertThat(tPicture.width()).isEqualTo(expectedWidth);
    }

    @Test
    public void testRemoveVerticalSeam73() throws Exception {
        picture = new Picture("src/test/resources/seamcarving/7x3.png");
        int expectedHeight = picture.height();
        int expectedWidth = picture.width();
        seamCarver = new SeamCarver(picture);
        int[] verticalSeam = seamCarver.findVerticalSeam();
        Assertions.assertThat(verticalSeam).isNotNull();
        Assertions.assertThat(verticalSeam.length).isEqualTo(expectedHeight);
        seamCarver.removeVerticalSeam(verticalSeam);
        Picture tPicture = seamCarver.picture();
        Assertions.assertThat(tPicture.height()).isEqualTo(expectedHeight);
        Assertions.assertThat(tPicture.width()).isEqualTo(expectedWidth - 1);

    }

    @Test
    public void testRemoveVerticalSeam46() throws Exception {
        picture = new Picture("src/test/resources/seamcarving/4x6.png");
        int expectedHeight = picture.height();
        int expectedWidth = picture.width();
        seamCarver = new SeamCarver(picture);
        int[] verticalSeam = seamCarver.findVerticalSeam();
        Assertions.assertThat(verticalSeam).isNotNull();
        Assertions.assertThat(verticalSeam.length).isEqualTo(expectedHeight);
        seamCarver.removeVerticalSeam(verticalSeam);
        Picture tPicture = seamCarver.picture();
        Assertions.assertThat(tPicture.height()).isEqualTo(expectedHeight);
        Assertions.assertThat(tPicture.width()).isEqualTo(expectedWidth - 1);

    }

    @Test
    public void testRemoveHorizontalSeam() throws Exception {
        picture = new Picture("src/test/resources/seamcarving/6x5.png");
        int expectedHeight = picture.height();
        int expectedWidth = picture.width();
        seamCarver = new SeamCarver(picture);
        int[] horizontalSeam = seamCarver.findHorizontalSeam();
        Assertions.assertThat(horizontalSeam).isNotNull();
        Assertions.assertThat(horizontalSeam.length).isEqualTo(expectedWidth);
        seamCarver.removeHorizontalSeam(horizontalSeam);
        Picture tPicture = seamCarver.picture();
        Assertions.assertThat(tPicture.height()).isEqualTo(expectedHeight - 1);
        Assertions.assertThat(tPicture.width()).isEqualTo(expectedWidth);

    }

    @Test
    public void testRemoveBorderHorizontalSeam() throws Exception {
        picture = new Picture("src/test/resources/seamcarving/6x5.png");
        int expectedHeight = picture.height();
        int expectedWidth = picture.width();
        seamCarver = new SeamCarver(picture);
        int[] horizontalSeam = new int[expectedWidth];
        for (int i = 0; i < horizontalSeam.length; i++) {
            horizontalSeam[i] = 0;
        }
        seamCarver.removeHorizontalSeam(horizontalSeam);
        Picture tPicture = seamCarver.picture();
        Assertions.assertThat(tPicture.height()).isEqualTo(expectedHeight - 1);
        Assertions.assertThat(tPicture.width()).isEqualTo(expectedWidth);

    }
}
