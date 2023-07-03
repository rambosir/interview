package org.example.juc.future;

import java.util.concurrent.*;
import org.junit.Test;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-01 12:18
 */
public class CompletableFutureDemo {

  @Test
  public void demo1() throws InterruptedException, ExecutionException {
    ThreadPoolExecutor executor =
        new ThreadPoolExecutor(
            1,
            3,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50),
            Executors.defaultThreadFactory());

    CompletableFuture<Void> future1 =
        CompletableFuture.runAsync(
            () -> System.out.println(Thread.currentThread().getName() + "---come in"));
    System.out.println(future1.get());

    CompletableFuture<Void> future2 =
        CompletableFuture.runAsync(
            (() -> System.out.println(Thread.currentThread().getName() + "---come in")), executor);
    System.out.println(future2.get());

    CompletableFuture<Integer> future3 =
        CompletableFuture.supplyAsync(
            () -> {
              System.out.println(Thread.currentThread().getName() + "---come in");
              return 11;
            });
    System.out.println(future3.get());

    CompletableFuture<Integer> future4 =
        CompletableFuture.supplyAsync(
            () -> {
              System.out.println(Thread.currentThread().getName() + "---come in");
              return 12;
            },
            executor);
    System.out.println(future4.get());

    executor.shutdown();
  }

  @Test
  public void demo2() throws ExecutionException, InterruptedException {
    ThreadPoolExecutor executor =
        new ThreadPoolExecutor(
            1,
            3,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50),
            Executors.defaultThreadFactory());

    CompletableFuture<Integer> future3 =
        CompletableFuture.supplyAsync(
                () -> {
                  try {
                    TimeUnit.SECONDS.sleep(2);
                  } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                  }
                  return 1;
                })
            .thenApply(result -> result + 2)
            .whenComplete(
                (v, e) -> {
                  if (e == null) {
                    System.out.println("---result=" + v);
                  }
                })
            .exceptionally(
                e -> {
                  e.printStackTrace();
                  return null;
                });

    System.out.println("--- main over");
    TimeUnit.SECONDS.sleep(3);
    

    executor.shutdown();
  }

  @Test
  public void demo3() throws InterruptedException, ExecutionException {

    Integer s =
        CompletableFuture.supplyAsync(
                () -> {
                  try {
                    TimeUnit.SECONDS.sleep(3);
                  } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                  }
                  return 1;
                })
            .whenComplete(
                (r, e) -> {
                  if (e == null) {
                    System.out.println("result:" + r);
                  }
                })
            .exceptionally(
                e -> {
                  e.printStackTrace();
                  return null;
                })
            .join();

    System.out.println("---main over");
    TimeUnit.SECONDS.sleep(1);
  }
}
