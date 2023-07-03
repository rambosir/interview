package org.example.juc.future;

import java.util.concurrent.*;
import org.junit.Test;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-01 13:36
 */
public class CompletableFutureApiDemo {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {}

  /**
   * @throws InterruptedException
   */
  @Test
  public void m6() throws InterruptedException {
    Integer result =
        CompletableFuture.supplyAsync(() -> 10)
            .thenCompose(rs -> CompletableFuture.supplyAsync(() -> rs + 20))
            .join();
    System.out.println(result);

    Integer result1 = CompletableFuture.supplyAsync(() -> 10).thenApply(rs -> rs + 20).join();
    System.out.println(result1);
  }
  /**
   * 合并结果集
   *
   * @throws InterruptedException
   */
  @Test
  public void m5() throws InterruptedException {

    Integer result =
        CompletableFuture.supplyAsync(
                () -> {
                  System.out.println("第一次计算：" + 10);
                  return 10;
                })
            .thenCombine(
                CompletableFuture.supplyAsync(
                    () -> {
                      try {
                        TimeUnit.SECONDS.sleep(5);
                      } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                      }
                      System.out.println("第二次计算：" + 20);
                      return 20;
                    }),
                (r1, r2) -> {
                  System.out.println("汇总计算");
                  return r1 + r2;
                })
            .thenCombine(CompletableFuture.supplyAsync(() -> 30), (r3, r4) -> r3 + r4)
            .join();
    System.out.println("结果：" + result);
  }

  /**
   * applyToEither 计算速度进行选用，谁先结束用谁
   *
   * @throws InterruptedException
   */
  @Test
  public void m4() throws InterruptedException {
    Integer result =
        CompletableFuture.supplyAsync(
                () -> {
                  try {
                    TimeUnit.SECONDS.sleep(5);
                  } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                  }
                  return 1;
                })
            .applyToEither(
                CompletableFuture.supplyAsync(
                    () -> {
                      try {
                        TimeUnit.SECONDS.sleep(1);
                      } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                      }
                      return 2;
                    }),
                r -> r)
            .join();
    System.out.println(result);
  }

  @Test
  public void m1() {

    CompletableFuture.supplyAsync(() -> 1)
        .thenApply(f -> f + 2)
        .thenApply(f -> f + 3)
        .thenAccept(System.out::println);

    System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {}).join());

    System.out.println(
        CompletableFuture.supplyAsync(() -> "resultA").thenAccept(resultA -> {}).join());

    System.out.println(
        CompletableFuture.supplyAsync(() -> "resultA")
            .thenApply(resultA -> resultA + " resultB")
            .join());
  }

  @Test
  public void m2() {
    ThreadPoolExecutor executor =
        new ThreadPoolExecutor(
            1,
            3,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50),
            Executors.defaultThreadFactory());

    Integer result =
        CompletableFuture.supplyAsync(() -> 1, executor)
            .handle((f, e) -> f + 2)
            .handle((f, e) -> f + 3)
            .handle((f, e) -> f + 4)
            .whenComplete(
                (v, e) -> {
                  if (e == null) {
                    System.out.println("---result=" + v);
                  }
                })
            .exceptionally(e -> null)
            .join();
    System.out.println(result);
    executor.shutdown();
  }

  @Test
  public void m3() throws InterruptedException, ExecutionException {
    ThreadPoolExecutor executor =
        new ThreadPoolExecutor(
            1,
            3,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50),
            Executors.defaultThreadFactory());
    CompletableFuture<Integer> future =
        CompletableFuture.supplyAsync(
            () -> {
              try {
                TimeUnit.SECONDS.sleep(1);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
              return 1;
            },
            executor);
    //    System.out.println(future.get());
    //    System.out.println(future.get(2L, TimeUnit.SECONDS));
    //    System.out.println(future.getNow(999));
    // 打断是否成功，打断失败返回计算结果，打断成功，返回complete参数值
    System.out.println(future.complete(-44) + "\t" + future.get());

    executor.shutdown();
  }
}
