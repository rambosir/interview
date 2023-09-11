package org.example.netty.upload.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.netty.upload.FileDTO;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-08 12:18
 */
public class UploadServerHandler extends ChannelInboundHandlerAdapter { // (1)

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {}

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    if (msg instanceof FileDTO) {
      FileDTO fileDTO = (FileDTO) msg;
      Path filePath = Paths.get("d:/" + fileDTO.getFileName());
      if (fileDTO.getCommand() == 1) {
        // 创建文件
        if (Files.notExists(filePath)) {
          try {
            Files.createFile(filePath);
          } catch (IOException e) {
            System.out.println("创建文件失败");
          }
        }

      } else if (fileDTO.getCommand() == 2) {
        // 写入文件
        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
          outputStream.write(fileDTO.getBytes());
          outputStream.flush();
        } catch (Exception exception) {
          System.out.println("写入文件失败");
        }
      }
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
    // Close the connection when an exception is raised.
    cause.printStackTrace();
    ctx.close();
  }
}
