package org.example.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-04 14:48
 */
public class ABADemo {

  static AtomicInteger atomicInteger = new AtomicInteger(100);
  static AtomicStampedReference<Integer> atomicStampedReference =
      new AtomicStampedReference<>(100, 1);

  public static void main(String[] args) {
    new Thread(
            () -> {
              int stamp = atomicStampedReference.getStamp();
              System.out.println(Thread.currentThread().getName() + "\t" + "默认版本号：" + stamp);
              // 让后面t4获得和t3的版本号，都是1
              try {
                TimeUnit.SECONDS.sleep(1);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }

              atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
              System.out.println(
                  Thread.currentThread().getName()
                      + "\t"
                      + "----第1次版本号："
                      + atomicStampedReference.getStamp());

              atomicStampedReference.compareAndSet(
                  101,
                  100,
                  atomicStampedReference.getStamp(),
                  atomicStampedReference.getStamp() + 1);

              System.out.println(
                  Thread.currentThread().getName()
                      + "\t"
                      + "----第2次版本号："
                      + atomicStampedReference.getStamp());
            },
            "t3")
        .start();

    new Thread(
            () -> {
              int stamp = atomicStampedReference.getStamp();
              System.out.println(Thread.currentThread().getName() + "\t" + "默认版本号：" + stamp);

              // 让t3完成ABA问题
              try {
                TimeUnit.SECONDS.sleep(3);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
              boolean b = atomicStampedReference.compareAndSet(100, 666, stamp, stamp + 1);
              System.out.println(
                  Thread.currentThread().getName()
                      + "\t"
                      + "修改是否成功："
                      + b
                      + "\t最新版本号:"
                      + atomicStampedReference.getStamp()
                      + "\t最新值："
                      + atomicStampedReference.getReference());
            },
            "t4")
        .start();
  }

  private static void abaProblem() {
    new Thread(
            () -> {
              atomicInteger.compareAndSet(100, 101);
              atomicInteger.compareAndExchange(101, 100);
            },
            "t1")
        .start();

    try {
      TimeUnit.MILLISECONDS.sleep(10);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    new Thread(
            () -> {
              boolean b = atomicInteger.compareAndSet(100, 555);
              System.out.println(
                  Thread.currentThread().getName()
                      + "\t"
                      + "修改是否成功："
                      + b
                      + "\t结果:"
                      + atomicInteger.get());
            },
            "t2")
        .start();
  }
}
