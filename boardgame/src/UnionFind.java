import java.io.*;

public class UnionFind {

    private int[] parent;
    private int[] size;
    private int N;      // maximum number of items from {0,1,...,N-1}
    private int K;      // number of items created

    UnionFind(int N) {
        this.N = N;
        this.K = 0;
        parent = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
            size[i] = 0;
        }
    }

    void makeSet(int v) {
        if (parent[v] != -1) {
            return; // item v already belongs in a set
        }
        parent[v] = v;
        size[v] = 1;
        K++;
    }

    int find(int v) {
        // Recursively search the parent until finding the root
        int p; // Eventually the root
        if (parent[v] != v) {
            p = find(parent[v]);
            parent[v] = p; // Before returning the root, make it my parent. "Compression"
            return p;
        }
        return v;
    }

    void unite(int v, int u) {
        // Algorithm slide 22. "Weighted Fast Union"
        int k = find(v);
        int l = find(u);
        if (k != l) {
            if (size[k] >= size[l]) {
                parent[l] = k;
                size[k] += size[l];
                size[l] = 0;
            } else {
                parent[k] = l;
                size[l] += size[k];
                size[k] = 0;
            }
        }
    }

    int setCount() {
        // Just counts the number of roots
        int i, count = 0;
        for (i = 0; i < N; i++) {
            if (parent[i] == i) {
                count++;
            }
        }
        return count;

    }

    // public void setK(int numOfClusters) {
    //     this.K = numOfClusters;
    // }

    int itemCount() {
        return K;
    }


    void print() {
        System.out.println("Set Union Data Structure");
        for (int k=0; k<N; k++) {
            System.out.println("parent["+k+"]="+parent[k]);
            if (parent[k]==k) System.out.println("size["+k+"]="+size[k]);
        }
    }

    public static void main(String[] args) {
        System.out.println("Test Union-Find");

        int N = 16;

        long startTime = System.currentTimeMillis();
        UnionFind S = new UnionFind(N);
        System.out.println("Maximum number of items = " + N);

        for (int i=0; i<N; i+=2) {
            S.makeSet(i);
        }

        S.unite(0,2);
        S.unite(4,6);
        S.unite(8,10);
        S.unite(12,14);

        S.unite(2,6);
        S.unite(10,14);

        S.unite(6,14);

        S.print();

        System.out.println("Number of items created = " + S.itemCount());
        System.out.println("Number of sets = " + S.setCount());
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("total time = " + totalTime);
    }
}
