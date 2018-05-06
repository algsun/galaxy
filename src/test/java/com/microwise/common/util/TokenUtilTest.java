/**
 * 
 */
package com.microwise.common.util;

import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 * @author zhangpeng
 * @date 2012-11-26
 */
public class TokenUtilTest {

	@Test
	public void test() {
		String token = TokenUtil.createToken();
		Assert.assertNotNull(token);
		Assert.assertEquals(32, token.length());
	}

}
