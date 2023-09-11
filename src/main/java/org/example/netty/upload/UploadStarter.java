package org.example.netty.upload;

import org.example.netty.upload.server.UploadServer;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-08 11:49
 */
public class UploadStarter {


  
  // 请求上传 -> 是否可上传 -> 上传（客户端）-> 将客户端数据写入本地磁盘
  public static void main(String[] args) {
    try {
      new UploadServer(9001).run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
