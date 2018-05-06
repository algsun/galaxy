package com.microwise.blackhole.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Cookie 工具类
 *
 * @author bastengao
 * @date 12-11-19 14:12
 * @check @wang.yunlong &li.jianfei   #132    2012-12-18
 */
public class CookieUtil {
    /**
     * 是否有指定 cookie
     *
     * @param name
     * @param request
     * @return
     */
    public static boolean hasCookie(String name, HttpServletRequest request) {
        return hasCookie(name, request.getCookies());
    }

    /**
     * 获取指定名称的 cookie, 如果没有返回 null.
     *
     * @param name
     * @param request
     * @return
     */
    public static Cookie getCookie(String name, HttpServletRequest request) {
        return getCookie(name, request.getCookies());
    }

    /**
     * 返回指定名称的 cookie value, 如果不存在返回 null.
     *
     * @param name
     * @param request
     * @return
     */
    public static String getValue(String name, HttpServletRequest request) {
        if (hasCookie(name, request)) {
            return getCookie(name, request).getValue();
        }
        return null;
    }

    /**
     * 是否有指定 cookie
     *
     * @param name
     * @param cookies
     * @return
     */
    public static boolean hasCookie(String name, Cookie[] cookies) {
        if (cookies == null) {
            return false;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定名称的 cookie, 如果没有返回 null.
     *
     * @param name
     * @param cookies
     * @return
     */
    public static Cookie getCookie(String name, Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 返回指定名称的 cookie value, 如果不存在返回 null.
     *
     * @param name
     * @param cookies
     * @return
     */
    public static String getValue(String name, Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }

        if (hasCookie(name, cookies)) {
            getCookie(name, cookies).getValue();
        }
        return null;
    }
}
