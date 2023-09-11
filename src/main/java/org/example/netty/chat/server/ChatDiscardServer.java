package org.example.netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.nio.charset.StandardCharsets;
import org.example.netty.chat.handler.ChatDiscardServerHandler;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-08 11:48
 */
public class ChatDiscardServer {

  private final int port;

  public ChatDiscardServer(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1); // (1) 线程池
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap(); // (2)
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class) // (3)
          .childHandler(
              new ChannelInitializer<SocketChannel>() { // (4)
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                  // ch.pipeline().addLast(new FixedLengthFrameDecoder(8));
                  //                  ch.pipeline()
                  //                      .addLast(
                  //                          new DelimiterBasedFrameDecoder(
                  //                              1024, Unpooled.copiedBuffer("_".getBytes())));
                  ch.pipeline().addLast("decoder", new StringDecoder(StandardCharsets.UTF_8));
                  ch.pipeline().addLast("encoder", new StringEncoder(StandardCharsets.UTF_8));
                  ch.pipeline().addLast(new ChatDiscardServerHandler());
                }
              })
          .option(ChannelOption.SO_BACKLOG, 128) // (5)
          .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

      System.out.println("启动成功");
      // Bind and start to accept incoming connections.
      ChannelFuture f = b.bind(port).sync(); // (7)

      // Wait until the server socket is closed.
      // In this example, this does not happen, but you can do that to gracefully
      // shut down your server.
      f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }
}
