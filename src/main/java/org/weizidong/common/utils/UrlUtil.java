package org.weizidong.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Url工具类
 *
 * @author WeiZiDong
 */
public class UrlUtil {
    private UrlUtil() {
    }

    /**
     * url解码
     *
     * @param str 源字符串
     * @return 解码后的结果
     */
    public static String decode(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogUtil.error(UrlUtil.class, e);
        }
        return null;
    }
}
