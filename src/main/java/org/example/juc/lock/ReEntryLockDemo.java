package org.example.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-03 10:21
 *     <p>指的是可重复可递归调用的锁，在外层使用锁之后，在内层仍然可以使用并且不发生死锁，这样的锁就叫做可重入锁。
 *     <p>简单的来说就是: 在一个synchronized修饰的方法或代码块的内部调用本类的其他synchronized修饰的方法或代码块时， 是永远可以得到锁的
 */
public class ReEntryLockDemo {

  static final Object object = new Object();

  static final Object o2 = new Object();

  public static void main(String[] args) {
    // new ReEntryLockDemo().m1();
    Lock lock = new ReentrantLock();
    new Thread(
            () -> {
              lock.lock();
              try {
                System.out.println(Thread.currentThread().getName() + "---外层");
                lock.lock();
                try {
                  System.out.println(Thread.currentThread().getName() + "---内层");
                } finally {
                  lock.unlock();
                }
              } finally {
                lock.unlock();
              }
            },
            "t1")
        .start();

    new Thread(
            () -> {
              try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "-----");
              } finally {
                lock.unlock();
              }
            },
            "t2")
        .start();
  }

  public synchronized void m1() {
    System.out.println("----m1");
    m2();
  }

  public synchronized void m2() {
    System.out.println("-----m2");
    m3();
  }

  public synchronized void m3() {
    System.out.println("-----m3");
  }

  private static void syncBlock() {
    new Thread(
            () -> {
              synchronized (object) {
                System.out.println("------外层");
                synchronized (object) {
                  System.out.println("------中层");
                  synchronized (object) {
                    System.out.println("------内层");
                  }
                }
              }
            })
        .start();
  }
}
