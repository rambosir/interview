package org.example.base.recursion;

import org.example.base.common.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-25 14:06
 **/
public class LinkedListCreator {

    public Node createLinkedList(List<Integer> data) {
        if (data.isEmpty()) {
            return null;
        }
        Node firstNode = new Node(data.get(0));
        firstNode.setNext(createLinkedList(data.subList(1, data.size())));
        return firstNode;
    }

    public Node createLargeLinkedList(int size) {
        Node prev = null;
        Node head = null;
        for (int i = 1; i <= size; i++) {
            Node node = new Node(i);
            if (prev != null) {
                prev.setNext(node);
            } else {
                head = node;
            }
            prev = node;
        }
        return head;
    }
    

    public static void main(String[] args) {
        LinkedListCreator linkedListCreator = new LinkedListCreator();
        Node.printNode(linkedListCreator.createLinkedList(new ArrayList<>()));
        Node.printNode(linkedListCreator.createLinkedList(Collections.singletonList(1)));
        Node.printNode(linkedListCreator.createLinkedList(Arrays.asList(1, 2, 3, 4, 5)));
        
    }
}
