package Assignment3;

import edu.princeton.cs.algs4.In;

/**
 * Created by LiuMingyu on 2/4/17.
 */

public class Main_3 {

    public static void main(String[] args) {

        In reader = new In(args[0]);
        int[] data = new int[Integer.parseInt(args[1])];
        for (int i = 0; reader.hasNextLine(); i++) data[i] = reader.readInt();

        Solver solver = new Solver(data);
        int sum = 0;
        for (int k = 1; k <= Integer.parseInt(args[1]); k++) {
            int median = solver.median(k);
            sum += median;
            System.out.println(k + ": " + median);
        }
        System.out.println("sum modulus 10000 is " + sum % 10000);
    }

}

class Solver {

    private MinHeap Hhi;
    private MaxHeap Hlo;
    private int[] data;
    private int index = 0;
    private int median = 0;

    public Solver(int[] data) {
        this.Hhi = new MinHeap();
        this.Hlo = new MaxHeap();
        this.data = data;
    }

    public int median(int k) {
        while (index < k) {
            if (this.data[index] > median) this.Hhi.insert(this.data[index++]);
            else this.Hlo.insert(this.data[index++]);
        }
        if (Hhi.size() == 1 && median == 0) return Hhi.min();
        balance();
        median = Hlo.max();
        return median;
    }


    private void balance() {
        while (Hhi.size() - Hlo.size() >= 1) Hlo.insert(Hhi.delMin());
        while (Hlo.size() - Hhi.size() > 1) Hhi.insert(Hlo.delMax());
    }
}

class MinHeap {

    private int[] pq;
    private int size = 0;
    public MinHeap() {
        this.pq = new int[2];
    }

    public void insert(int v) {
        if (size >= this.pq.length/4) resize(2*this.pq.length);
        this.pq[++size] = v;
        swim(size);
    }

    public int min() {
        if (isEmpty()) return 0;
        return pq[1];
    }

    public int delMin() {
        if (isEmpty()) return 0;
        int min = this.min();
        exch(1, size);
        this.pq[size--] = 0;
        sink(1);
        return min;
    }

    public int size() { return size; }

    private void sink(int k) {
        while (2*k <= this.size) {
            int j = 2*k;
            if (j < this.size && pq[j+1] < pq[j]) j++;
            if (this.pq[k] <= this.pq[j]) break;
            exch(k, j);
            k = j;
        }
    }

    private void swim(int k) {
        while (k > 1) {
            if (this.pq[k] < this.pq[k/2]) {
                exch(k, k/2);
                k = k/2;
            }
            else break;
        }
    }

    private void exch(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    public boolean isEmpty() { return size == 0; }

    private void resize(int k) {
        int[] newPQ = new int[k];
        for (int i = 1; i <= size; i++) newPQ[i] = this.pq[i];
        this.pq = null;
        this.pq = newPQ;
    }

}

class MaxHeap {
    private int[] pq;
    private int size = 0;
    public MaxHeap() {
        this.pq = new int[2];
    }

    public void insert(int v) {
        if (size >= this.pq.length/4) resize(2*this.pq.length);
        this.pq[++size] = v;
        swim(size);
    }

    public int max() {
        if (isEmpty()) return 0;
        return pq[1];
    }

    public int delMax() {
        if (isEmpty()) return 0;
        int max = this.max();
        exch(1, size);
        this.pq[size--] = 0;
        sink(1);
        return max;
    }

    public int size() { return size; }

    private void sink(int k) {
        while (2*k <= this.size) {
            int j = 2*k;
            if (j < this.size && pq[j+1] > pq[j]) j++;
            if (this.pq[k] >= this.pq[j]) break;
            exch(k, j);
            k = j;
        }
    }

    private void swim(int k) {
        while (k > 1) {
            if (this.pq[k] > this.pq[k/2]) {
                exch(k, k/2);
                k = k/2;
            }
            else break;
        }
    }

    private void exch(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    public boolean isEmpty() { return size == 0; }

    private void resize(int k) {
        int[] newPQ = new int[k];
        for (int i = 1; i <= size; i++) newPQ[i] = this.pq[i];
        this.pq = null;
        this.pq = newPQ;
    }
}