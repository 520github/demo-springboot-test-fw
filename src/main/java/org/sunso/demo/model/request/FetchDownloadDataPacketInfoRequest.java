package org.sunso.demo.model.request;

import lombok.Data;

/**
 * @author sunso520
 * @Title:FetchDownloadDataPacketInfoRequest
 * @Description: <br>
 * @Created on 2025/2/11 15:27
 */
@Data
public class FetchDownloadDataPacketInfoRequest {
    private String organKey;
    private String dataEnv; // 数据环境,
    private String dataVersion; // 数据包版本号
    private String listDataType; // 数据业务类型
    private String listType; // 数据生成类型
    private String dataDt; // 数据日期
    private String splitType; // 数据拆分方式，默认为count，按条数拆分
    private String encryptType; // 数据加密方式，默认为aes
    private String dataPkType; // 数据包类型,默认为zip
}
