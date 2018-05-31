package org.weizidong.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 *
 * @author WeiZiDong
 */
public class CookieUtil {
    /**
     * 最大过期时间，一小时
     */
    public static final Integer MAXAGE_HOUR = 60 * 60;
    /**
     * 最大过期时间，一天
     */
    public static final Integer MAXAGE_DAY = 60 * 60 * 24;
    /**
     * 最大过期时间，一周（7天）
     */
    public static final Integer MAXAGE_WEEK = 60 * 60 * 24 * 7;

    private CookieUtil() {
    }

    /**
     * 添加cookie
     *
     * @param response HttpServletResponse
     * @param name     cookie键
     * @param value    cookie值
     * @param maxAge   过期时间：0、不记录cookie；-1、会话级cookie，关闭浏览器失效；n(1-∞)、过期时间为n秒
     */
    public static void add(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param response HttpServletResponse
     * @param name     cookie键
     */
    public static void remove(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie值
     *
     * @param request HttpServletRequest
     * @param name    cookie键
     * @return cookie值
     */
    public static String get(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}