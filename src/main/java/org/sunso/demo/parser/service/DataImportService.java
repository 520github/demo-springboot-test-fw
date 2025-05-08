package org.sunso.demo.parser.service;

/**
 * @author sunso520
 * @Title:DataImportService
 * @Description: <br>
 * @Created on 2025/4/5 22:43
 */
//import com.example.demo.config.DataSourceContextHolder;
import org.sunso.demo.parser.entity.GenericEntity;
import org.sunso.demo.parser.util.SqlBuilder;
import org.sunso.demo.parser.xml.XmlHandler;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;

@Service
public class DataImportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void importDataFromZip(String zipFilePath, String dataSource, String tableName, int batchSize) throws Exception {
        //DataSourceContextHolder.setDataSource(dataSource);
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Map<String, String> metadata = readMetadata(zipFile);
            String xmlFileName = findXmlFileName(zipFile);
            ZipArchiveEntry xmlEntry = zipFile.getEntry(xmlFileName);
            InputStream xmlInputStream = zipFile.getInputStream(xmlEntry);
            processXmlData(xmlInputStream, metadata, tableName, dataSource, batchSize);
        } finally {
            //DataSourceContextHolder.clearDataSource();
        }
    }

    private Map<String, String> readMetadata(ZipFile zipFile) throws Exception {
        Map<String, String> metadata = new HashMap<>();
        Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
        while (entries.hasMoreElements()) {
            ZipArchiveEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".txt")) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        metadata.put(parts[0], parts[1] + "," + parts[2] + "," + parts[3]);
                    }
                }
                break;
            }
        }
        return metadata;
    }

    private String findXmlFileName(ZipFile zipFile) {
        Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
        while (entries.hasMoreElements()) {
            ZipArchiveEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".xml")) {
                return entry.getName();
            }
        }
//        for (ZipEntry entry : zipFile.getEntries()) {
//            if (entry.getName().endsWith(".xml")) {
//                return entry.getName();
//            }
//        }
        return null;
    }

    private void processXmlData(InputStream xmlInputStream, Map<String, String> metadata, String tableName, String dataSource, int batchSize) throws Exception {
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        XmlHandler handler = new XmlHandler(this, tableName, dataSource, metadata, batchSize);
        xmlReader.setContentHandler(handler);
        xmlReader.parse(new InputSource(xmlInputStream));
    }

    public void processBatchRecords(List<Map<String, Object>> batchRecords, String tableName, String dataSource) throws Exception {
        for (Map<String, Object> data : batchRecords) {
            String sql = SqlBuilder.buildMergeSql(tableName, data, "objId", dataSource);
            Object[] params = new Object[data.size()];
            int index = 0;
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                params[index++] = entry.getValue();
            }
            if (dataSource.equalsIgnoreCase("oracle")) {
                Object[] newParams = new Object[params.length * 2 - 1];
                System.arraycopy(params, 0, newParams, 0, params.length - 1);
                System.arraycopy(params, 1, newParams, params.length - 1, params.length - 1);
                newParams[newParams.length - 1] = params[0];
                params = newParams;
            }
            jdbcTemplate.update(sql, params);
        }
    }

    public String decrypt(String encryptedValue, String encryptionMethod) throws Exception {
        switch (encryptionMethod.toLowerCase()) {
            case "base64":
                return new String(Base64.decodeBase64(encryptedValue), StandardCharsets.UTF_8);
            case "aes":
                byte[] key = "0123456789abcdef".getBytes(StandardCharsets.UTF_8);
                SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encryptedValue));
                return new String(decryptedBytes, StandardCharsets.UTF_8);
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
}
