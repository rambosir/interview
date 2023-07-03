package org.example.java11;

import java.util.List;

/**
 * @program interview
 * @author Ben
 * @date 2023-06-29 14:50
 */
public class ListTest {

  public static void main(String[] args) {

    List<String> list = List.of("a", "c", "b");
    System.out.println(list);

    var serializables = List.of(1, 2, 3, "4");
    System.out.println(serializables);
    serializables.forEach(x -> System.out.println(x.getClass()));

  }
}
