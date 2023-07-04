package org.example.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @program interview
 * @author Ben
 * @date 2023-06-15 15:32
 */
public class AtomicIntegerArrayDemo {

  public static void main(String[] args) {

    AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[4]);
    AtomicIntegerArray atomicIntegerArray1 = new AtomicIntegerArray(5);
    AtomicIntegerArray atomicIntegerArray2 = new AtomicIntegerArray(new int[] {1, 2, 3, 4});

    for (int i1 = 0; i1 < atomicIntegerArray.length(); i1++) {
      System.out.println(atomicIntegerArray.get(i1));
    }

    int tmpInt = atomicIntegerArray.getAndSet(0, 123);
    System.out.println(tmpInt + "\t" + atomicIntegerArray.get(0));
    System.out.println(atomicIntegerArray.get(0));
    atomicIntegerArray.getAndIncrement(1);
    atomicIntegerArray.getAndIncrement(1);
    tmpInt = atomicIntegerArray.getAndIncrement(1);
    System.out.println(tmpInt + "\t" + atomicIntegerArray.get(1));
  }
}
