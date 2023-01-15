import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        dicts = new brute_dict[26][26][26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 26; k++)
                    dicts[i][j][k] = new brute_dict();
            }
        }
        for (String s : dictionary) {
            if (s.length() < 3)
                continue;
            int[] index = new int[3];
            for (int i = 0; i < 3; i++)
                index[i] = s.charAt(i) - 'A';
            dicts[index[0]][index[1]][index[2]].addWord(s.substring(3));
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        words = new HashSet<>();
        visited = new boolean[board.rows()][board.cols()];
        col = board.cols();
        row = board.rows();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                search(board, i, j, "");
            }
        }
        return words;
    }

    private void search(BoggleBoard board, int row, int col, String s) {
        visited[row][col] = true;
        char c = board.getLetter(row, col);
        if (c == 'Q')
            s += "QU";
        else
            s = s + c;
        if (s.length() >= 3) {
            brute_dict dict = getDict(s);
            if (dict.isWord(s.substring(3)))
                words.add(s);
            if (dict.isPrefix(s.substring(3))) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (validIndex(row + i, col+ j) && !visited[row + i][col + j])
                            search(board, row + i, col + j, s);
                    }
                }
            }
        } else {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (validIndex(row + i, col+ j) && !visited[row + i][col + j])
                        search(board, row + i, col + j, s);
                }
            }
        }
        visited[row][col] = false;
    }

    private boolean validIndex(int x, int y) {
        if (x < 0 || y < 0 || x >= row || y >= col)
            return false;
        return true;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word.length() < 3)
            return 0;
        int[] index = new int[3];
        for (int i = 0; i < 3; i++)
            index[i] = word.charAt(i) - 'A';
        if (dicts[index[0]][index[1]][index[2]].isWord(word.substring(3))) {
            if (word.length() == 3 || word.length() == 4)
                return 1;
            if (word.length() == 5)
                return 2;
            if (word.length() == 6)
                return 3;
            if (word.length() == 7)
                return 5;
            return 11;
        }
        return 0;
    }

    private brute_dict[][][] dicts;
    private Set<String> words;
    private boolean[][] visited;
    private int col;
    private int row;

    private brute_dict getDict(String s) {
        return dicts[s.charAt(0) - 'A'][s.charAt(1) - 'A'][s.charAt(2) - 'A'];
    }

    private class brute_dict {
        Set<String> prefix;
        Set<String> word;

        public brute_dict() {
            prefix = new HashSet<>();
            word = new HashSet<>();
        }

        public boolean isPrefix(String s) {
            return prefix.contains(s);
        }

        public boolean isWord(String s) {
            return word.contains(s);
        }

        public void addWord(String s) {
            word.add(s);
            for (int i = 0; i < s.length(); i++) {
                prefix.add(s.substring(0, i));
            }
        }
    }

    public static void main(String[] args) {
        In in = new In("dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("board-q.txt");
        int score = 0;
        long start = System.currentTimeMillis();
        Iterable<String> strings = solver.getAllValidWords(board);
        long end = System.currentTimeMillis();
        for (String word : strings) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        StdOut.println("Time = " + (end - start));
    }
}
