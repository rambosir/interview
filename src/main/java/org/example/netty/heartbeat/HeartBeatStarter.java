package org.example.netty.heartbeat;

import org.example.netty.heartbeat.server.HeartBeatServer;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-08 11:49
 */
public class HeartBeatStarter {

  public static void main(String[] args) {
    try {
      new HeartBeatServer(9000).run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
