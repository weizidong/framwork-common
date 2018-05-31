package org.weizidong.conmon.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * http工具类
 *
 * @author WeiZiDong
 */
public class HttpUtil {

	/**
	 * url解码
	 *
	 * @param str
	 * @return
	 */
	public static String decode(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("url解码失败", e);
		}
	}
}
