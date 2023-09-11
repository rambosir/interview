package org.example.netty.heartbeat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-08 12:18
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

  int readTimeout = 0;

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    IdleStateEvent event = (IdleStateEvent) evt;
    if (event.state() == IdleState.READER_IDLE) {
      readTimeout++;
    }
    if (readTimeout >= 3) {
      System.out.println("超时超过3次，断开连接");
      ctx.close();
    }

    //    System.out.println("触发了" + idleStateEvent.state() + "事件");
  }
}
