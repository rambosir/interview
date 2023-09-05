package org.example.base.java11;

import java.io.Serializable;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @program interview
 * @author Ben
 * @date 2023-06-29 17:42
 */
public class StreamTest {

  @Test
  public void test1() {
    Stream<? extends Serializable> stream = Stream.of(1, 2, "3");
    stream.forEach(System.out::println);
  }

  @Test
  public void test2() {

    Stream<Integer> integerStream = Stream.of(3, 9, 20, 22, 40, 7);
    Stream<Integer> integerStream1 = integerStream.takeWhile(t -> t % 2 != 0);
    integerStream1.forEach(System.out::println);

    System.out.println("----------------------------------------");
    integerStream = Stream.of(3, 9, 20, 22, 40, 7);
    Stream<Integer> integerStream2 = integerStream.dropWhile(t -> t % 2 != 0);
    integerStream2.forEach(System.out::println);
  }

  @Test
  public void test3() {
    Stream<Integer> iterate = Stream.iterate(1, t -> (2 * t) + 1);
    iterate.limit(10).forEach(System.out::println);
    System.out.println("------------------------------------");
    Stream<Integer> iterate2 = Stream.iterate(1, t -> t < 1000, t -> (2 * t) + 1);
    iterate2.forEach(System.out::println);

  }
}
