import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException();
        this.pic = new Picture(picture);
        width = picture.width();
        height = picture.height();
        cal_enegry();
    }

    // current picture
    public Picture picture() {
        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x >= width || x < 0 || y >= height || y < 0)
            throw new IllegalArgumentException();
        return enegry[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findVerticalSeam() {
        int[] ans = new int[height];
        if (width <= 2 || height <= 2) {
            for (int i = 0; i < height; i++)
                ans[i] = 0;
            return ans;
        }
        double[][] minimum = new double[width][height];
        for (int i = 0; i < width; i++) {
            minimum[i][0] = enegry[i][0];
        }
        for (int j = 1; j < height; j++) {
            minimum[0][j] = minimum[0][j - 1] + enegry[0][j];
            minimum[width - 1][j] = minimum[width - 1][j - 1] + enegry[width - 1][j];
        }
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                minimum[j][i] = min(minimum[j - 1][i - 1], minimum[j][i - 1], minimum[j + 1][i - 1]) + enegry[j][i];
            }
        }
        double smallest = 1e10;
        for (int i = 0; i < width; i++) {
            if (smallest > minimum[i][height - 2]) {
                smallest = minimum[i][height - 2];
                ans[height - 2] = i;
            }
        }
        for (int i = height - 3; i > 0; i--) {
            smallest = 1e10;
            for (int j = -1; j < 2; j++) {
                if (smallest > minimum[ans[i + 1] + j][i]) {
                    smallest = minimum[ans[i + 1] + j][i];
                    ans[i] = ans[i + 1] + j;
                }
            }
        }
        ans[height - 1] = ans[height - 2];
        ans[0] = ans[1];
        return ans;
    }

    // sequence of indices for vertical seam
    public int[] findHorizontalSeam() {
        int[] ans = new int[width];
        if (width <= 2 || height <= 2) {
            for (int i = 0; i < width; i++)
                ans[i] = 0;
            return ans;
        }
        double[][] minimum = new double[width][height];
        for (int i = 0; i < height; i++) {
            minimum[0][i] = enegry[0][i];
        }
        for (int j = 1; j < width; j++) {
            minimum[j][0] = minimum[j - 1][0] + enegry[j][0];
            minimum[j][height - 1] = minimum[j - 1][height - 1] + enegry[j][height - 1];
        }
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                minimum[i][j] = min(minimum[i - 1][j - 1], minimum[i - 1][j], minimum[i - 1][j + 1]) + enegry[i][j];
            }
        }
        double smallest = 1e10;
        for (int i = 0; i < height; i++) {
            if (smallest > minimum[width - 2][i]) {
                smallest = minimum[width - 2][i];
                ans[width - 2] = i;
            }
        }
        for (int i = width - 3; i > 0; i--) {
            smallest = 1e10;
            for (int j = -1; j < 2; j++) {
                if (smallest > minimum[i][ans[i + 1] + j]) {
                    smallest = minimum[i][ans[i + 1] + j];
                    ans[i] = ans[i + 1] + j;
                }
            }
        }
        ans[width - 1] = ans[width - 2];
        ans[0] = ans[1];
        return ans;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || height <= 1 || seam.length != width)
            throw new IllegalArgumentException();
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] >= height || seam[i] < 0 || Math.abs(seam[i] - seam[i + 1]) > 1)
                throw new IllegalArgumentException();
        }
        height--;
        Picture picture = new Picture(width, height);
        for (int col = 0; col < picture.width(); col++) {
            for (int row = 0; row < picture.height(); row++) {
                if (row < seam[col])
                    picture.set(col, row, pic.get(col, row));
                else {
                    picture.set(col, row, pic.get(col, row + 1));
                }
            }
        }
        pic = picture;
        cal_enegry();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || width <= 1 || seam.length != height)
            throw new IllegalArgumentException();
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] >= width || seam[i] < 0 || Math.abs(seam[i] - seam[i + 1]) > 1)
                throw new IllegalArgumentException();
        }
        width--;
        Picture picture = new Picture(width, height);
        for (int col = 0; col < picture.width(); col++) {
            for (int row = 0; row < picture.height(); row++) {
                if (col < seam[row])
                    picture.set(col, row, pic.get(col, row));
                else {
                    picture.set(col, row, pic.get(col + 1, row));
                }
            }
        }
        pic = picture;
        cal_enegry();
    }

    private void cal_enegry() {
        enegry = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    enegry[i][j] = Math.pow(1000, 2);
                } else {
                    enegry[i][j] = Math.pow(pic.get(i - 1, j).getRed() - pic.get(i + 1, j).getRed(), 2);
                    enegry[i][j] += Math.pow(pic.get(i - 1, j).getBlue() - pic.get(i + 1, j).getBlue(), 2);
                    enegry[i][j] += Math.pow(pic.get(i - 1, j).getGreen() - pic.get(i + 1, j).getGreen(), 2);
                    enegry[i][j] += Math.pow(pic.get(i, j - 1).getRed() - pic.get(i, j + 1).getRed(), 2);
                    enegry[i][j] += Math.pow(pic.get(i, j - 1).getBlue() - pic.get(i, j + 1).getBlue(), 2);
                    enegry[i][j] += Math.pow(pic.get(i, j - 1).getGreen() - pic.get(i, j + 1).getGreen(), 2);
                }
                enegry[i][j]=Math.sqrt(enegry[i][j]);
            }
        }
    }

    private Picture pic;
    private double[][] enegry;
    private int width;
    private int height;

    private double min(double... doubles) {
        double min = 1e10;
        for (double d : doubles)
            min = Math.min(d, min);
        return min;
    }

    //  unit testing (optional)
    public static void main(String[] args) {
    }

}