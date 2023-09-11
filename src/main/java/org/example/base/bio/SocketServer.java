package org.example.base.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-07 10:59
 */
public class SocketServer {

  // 1.0 服务端在处理完第一个客户端的所有事件之前，无法为其他客户端提供服务。
  // 2.0 会产生大量空闲线程，占用服务器资源，线程池同理
  public static void main(String[] args) throws Exception {
    try (ServerSocket serverSocket = new ServerSocket(9001)) {
      while (true) {
        System.out.println("等待连接...");
        // 阻塞方法
        Socket clientSocket = serverSocket.accept();
        System.out.println("有客户端连接了..");

        //        handler(clientSocket);

        Thread thread =
            new Thread(
                () -> {
                  try {
                    handler(clientSocket);
                  } catch (IOException e) {
                    System.out.println(e.getMessage());
                  }
                });
        thread.start();
      }
    }
  }

  private static void handler(Socket clientSocket) throws IOException {
    byte[] bytes = new byte[1024];
    System.out.println("准备read...");
    // 接收客户端数据，阻塞方法，没有数据可读时就阻塞
    int read = clientSocket.getInputStream().read(bytes);
    System.out.println("read完毕..");
    if (read != -1) {
      System.out.println("接收到客户端的数据：" + new String(bytes, 0, read));
    }
  }
}
