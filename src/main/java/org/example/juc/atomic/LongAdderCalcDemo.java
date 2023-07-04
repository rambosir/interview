package org.example.juc.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-04 16:31
 */
public class LongAdderCalcDemo {

  static final int SIZE_THREAD = 50;

  static final int _1W = 10000;

  public static void main(String[] args) {
    addSynchronized();
    addAtomicInteger();
    addAtomicLong();
    addLongAdder();
    addLongAccumulator();
  }

  private static void addLongAccumulator() {
    ClickNumber clickNumber = new ClickNumber();
    CountDownLatch countDownLatch = new CountDownLatch(SIZE_THREAD);
    long startTime = System.currentTimeMillis();
    for (int i = 1; i <= SIZE_THREAD; i++) {
      new Thread(
              () -> {
                try {
                  for (int i1 = 1; i1 <= (100 * _1W); i1++) {
                    clickNumber.addLongAccumulator();
                  }
                } finally {
                  countDownLatch.countDown();
                }
              },
              String.valueOf(i))
          .start();
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(
        "----costTime："
            + (endTime - startTime)
            + "毫秒"
            + "\taddLongAccumulator"
            + "\t"
            + clickNumber.longAccumulator.get());
  }

  private static void addLongAdder() {
    ClickNumber clickNumber = new ClickNumber();
    CountDownLatch countDownLatch = new CountDownLatch(SIZE_THREAD);
    long startTime = System.currentTimeMillis();
    for (int i = 1; i <= SIZE_THREAD; i++) {
      new Thread(
              () -> {
                try {
                  for (int i1 = 1; i1 <= (100 * _1W); i1++) {
                    clickNumber.addLongAdder();
                  }
                } finally {
                  countDownLatch.countDown();
                }
              },
              String.valueOf(i))
          .start();
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(
        "----costTime："
            + (endTime - startTime)
            + "毫秒"
            + "\tlongAdder"
            + "\t"
            + clickNumber.longAdder.longValue());
  }

  private static void addAtomicLong() {
    ClickNumber clickNumber = new ClickNumber();
    CountDownLatch countDownLatch = new CountDownLatch(SIZE_THREAD);
    long startTime = System.currentTimeMillis();
    for (int i = 1; i <= SIZE_THREAD; i++) {
      new Thread(
              () -> {
                try {
                  for (int i1 = 1; i1 <= (100 * _1W); i1++) {
                    clickNumber.addAtomicLong();
                  }
                } finally {
                  countDownLatch.countDown();
                }
              },
              String.valueOf(i))
          .start();
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(
        "----costTime："
            + (endTime - startTime)
            + "毫秒"
            + "\taddAtomicLong"
            + "\t"
            + clickNumber.atomicLong.get());
  }

  private static void addAtomicInteger() {
    ClickNumber clickNumber = new ClickNumber();
    CountDownLatch countDownLatch = new CountDownLatch(SIZE_THREAD);
    long startTime = System.currentTimeMillis();
    for (int i = 1; i <= SIZE_THREAD; i++) {
      new Thread(
              () -> {
                try {
                  for (int i1 = 1; i1 <= (100 * _1W); i1++) {
                    clickNumber.addAtomicInteger();
                  }
                } finally {
                  countDownLatch.countDown();
                }
              },
              String.valueOf(i))
          .start();
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(
        "----costTime："
            + (endTime - startTime)
            + "毫秒"
            + "\taddAtomicInteger"
            + "\t"
            + clickNumber.atomicInteger.get());
  }

  private static void addSynchronized() {
    ClickNumber clickNumber = new ClickNumber();
    CountDownLatch countDownLatch = new CountDownLatch(SIZE_THREAD);
    long startTime = System.currentTimeMillis();
    for (int i = 1; i <= SIZE_THREAD; i++) {
      new Thread(
              () -> {
                try {
                  for (int i1 = 1; i1 <= (100 * _1W); i1++) {
                    clickNumber.addSynchronized();
                  }
                } finally {
                  countDownLatch.countDown();
                }
              },
              String.valueOf(i))
          .start();
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(
        "----costTime："
            + (endTime - startTime)
            + "毫秒"
            + "\taddSynchronized"
            + "\t"
            + clickNumber.number);
  }
}

class ClickNumber {

  int number = 0;

  public synchronized void addSynchronized() {
    number++;
  }

  AtomicInteger atomicInteger = new AtomicInteger();

  public void addAtomicInteger() {
    atomicInteger.incrementAndGet();
  }

  AtomicLong atomicLong = new AtomicLong();

  public void addAtomicLong() {
    atomicLong.incrementAndGet();
  }

  LongAdder longAdder = new LongAdder();

  public void addLongAdder() {
    longAdder.increment();
  }

  LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

  public void addLongAccumulator() {
    longAccumulator.accumulate(1L);
  }
}
