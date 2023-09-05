package org.example.base.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-03 11:15
 * 
 * jps -l +  jstack xxx
 * jconsole
 */
public class DeadLockDemo {

  static final Object objectA = new Object();

  static final Object objectB = new Object();

  public static void main(String[] args) {

    new Thread(
            () -> {
              synchronized (objectA) {
                System.out.println(Thread.currentThread().getName() + "\t" + " 自己持有A锁，期待获得B锁");
                try {
                  TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                synchronized (objectB) {
                  System.out.println(Thread.currentThread().getName() + "\t 获得锁B成功");
                }
              }
            },
            "t1")
        .start();

    new Thread(
            () -> {
              synchronized (objectB) {
                System.out.println(Thread.currentThread().getName() + "\t" + " 自己持有B锁，期待获得A锁");
                try {
                  TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                synchronized (objectA) {
                  System.out.println(Thread.currentThread().getName() + "\t 获得锁A成功");
                }
              }
            },
            "t2")
        .start();
  }
}
