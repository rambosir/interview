package org.example.juc.jmm;

import java.util.concurrent.TimeUnit;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-03 20:36
 */
public class VolatileNoAtomicDemo {

  public static void main(String[] args) {
    MyNumber myNumber = new MyNumber();
    for (int i = 0; i < 10; i++) {
      new Thread(
              () -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                  myNumber.addPlusPlus();
                }
              },
              String.valueOf(i))
          .start();
    }

    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.println(Thread.currentThread().getName() + "\t" + myNumber.number);
  }
}

class MyNumber {

  volatile int number = 0;

  public void addPlusPlus() {
    number++;
  }
}
