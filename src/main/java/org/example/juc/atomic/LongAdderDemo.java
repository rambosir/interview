package org.example.juc.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-04 16:31
 */
public class LongAdderDemo {

  public static void main(String[] args) {

    LongAdder longAdder = new LongAdder();
    longAdder.increment();
    longAdder.increment();
    longAdder.increment();
    System.out.println(longAdder.longValue());

    LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);
    longAccumulator.accumulate(1);
    longAccumulator.accumulate(2);
    longAccumulator.accumulate(3);
    System.out.println(longAccumulator.longValue());

    LongAccumulator longAccumulator2 = new LongAccumulator((x, y) -> x - y, 100);
    longAccumulator2.accumulate(1);
    longAccumulator2.accumulate(2);
    longAccumulator2.accumulate(3);
    System.out.println(longAccumulator2.get());
  }
}
