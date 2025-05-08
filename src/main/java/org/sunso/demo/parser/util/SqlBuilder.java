package org.sunso.demo.parser.util;

/**
 * @author sunso520
 * @Title:SqlBuilder
 * @Description: <br>
 * @Created on 2025/4/5 22:41
 */
import org.sunso.demo.parser.file.Field;

import java.util.Map;

public class SqlBuilder {

    public static String buildInsertSqlField(String tableName, Map<String, Field> data) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Map.Entry<String, Field> entry : data.entrySet()) {
            if (columns.length() > 0) {
                columns.append(", ");
                values.append(", ");
            }
            columns.append(entry.getKey());
            values.append("?");
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns.toString(), values.toString());
    }

    public static String buildInsertSql(String tableName, Map<String, Object> data) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (columns.length() > 0) {
                columns.append(", ");
                values.append(", ");
            }
            columns.append(entry.getKey());
            values.append("?");
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns.toString(), values.toString());
    }

    public static String buildUpdateSql(String tableName, Map<String, Object> data, String primaryKey) {
        StringBuilder setClause = new StringBuilder();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (!entry.getKey().equals(primaryKey)) {
                if (setClause.length() > 0) {
                    setClause.append(", ");
                }
                setClause.append(entry.getKey()).append(" = ?");
            }
        }

        return String.format("UPDATE %s SET %s WHERE %s = ?", tableName, setClause.toString(), primaryKey);
    }

    public static String buildMergeSql(String tableName, Map<String, Object> data, String primaryKey, String databaseType) {
        StringBuilder insertColumns = new StringBuilder();
        StringBuilder insertValues = new StringBuilder();
        StringBuilder updateClause = new StringBuilder();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (insertColumns.length() > 0) {
                insertColumns.append(", ");
                insertValues.append(", ");
                if (!entry.getKey().equals(primaryKey)) {
                    updateClause.append(", ");
                }
            }
            insertColumns.append(entry.getKey());
            insertValues.append("?");
            if (!entry.getKey().equals(primaryKey)) {
                updateClause.append(entry.getKey()).append(" = EXCLUDED.").append(entry.getKey());
            }
        }

        switch (databaseType.toLowerCase()) {
            case "mysql":
                return String.format("INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s",
                        tableName, insertColumns.toString(), insertValues.toString(), updateClause.toString());
            case "oracle":
                StringBuilder mergeColumns = new StringBuilder();
                StringBuilder mergeValues = new StringBuilder();
                StringBuilder updateSet = new StringBuilder();

                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    if (mergeColumns.length() > 0) {
                        mergeColumns.append(", ");
                        mergeValues.append(", ");
                        updateSet.append(", ");
                    }
                    mergeColumns.append(entry.getKey());
                    mergeValues.append("?");
                    if (!entry.getKey().equals(primaryKey)) {
                        updateSet.append(entry.getKey()).append(" = ?");
                    }
                }

                return String.format("MERGE INTO %s USING DUAL ON (%s = ?) " +
                                "WHEN MATCHED THEN UPDATE SET %s " +
                                "WHEN NOT MATCHED THEN INSERT (%s) VALUES (%s)",
                        tableName, primaryKey, updateSet.toString(), mergeColumns.toString(), mergeValues.toString());
            case "gbase8a":
                // GBase 8a 语法与 MySQL 类似
                return String.format("INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s",
                        tableName, insertColumns.toString(), insertValues.toString(), updateClause.toString());
            case "gbase8s":
                // GBase 8s 使用 MERGE 语法
                return String.format("MERGE INTO %s T1 USING (VALUES (%s)) T2 (%s) " +
                                "ON T1.%s = T2.%s " +
                                "WHEN MATCHED THEN UPDATE SET %s " +
                                "WHEN NOT MATCHED THEN INSERT (%s) VALUES (%s)",
                        tableName, insertValues.toString(), insertColumns.toString(),
                        primaryKey, primaryKey, updateClause.toString(),
                        insertColumns.toString(), insertValues.toString());
            case "sqlserver":
                return String.format("MERGE %s WITH (HOLDLOCK) AS T " +
                                "USING (VALUES (%s)) AS S (%s) " +
                                "ON T.%s = S.%s " +
                                "WHEN MATCHED THEN UPDATE SET %s " +
                                "WHEN NOT MATCHED THEN INSERT (%s) VALUES (%s);",
                        tableName, insertValues.toString(), insertColumns.toString(),
                        primaryKey, primaryKey, updateClause.toString(),
                        insertColumns.toString(), insertValues.toString());
            case "db2":
                return String.format("MERGE INTO %s T " +
                                "USING (VALUES (%s)) S (%s) " +
                                "ON T.%s = S.%s " +
                                "WHEN MATCHED THEN UPDATE SET %s " +
                                "WHEN NOT MATCHED THEN INSERT (%s) VALUES (%s);",
                        tableName, insertValues.toString(), insertColumns.toString(),
                        primaryKey, primaryKey, updateClause.toString(),
                        insertColumns.toString(), insertValues.toString());
            case "postgresql":
                return String.format("INSERT INTO %s (%s) VALUES (%s) " +
                                "ON CONFLICT (%s) DO UPDATE SET %s",
                        tableName, insertColumns.toString(), insertValues.toString(),
                        primaryKey, updateClause.toString());
            default:
                throw new UnsupportedOperationException("Unsupported database type: " + databaseType);
        }
    }
}