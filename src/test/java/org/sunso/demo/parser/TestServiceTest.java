package org.sunso.demo.parser;

import org.junit.Test;
import org.sunso.demo.BaseSpringBootTest;
import org.sunso.demo.parser.service.TestService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author sunso520
 * @Title:TestServiceTest
 * @Description: <br>
 * @Created on 2025/4/7 08:24
 */
public class TestServiceTest extends BaseSpringBootTest {

    @Test
    public void testZip() throws Exception {
        TestService testService = new TestService();
        String path = "/Users/sunso520/home/tmp/data1/DD_OBLIST/20250318/DD_OBLIST_CODE_20250318_F.zip";
        //path = "/Users/sunso520/home/tmp/data1/DD_OBLIST/20250318/prod_v3_DD_OBLIST_F_aes_count_4000003_5000003.zip";
        //path = "/Users/sunso520/home/tmp/data1/DD_OBLIST/20250318/prod_v3_DD_OBLIST_F_aes_count_3_1000003.zip";
        //path = "/Users/sunso520/Downloads/test.xml";
        testService.testZip(path);
    }

    @Test
    public void processXmlDataTest() throws Exception {
        TestService testService = new TestService();
        String path = "/Users/sunso520/home/tmp/test.xml";
        testService.processXmlData(new FileInputStream(path));
    }
}
