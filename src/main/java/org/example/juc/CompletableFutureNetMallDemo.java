package org.example.juc;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-01 13:36
 */
public class CompletableFutureNetMallDemo {

  private static final List<NetMall> mallList =
      Arrays.asList(
          new NetMall("jd"),
          new NetMall("pdd"),
          new NetMall("tmall"),
          new NetMall("dangdang"),
          new NetMall("taobao"));

  public static List<String> calcPriceStepByStep(List<NetMall> netMalls, String productName) {
    return netMalls.stream()
        .map(
            netMall ->
                String.format(
                    productName + "%s in %s price is %.2f",
                    productName,
                    netMall.getMallName(),
                    netMall.calcPrice(productName)))
        .collect(Collectors.toList());
  }

  public static List<String> calcPriceAsync(List<NetMall> netMalls, String productName) {
    return netMalls.stream()
        .map(
            netMall ->
                CompletableFuture.supplyAsync(
                    () ->
                        String.format(
                            productName + "%s in %s price is %.2f",
                            productName,
                            netMall.getMallName(),
                            netMall.calcPrice(productName))))
        .collect(Collectors.toList())
        .stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
  }

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    List<String> list = calcPriceStepByStep(mallList, "mysql");
    list.forEach(System.out::println);
    long endTime = System.currentTimeMillis();
    System.out.println("---costTime:" + (endTime - startTime));
    System.out.println();
    long startTime1 = System.currentTimeMillis();
    List<String> list1 = calcPriceAsync(mallList, "mysql");
    list1.forEach(System.out::println);
    long endTime1 = System.currentTimeMillis();
    System.out.println("---costTime:" + (endTime1 - startTime1));
  }

  @RequiredArgsConstructor
  static class NetMall {

    @Getter private final String mallName;

    public double calcPrice(String productName) {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
  }
}
