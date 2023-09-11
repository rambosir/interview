package org.example.netty.base;

import org.example.netty.base.server.DiscardServer;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-08 11:49
 */
public class Starter {

  public static void main(String[] args) {
    try {
      new DiscardServer(9001).run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
