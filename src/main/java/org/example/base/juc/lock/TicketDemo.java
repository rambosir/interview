package org.example.base.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-02 13:41
 */
public class TicketDemo {

  public static void main(String[] args) {
    Ticket ticket = new Ticket();

    new Thread(
            () -> {
              for (int i = 1; i <= 55; i++) {
                ticket.sale();
              }
            },
            "a")
        .start();

    new Thread(
            () -> {
              for (int i = 1; i <= 55; i++) {
                ticket.sale();
              }
            },
            "b")
        .start();

    new Thread(
            () -> {
              for (int i = 1; i <= 55; i++) {
                ticket.sale();
              }
            },
            "c")
        .start();

    new Thread(
            () -> {
              for (int i = 1; i <= 55; i++) {
                ticket.sale();
              }
            },
            "d")
        .start();

    new Thread(
            () -> {
              for (int i = 1; i <= 55; i++) {
                ticket.sale();
              }
            },
            "e")
        .start();
  }
}

class Ticket {
  private int number = 50;

  private final Lock lock = new ReentrantLock();

  public void sale() {
    lock.lock();
    try {

      if (number > 0) {
        System.out.println(
            Thread.currentThread().getName() + "\t卖出第：" + (number--) + "\t 还剩：" + number);
      }

    } finally {
      lock.unlock();
    }
  }
}
