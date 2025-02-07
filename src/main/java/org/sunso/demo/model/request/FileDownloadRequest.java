package org.sunso.demo.model.request;

import lombok.Data;

/**
 * @author sunso520
 * @Title:FileDownloadRequest
 * @Description: <br>
 * @Created on 2025/1/22 14:49
 */
@Data
public class FileDownloadRequest {
    private String version;
    private String fileMd5;
}
