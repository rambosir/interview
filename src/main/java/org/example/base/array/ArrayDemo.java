package org.example.base.array;

import java.util.Arrays;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-05 12:36
 */
public class ArrayDemo {

  public static void main(String[] args) {
    Integer[] a = {1, 4, 5, 3};
    Arrays.sort(a);
    for (Integer integer : a) {
      System.out.println(integer);
    }
    System.out.println("-----------");
    Arrays.stream(a).sorted((x, y) -> y - x).forEach(System.out::println);
  }
}
