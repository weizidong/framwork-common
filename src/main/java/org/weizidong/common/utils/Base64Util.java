package org.weizidong.common.utils;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * Base64工具类
 *
 * @author WeiZiDong
 * @date 2018-06-04
 */
public class Base64Util {
    private Base64Util() {
    }

    /**
     * 获取编码器
     *
     * @return {@link Encoder}
     */
    public static Encoder getEncoder() {
        return Base64.getEncoder();
    }

    /**
     * 获取解码器
     *
     * @return {@link Encoder}
     */
    public static Decoder getDecoder() {
        return Base64.getDecoder();
    }

    /**
     * 编码成字节数组
     *
     * @param input 待编码内容
     * @return 编码结果
     */
    public static byte[] encode(byte[] input) {
        return Base64.getEncoder().encode(input);
    }

    /**
     * 编码成字节数组
     *
     * @param input 待编码内容
     * @return 编码结果
     */
    public static byte[] encode(String input) {
        return Base64.getEncoder().encode(input.getBytes());
    }

    /**
     * 编码成字符串
     *
     * @param input 待编码内容
     * @return 编码结果
     */
    public static String encodeToString(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    /**
     * 编码成字符串
     *
     * @param input 待编码内容
     * @return 编码结果
     */
    public static String encodeToString(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    /**
     * 解码成字节数组
     *
     * @param input 待解码内容
     * @return 解码结果
     */
    public static byte[] decode(byte[] input) {
        return Base64.getDecoder().decode(input);
    }

    /**
     * 解码成字节数组
     *
     * @param input 待解码内容
     * @return 解码结果
     */
    public static byte[] decode(String input) {
        return Base64.getDecoder().decode(input);
    }

    /**
     * 解码成字符串
     *
     * @param input 待解码内容
     * @return 解码结果
     */
    public static String decodeToString(byte[] input) {
        return new String(Base64.getDecoder().decode(input));
    }

    /**
     * 解码成字符串
     *
     * @param input 待解码内容
     * @return 解码结果
     */
    public static String decodeToString(String input) {
        return new String(Base64.getDecoder().decode(input));
    }
}
