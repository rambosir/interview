package org.example.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import lombok.SneakyThrows;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-15 16:34
 */
public class JavaNio {

  @SneakyThrows
  public static void copyFileByChannel(File source, File dest) {
    try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
        FileChannel targetChannel = new FileOutputStream(dest).getChannel()) {
      for (long count = sourceChannel.size(); count > 0; ) {
        long transferred = sourceChannel.transferTo(sourceChannel.position(), count, targetChannel);
        sourceChannel.position(sourceChannel.position() + transferred);
        count -= transferred;
      }
    }
  }
}
