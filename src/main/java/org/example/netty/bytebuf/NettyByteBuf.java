package org.example.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-11 14:38
 */
public class NettyByteBuf {

  public static void main(String[] args) {
    ByteBuf byteBuf = Unpooled.buffer(10);
    System.out.println("byteBuf=" + byteBuf);
    for (int i = 0; i < 8; i++) {
      byteBuf.writeByte(i);
    }
    System.out.println("byteBuf=" + byteBuf);

    for (int i = 0; i < 5; i++) {
      System.out.println(byteBuf.getByte(i));
    }
    System.out.println("byteBuf=" + byteBuf);

    for (int i = 0; i < 5; i++) {
      System.out.println(byteBuf.readByte());
    }
    System.out.println("byteBuf=" + byteBuf);
  }
}
