package org.example.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-04 15:46
 */
public class AtomicMarkableReferenceDemo {

  static AtomicMarkableReference<Integer> atomicMarkableReference =
      new AtomicMarkableReference<>(100, false);

  public static void main(String[] args) {
    new Thread(
            () -> {
              boolean marked = atomicMarkableReference.isMarked();
              System.out.println(Thread.currentThread().getName() + "\t----默认修改标志：" + marked);
              try {
                TimeUnit.SECONDS.sleep(1);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
              atomicMarkableReference.compareAndSet(100, 101, marked, !marked);
            },
            "t1")
        .start();

    new Thread(
            () -> {
              boolean marked = atomicMarkableReference.isMarked();
              System.out.println(Thread.currentThread().getName() + "\t----默认修改标志：" + marked);
              try {
                TimeUnit.SECONDS.sleep(2);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
              boolean b = atomicMarkableReference.compareAndSet(100, 102, marked, !marked);
              System.out.println(
                  Thread.currentThread().getName()
                      + "\t----操作是否成功："
                      + b
                      + "\t结果："
                      + atomicMarkableReference.getReference());
            },
            "t2")
        .start();
  }
}
