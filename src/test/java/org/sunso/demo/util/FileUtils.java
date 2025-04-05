package org.sunso.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author sunso520
 * @Title:FileUtils
 * @Description: <br>
 * @Created on 2025/2/28 10:35
 */
public class FileUtils {

    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    public static String calculateMD5CatchException(String filePath)  {
        try {
            return calculateMD5(filePath);
        }catch (Exception e) {
            return "计算文件MD5值发生异常";
        }
    }

    public static String calculateMD5(String filePath) throws NoSuchAlgorithmException, IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return "文件不存在";
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
        }
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
