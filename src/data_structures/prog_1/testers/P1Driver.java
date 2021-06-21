package data_structures.prog_1.testers;

import data_structures.prog_1.*;

public class P1Driver {
    private LinearListADT<Integer> list;
    private static final int MAX_SIZE = 1000000;

    public P1Driver() {
        list = new ArrayLinearList<Integer>(MAX_SIZE);
        runTests();
    }

    private void runTests() {
        try {
            for (int i = 100; i > 0; i--)
                list.addFirst(i);
            print("1 Should print 1 .. 100");
            print();
            for (int i = 101; i <= 200; i++)
                list.addLast(i);
            print("2 Should print 1 .. 200");
            print();
            for (int i = 1; i <= 100; i++) {
                if (list.removeFirst() != i)
                    print("3 ERROR in removeFirst at" + i);
            }

            for (int i = 200; i > 102; i--) {
                // leave two elements
                if (list.removeLast() != i)
                    print("4 ERROR in removeFirst at" + i);
            }
            print("Should print 101 102");
            print();
            for (int i = 0; i < MAX_SIZE - 2; i++)
                list.addFirst(-i);
            print("5 Size should be 1000000 and is now: " + list.size());

            for (int i = 0; i < MAX_SIZE >> 2; i++) {
                list.removeFirst();
                list.removeLast();
            }

            print("6 Size should be 500000 and is now: " + list.size());
            list = new ArrayLinearList<Integer>(10);
        } catch (Exception e) {
            System.out.println("In first catch block");
            e.printStackTrace();
        }
    }

    private void print(String s) {
        System.out.println(s);
    }

    private void print() {
        for (Integer i : list) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new P1Driver();
    }
}
