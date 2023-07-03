package org.example.juc.lock;
/**
 * @program interview
 * @author Ben
 * @date 2023-07-02 13:11
 */
public class LockByteCodeDemo {

  final Object object = new Object();

  public void m1() {
    synchronized (object) {
      System.out.println("-----hello sync");
      throw new RuntimeException("----ex");
    }
  }

  public static void main(String[] args) {}

  public synchronized void m2() {}

  public static synchronized void m3() {}
}
