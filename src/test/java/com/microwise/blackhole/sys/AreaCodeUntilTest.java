package com.microwise.blackhole.sys;

import java.util.List;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;

import com.microwise.common.sys.test.BaseTest;
import org.junit.runners.MethodSorters;

/**
 * 地区行政 工具类测试
 * 
 * @author xubaoji
 * @date 2012-11-29
 * 
 * @check 2012-11-29 zhangpeng svn:493
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AreaCodeUntilTest extends BaseTest {

	/**
	 * 测试获取某个地区下的所有子孙节点地区编码
	 * 
	 * @author xubaoji
	 * @date 2012-11-29
	 */
	@Test
	public void testGetAllSubordinates() {
		List<Integer> list = AreaCodeUtil.getAllSubordinates(110000);
		Assert.assertEquals(18, list.size());
	}

}
