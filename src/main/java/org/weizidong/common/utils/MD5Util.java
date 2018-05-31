package org.weizidong.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * MD5工具类
 *
 * @author WeiZiDong
 */
public class MD5Util {
    private MD5Util() {
    }

    /**
     * 获得MD5加密密码的方法
     */
    public static String getMD5ofStr(String origString) {
        String origMD5 = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] result = md5.digest(origString.getBytes());
            origMD5 = byteArray2HexStr(result);
            return origMD5.toLowerCase();
        } catch (Exception e) {
            LogUtil.error(MD5Util.class, e);
        }
        return origMD5;
    }

    /**
     * 处理字节数组得到MD5密码的方法
     */
    private static String byteArray2HexStr(byte[] bs) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bs) {
            sb.append(byte2HexStr(b));
        }
        return sb.toString();
    }

    /**
     * 字节标准移位转十六进制方法
     */
    private static String byte2HexStr(byte b) {
        String hexStr;
        int n = b;
        if (n < 0) {
            // 若需要自定义加密,请修改这个移位算法即可
            n = b & 0x7F + 128;
        }
        hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
        return hexStr.toUpperCase();
    }

    /**
     * 提供一个MD5多次加密方法
     */
    public static String getMD5ofStr(String origString, int times) {
        String md5 = getMD5ofStr(origString);
        for (int i = 0; i < times - 1; i++) {
            md5 = getMD5ofStr(md5);
        }
        return getMD5ofStr(md5);
    }

    /**
     * 密码验证方法
     */
    public static boolean verifyPassword(String inputStr, String md5code) {
        return StringUtils.equals(getMD5ofStr(inputStr), md5code);
    }

    /**
     * 重载一个多次加密时的密码验证方法
     */
    public static boolean verifyPassword(String inputStr, String md5code, int times) {
        return StringUtils.equals(getMD5ofStr(inputStr, times), md5code);
    }

}
