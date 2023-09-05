package org.example.base.juc.jmm;

/**
 * @program interview
 * @author Ben
 * @date 2023-06-03 21:58
 *     <p>使用：当读多于写，结合使用内部锁和volatile变量减少同步的开销
 *     <p>理由：利用volatile保证读取操作的可见性，利用synchronized保证符合操作的原子性
 */
public class UseVolatileDemo2 {

  public class Counter {

    private volatile int value;

    public int getValue() {
      // 利用volatile保证读取操作的可见性
      return value;
    }

    public synchronized int increment() {
      // 利用synchronized保证复合操作的原子性
      return value++;
    }
  }
}
