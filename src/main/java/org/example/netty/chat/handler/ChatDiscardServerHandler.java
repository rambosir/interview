package org.example.netty.chat.handler;

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
public class ChatDiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

  static Set<Channel> channels = new HashSet<>();

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    // 通知其他人 我上线了
    channels.forEach(
        channel -> {
          channel.writeAndFlush("客户端：" + ctx.channel().remoteAddress() + " 上线了");
        });
    channels.add(ctx.channel());
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    // 分发给聊天室内的所有客户端
    String message = (String) msg;
    channels.forEach(
        channel -> {
          if (ctx.channel() == channel) {
            channel.writeAndFlush("[自己]：" + message);
          } else {
            channel.writeAndFlush("客户端：" + channel.remoteAddress() + "：" + message);
          }
        });
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
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    // 通知其他客户端，我下线了
    channels.remove(ctx.channel());
    channels.forEach(
        channel -> {
          channel.writeAndFlush("客户端：" + ctx.channel().remoteAddress() + " 下线了");
        });
  }
}
