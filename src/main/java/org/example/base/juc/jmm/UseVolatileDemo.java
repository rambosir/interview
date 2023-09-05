package org.example.base.juc.jmm;

import java.util.concurrent.TimeUnit;

/**
 * @program interview
 * @author Ben
 * @date 2023-06-03 21:58
 *     <p>使用：作为一个不二状态标志，用于指示发生了一个重要的一次性时间，例如完成初始化或任务结束
 *     <p>理由：状态标志并不依赖于程序内任何其他状态，且通常只有一种状态转换
 *     <p>例子：判断业务是否结束
 */
public class UseVolatileDemo {

  private static volatile boolean flag = true;

  public static void main(String[] args) {
    new Thread(
            () -> {
              while (flag) {
                System.out.println("---come in");
              }
            },
            "t1")
        .start();

    try {
      TimeUnit.SECONDS.sleep(2L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    new Thread(
            () -> {
              flag = false;
            },
            "t2")
        .start();
  }
}
