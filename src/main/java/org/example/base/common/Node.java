package org.example.base.common;

import lombok.Data;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-25 14:06
 **/

@Data
public class Node {

    private final int value;

    private Node next;


    public Node(int value) {
        this.value = value;
        this.next = null;
    }

    public static void printNode(Node head) {
        while (head != null) {
            System.out.print(head.getValue());
            System.out.print(" ");
            head = head.getNext();
        }
        System.out.println();
    }
}
