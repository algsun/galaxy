package com.microwise.blueplanet.util;


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * <pre>
 * 环境监测系统工具类
 * 1.风向转换(由度数转为风向英文标识) updateWindDirection()
 * </pre>
 * 
 * @author he.ming
 * @date Sep 14, 2012
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WindToolsTest {

	/**
	 * @author he.ming
	 * @date Sep 14, 2012
	 */
	@Test
	public void test() {
		Assert.assertEquals("E", WindTools.updateWindDirection("90"));
	}

}
