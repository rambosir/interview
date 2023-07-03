package org.example.loop;

import org.example.common.Node;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-26 18:47
 **/
public class LinkedListDeletor {


    public Node deleteIfEquals(Node head, int value) {

        while (head != null && head.getValue() == value) {
            head = head.getNext();
        }
        if (head == null) {
            return null;
        }
        Node prev = head;
        while (prev.getNext() != null) {
            if (prev.getNext().getValue() == value) {
                prev.setNext(prev.getNext().getNext());
            } else {
                prev = prev.getNext();
            }
        }
        return head;
    }

    public static void main(String[] args) {

    }
}
