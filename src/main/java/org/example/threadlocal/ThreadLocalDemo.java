package org.example.threadlocal;

import org.example.common.House;
import org.example.common.Ticket;

import java.util.Random;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-06 13:36
 */
public class ThreadLocalDemo {

  public static void main(String[] args) {
    // saleTicket();

    saleHouse();
  }

  private static void saleHouse() {
    House house = new House();
    for (int i = 0; i < 3; i++) {
      new Thread(
              () -> {
                try {
                  for (int i1 = 1; i1 <= new Random().nextInt(9); i1++) {
                    house.sale();
                  }
                    System.out.println(
                            Thread.currentThread().getName() + "\t" + "卖出：" + house.getThreadLocal().get());
                } finally {
                    house.getThreadLocal().remove();
                }
              },
              String.valueOf(i))
          .start();
    }
    System.out.println(
        Thread.currentThread().getName() + "\t" + "卖出：" + house.getThreadLocal().get());
  }

  private static void saleTicket() {
    Ticket ticket = new Ticket();
    for (int i = 0; i < 3; i++) {
      new Thread(
              () -> {
                for (int i1 = 1; i1 <= 20; i1++) {
                  ticket.saleTicket();
                }
              },
              String.valueOf(i))
          .start();
    }
  }
}
