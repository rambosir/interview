package org.example.netty.upload.codes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.example.netty.upload.FileDTO;

import java.util.List;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-11 14:23
 */
public class UploadDecoder extends ByteToMessageDecoder {

  // 请求上传
  // 创建文件
  // 将客户端数据写入本地磁盘
  // command 4 fileName 4

  // 数据长度+数据
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    if (in.readableBytes() < 8) {
      return;
    }

    // command 4
    int command = in.readInt();
    FileDTO fileDTO = new FileDTO();

    int fileNameLen = in.readInt();
    if (in.readableBytes() < fileNameLen) {
      in.resetReaderIndex();
      return;
    }

    byte[] data = new byte[fileNameLen];
    in.readBytes(data);
    String fileName = new String(data);
    fileDTO.setCommand(command);
    fileDTO.setFileName(fileName);

    if (command == 2) {
      int dataLen = in.readInt();
      if (in.readableBytes() < dataLen) {
        in.resetReaderIndex();
        return;
      }
      byte[] fileData = new byte[dataLen];
      in.readBytes(dataLen);
      fileDTO.setBytes(fileData);
    }
    in.markReaderIndex();
    out.add(fileDTO);
  }
}
