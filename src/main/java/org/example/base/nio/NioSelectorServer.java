package org.example.base.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-07 12:24
 */
public class NioSelectorServer {

  public static void main(String[] args) throws IOException {
    // 创建NIO ServerSocketChannel，与BiO的ServerSocket类似
    try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
      serverSocket.socket().bind(new InetSocketAddress(9001));
      // 设置ServerSocketChannel为非阻塞
      serverSocket.configureBlocking(false);
      // 打开Selector处理Channel，即创建epoll
      try (Selector selector = Selector.open()) {
        // 把ServerSocketChannel注册到Selector上，并且selector对客户端accept连接操作感兴趣
        SelectionKey selectionKey = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动成功");
        while (true) {
          // 阻塞等待需要处理的事件发生 已注册事件发生后，会执行后面逻辑
          selector.select();

          // 获取selector中注册的全部事件的SelectionKey实例
          Set<SelectionKey> selectionKeys = selector.selectedKeys();
          Iterator<SelectionKey> iterator = selectionKeys.iterator();

          // 遍历SelectionKey对事件进行处理
          while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            // 如果是OP_ACCEPT事件，则进行连接获取和事件注册
            if (key.isAcceptable()) {
              ServerSocketChannel channel = (ServerSocketChannel) key.channel();
              SocketChannel socketChannel = channel.accept();
              socketChannel.configureBlocking(false);
              // 这里只注册了读事件，如果需要给客户端发送数据可以注册写事件
              socketChannel.register(selector, SelectionKey.OP_READ);
              System.out.println("客户端连接成功");
            } else if (key.isReadable()) {
              // 如果是OP_READ事件，则进行读取和打印
              SocketChannel socketChannel = (SocketChannel) key.channel();
              ByteBuffer byteBuffer = ByteBuffer.allocate(128);
              int len = socketChannel.read(byteBuffer);
              if (len > 0) {
                System.out.println(
                    Thread.currentThread().getName() + " 接收到消息：" + new String(byteBuffer.array()));
              } else if (len == -1) {
                System.out.println("客户端断开连接");
                socketChannel.close();
              }
            }
            // 从事件集合里删除本次处理的key，防止下次select重复处理
            iterator.remove();
          }
        }
      }
    }
  }
}
