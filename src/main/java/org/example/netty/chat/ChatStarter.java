package org.example.netty.chat;

import org.example.netty.chat.server.ChatDiscardServer;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-08 11:49
 */
public class ChatStarter {

  public static void main(String[] args) {
    try {
      new ChatDiscardServer(9000).run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
