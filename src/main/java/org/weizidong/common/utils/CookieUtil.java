package org.weizidong.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 * 
 * @author WeiZiDong
 *
 */
public class CookieUtil {

	private CookieUtil() {
	}

	/**
	 * 添加cookie
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * 删除cookie
	 * 
	 * @param response
	 * @param name
	 */
	public static void removeCookie(HttpServletResponse response, String name) {
		Cookie uid = new Cookie(name, null);
		uid.setPath("/");
		uid.setMaxAge(0);
		response.addCookie(uid);
	}

	/**
	 * 获取cookie值
	 * 
	 * @param request
	 * @return
	 */
	public static String getUid(HttpServletRequest request, String cookieName) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
}