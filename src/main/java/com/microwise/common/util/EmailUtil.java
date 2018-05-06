package com.microwise.common.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.*;

/**
 * 邮箱工具类
 * <p/>
 * Date: 11-11-4  Time: 下午11:32
 *
 * @author gaohui
 */
public class EmailUtil {
    private static final Map<String, String> EMAILS = loadEmails();

    private EmailUtil() {
    }

    private static Map<String, String> loadEmails() {
        Properties emailProperties = new Properties();
        try {
            emailProperties.load(Resources.getResource("common/email/mails.properties").openStream());
            Map<String, String> emailMap = new HashMap<String, String>(emailProperties.size());
            for (Enumeration<String> enumeration = (Enumeration<String>) emailProperties.propertyNames(); enumeration.hasMoreElements(); ) {
                String key = enumeration.nextElement();
                emailMap.put(key, emailProperties.getProperty(key));
            }
            return emailMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    /**
     * 返回邮箱地址的域名部分。（如 abc@163.com 则返回 163.com）
     * 注意：如果无法解析了域名部分，则返回空字符串。
     *
     * @param email
     * @return
     */
    public static String parseDomain(String email) {
        Preconditions.checkNotNull(email);

        int index = email.lastIndexOf("@");
        if (index == -1) {
            return "";
        }
        return email.substring(index + 1, email.length());
    }

    /**
     * 通过邮箱返回此邮箱的登录页面链接，如果找不到返回 null。
     *
     * @param email
     * @return
     */
    public static String getLoginUrl(String email) {
        return EMAILS.get(parseDomain(email));
    }

    /**
     * 混淆邮箱地址
     *
     * @param email
     * @return
     */
    public static String obscured(String email) {
        Splitter splitter = Splitter.on("@").omitEmptyStrings().trimResults();
        FluentIterable<String> segments = FluentIterable.from(splitter.split(email));
        if (segments.size() != 2) {
            return null;
        }

        String username = segments.get(0);
        if (username.length() <= 0) {
            return null;
        }
        return username.substring(0, (username.length() + 1) / 2) + "...@" + segments.get(1);
    }
}
