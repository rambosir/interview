package org.example.base.juc.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-16 13:51
 */
public class AtomicIntegerDemo {

  public static void main(String[] args) {
    coutdownlatch();
    // sleep();
    // uncorrect();
  }

  private static void uncorrect() {
    MyNumber myNumber = new MyNumber();
    for (int i = 0; i < 10; i++) {
      new Thread(
              () -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                  myNumber.addPlusPlus();
                }
              },
              String.valueOf(i))
          .start();
    }
    System.out.println(Thread.currentThread().getName() + "\t" + myNumber.atomicInteger.get());
  }

  private static void sleep() {
    MyNumber myNumber = new MyNumber();
    for (int i = 0; i < 10; i++) {
      new Thread(
              () -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                  myNumber.addPlusPlus();
                }
              },
              String.valueOf(i))
          .start();
    }

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.println(Thread.currentThread().getName() + "\t" + myNumber.atomicInteger.get());
  }

  private static void coutdownlatch() {
    MyNumber myNumber = new MyNumber();
    CountDownLatch countDownLatch = new CountDownLatch(10);
    for (int i = 0; i < 10; i++) {
      new Thread(
              () -> {
                try {
                  for (int i1 = 0; i1 < 1000; i1++) {
                    myNumber.addPlusPlus();
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

    System.out.println(Thread.currentThread().getName() + "\t" + myNumber.atomicInteger.get());
  }
}

class MyNumber {

  AtomicInteger atomicInteger = new AtomicInteger(0);

  public void addPlusPlus() {
    atomicInteger.incrementAndGet();
  }
}
