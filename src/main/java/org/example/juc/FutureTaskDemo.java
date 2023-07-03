package org.example.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-01 12:00
 */
public class FutureTaskDemo {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {

    FutureTask<Integer> integerFutureTask =
        new FutureTask<>(
            () -> {
              System.out.println(Thread.currentThread().getName() + "\t" + "-----come in");
              TimeUnit.SECONDS.sleep(5);
              return 1024;
            });
    new Thread(integerFutureTask, "t1").start();

    // 不见不散，不管是否计算完成都阻塞等待结果出来再运行
    // System.out.println(integerFutureTask.get());

    // 过时不候
    // System.out.println(integerFutureTask.get(2L, TimeUnit.SECONDS));

    // 不要用阻塞，尽量用轮询替代
    while (true) {
      if (integerFutureTask.isDone()) {
        System.out.println("result:" + integerFutureTask.get());
        break;
      } else {
        System.out.println("还在计算中");
      }
    }

    System.out.println("---继续工作");
  }
}
