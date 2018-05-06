/**
 * 
 */
package com.microwise.blackhole.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;

import com.microwise.common.util.VerifyCodeUtil;
import org.junit.runners.MethodSorters;

/**
 * 验证码测试
 * 
 * @author zhangpeng
 * @date 2012-11-2
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VerifyCodeUtilTest {

	@Test
	@Ignore //忽略测试的注解
	public void test() throws IOException {
		OutputStream out = new ByteArrayOutputStream();
		try {
			System.out.println(VerifyCodeUtil.createVerifyCode(out));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
