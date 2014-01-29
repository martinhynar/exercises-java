package martinhynar.seamcarving;

import java.awt.Color;

import stanford.stdlib.Picture;

/**
 * @author Martin Hynar
 * 
 */
public class SeamCarver {
    // sq(255) + sq(255) + sq(255)
    private static final int BORDER_PIXEL_ENERGY = 195075;

    private int[][] energyMatrix;
    private int[][] colorMatrix;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        this.height = picture.height();
        this.width = picture.width();
        energyMatrix = new int[height][width];
        colorMatrix = new int[height][width];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                energyMatrix[h][w] = intGradientEnergy(w, h, picture);
                colorMatrix[h][w] = picture.get(w, h).getRGB();
            }
        }
    }

    /**
     * current picture
     */
    public Picture picture() {
        Picture picture = new Picture(width, height);
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                picture.set(w, h, new Color(colorMatrix[h][w]));
            }
        }
        return picture;
    }

    /**
     * width of current picture
     */
    public int width() {
        return width;
    }

    /**
     * height of current picture
     */
    public int height() {
        return height;
    }

    /**
     * energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        return energyMatrix[y][x];
    }

    private int intGradientEnergy(int w, int h, Picture originalPicture) {
        if (w < 0 || h < 0 || w > width || h > height) {
            throw new IndexOutOfBoundsException("Incorrect position");
        }
        if (w == 0 || w == width - 1 || h == 0 || h == height - 1) {
            return BORDER_PIXEL_ENERGY;
        }
        Color colorXp1Y = originalPicture.get(w + 1, h);
        Color colorXm1Y = originalPicture.get(w - 1, h);
        Color colorXYp1 = originalPicture.get(w, h + 1);
        Color colorXYm1 = originalPicture.get(w, h - 1);
        return gr(colorXp1Y, colorXm1Y, colorXYp1, colorXYm1);
    }

    private int gr(Color colorXp1Y, Color colorXm1Y, Color colorXYp1, Color colorXYm1) {
        int gradientX = gradient(colorXp1Y, colorXm1Y);
        int gradientY = gradient(colorXYp1, colorXYm1);
        return gradientX + gradientY;
    }

    private int gradient(Color color1, Color color2) {
        int gradient = 0;
        int red = Math.abs(color1.getRed() - color2.getRed());
        int green = Math.abs(color1.getGreen() - color2.getGreen());
        int blue = Math.abs(color1.getBlue() - color2.getBlue());
        gradient = (red * red) + (green * green) + (blue * blue);
        return gradient;
    }

    /**
     * sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        int[][] path = new int[height][width];
        int[][] energy = new int[height][width];
        for (int h = 0; h < width; h++) {
            for (int w = 0; w < height; w++) {
                if (h == 0) {
                    path[w][h] = h;
                    energy[w][h] = BORDER_PIXEL_ENERGY;
                } else if (h == 1) {
                    path[w][h] = (w - 1 >= 0) ? w - 1 : w;
                    energy[w][h] = energyMatrix[w][h] + BORDER_PIXEL_ENERGY;
                } else {
                    // Initialize with global maximums
                    energy[w][h] = Integer.MAX_VALUE;
                    path[w][h] = -1;
                    // Try to relax and find better
                    int[] predecessors = predecessors(w, height);
                    int pEnergy = energyMatrix[w][h];
                    for (int p = 0; p < predecessors.length; p++) {
                        int preEnergy = energy[predecessors[p]][h - 1];
                        int relaxedEnergy = preEnergy + pEnergy;
                        if (energy[w][h] > relaxedEnergy) {
                            energy[w][h] = relaxedEnergy;
                            path[w][h] = predecessors[p];
                        }
                    }
                }
            }
        }

        // Find first smallest summed weight in latest row
        int lowestEnergy = Integer.MAX_VALUE;
        int lowestEnergyIndex = -1;
        for (int i = 0; i < height; i++) {
            int energ = energy[i][width - 1];
            if (lowestEnergy > energ) {
                lowestEnergy = energ;
                lowestEnergyIndex = i;
            }
        }

        // Fill iterable with indices
        int[] seam = new int[width];
        for (int i = width - 1; i >= 0; i--) {
            seam[i] = lowestEnergyIndex;
            lowestEnergyIndex = path[lowestEnergyIndex][i];
        }
        return seam;
    }

    /**
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        int[][] path = new int[height][width];
        int[][] energy = new int[height][width];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (h == 0) {
                    path[h][w] = w;
                    energy[h][w] = BORDER_PIXEL_ENERGY;
                } else if (h == 1) {
                    path[h][w] = (w - 1 >= 0) ? w - 1 : w;
                    energy[h][w] = energyMatrix[h][w] + BORDER_PIXEL_ENERGY;
                } else {
                    // Initialize with global maximums
                    energy[h][w] = Integer.MAX_VALUE;
                    path[h][w] = -1;
                    // Try to relax and find better
                    int[] predecessors = predecessors(w, width);
                    int pEnergy = energyMatrix[h][w];
                    for (int p = 0; p < predecessors.length; p++) {
                        int preEnergy = energy[h - 1][predecessors[p]];
                        int relaxedEnergy = preEnergy + pEnergy;
                        if (energy[h][w] > relaxedEnergy) {
                            energy[h][w] = relaxedEnergy;
                            path[h][w] = predecessors[p];
                        }
                    }
                }
            }
        }

        // Find first smallest summed weight in latest row
        int lowestEnergy = Integer.MAX_VALUE;
        int lowestEnergyIndex = -1;
        for (int i = 0; i < width; i++) {
            int energ = energy[height - 1][i];
            if (lowestEnergy > energ) {
                lowestEnergy = energ;
                lowestEnergyIndex = i;
            }
        }

        // Fill iterable with indices
        int[] seam = new int[height];
        for (int i = height - 1; i >= 0; i--) {
            seam[i] = lowestEnergyIndex;
            lowestEnergyIndex = path[i][lowestEnergyIndex];
        }
        return seam;
    }

    private int[] predecessors(int x, int max) {
        int[] pre;
        if (x == 0) {
            pre = new int[1];
            pre[0] = x + 1;
        } else if (x == max - 1) {
            pre = new int[1];
            pre[0] = max - 1 - 1;
        } else {
            pre = new int[3];
            pre[0] = x - 1;
            pre[1] = x;
            pre[2] = x + 1;

        }
        return pre;
    }

    /**
     * remove horizontal seam from picture
     */
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width) {
            throw new IllegalArgumentException("Incorrect seam size");
        }
        for (int s = 0; s < seam.length - 1; s++) {
            if (Math.abs(seam[s] - seam[s + 1]) > 1) {
                throw new IllegalArgumentException("Incorrect size");
            }
        }

        for (int h = 0; h < width; h++) {
            for (int w = seam[h]; w < height - 1; w++) {
                colorMatrix[w][h] = colorMatrix[w + 1][h];
            }
            colorMatrix[height - 1][h] = 0;
            energyMatrix[height - 1][h] = 0;
        }
        this.height--;
        for (int h = 0; h < width; h++) {
            for (int w = 0; w < height; w++) {
                if (h == 0 || h == width - 1) {
                    energyMatrix[w][h] = BORDER_PIXEL_ENERGY;
                } else if (w == 0 || w == height - 1) {
                    energyMatrix[w][h] = BORDER_PIXEL_ENERGY;
                } else {
                    energyMatrix[w][h] = gr(getColor(w - 1, h), getColor(w + 1, h), getColor(w, h - 1), getColor(w, h + 1));
                }
            }
        }
    }

    /**
     * remove vertical seam from picture
     */
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height) {
            throw new IllegalArgumentException("Incorrect seam size");
        }
        for (int s = 0; s < seam.length - 1; s++) {
            if (Math.abs(seam[s] - seam[s + 1]) > 1) {
                throw new IllegalArgumentException("Incorrect size");
            }
        }
        for (int h = 0; h < height; h++) {
            for (int w = seam[h]; w < width - 1; w++) {
                colorMatrix[h][w] = colorMatrix[h][w + 1];
            }
            colorMatrix[h][width - 1] = 0;
            energyMatrix[h][width - 1] = 0;
        }
        this.width--;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (h == 0 || h == height - 1) {
                    energyMatrix[h][w] = BORDER_PIXEL_ENERGY;
                } else if (w == 0 || w == width - 1) {
                    energyMatrix[h][w] = BORDER_PIXEL_ENERGY;
                } else {
                    energyMatrix[h][w] = gr(getColor(h, w - 1), getColor(h, w + 1), getColor(h - 1, w), getColor(h + 1, w));
                }
            }
        }
    }

    // private void printArray(int[][] ia) {
    // System.out.println("-------------------------------------");
    // for (int i = 0; i < ia.length; i++) {
    // System.out.println(Arrays.toString(ia[i]));
    // }
    // }

    private Color getColor(int x, int y) {
        return new Color(colorMatrix[x][y]);
    }
}
