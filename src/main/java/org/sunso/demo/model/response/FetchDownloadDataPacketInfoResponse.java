package org.sunso.demo.model.response;

import lombok.Data;
import org.sunso.demo.util.FileUtils;

import java.util.List;

/**
 * @author sunso520
 * @Title:FetchDownloadDataPacketInfoResponse
 * @Description: <br>
 * @Created on 2025/2/11 15:27
 */
@Data
public class FetchDownloadDataPacketInfoResponse {
    //private DownloadDataPackageInfo data;
    private DownloadDataPackageTotal total;
    private List<DownloadDataPackageFileItem> fileList;

    @Data
    public static class DownloadDataPackageInfo {
        private DownloadDataPackageTotal total;
        private List<DownloadDataPackageFileItem> fileList;
    }

    // Inner class Total
    @Data
    public static class DownloadDataPackageTotal {
        private String dataEnv; // 数据环境
        private String dataVersion; // 数据包版本号
        private String listDataType; // 数据业务类型
        private String listType; // 数据生成类型
        private String dataDt; // 数据日期
        private String splitType; // 数据拆分方式
        private String encryptType; // 数据加密方式
        private String dataPkType; // 数据包类型
        private int fileNum; // 文件数
    }

    // Inner class FileItem
    @Data
    public static class DownloadDataPackageFileItem {
        private String dataPkName;// 数据包名称
        private String dataPkPathMd5; // 数据包路径md5值
        private Long dataPkSize; // 数据包文件大小，单位字节
        private Integer dataPkCount; // 数据包数据条数
        private String dataPkMd5; // 数据包md5值
        private Integer dataPkSort; // 数据包顺序
        private String dataRange; // 数据包数据范围说明


        public long fetchDataPkSize() {
            return FileUtils.getFileSize(fetchFilePath());
        }

        public String fetchDataPkMd5() {
            return FileUtils.calculateMD5CatchException(fetchFilePath());
        }

        private String fetchFilePath() {
            return "/Users/sunso520/home/tmp/" + dataPkName;
        }
    }
}
