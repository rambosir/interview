package org.example.juc.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-03 12:34
 */
public class InterruptDemo {

  static volatile boolean isStopped = false;

  static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

  public static void main(String[] args) {
    volatileTest();
    atomicTest();
    isInterruptedTest();
    isInterruptedTest2();
    isInterruptedTest3();
    isInterruptedTest4();
  }

  private static void isInterruptedTest4() {
    System.out.println(Thread.currentThread().getName() + "----" + Thread.interrupted());
    System.out.println(Thread.currentThread().getName() + "----" + Thread.interrupted());
    System.out.println("11111");
    Thread.currentThread().interrupt();
    System.out.println("22222");
    System.out.println(Thread.currentThread().getName() + "----" + Thread.interrupted());
    System.out.println(Thread.currentThread().getName() + "----" + Thread.interrupted());
  }

  /** 中断只是一种协商机制，修改中断标识位 */
  private static void isInterruptedTest3() {
    Thread t1 =
        new Thread(
            () -> {
              while (true) {
                if (Thread.currentThread().isInterrupted()) {
                  System.out.println("-----isInterrupted()==true,程序结束");
                  break;
                }
                try {
                  TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                  // 线程中断标志复位为false，无法停下，需要再次调用interrupt()
                  Thread.currentThread().interrupt();
                }
                System.out.println("-----hello isInterrupted");
              }
            },
            "t1");
    t1.start();
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    new Thread(
            () -> {
              // 修改t1线程的中断标志位为true
              t1.interrupt();
            },
            "t2")
        .start();
  }

  private static void isInterruptedTest2() {
    // 中断为true后，并不是立刻stop程序
    Thread t1 =
        new Thread(
            () -> {
              for (int i = 1; i <= 300; i++) {
                System.out.println("------i: " + i);
              }
              System.out.println(
                  "t1. interrupt()调用之后02: " + Thread.currentThread().isInterrupted());
            },
            "t1");
    t1.start();
    System.out.println("t1. interrupt()调用之前, t1线程的中断标识默认值:" + t1.isInterrupted());
    try {
      TimeUnit.MILLISECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // 实例方法interrupt()仅仅是没置线程的中断状态位设置为true，不会停止线程
    t1.interrupt();
    System.out.println("t1. interrupt()调用之后01: " + t1.isInterrupted());
    try {
      TimeUnit.MILLISECONDS.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("t1. interrupt()调用之后03: " + t1.isInterrupted());
  }

  private static void isInterruptedTest() {
    Thread t1 =
        new Thread(
            () -> {
              while (true) {
                if (Thread.currentThread().isInterrupted()) {
                  System.out.println("-----isInterrupted()==true,程序结束");
                  break;
                }
                System.out.println("-----hello isInterrupted");
              }
            },
            "t1");
    t1.start();
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    new Thread(
            () -> {
              // 修改t1线程的中断标志位为true
              t1.interrupt();
            },
            "t2")
        .start();
  }

  private static void atomicTest() {
    new Thread(
            () -> {
              while (true) {
                if (atomicBoolean.get()) {
                  System.out.println("-----atomicBoolean.get(),程序结束");
                  break;
                }
                System.out.println("-----hello atomicBoolean");
              }
            },
            "t1")
        .start();
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    new Thread(
            () -> {
              atomicBoolean.set(true);
            },
            "t2")
        .start();
  }

  /** volatile */
  private static void volatileTest() {
    new Thread(
            () -> {
              while (true) {
                if (isStopped) {
                  System.out.println("-----isStopped=true,程序结束");
                  break;
                }
                System.out.println("-----hello isStop");
              }
            },
            "t1")
        .start();
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    new Thread(
            () -> {
              isStopped = true;
            },
            "t2")
        .start();
  }
}
