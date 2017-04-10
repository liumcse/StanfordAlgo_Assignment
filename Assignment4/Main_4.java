package Assignment4;

import edu.princeton.cs.algs4.In;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Created by LiuMingyu on 9/4/17.
 * In this problem, our hash table use separate-chaining
 * AND: key == value, key represents value
 */
public class Main_4 {

    public static void main(String[] args) {

        TreeSet<Integer> tTree = new TreeSet<>();

        In reader = new In(args[0]);
        HashTable testTable = new HashTable();

        while (reader.hasNextLine()) testTable.put(new BigInteger(reader.readString()));

        Stack<BigInteger> keys = testTable.keys();

        while (!keys.isEmpty()) {

            BigInteger value = keys.pop();

            for (Integer t = -10000; t <= 10000; t = t + 1) {
                if (tTree.contains(t)) continue;
                BigInteger subtraction = new BigInteger(t.toString()).subtract(value);  // t - value
                if (subtraction.equals(value)) continue;
                if (testTable.find(subtraction)) {
                    tTree.add(t);
                    System.out.println("keys size is " + keys.size() + ", t == " + t + ", tTree.size == " + tTree.size());
                }
            }

        }

        System.out.println(tTree.size());

    }



}


class HashTable {

    private final LinkedList<BigInteger>[] A;
    private Stack<BigInteger> keys = new Stack<>();

    public HashTable() {
        A = (LinkedList<BigInteger>[]) new LinkedList[1000000];
        for (int i = 0; i < 1000000; i++) A[i] = new LinkedList<>();
    }

    public void put(BigInteger value) {
        if (find(value)) return;    // de-duplicates
        int hashCode = hash(value);
        A[hashCode].add(value);
        keys.push(value);
    }

    public int hash(BigInteger value) {
        return Math.abs(value.mod(new BigInteger("1000000")).intValue()) ;
    }

    public boolean find(BigInteger value) {
        int hashCode = hash(value);
        return A[hashCode].contains(value);
    }

    public Stack<BigInteger> keys() { return this.keys; }
}