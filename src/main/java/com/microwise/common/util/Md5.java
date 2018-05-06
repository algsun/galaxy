package com.microwise.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * 
 * @author zhangpeng
 * @date 2011-11-20
 */
public class Md5 {

    public static final String MD5 = "MD5";
    public static final String FORMAT = "%02X";

    private Md5() {}

    /**
     * md5 hash
     *
     * @author gaohui
     * @date 2012-11-20
     * @param plainText
     * @return
     */
	public static String getMd5(String plainText) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(MD5);
            md.update(plainText.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : md.digest()) {
                sb.append(String.format(FORMAT, b));
            }
            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
	}
}