package org.sunso.demo.parser.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: cyy
 * @createdate: 2018-09-14
 */
@Slf4j
public class AES {


    public static final String ENCODING = "UTF-8";

    private static String ivParameter = "riskgw0123456789";

    // license 专用
    public static String decryptByKey(String sSrc, String key) {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new Base64().decode(sSrc.getBytes());// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static byte[] hexToByte(String s) throws IOException {
        int i = s.length() / 2;
        byte abyte0[] = new byte[i];
        int j = 0;
        if (s.length() % 2 != 0)
            throw new IOException("hexadecimal string with odd number of characters");
        for (int k = 0; k < i; k++) {
            char c = s.charAt(j++);
            int l = "0123456789abcdef0123456789ABCDEF".indexOf(c);
            if (l == -1)
                throw new IOException("hexadecimal string contains non hex character");
            int i1 = (l & 0xf) << 4;
            c = s.charAt(j++);
            l = "0123456789abcdef0123456789ABCDEF".indexOf(c);
            i1 += l & 0xf;
            abyte0[k] = (byte) i1;
        }

        return abyte0;
    }

    /**
     * 解密数据
     */
    public static byte[] decrypt(String str, String key)
            throws Exception {
        if (null == str || str.trim().length() < 1) {
            return null;
        }

        byte[] keybytes = hexToByte(key);
        byte[] ivbytes = new byte[16];

        SecretKeySpec skeySpec = new SecretKeySpec(keybytes, "AES");


        IvParameterSpec iv = new IvParameterSpec(ivbytes);


        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] encrypted1 = hexToByte(str);

        byte[] original = cipher.doFinal(encrypted1);

        return original;
    }


    /**
     * 解密数据
     */
    public static String decryptStr(String str, String key) {
        if (str == null){
            return "";
        }
        if (StrUtil.isBlank(str)) {
            return "";
        }
        try {
            byte[] data = decrypt(str, key);
            return new String(data, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("AES 解密错误str={}", str, e);
        }
        return "";
    }

    //转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder("");
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0");
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 加密数据
     */
    public static String encrypt(String str, String key) {
        if (null == str || str.trim().length() < 1) {
            return "";
        }
        try {
            byte[] keybytes = hexToByte(key);
            byte[] ivbytes = new byte[16];
            SecretKeySpec skeySpec = new SecretKeySpec(keybytes, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivbytes);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = str.getBytes(ENCODING);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = byte2hex(original);
            return originalString;
        } catch (Exception e) {
            log.error("AES 加密错误,str={}", str, e);
        }
        return "";
    }

    // L0bylWqEnwpcc6js
    public static void main(String[] args) {
    }

}
