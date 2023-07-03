package org.example.pattern;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-15 16:50
 */
public class Singleton {

  private static volatile Singleton singleton;

  private Singleton() {}

  public static Singleton getInstance() {
    if (singleton == null) {
      // 多线程并发创建对象时，加锁保证只有一个线程能创建对象
      synchronized (Singleton.class) {
        if (singleton == null) {
          singleton = new Singleton();
        }
      }
    }
    return singleton;
  }

  public static void main(String[] args) {

    for (int i = 0; i < 100; i++) {
      new Thread(
              () -> {
                Singleton singleton = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + "\t singleton=" + singleton);
              },
              String.valueOf(i))
          .start();
    }
  }
}
