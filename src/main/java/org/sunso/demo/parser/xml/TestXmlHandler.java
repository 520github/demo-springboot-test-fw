package org.sunso.demo.parser.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sunso.demo.parser.dto.XmlHandlerDTO;
import org.sunso.demo.parser.entity.GenericEntity;
import org.sunso.demo.parser.file.Field;
import org.sunso.demo.parser.util.AES;
import org.sunso.demo.parser.util.SqlBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunso520
 * @Title:TestXmlHandler
 * @Description: <br>
 * @Created on 2025/4/6 17:06
 */
public class TestXmlHandler extends DefaultHandler  {
    private XmlHandlerDTO xmlHandlerDTO;

    private static String recordTag = "DD_OBLIST_CATEGORY_CODE";//DD_OBLIST_CATEGORY_CODE DD_OBLIST
    private Map<String, Object> currentData;
    private String currentElement;
    private List<Map<String, Object>> batchRecords = new ArrayList<>();
    private long recordCount;
    private long totalCount;
    private long totalBytes;
    private long batchSize = 5000;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TestXmlHandler(XmlHandlerDTO xmlHandlerDTO) {
        this.xmlHandlerDTO = xmlHandlerDTO;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (recordTag.equals(qName)) {
//            currentEntity = new GenericEntity();
//            currentEntity.setObjId(attributes.getValue("objId"));
            currentData = new HashMap<>();
        }
        currentElement = qName;
        //System.out.println("uri:" + uri);
        //System.out.println("localName:" + localName);
        //System.out.println("qName:" + qName);
        //System.out.println("attributes:" + attributes);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentData != null && currentElement != null && !recordTag.equals(currentElement)) {
            String fieldValue = new String(ch, start, length);
//            String[] fieldInfo = metadata.get(currentElement).split(",");
//            String isEncrypted = fieldInfo[1];
//            String encryptionMethod = fieldInfo[2];
//            try {
//                if ("true".equals(isEncrypted)) {
//                    fieldValue = dataImportService.decrypt(fieldValue, encryptionMethod);
//                }
//            } catch (Exception e) {
//                throw new SAXException("Decryption error", e);
//            }
            //System.out.println(currentElement + ":" + fieldValue);
            currentData.put(currentElement, fieldValue);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (recordTag.equals(qName)) {
            //currentData.put("objId", currentEntity.getObjId());
            batchRecords.add(currentData);
            recordCount++;
            if (recordCount % batchSize == 0) {
                System.out.println("Processed " + recordCount + " records");
                long currentBytes = getTotalByteCount(batchRecords);
                System.out.println("Processed bytes: " + currentBytes);
                totalCount += recordCount;
                totalBytes += currentBytes;
                batchRecords.clear();
                recordCount = 0;
            }
            currentData = null;
        }
        currentElement = null;
    }

    @Override
    public void endDocument() throws SAXException {
        if (!batchRecords.isEmpty()) {
            try {
                System.out.println("Processed " + recordCount + " records");
                long currentBytes = getTotalByteCount(batchRecords);
                System.out.println("Processed bytes: " + currentBytes);
                totalCount += recordCount;
                totalBytes += currentBytes;
                System.out.println(batchRecords.get(0));
                System.out.println(buildSql());
                executeSql();
            } catch (Exception e) {
                throw new SAXException("Error processing remaining records", e);
            }
        }
        System.out.println("Processed  total records: " + totalCount + " ");
        System.out.println("Processed total bytes: " + totalBytes);
        System.out.println("Processed total bytes: " + totalBytes/1024 + " kB");
        System.out.println("Processed total bytes: " + totalBytes/1024/1024 + " MB");
        System.out.println("Processed total bytes: " + totalBytes/1024/1024/1024 + " GB");
    }

    private String buildSql() {
        //return SqlBuilder.buildMergeSql(recordTag, batchRecords.get(0), "id", "mysql");
        return SqlBuilder.buildInsertSqlField(xmlHandlerDTO.getMetadata().getTableName(), xmlHandlerDTO.getMetadata().getFields());
    }

    private Map<String, Object> getDataMap(Map<String, Object> xmlData) {
        Map<String, Object> dataMap = new HashMap<>();
        for(String fieldKey: xmlHandlerDTO.getMetadata().getFields().keySet()) {
            Field field = xmlHandlerDTO.getMetadata().getFields().get(fieldKey);
            String xmlKey = field.getXmlTag();
            if (StrUtil.isBlank(xmlKey)) {
                xmlKey = fieldKey;
            }
            Object xmlValue = xmlData.get(xmlKey);
            if (!"none".equals(field.getEncryptType()) && xmlValue != null) {
                try {
                    xmlValue = decrypt(xmlValue.toString(), field.getEncryptType(), xmlHandlerDTO.getMetadata().getEncryptConfig().getAesKey());
                }catch (Exception e) {

                }
            }
            dataMap.put(fieldKey, xmlValue);
        }
        return dataMap;
    }

    private void executeSql() {
        String sql = buildSql();
        Map<String, Object> data = getDataMap(batchRecords.get(0));
        Object[] params = new Object[data.size()];
        int index = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            params[index++] = entry.getValue();
        }
        if ("dataSource".equalsIgnoreCase("oracle")) {
            Object[] newParams = new Object[params.length * 2 - 1];
            System.arraycopy(params, 0, newParams, 0, params.length - 1);
            System.arraycopy(params, 1, newParams, params.length - 1, params.length - 1);
            newParams[newParams.length - 1] = params[0];
            params = newParams;
        }
        JdbcTemplate jdbcTemplate1 = SpringUtil.getBean(JdbcTemplate.class);
        jdbcTemplate1.update(sql, params);
        List<Map<String,Object>> dataList = jdbcTemplate1.queryForList("select * from DD_OBLIST_CATEGORY_CODE");
        System.out.println("searchDataList: " + dataList);
    }

    public static int getTotalByteCount(List<Map<String, Object>> batchRecords) {
        int totalByteCount = 0;

        for (Map<String, Object> record : batchRecords) {
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

//                // 计算键的字节数
//                totalByteCount += key.getBytes(StandardCharsets.UTF_8).length;

                // 计算值的字节数
                if (value != null) {
                    totalByteCount += value.toString().getBytes(StandardCharsets.UTF_8).length;
                }
            }
        }

        return totalByteCount;
    }

    public static String decrypt(String encryptedValue, String encryptionMethod, String encryptKey) throws Exception {
        switch (encryptionMethod.toLowerCase()) {
            case "base64":
                return new String(Base64.decodeBase64(encryptedValue), StandardCharsets.UTF_8);
            case "aes":
                return AES.decryptStr(encryptedValue, encryptKey);
            case "des":
                byte[] desKey = "01234567".getBytes(StandardCharsets.UTF_8);
                SecretKeySpec desSecretKeySpec = new SecretKeySpec(desKey, "DES");
                Cipher desCipher = Cipher.getInstance("DES");
                desCipher.init(Cipher.DECRYPT_MODE, desSecretKeySpec);
                byte[] desDecryptedBytes = desCipher.doFinal(Base64.decodeBase64(encryptedValue));
                return new String(desDecryptedBytes, StandardCharsets.UTF_8);
            default:
                return encryptedValue;
        }
    }

    public static String encrypt(String value, String encryptionMethod, String encryptKey) throws Exception {
        switch (encryptionMethod.toLowerCase()) {
            case "base64":
                return Base64.encodeBase64String(value.getBytes(StandardCharsets.UTF_8));
            case "aes":
                return AES.encrypt(value, encryptKey);
            case "des":
                byte[] desKey = "01234567".getBytes(StandardCharsets.UTF_8);
                SecretKeySpec desSecretKeySpec = new SecretKeySpec(desKey, "DES");
                Cipher desCipher = Cipher.getInstance("DES");
                desCipher.init(Cipher.ENCRYPT_MODE, desSecretKeySpec);
                byte[] desEncryptedBytes = desCipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
                return Base64.encodeBase64String(desEncryptedBytes);
            default:
                return value;
        }
    }

    public static void main(String[] args) throws Exception {
        String result = encrypt("PEP", "aes", "5c1456772113dfE5b3316ac68658Acda");
        System.out.println(result);
        result = decrypt(result, "aes", "5c1456772113dfE5b3316ac68658Acda");
        System.out.println(result);
    }

}
