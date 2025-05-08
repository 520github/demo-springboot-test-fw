package org.sunso.demo.parser.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.Map;

/**
 * @author sunso520
 * @Title:Metadata
 * @Description: <br>
 * @Created on 2025/4/7 10:41
 */
@Data
public class Metadata {
    private String tableName;
    private String primaryKey;
    private EncryptConfig encryptConfig;
    private Map<String, Field> fields;

    public static  Metadata getMetadata() {
        String data = FileUtil.readString("/Users/sunso520/home/tmp/metadata.json", "UTF-8");
        JSONObject jsonObject = JSONUtil.parseObj(data);
        Metadata metadata = jsonObject.toBean(Metadata.class);
        return metadata;
    }


    public static void main(String[] args) {
        String data = FileUtil.readString("/Users/sunso520/home/tmp/metadata.json", "UTF-8");
        System.out.println(data);
        JSONObject jsonObject = JSONUtil.parseObj(data);
        Metadata metadata = jsonObject.toBean(Metadata.class);
        System.out.println(metadata);
    }
}
