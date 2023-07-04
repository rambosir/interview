package org.example.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-04 16:16
 *     <p>多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，要求只能初始化一次
 *     <p>以前，卖票， 多对一,多个线程操作同一个票资源类，加synchronized 重，正确
 *     <p>多对一,多个线程操作同一个系统类，不加synchronized, 微创， 只对某个字段进行原子操作保护，轻，正确
 */
public class AtomicReferenceFieldUpdaterDemo {

  public static void main(String[] args) {
    MyVar myVar = new MyVar();
    for (int i = 0; i < 5; i++) {
      new Thread(
              () -> {
                myVar.init(myVar);
              },
              String.valueOf(i))
          .start();
    }
  }
}

class MyVar {
  public volatile Boolean isInit = Boolean.FALSE;

  static final AtomicReferenceFieldUpdater<MyVar, Boolean> fieldUpdater =
      AtomicReferenceFieldUpdater.newUpdater(MyVar.class, Boolean.class, "isInit");

  public void init(MyVar myVar) {
    if (fieldUpdater.compareAndSet(myVar, Boolean.FALSE, Boolean.TRUE)) {
      System.out.println(Thread.currentThread().getName() + "\t" + "----start init");
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println(Thread.currentThread().getName() + "\t" + "----end init");
    } else {
      System.out.println(Thread.currentThread().getName() + "\t" + "----失败，其他线程正在处理");
    }
  }
}
