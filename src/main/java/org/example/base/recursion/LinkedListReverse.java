package org.example.base.recursion;

import org.example.base.common.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-25 14:18
 **/
public class LinkedListReverse {

    /**
     * @param head linked list to reverse
     * @return reversed linked list
     */
    public Node reverseLinkedList(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node newHead = reverseLinkedList(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return newHead;
    }

    public static void main(String[] args) {
        LinkedListCreator linkedListCreator = new LinkedListCreator();
        LinkedListReverse linkedListReverse = new LinkedListReverse();

        Node.printNode(linkedListReverse.reverseLinkedList(linkedListCreator.createLinkedList(new ArrayList<>())));
        Node.printNode(linkedListReverse.reverseLinkedList(linkedListCreator.createLinkedList(Collections.singletonList(1))));
        Node.printNode(linkedListReverse.reverseLinkedList(linkedListCreator.createLinkedList(Arrays.asList(1, 2, 3, 4, 5))));

        linkedListReverse.reverseLinkedList(linkedListCreator.createLargeLinkedList(10000000));
    }
}
