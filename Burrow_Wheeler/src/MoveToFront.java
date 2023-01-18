import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] table = new char[256];
        for (int i = 0; i < 256; i++)
            table[i] = (char) i;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();

            for (int i = 0; i < table.length; i++) {
                if (table[i] == c) {
                    System.arraycopy(table, 0, table, 1, i);
                    table[0] = c;
                    BinaryStdOut.write((char) i);
                    if (i == 13)
                        BinaryStdOut.flush();
                    break;
                }
            }
            BinaryStdOut.flush();
        }
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] table = new char[256];
        for (int i = 0; i < 256; i++)
            table[i] = (char) i;
        while (!BinaryStdIn.isEmpty()) {
            char b = BinaryStdIn.readChar();
            char c = table[b];
            System.arraycopy(table, 0, table, 1, b);
            table[0] = c;
            BinaryStdOut.write(c);
        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("+"))
            encode();
        else
            decode();
    }
}