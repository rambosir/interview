package org.example.base.pattern;
/**
 * @program interview
 * @author Ben
 * @date 2023-07-03 22:30
 */
public class SingletonDemo {

  private SingletonDemo() {}

  private static final class SingletonHandler {
    private static final SingletonDemo instance = new SingletonDemo();
  }

  public static SingletonDemo getInstance() {
    return SingletonHandler.instance;
  }

  public static void main(String[] args) {
    SingletonDemo singletonDemo = SingletonDemo.getInstance();
  }
}
