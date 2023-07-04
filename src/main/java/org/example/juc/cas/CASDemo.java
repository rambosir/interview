package org.example.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-04 11:13
 */
public class CASDemo {

  public static void main(String[] args) {

    AtomicInteger atomicInteger = new AtomicInteger(5);
    System.out.println(atomicInteger.get());
    System.out.println(atomicInteger.compareAndSet(5, 308) + "\t" + atomicInteger.get());
    System.out.println(atomicInteger.compareAndSet(5, 333) + "\t" + atomicInteger.get());
  }
}
