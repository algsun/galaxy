package com.microwise.proxima.util;

import com.google.common.base.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URLEncoder 工具类
 * 
 * @author gaohui
 * @date 2012-6-4
 * 
 * @check  fenghua  2012年6月14日
 */
public class URLEncodes {
	private URLEncodes() {
	}

	/**
	 * 通过utf-8编码
	 * 
	 * @author gaohui
	 * @date 2012-6-4
	 * 
	 * 
	 */
	public static String encode(String str, String charset) {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String encodeUTF8(String str) {
		return encode(str, "utf-8");
	}

	/**
	 * 如果 str 为 null 或者 空，则直接返回 str，否则进行解码。
	 * 
	 * @author zhang.licong
	 * @date 2012-6-4
	 * 
	 * 
	 */
	public static String decode(String str, String charset) {
		try {
			if (Strings.isNullOrEmpty(str)) {
				return str;
			} else {
				return URLDecoder.decode(str, charset);
			}
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
