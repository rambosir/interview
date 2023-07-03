package org.example.java11;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.FileOutputStream;

/**
 * @program interview
 * @author Ben
 * @date 2023-06-30 11:41
 */
public class InputStreamTest {

  @SneakyThrows
  @Test
  public void test() {
    var cl = this.getClass().getClassLoader();
    try (var is = cl.getResourceAsStream("test/InputStreamTest");
         var os = new FileOutputStream("InputStreamTest2.txt")) {
      is.transferTo(os);
    }
  }
}
