package org.sunso.demo.parser.controller;

/**
 * @author sunso520
 * @Title:DataImportController
 * @Description: <br>
 * @Created on 2025/4/5 23:04
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sunso.demo.parser.service.DataImportService;

@RestController
public class DataImportController {

    @Autowired
    private DataImportService dataImportService;

    @PostMapping("/import-data")
    public String importData(@RequestParam("zipFilePath") String zipFilePath,
                             @RequestParam("dataSource") String dataSource,
                             @RequestParam("tableName") String tableName,
                             @RequestParam("batchSize") int batchSize) {
        try {
            dataImportService.importDataFromZip(zipFilePath, dataSource, tableName, batchSize);
            return "Data imported successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error importing data: " + e.getMessage();
        }
    }
}
