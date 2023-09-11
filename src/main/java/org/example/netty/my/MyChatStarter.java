package org.example.netty.my;

import org.example.netty.my.server.MyChatDiscardServer;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-08 11:49
 */
public class MyChatStarter {

  public static void main(String[] args) {
    try {
      new MyChatDiscardServer(9000).run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
