package org.example.common;
/**
 * @program interview
 * @author Ben
 * @date 2023-07-06 13:36
 */
public class Ticket {

  int number = 50;

  public synchronized void saleTicket() {
    if (number > 0) {
      System.out.println(Thread.currentThread().getName() + "\t" + "---卖出第:" + (number--));
    } else {
      System.out.println("---卖光了");
    }
  }
}
