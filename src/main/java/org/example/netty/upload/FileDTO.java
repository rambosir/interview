package org.example.netty.upload;

import lombok.Data;

/**
 * @program interview
 * @author Ben
 * @date 2023-09-11 16:00
 */
@Data
public class FileDTO {
    
    private String fileName;
    
    private Integer command;
    
    private byte[] bytes;
}
