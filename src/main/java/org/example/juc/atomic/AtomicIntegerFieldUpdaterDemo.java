package org.example.juc.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-04 16:04
 */
public class AtomicIntegerFieldUpdaterDemo {

  public static void main(String[] args) {

    CountDownLatch countDownLatch = new CountDownLatch(100);
    BankAccount bankAccount = new BankAccount();
    for (int i = 0; i < 100; i++) {
      new Thread(
              () -> {
                try {
                  bankAccount.transfer(bankAccount);
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

    System.out.println("结果：" + bankAccount.money);
  }
}

class BankAccount {

  String bankName = "ccb";

  // 以一种线程安全的方式操作非线程安全对象内的某些字段

  // 1 更新的对象属性必须使用public volatile 修饰符。
  public volatile int money = 0;
  // 2 因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须
  // 使用静态方法newUpdater()创建一个更新器， 并且需要设置想要更新的关和属性。
  static final AtomicIntegerFieldUpdater<BankAccount> atomicIntegerFieldUpdater =
      AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

  public void transfer(BankAccount bankAccount) {
    atomicIntegerFieldUpdater.incrementAndGet(bankAccount);
  }
}
