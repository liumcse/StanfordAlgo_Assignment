import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by LiuMingyu on 21/3/17.
 */
public class Main {
    public static void main(String[] args) {

//        //  only for unit test
//        Digraph G = new Digraph(10);
//        G.addEdge(1, 4);
//        G.addEdge(2, 8);
//        G.addEdge(3, 6);
//        G.addEdge(4, 7);
//        G.addEdge(5, 2);
//        G.addEdge(6, 9);
//        G.addEdge(7, 1);
//        G.addEdge(8, 5);
//        G.addEdge(8, 6);
//        G.addEdge(9, 7);
//        G.addEdge(9, 3);
//        //  test

        //  read from file
        Digraph G = new Digraph(Integer.parseInt(args[1]));
        In reader = new In(args[0]);
        while (reader.hasNextLine()) {
            try {
                int v = reader.readInt();
                int w = reader.readInt();
                G.addEdge(v, w);
            }
            catch (Exception e) {
                break;
            }
        }

        Solver solver = new Solver(G);
    }
}

class Digraph {

    private final int V;
    private LinkedList<Integer>[] adj;

    public Digraph(int v) {
        this.V = v;
        adj = (LinkedList<Integer>[]) new LinkedList[v];
        for (int i = 1; i < v; i++)
            adj[i] = new LinkedList<>();
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public int V() { return this.V; }
    public Iterable<Integer> adj(int v) { return adj[v]; }

    public Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 1; v < R.V; v++) {
            for (int w : adj[v]) { R.addEdge(w, v); }
        }
        return R;
    }
}

class Solver {
    //  Run DFS on G(Reversed) to get "reversePostOrder of G(Reversed)"
    //  Run DFS through on G through reversePostOrder of "G(Reversed)" to compute SCC
    //  Since input is pretty large, put -Xss10m as VM options

    private Stack<Integer> order;
    private boolean[] marked;
    private int count;
    private int countArray[];

    public Solver(Digraph G) {
        order = new Stack();
        marked = new boolean[G.V()];
        Digraph R = G.reverse();

        //  Run DFS on R
        for (int v = 1; v < R.V(); v++) {
            if (!marked[v]) DFS(R, v);
        }


        Arrays.fill(marked, false);
        countArray = new int[G.V()];    //  G.V() is likely larger than what the array actually needs, a waste of space

        //  Run DFS on G by reversePostOrder
        while(!order.empty()) {     //  Java util API is a bit tricky, which is quite different the one from algs4.jar
            int v = order.pop();
            if (!marked[v]) {
                count++;
                DFS_SCC(G, v);
            }
        }

        Arrays.sort(countArray);

        for (int e : topFive(G.V())) {
            System.out.print(e + " ");
        }

    }

    private void DFS(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) DFS(G, w);
        }
        order.push(v);
    }

    private void DFS_SCC(Digraph G, int v) {
        marked[v] = true;
        countArray[count]++;    // the only difference
        for (int w : G.adj(v)) {
            if (!marked[w]) DFS_SCC(G, w);
        }
    }

    private int[] topFive(int n) {
        int[] topFive = new int[5];
        topFive[0] = countArray[n - 1];
        topFive[1] = countArray[n - 2];
        topFive[2] = countArray[n - 3];
        topFive[3] = countArray[n - 4];
        topFive[4] = countArray[n - 5];
        return topFive;
    }

}
