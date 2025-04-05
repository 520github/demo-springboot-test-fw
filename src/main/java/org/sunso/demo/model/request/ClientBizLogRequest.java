package org.sunso.demo.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ClientBizLogRequest {

    /**
     * 自增序列主键
     */
    private Long id;

    /**
     * 客户端版本号
     */
    private String clientVersion;

    /**
     * 客户端账号
     */
    private String clientAccount;

    /**
     * 客户端账号相关数据
     */
    private String clientAccountData;

    /**
     * 数据环境，prod:正式包，test:试用包
     */
    private String dataEnv;

    /**
     * 业务模块, 如：数据下载、数据解析入库
     */
    private String bizModule;

    /**
     * 数据日期
     */
    private String dataDt;

    /**
     * 数据业务类型，DD、RDD、EDD
     */
    private String listDataType;

    /**
     * 数据生成类型，F:全量, D:每天增量
     */
    private String listType;

    /**
     * 数据包版本号, V1、V2、V3
     */
    private String dataVersion;

    /**
     * 数据包类型，ZIP、RAR
     */
    private String dataPkType;

    /**
     * 数据拆分方式， 不拆分:all, 按条数:count, 按年:year
     */
    private String splitType;

    /**
     * 数据包加密方式， NOT、AES
     */
    private String encryptType;

    /**
     * 数据包原始文件路径
     */
    private String dataOriginalPacketPath;

    /**
     * 数据包拆分文件路径
     */
    private String dataSplitPacketPath;

    /**
     * 数据包实际文件大小，单位字节
     */
    private Long dataPacketActualSize;

    /**
     * 数据包实际数据条数
     */
    private Integer dataPacketActualCount;

    /**
     * 数据包实际md5值，解析入库可不传
     */
    private String dataPacketActualMd5;

    /**
     * 处理结果，成功、失败、部分成功
     */
    private String resultStatus;

    /**
     * 处理方式，直接下载、断点续传、批量导入、逐条导入
     */
    private String handleType;

    /**
     * 业务处理的一些重要参数, json格式
     */
    private String bizParameter;

    /**
     * 业务处理结果详情信息
     */
    private String bizResult;

    /**
     * 业务处理开始时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bizStartTime;

    /**
     * 业务处理结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bizEndTime;

    /**
     * 业务处理耗时时间
     */
    private Long bizConsumeTime;

    /**
     * 重复处理次数
     */
    private Integer retryTimes;

    /**
     * 创建时间
     */
    private Date createdByDate;

    /**
     * 创建者
     */
    private String createdByName;

    /**
     * 修改时间
     */
    private Date lastUpdatedByDate;

    /**
     * 修改者
     */
    private String lastUpdatedByName;
}
