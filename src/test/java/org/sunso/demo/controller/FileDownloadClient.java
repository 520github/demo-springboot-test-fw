package org.sunso.demo.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.util.StringUtils;
import org.sunso.demo.model.request.FileDownloadRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunso520
 * @Title:FileDownloadTest
 * @Description: <br>
 * @Created on 2025/1/22 16:06
 */
public class FileDownloadClient {

    public static void downloadFile(String requestUrl, Map<String, String> requestHeaders,  String savePath, long downloadFileSize) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            setRequestHeader(connection, requestHeaders);
            File file = new File(savePath);
//            long downloadBytes = 0;
//            long downloadEndBytes = 0;
//            if (file.exists()) {
//                downloadBytes = file.length();
//                System.out.println("downloadBytes:" + downloadBytes);
//                connection.setRequestProperty("Range", "bytes=" + downloadBytes + "-");
//            }
//            if (downloadEndBytes > 0) {
//                downloadBytes = file.length();
//                connection.setRequestProperty("Range", "bytes=" + downloadBytes + "-" + downloadEndBytes);
//            }
            setDownloadRange(connection, file, downloadFileSize);
            connection.connect();
            writeData2LocalFile(connection, file);

            System.out.println("下载完成");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setDownloadRange(HttpURLConnection connection, File file,  long downloadFileSize) {
        long alreadyDownloadBytes = 0;
        if (file.exists()) {
            alreadyDownloadBytes = file.length();
        }
        String rangeValue = "bytes=" + alreadyDownloadBytes + "-";
        if (downloadFileSize > 0) {
            rangeValue+=downloadFileSize;
        }
        connection.setRequestProperty("Range", rangeValue);
    }

    public static void postDownloadFile(String requestUrl, String requestBody, Map<String, String> requestHeaders, String savePath, long downloadFileSize) {
        try {
            URL url = new URL(requestUrl);
            File file = new File(savePath);
            Proxy proxy = getProxy("", 0);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            setRequestHeader(connection, requestHeaders);
            setDownloadRange(connection, file, downloadFileSize);
            if (!StringUtils.isEmpty(requestBody)) {
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
                }
            }
            connection.connect();
            writeData2LocalFile(connection, file);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Proxy getProxy(String proxyHost, int proxyPort) {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
    }

    private static void setRequestHeader(HttpURLConnection connection, Map<String, String> requestHeaders) {
        if (requestHeaders == null || requestHeaders.isEmpty()) {
            return;
        }
        for(String key: requestHeaders.keySet()) {
            String value = requestHeaders.get(key);
            connection.setRequestProperty(key, value);
        }
    }


    private static int writeData2LocalFile(HttpURLConnection connection, File localFile) throws Exception {
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_PARTIAL) {
            int downloadBytes = writeData2OutputStream(connection, new FileOutputStream(localFile, true));
            System.out.println("部分下载本次读取文件大小：" + downloadBytes);
            return downloadBytes;
        }
        else if (responseCode == HttpURLConnection.HTTP_OK) {
            return writeData2OutputStream(connection, new FileOutputStream(localFile));
        }
        else {
            System.out.println("Server returned HTTP code: " + responseCode);
            return -1;
        }
    }

    private static int writeData2OutputStream(HttpURLConnection connection, FileOutputStream outputStream) throws Exception{
        int downloadBytes = 0;
        try(InputStream inputStream = connection.getInputStream();
            FileOutputStream fileOutputStream = outputStream){
            byte[] buffer = new byte[4096];
            int byteRead;
            while((byteRead = inputStream.read(buffer)) !=-1) {
                fileOutputStream.write(buffer,  0, byteRead);
                downloadBytes+=byteRead;
            }
        }
        return downloadBytes;
    }

    public static void main(String[] args) {
        String fileUrl = "http://localhost:8080/download/file?filename=/path/to/your/file.zip&version=v3&fileMd5=md5"; // 替换为服务端的文件下载 URL
        String savePath = "/Users/sunso520/Downloads/download-test.docx"; // 替换为本地保存文件的路径
        //downloadFile(fileUrl, null, savePath, -1);
        postDownloadFile(fileUrl, null, getRequestHeader(), savePath, -1);
    }

    private static String getJsonRequestBody() {
        FileDownloadRequest request = new FileDownloadRequest();
        request.setVersion("v3");
        request.setFileMd5("md5");
        return JSONUtil.toJsonStr(request);
    }

    private static String getFormRequestBody() {
        return "version=v3&fileMd5=md5";
    }

    private static Map<String, String> getRequestHeader() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("version", "v3");
        headerMap.put("organkey", "12345678");
        headerMap.put("sign", "dddd");
        return headerMap;
    }

}
