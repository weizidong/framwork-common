package org.weizidong.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数工具类
 *
 * @author WeiZiDong
 */
public class MathUtil {
    /**
     * 大写字母种子
     */
    public static final String UPPERCASE_SEED = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 小写字母种子
     */
    public static final String LOWERCASE_SEED = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 数字种子
     */
    public static final String NUMBER_SEED = "0123456789";

    private MathUtil() {
    }

    /**
     * 生成一个UUID 32位
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 随机生成字符串
     *
     * @param length 想要生成的长度
     * @param base   字符集
     * @return 生成的随机字符串
     */
    public static String get(int length, String base) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取指定长度的随机数字
     *
     * @param length 想要生成的长度
     * @return 生成的随机数字
     */
    public static String get(int length) {
        return get(length, NUMBER_SEED);
    }

}