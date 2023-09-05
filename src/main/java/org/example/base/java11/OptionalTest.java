package org.example.base.java11;

import java.util.Optional;
import org.junit.Test;

/**
 * @program interview
 * @author Ben
 * @date 2023-06-30 11:27
 */
public class OptionalTest {

  @Test
  public void test() {
    Optional<String> optionalS = Optional.ofNullable(null);
    optionalS.ifPresentOrElse(System.out::println, () -> System.out.println("这是个空的"));
    optionalS.or(() -> Optional.of("ggg")).ifPresent(System.out::println);
  }
}
