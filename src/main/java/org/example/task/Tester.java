package org.example.task;

import java.io.IOException;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-28 14:58
 */
public class Tester {

  public static void main(String[] args) throws IOException, InterruptedException {
    new LoggingRunnable(new TransactionRunnable(new CodingTask())).run();
  }
}
