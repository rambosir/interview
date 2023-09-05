package org.example.base.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-04 14:26
 */
public class SpinLockDemo {

  AtomicReference<Thread> atomicReference = new AtomicReference<>();

  public void myLock() {
    System.out.println(Thread.currentThread().getName() + "\t" + "----come in");
    while (!atomicReference.compareAndSet(null, Thread.currentThread())) {
    }

    System.out.println(Thread.currentThread().getName() + "\t" + "----持有锁成功");
  }

  public void myUnlock() {
    atomicReference.compareAndSet(Thread.currentThread(), null);
    System.out.println(Thread.currentThread().getName() + "\t" + "----释放锁成功");
  }

  public static void main(String[] args) {
    SpinLockDemo spinLockDemo = new SpinLockDemo();
    new Thread(
            () -> {
              spinLockDemo.myLock();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
              spinLockDemo.myUnlock();
            },
            "t1")
        .start();
      
    new Thread(
            () -> {
              spinLockDemo.myLock();
              spinLockDemo.myUnlock();
            },
            "t2")
        .start();
  }
}
