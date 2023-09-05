package org.example.base.juc.jmm;


import java.util.concurrent.TimeUnit;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-03 20:04
 */
public class VolatileSeeDemo {

  // 不加volatile，没有可见性
  // static boolean isStopped = true;
  // 加了volatile，保证可见性
  static volatile boolean isStopped = true;

  public static void main(String[] args) {
    new Thread( 
            () -> {
              System.out.println(Thread.currentThread().getName() + "------come in");
              while (isStopped) {
                Integer.valueOf(308);
              }
              System.out.println("-----t1 over");
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
              isStopped = false;
            },
            "t2")
        .start();
  }
}
