/**
 *
 */
package com.microwise.blackhole.util;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mindrot.jbcrypt.BCrypt;

import com.microwise.common.sys.Constants;

/**
 * 加密测试例子
 * 
 * @author zhangpeng
 * @date 2012-11-2
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JBcryptTest {

	/**
	 * 测试方法，给传入的String字符串加密，然后对比加密后的hash是否和明文匹配
	 * 
	 * @param password
	 *            需要加密的字符串
	 * @author zhangpeng
	 * @date 2012-11-2
	 * @return 密码明文与加密后的字符串是否匹配
	 */
	public static boolean encryptStr(String password) {
		// 加密，12应该为一个常量，决定加密复杂度，不得小于10，无参该方法默认为
		String hashed = BCrypt.hashpw(password,
				BCrypt.gensalt(Constants.BCRYPT_SALT));
		// 比对明文和加密后的hash
		return BCrypt.checkpw(password, hashed);
	}

	/**
	 * 测试加密效率
	 * 
	 * @param password
	 *            需要加密的字符串
	 * @param salt
	 *            加密复杂度salt值，不得小于10，否则报异常
	 * @param encryptNmb
	 *            加密次数，必须大于0，测试可以往1000以上
	 * @author zhangpeng
	 * @date 2012-11-2
	 * @return 加密消耗时间
	 */
	public static long testEncryptEfficiency(String password, int salt,
			int encryptNmb) {
		long before = System.currentTimeMillis();
		// 批量加密
		for (int i = 0; i < encryptNmb; i++) {
			BCrypt.hashpw(password, BCrypt.gensalt(salt));
		}
		long after = System.currentTimeMillis();
		return after - before;
	}

	@Test
	public void test() {
		Assert.assertTrue(encryptStr("1"));
	}

	@Test
	public void testBcrypt() {
		// 加密
		String password = "1";
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt(11));
		Assert.assertNotNull(hashed);

		// 比对
		Assert.assertTrue(BCrypt.checkpw(password, hashed));
	}

	@Test
	@Ignore //忽略测试的注解
	public void test1() {
		System.out.println("salt10用时："
				+ testEncryptEfficiency("password", 10, 100));
		System.out.println("salt11用时："
				+ testEncryptEfficiency("password", 11, 100));
		System.out.println("salt12用时："
				+ testEncryptEfficiency("password", 12, 100));
	}

}
