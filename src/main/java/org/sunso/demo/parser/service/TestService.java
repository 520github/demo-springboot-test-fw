package org.sunso.demo.parser.service;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.sunso.demo.parser.dto.XmlHandlerDTO;
import org.sunso.demo.parser.xml.TestXmlHandler;
import org.sunso.demo.parser.xml.XmlHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author sunso520
 * @Title:TestService
 * @Description: <br>
 * @Created on 2025/4/6 17:09
 */
public class TestService {

    public void testZip(String zipFilePath) throws Exception{
        long beginTime = System.currentTimeMillis();
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            String xmlFileName = findXmlFileName(zipFile);
            ZipArchiveEntry xmlEntry = zipFile.getEntry(xmlFileName);
            InputStream xmlInputStream = zipFile.getInputStream(xmlEntry);
            processXmlData(xmlInputStream);
        } finally {
            //DataSourceContextHolder.clearDataSource();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - beginTime)/1000 + "秒");
    }

    public void processXmlData(InputStream xmlInputStream) throws Exception {
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        TestXmlHandler handler = new TestXmlHandler(XmlHandlerDTO.of());
        xmlReader.setContentHandler(handler);
        xmlReader.parse(new InputSource(xmlInputStream));
    }

    private String findXmlFileName(ZipFile zipFile) {
        Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
        while (entries.hasMoreElements()) {
            ZipArchiveEntry entry = entries.nextElement();
            //DD_OBLIST_CODE_20250318_F.zip
            if (entry.getName().endsWith(".xml")) {
                System.out.println(entry.getName());
                return entry.getName();
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        TestService testService = new TestService();
        String path = "/Users/sunso520/home/tmp/data1/DD_OBLIST/20250318/DD_OBLIST_CODE_20250318_F.zip";
        //path = "/Users/sunso520/home/tmp/data1/DD_OBLIST/20250318/prod_v3_DD_OBLIST_F_aes_count_4000003_5000003.zip";
        //path = "/Users/sunso520/home/tmp/data1/DD_OBLIST/20250318/prod_v3_DD_OBLIST_F_aes_count_3_1000003.zip";
        //path = "/Users/sunso520/Downloads/test.xml";
        testService.testZip(path);
    }
}
