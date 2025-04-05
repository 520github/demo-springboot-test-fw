package org.sunso.demo.model.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sunso520
 * @Title:DownloadDataPacketRequest
 * @Description: <br>
 * @Created on 2025/2/11 16:08
 */
@Data
public class DownloadDataPacketRequest extends FetchDownloadDataPacketInfoRequest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String range;
    private String dataPkPathMd5;//需要下载的文件路径md5值
}
