package org.sunso.demo.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 客户端错误日志表
 */
@Data
public class ClientErrorLogRequest  {
    /**
     * 自增序列主键
     */
    private Long id;

    /**
     * 客户端account
     */
    private String clientAccount;

    /**
     * 客户端账号相关数据
     */
    private String clientAccountData;

    /**
     * 客户端错误日志id
     */
    private String clientErrorId;

    /**
     * 数据环境，prod:正式包，test:试用包
     */
    private String dataEnv;

    /**
     * 数据业务类型，DD、RDD、EDD
     */
    private String listDataType;

    /**
     * 版本号, V1、V2、V3
     */
    private String clientVersion;

    /**
     * 版本号, V1、V2、V3
     */
    private String dataVersion;

    /**
     * 业务模块, 如：数据下载、数据解析入库、版本更新、
     */
    private String bizModule;

    /**
     * 业务功能, 如：检查数据下载参数、拆分文件、删除下载临时目录
     */
    private String bizFunction;

    /**
     * 业务上下文参数值, json格式，超过长度自动做截取
     */
    private String bizParameter;

    /**
     * 错误类型, warn、error、exception
     */
    private String errorType;

    /**
     * 错误分类, 异常情况直接用异常类名
     */
    private String errorCategory;

    /**
     * 错误类
     */
    private String errorClass;

    /**
     * 错误方法
     */
    private String errorMethod;

    /**
     * 错误方法行数
     */
    private Integer errorMethodLine;

    /**
     * 错误信息,异常情况直接获取异常的堆栈信息，超过长度自动做截取
     */
    private String errorMessage;

    /**
     * 原始错误信息md5值
     */
    private String errorMessageMd5;

    /**
     * 本地ip
     */
    private String clientLocalIp;

    /**
     * 操作系统信息，json格式, ase加密
     */
    private String osInfo;

    /**
     * cpu信息，json格式
     */
    private String cpuInfo;

    /**
     * 内存信息，json格式
     */
    private String memoryInfo;

    /**
     * 磁盘信息，json格式
     */
    private String diskInfo;

    /**
     * jvm信息，json格式
     */
    private String jvmInfo;

    /**
     * 客户端创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date clientCreateDate;

    /**
     * 客户端修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date clientModifyDate;

    /**
     * 通知状态, init、success、fail、abandon
     */
    private String notifyStatus;

    /**
     * 通知方式, email、sms等
     */
    private String notifyType;

    /**
     * 通知对象 {"email":"","sms":""}
     */
    private String notifyObject;

    /**
     * 通知时间
     */
    private Date notifyDate;

    /**
     * 通知重试次数
     */
    private Integer notifyRetryNum;

    /**
     * 通知失败信息，超过长度自动做截取
     */
    private String notifyFailMessage;

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