package org.weizidong.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数工具类
 * 
 * @author WeiZiDong
 *
 */
public class MathUtil {

	/**
	 * 生成一个UUID 32位
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 随机生成字符串
	 * 
	 * @param length
	 *            想要生成的长度
	 * @return
	 */
	public static String getRandomString(int length) {
		return getRandomString(length, "abcdefghijklmnopqrstuvwxyz");
	}

	/**
	 * 随机生成字符串
	 * 
	 * @param length
	 *            想要生成的长度
	 * @param base
	 *            字符集
	 * @return
	 */
	public static String getRandomString(int length, String base) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获取6-10 的随机位数数字
	 * 
	 * @param length
	 *            想要生成的长度
	 * @return result
	 */
	public static String getRandom620(Integer length) {
		String result = "";
		Random rand = new Random();
		int n = 20;
		if (null != length && length > 0) {
			n = length;
		}
		int randInt = 0;
		for (int i = 0; i < n; i++) {
			randInt = rand.nextInt(10);

			result += randInt;
		}
		return result;
	}

}