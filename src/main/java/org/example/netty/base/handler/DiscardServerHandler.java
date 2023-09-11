package org.example.netty.base.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.StandardCharsets;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-08 12:18
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("有客户端连接了");
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    // Discard the received data silently.
    try {
      // Do something with msg
      ByteBuf byteBuf = ((ByteBuf) msg);
      System.out.println(byteBuf.toString(StandardCharsets.UTF_8));
    } finally {
      ReferenceCountUtil.release(msg);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
    // Close the connection when an exception is raised.
    cause.printStackTrace();
    ctx.close();
  }
}
