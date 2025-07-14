package org.sunso.demo.parser.util;


import cn.hutool.core.collection.CollectionUtil;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @author sunso520
 * @Title:ApiClientUtil
 * @Description: <br>
 * @Created on 2025/6/30 13:54
 */
public class ApiClientUtil {

    public static ApiClientUtil of() {
        return new ApiClientUtil();
    }

    public String doGet(String url, Map<String, String> headers) throws IOException {
        //log.info("doGet url[{}], headers[{}]. ", url, headers);
        HttpURLConnection connection = openConnection(url, headers, null);
        connection.setRequestMethod("GET");
        return readResponse(connection);
    }

    public String doPostJson(String url, String body) throws IOException {
        return doPostJson(url, null, body);
    }

    public String doPostJson(String url, Map<String, String> headers,  String body) throws IOException {
        return doPost(url, headers, body, "application/json");
    }

    public String doPost(String url, Map<String, String> headers, String body, String contentType) throws IOException {
        //log.info("doPost url[{}], contentType[{}], body[{}], headers[{}]. ", url, contentType, body, headers);
        HttpURLConnection connection = openConnection(url, headers, null);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", contentType);
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        String result = readResponse(connection);
        //log.info("doPost url[{}], result[{}]. ", url, result);
        return result;
    }

//    public String doPostForm(String url, Map<String, String> headers, Map<String, String> formParams) throws IOException {
//        return doPostForm(url, headers, null, formParams);
//    }

    public String doPostForm(String url, Map<String, String> headers, Map<String, String> formParams) throws Exception {
        String body = map2FormBody(formParams);
        return doPost(url, headers, body, "application/x-www-form-urlencoded");
    }
    private HttpURLConnection openConnection(String url, Map<String, String> headers, Map<String, String> cookies) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection connection;
//        if (proxyUrl != null && !proxyUrl.isEmpty()) {
//            String[] parts = proxyUrl.split(":");
//            if (parts.length == 2) {
//                InetSocketAddress addr = new InetSocketAddress(parts[0], Integer.parseInt(parts[1]));
//                Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
//                log.info("openConnection url[{}]使用了代理proxyUrl[{}]", url, proxyUrl);
//                connection = (HttpURLConnection) urlObj.openConnection(proxy);
//            } else {
//                throw new IllegalArgumentException("Invalid proxy URL format: " + proxyUrl);
//            }
//        } else {
//            connection = (HttpURLConnection) urlObj.openConnection();
//        }
        connection = (HttpURLConnection) urlObj.openConnection();
        if (connection instanceof HttpsURLConnection) {
            try {
                HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
                trustAllHttpsCertificates(httpsConnection);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

//        if (connection instanceof HttpsURLConnection) {
//            HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
//            httpsConnection.setHostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            });
//            httpsConnection.setSSLSocketFactory(DEFAULT_SSL_SOCKET_FACTORY);
//            try {
//                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                    // 信任所有
//                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                        return true;
//                    }
//                }).build();
//                httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());
//            }catch (Exception e){
//                log.error("ssl证书处理异常", e);
//            }
//
//        }

        int connectTimeout = 15000;
        int readTimeout = 60000;
//        log.info("openConnection url[{}], connectTimeout[{}], readTimeout[{}]",
//                url, connectTimeout, readTimeout);
        connection.setConnectTimeout(connectTimeout);// 连接超时15秒
        connection.setReadTimeout(readTimeout);// 读取超时60秒

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        if (cookies != null) {
            StringBuilder cookieHeader = new StringBuilder();
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                if (cookieHeader.length() > 0) {
                    cookieHeader.append("; ");
                }
                cookieHeader.append(entry.getKey()).append("=").append(entry.getValue());
            }
            connection.setRequestProperty("Cookie", cookieHeader.toString());
        }

        return connection;
    }

    public static void trustAllHttpsCertificates(HttpsURLConnection httpsConnection) throws NoSuchAlgorithmException, KeyManagementException {
        // 创建信任所有证书的 TrustManager
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // 信任所有客户端证书
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // 信任所有服务器证书
                    }
                }
        };

        // 初始化 SSLContext
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        httpsConnection.setSSLSocketFactory(sc.getSocketFactory());

        // 创建信任所有主机名的 HostnameVerifier
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        httpsConnection.setHostnameVerifier(allHostsValid);

        System.out.println("trust all");
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL) {
            return readResponseFromStream(connection);
        } else {
            //log.info("responseCode is not 200, responseCode is [{}]", responseCode);
            String body = readResponseFromOkOrErrorStream(connection);
            //log.info("responseCode is not 200, responseBody is [{}]", body);
            throw new IOException(String.format("Unexpected responseCode[%d], responseBody[%s] ",responseCode, body));
        }
    }

    private String readResponseFromStream(HttpURLConnection connection)  throws IOException  {
        if (connection.getInputStream() == null) {
            return null;
        }
        try (InputStream is = connection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line).append("\n");
            }
            return response.toString();
        }
    }

    private String readResponseFromErrorStream(HttpURLConnection connection) throws IOException {
        if (connection.getErrorStream() == null)  {
            return null;
        }
        try (InputStream es = connection.getErrorStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(es, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line).append("\n");
            }
            return response.toString();
        }
    }

    private String readResponseFromOkOrErrorStream(HttpURLConnection connection) throws IOException {
        String body = null;
        try {
            body = readResponseFromStream(connection);
        }catch (Exception e) {
            // IGNORE
        }
        try {
            if (body == null) {
                body = readResponseFromErrorStream(connection);
            }
        }catch (Exception e) {
            //log.error("readResponseFromErrorStream异常", e);
        }
        return body;
    }

    public static String map2FormBody(Map<String, String> formParams) throws Exception {
        if (CollectionUtil.isEmpty(formParams)) {
            return "";
        }
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : formParams.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8.name()));
            postData.append('=');
            postData.append(URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8.name()));
        }
        return postData.toString();
    }

    public static void main(String[] args) throws IOException {
        String url = "https://221.238.152.178:8080/api/v3/client/debug/test-network";
//        url = "https://testoblist.nifadata.com:8080/api/v3/client/debug/test-network";
//        url = "https://10.30.255.1:19214/cpam/cpam-token-decrypt/token/get-access";
        String result = ApiClientUtil.of().doGet(url, null);
        System.out.println(result);
    }
}
