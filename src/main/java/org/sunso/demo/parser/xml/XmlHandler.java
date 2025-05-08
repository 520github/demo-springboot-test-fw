package org.sunso.demo.parser.xml;

/**
 * @author sunso520
 * @Title:XmlHandler
 * @Description: <br>
 * @Created on 2025/4/5 22:40
 */

import org.sunso.demo.parser.entity.GenericEntity;
import org.sunso.demo.parser.service.DataImportService;
import org.sunso.demo.parser.util.SqlBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlHandler extends DefaultHandler {
    private DataImportService dataImportService;
    private String tableName;
    private String dataSource;
    private Map<String, String> metadata;
    private GenericEntity currentEntity;
    private String currentElement;
    private Map<String, Object> currentData;
    private List<Map<String, Object>> batchRecords;
    private int batchSize;
    private int recordCount = 0;

    public XmlHandler(DataImportService dataImportService, String tableName, String dataSource, Map<String, String> metadata, int batchSize) {
        this.dataImportService = dataImportService;
        this.tableName = tableName;
        this.dataSource = dataSource;
        this.metadata = metadata;
        this.batchSize = batchSize;
        this.batchRecords = new ArrayList<>(batchSize);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("record".equals(qName)) {
            currentEntity = new GenericEntity();
            currentEntity.setObjId(attributes.getValue("objId"));
            currentData = new HashMap<>();
        }
        currentElement = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentEntity != null && currentElement != null && !"record".equals(currentElement)) {
            String fieldValue = new String(ch, start, length);
            String[] fieldInfo = metadata.get(currentElement).split(",");
            String isEncrypted = fieldInfo[1];
            String encryptionMethod = fieldInfo[2];
            try {
                if ("true".equals(isEncrypted)) {
                    fieldValue = dataImportService.decrypt(fieldValue, encryptionMethod);
                }
            } catch (Exception e) {
                throw new SAXException("Decryption error", e);
            }
            currentData.put(currentElement, fieldValue);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("record".equals(qName)) {
            currentData.put("objId", currentEntity.getObjId());
            batchRecords.add(currentData);
            recordCount++;
            if (recordCount % batchSize == 0) {
                try {
                    dataImportService.processBatchRecords(batchRecords, tableName, dataSource);
                } catch (Exception e) {
                    throw new SAXException("Error processing batch records", e);
                }
                batchRecords.clear();
            }
            currentEntity = null;
            currentData = null;
        }
        currentElement = null;
    }

    @Override
    public void endDocument() throws SAXException {
        if (!batchRecords.isEmpty()) {
            try {
                dataImportService.processBatchRecords(batchRecords, tableName, dataSource);
            } catch (Exception e) {
                throw new SAXException("Error processing remaining records", e);
            }
        }
    }
}