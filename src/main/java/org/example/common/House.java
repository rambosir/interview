package org.example.common;

import lombok.Data;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-06 13:36
 */
@Data
public class House {

  private ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

  public void sale() {
    Integer value = threadLocal.get();
    value++;
    threadLocal.set(value);
  }
}
