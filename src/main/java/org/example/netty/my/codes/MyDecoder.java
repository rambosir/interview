package org.example.netty.my.codes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-11 14:23
 */
public class MyDecoder extends ByteToMessageDecoder {

  // 数据长度+数据
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    if (in.readableBytes() < 4) {
      return;
    }

    // 数据长度 4 + 10000 9999
    int len = in.readInt();
    if (in.readableBytes() < len) {
      in.resetReaderIndex();
      return;
    }
    byte[] data = new byte[len];
    in.readBytes(data);
    System.out.println(new String(data));
    in.markReaderIndex();
  }
}
