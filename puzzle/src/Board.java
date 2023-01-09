import java.util.Arrays;
import java.util.Stack;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.size = tiles.length;
        hammingflag = false;
        manhattanflag = false;
    }

    // string representation of this board
    public String toString() {
        String string = size + "\n";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(tiles[i][j]!=0)
                    string += tiles[i][j] + "\t";
            }
            string += "\n";
        }
        return string;
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        if (hammingflag) {
            return hamming;
        }
        int distance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0)
                    continue;
                else {
                    if (i * size + j + 1 != tiles[i][j])
                        distance++;
                }
            }
        }
        hamming = distance;
        hammingflag = true;
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanflag)
            return manhattan;
        int distance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                } else {
                    distance += Math.abs(i - (tiles[i][j] - 1) / size) + Math.abs(j - (tiles[i][j] - 1) % size);
                }
            }
        }
        manhattanflag = true;
        manhattan = distance;
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }


    // does this board equal y?
    public boolean equals(Object y) {
        if (y instanceof Board) {
            return Arrays.deepEquals(((Board) y).tiles,tiles);
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int x = 0;
        int y = 0;
        for (int i=0; i < size; i++) {
            for (int j=0; j < size; j++) {
                if (tiles[i][j]==0) {
                    x=i;
                    y=j;
                    break;
                }
            }
        }
        Stack<Board> stack = new Stack<>();
        if (x > 0) {
            int[][] temp = new int[size][size];
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++)
                    temp[i][j]=tiles[i][j];
            }
            int t = temp[x - 1][y];
            temp[x - 1][y] = temp[x][y];
            temp[x][y] = t;
            stack.add(new Board(temp));
        }
        if (x < size - 1) {
            int[][] temp = new int[size][size];
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++)
                    temp[i][j]=tiles[i][j];
            }
            int t = temp[x + 1][y];
            temp[x + 1][y] = temp[x][y];
            temp[x][y] = t;
            stack.add(new Board(temp));
        }
        if (y < size - 1) {
            int[][] temp = new int[size][size];
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++)
                    temp[i][j]=tiles[i][j];
            }
            int t = temp[x][y + 1];
            temp[x][y + 1] = temp[x][y];
            temp[x][y] = t;
            stack.add(new Board(temp));
        }
        if (y > 0) {
            int[][] temp = new int[size][size];
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++)
                    temp[i][j]=tiles[i][j];
            }
            int t = temp[x][y - 1];
            temp[x][y - 1] = temp[x][y];
            temp[x][y] = t;
            stack.add(new Board(temp));
        }
        return stack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] temp = new int[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++)
                temp[i][j]=tiles[i][j];
        }
        if (temp[0][0] != 0 && temp[0][1] != 0) {
            int t = temp[0][0];
            temp[0][0] = temp[0][1];
            temp[0][1] = t;
        } else {
            int t = temp[1][0];
            temp[1][0] = temp[1][1];
            temp[1][1] = t;
        }
        return new Board(temp);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] t=new int[][]{{2,3},{1,0}};
        Board b=new Board(t);
        for(Board board:b.neighbors())
            System.out.println(board);
    }

    private int[][] tiles;
    private boolean hammingflag;
    private boolean manhattanflag;
    private int hamming;
    private int manhattan;
    private int size;
}