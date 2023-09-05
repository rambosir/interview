package org.example.base.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-03 14:34
 */
public class LockSupportDemo {

  static final Object object = new Object();

  static Lock lock = new ReentrantLock();

  static Condition condition = lock.newCondition();

  public static void main(String[] args) {

    // wait和notify必须在synchronized块或同步方法里，且成对出现使用
    // 先wait 和 notify 才ok
    // synchronizedWaitNotify();

    // Condition中的线程等待和唤醒方法之前，需要先获取锁
    // 一定要先await后signal，不要反了
    // conditionTest();

    // LockSupport是用来创建锁和其他同步类的基本线程阻塞原语。
    // LockSupport类使用了一种名为Permit (许可)的概念来做到阻塞和唤醒线程的功能，每个线程都有一个许可(permit),
    // permit只有两个值1和零，默认是零。
    // 可以把许可看成是一种(0,1)信号量(Semaphore)，但与Semaphore不同的是，许可的累加上限是1。
    // **成双成对
    Thread t1 =
        new Thread(
            () -> {
              System.out.println(Thread.currentThread().getName() + "\t" + "----come in");
              LockSupport.park();
              System.out.println(Thread.currentThread().getName() + "\t" + "----被唤醒");
            },
            "t1");
    t1.start();
    new Thread(
            () -> {
              System.out.println(Thread.currentThread().getName() + "\t" + "----发出通知");
              LockSupport.unpark(t1);
            },
            "t2")
        .start();
  }

  private static void conditionTest() {
    new Thread(
            () -> {
              lock.lock();
              try {
                System.out.println(Thread.currentThread().getName() + "\t" + "----come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t" + "----被唤醒");
              } catch (InterruptedException e) {
                e.printStackTrace();
              } finally {
                lock.unlock();
              }
            },
            "t1")
        .start();

    new Thread(
            () -> {
              lock.lock();
              try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t" + "----发出通知");
              } finally {
                lock.unlock();
              }
            },
            "t2")
        .start();
  }

  private static void synchronizedWaitNotifyTest() {
    new Thread(
            () -> {
              synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t" + "----come in");
                try {
                  object.wait();
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "----被唤醒");
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
              synchronized (object) {
                object.notify();
                System.out.println(Thread.currentThread().getName() + "\t" + "----发出通知");
              }
            },
            "t2")
        .start();
  }
}
