import java.io.*;
import java.util.Random;

public class BoardGame {

    public static final int ZERO = 0;
    static int[][] Board;
    private int n;  // number of rows/columns
    private int N;  // total number of squares
    private int K;  // number of black squares

    UnionFind S;

    BoardGame(int n) {
        this.K = 0;
        this.n = n;
        this.N = n*n;
        Board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Board[i][j] = 0; // all squares are initially white
            }
        }
        S = new UnionFind(N);
    }

    // make square (i,j) black and update clusters
    void colorBlack(int i, int j) {
        if (Board[i][j] == 1) {
            return;
        }
        Board[i][j] = 1;
        S.makeSet(i * n + j); // Create set of one
        K++;
        if (i > 0 && Board[i - 1][j] == 1) { // Check upper square and unite if 1
            S.unite(i * n + j, (i - 1) * n + j);
        }
        if (j > 0 && Board[i][j - 1] == 1) {  // Check left square and unite if 1
            S.unite(i * n + j, i * n + j - 1);
        }
        if (i < n - 1 && Board[i + 1][j] == 1) { // Check lower square and unite if 1
            S.unite(i * n + j, (i + 1) * n + j);
        }
        if (j < n - 1 && Board[i][j + 1] == 1) {  // Check right square and unite if 1
            S.unite(i * n + j, i * n + j + 1);
        }
    }

    // returns true if squares (i,j) and (k,l) are connected by a feasible path
    boolean test(int i, int j, int k, int l) {
        // If they have common root, they are in the same  cluster
        if (S.find(i * n + j) == S.find(k * n + l)) {
            return true;
        }
        return false;
    }

    // return the number of clusters
    int clusters() {
        return S.setCount();
    }

    // return the number of black squares
    int bCount() {
        return K;
    }

    // print game board
    void printBoard() {
        System.out.println("Game Board");
        System.out.print("    ");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + i + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print(i+ " : ");
            for (int j = 0; j < n; j++) {
                System.out.print(" " + Board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("Board Game");
        int n = Integer.parseInt("10");
        int N = n*n;

        long startTime = System.currentTimeMillis();

        BoardGame BG = new BoardGame(n);
        Random rand = new Random(0);

        BG.colorBlack(ZERO,ZERO);
        BG.colorBlack(ZERO,n-1);
        BG.colorBlack(n-1,ZERO);
        BG.colorBlack(n-1,n-1);

        //Change seed to get a different board
        rand = new Random(ZERO);

        for (int i = 0; i < (N + N)/4; i++) {
            int x = rand.nextInt(n);
            int y = rand.nextInt(n);
            BG.colorBlack(x,y);
        }

        System.out.println("Squares (0,0) and (0," + (n-1) + ") are connected: " + BG.test(0,0,0,n-1));
        System.out.println("Squares (0,0) and (" + (n-1) + ",0) are connected: " + BG.test(0,0,n-1,0));
        System.out.println("Squares (0,0) and (" + (n-1) + "," + (n-1) + ") are connected: " + BG.test(0,0,n-1,n-1));
        System.out.println("Number of black squares = " + BG.bCount());
        System.out.println("Number of clusters = " + BG.clusters());
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("total time = " + totalTime);

        if (n<=10) BG.printBoard();
    }
}
