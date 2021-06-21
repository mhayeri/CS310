package data_structures.prog_2.testers;

import data_structures.prog_2.*;

public class P2Tester {
    public static <E> void main(String[] args) {
        LinearList<Integer> myLinearList = new LinearList<Integer>();
        Stack<Integer> myStack = new Stack<Integer>();
        Queue<Integer> myQueue = new Queue<Integer>();
        // PEEKFIRST & PEEKLAST
        myStack.push(5);
        myStack.push(0);
        myStack.push(0);
        myStack.push(0);
        myStack.push(0);
        myStack.push(0);
        myStack.push(0);
        myStack.push(0);
        myStack.push(0);
        myStack.push(1);
        myStack.push(7);
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.pop();

        for (Integer i : myStack)
            System.out.print(i + " ");
        System.out.println("\nPeek is : " + myStack.peek());
        System.out.println(myStack.size());
        System.out.println(myStack.isEmpty());
        System.out.println(myStack.contains(1));
        System.out.println(myStack.remove(5));
        for (Integer i : myStack)
            System.out.print(i + " ");

    }
}
