package org.example.loop;

import org.example.recursion.LinkedListCreator;
import org.example.common.Node;

import java.util.Arrays;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-26 17:51
 **/
public class LinkedListReverser {

    public Node reverseLinkedList(Node head) {
        // already reversed linked list
        Node newHead = null;
        // not yet reversed linked list
        Node curHead = head;
        while (curHead != null) {
            // null <- 1 <- 2 <- 3 4 -> 5
            Node next = curHead.getNext();
            curHead.setNext(newHead);
            newHead = curHead;
            curHead = next;
        }
        return newHead;
    }

    public static void main(String[] args) {
        LinkedListCreator creator = new LinkedListCreator();
        LinkedListReverser reverser = new LinkedListReverser();
        Node.printNode(reverser.reverseLinkedList(creator.createLinkedList(Arrays.asList(1, 2, 3, 4, 5))));

        Node.printNode(reverser.reverseLinkedList(creator.createLargeLinkedList(100)));
        reverser.reverseLinkedList(creator.createLargeLinkedList(1000000));
        System.out.println("done");
    }
}
