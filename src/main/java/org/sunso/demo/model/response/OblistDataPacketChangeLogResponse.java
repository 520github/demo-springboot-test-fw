package org.sunso.demo.model.response;

import lombok.Data;

/**
 * OblistDataPacketInfoChangeLog 表结构对应的Bean类
 */
@Data
public class OblistDataPacketChangeLogResponse  {
    /**
     * 自增序列主键
     */
    private Long id;

    /**
     * 数据环境，prod:正式包，test:试用包
     */
    private String dataEnv;

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
     * 版本号, V1、V2、V3
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
     * 数据包根路径
     */
    private String dataPkPathMd5;

    /**
     * 变更之前，数据包总大小，单位字节
     */
    private Long beforeDataPkSize;

    /**
     * 变更之前，数据包总数据条数
     */
    private Long beforeDataPkCount;

    /**
     * 变更之前，数据包总大小，单位字节
     */
    private Long afterDataPkSize;

    /**
     * 变更之前，数据包总数据条数
     */
    private Long afterDataPkCount;


    /**
     * 0:禁用， 1:启用
     */
    private String status;


    /**
     * 备注说明，比如失败的原因
     */
    private String remark;

}