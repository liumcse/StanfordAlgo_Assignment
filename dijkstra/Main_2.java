package Assignment2;

import edu.princeton.cs.algs4.In;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by LiuMingyu on 29/3/17.
 */
public class Main_2 {

    public static void main(String[] args) {
//        //  unit test
//        Graph G = new Graph(9);
//        G.addEdge(new Edge(1, 2, 1));
//        G.addEdge(new Edge(1, 8, 2));
//        G.addEdge(new Edge(2, 1, 1));
//        G.addEdge(new Edge(2, 3, 1));
//        G.addEdge(new Edge(3, 2, 1));
//        G.addEdge(new Edge(3, 4, 1));
//        G.addEdge(new Edge(4, 3, 1));
//        G.addEdge(new Edge(4, 5, 1));
//        G.addEdge(new Edge(5, 4, 1));
//        G.addEdge(new Edge(5, 6, 1));
//        G.addEdge(new Edge(6, 5, 1));
//        G.addEdge(new Edge(6, 7, 1));
//        G.addEdge(new Edge(7, 6, 1));
//        G.addEdge(new Edge(7, 8, 1));
//        G.addEdge(new Edge(8, 7, 1));
//        G.addEdge(new Edge(8, 1, 2));

        Graph G = new Graph(Integer.parseInt(args[1]));
        In reader = new In(args[0]);
        while (reader.hasNextLine()) {
            String _Line = reader.readLine();
            String Line[] = _Line.split("\t");
            int from = Integer.parseInt(Line[0]);
            for (int i = 1; i < Line.length; i++) {
                String Edge[] = Line[i].split(",");
                int to = Integer.parseInt(Edge[0]);
                int weight = Integer.parseInt(Edge[1]);
                G.addEdge(new Edge(from, to, weight));
            }
        }

        Solver test = new Solver(G);
        Stack<Edge> stack = test.pathTo(10);
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();
        for (int i = 1; i < Integer.parseInt(args[1]); i++) {
            System.out.println("distTo[" + i + "] == " + test.distTo(i));
        }
        System.out.print(test.distTo(7) + ",");
        System.out.print(test.distTo(37) + ",");
        System.out.print(test.distTo(59) + ",");
        System.out.print(test.distTo(82) + ",");
        System.out.print(test.distTo(99) + ",");
        System.out.print(test.distTo(115) + ",");
        System.out.print(test.distTo(133) + ",");
        System.out.print(test.distTo(165) + ",");
        System.out.print(test.distTo(188) + ",");
        System.out.print(test.distTo(197));
    }


}

class Edge {

    private final int v; // origin of the edge
    private final int w; // destination of the edge
    private final int weight;

    public Edge(int from, int to, int weight) {
        this.v = from;
        this.w = to;
        this.weight = weight;
    }

    public int from() { return this.v; }
    public int to() { return this.w; }
    public int weight() {return this.weight; }

    public String toString() {
        return this.from() + "->" + this.to();
    }

}

class Graph {

    private final int V;   // number of total vertices
    private LinkedList<Edge>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = (LinkedList<Edge>[]) new LinkedList[V];
        for (int i = 0; i < V; i++) { adj[i] = new LinkedList<>(); }
    }

    public void addEdge(Edge e) {
        adj[e.from()].add(e);
    }

    public int V() { return this.V; }

    public Iterable<Edge> adj(int v) { return adj[v]; }

}

class Solver {

    private Edge[] edgeTo;
    private int[] distTo;
    private Graph G;

    public Solver(Graph G) {

        this.G = G;
        edgeTo = new Edge[G.V()];
        distTo = new int[G.V()];
        for (int i = 0; i < G.V(); i++) { distTo[i] = 1000000; }    // This means unreachable

        distTo[1] = 0;
        for (int j = 1; j < G.V(); j++) {
            Iterable<Edge> adj = G.adj(j);
            relax(j);
            for (Edge e: adj) {
                    relax(e.to());
            }
        }

    }

    public void relax(int v) {
        Iterable<Edge> adj = this.G.adj(v);
        for (Edge e : adj) {
//            if (relaxed[e.to()]) continue;
            if (distTo[v] + e.weight() < distTo[e.to()]) {
                distTo[e.to()] = distTo[v] + e.weight();
                edgeTo[e.to()] = e;
            }
        }
    }

    public boolean hasPathTo(int v) { return distTo[v] != 1000000; }

    public Stack<Edge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) path.push(e);
        return path;
    }

    public int distTo(int v) { return distTo[v]; }
}