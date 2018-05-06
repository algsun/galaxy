/**
 * 
 */
package com.microwise.common.util;

import java.util.UUID;

/**
 * 生成用户Token工具类
 * 
 * @author zhangpeng
 * @date 2012-11-26
 * @check @gaohui #347 2012-11-27
 */
public class TokenUtil {

	/**
	 * 静态方法：返回token
	 * 
	 * @author zhangpeng
	 * @date 2012-11-26
	 * @return String token
	 */
	public static String createToken() {
		return Md5.getMd5(UUID.randomUUID().toString());
	}

}
