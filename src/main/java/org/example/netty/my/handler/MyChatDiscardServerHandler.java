package org.example.netty.my.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.HashSet;
import java.util.Set;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-08 12:18
 */
public class MyChatDiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

  static Set<Channel> channels = new HashSet<>();

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {}

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
    // Close the connection when an exception is raised.
    cause.printStackTrace();
    ctx.close();
  }

  /**
   * channel处于不活跃时调用
   *
   * @param ctx
   * @throws Exception
   */
  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {}
}
