package data_structures.prog_1.testers;

import data_structures.prog_1.*;

public class P1Tester {
    private LinearListADT<Integer> list;

    public P1Tester() {

        list = new ArrayLinearList<Integer>(9);
        runTests();
    }

    private void runTests() {

        if (list.peekFirst() != null)
            print("ERROR in peekFirst");
        if (list.peekLast() != null)
            print("ERROR in peekLast");
        list.addFirst(5);
        list.contains(5);
        list.contains(5);
        list.clear();
        list.peekFirst();

    }

    private void print(String s) {
        System.out.println(s);
    }

    private void print(int i) {
        System.out.print(" " + i);
    }

    public static void main(String[] args) {
        new P1Tester();
    }
}
