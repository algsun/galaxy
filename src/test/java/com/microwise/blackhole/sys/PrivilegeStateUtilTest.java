package com.microwise.blackhole.sys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 测试权限状态算法util ：①权限类型：state二进制位上是1的代表拥有某个权限
 * ②权限状态：state字段，根据权限类型复合出的用于数据库存储的状态标识
 * 
 * @author zhangpeng
 * @date 2012-12-6
 * 
 * @check 2012-12-13 xubaoji svn:805
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrivilegeStateUtilTest {

	/**
	 * 根据权限类型列表获取你需要的权限状态
	 * 
	 * @author zhangpeng
	 * @date 2012-12-7
	 */
	@Test
	public void testCreateState() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		Assert.assertEquals(3, PrivilegeStateUtil.createState(list));
	}

	/**
	 * 测试判断一个权限状态是否包含某个权限类型
	 * 
	 * @author zhangpeng
	 * @date 2012-12-7
	 */
	@Test
	public void testCheckState() {
		Assert.assertEquals(false, PrivilegeStateUtil.checkState(2,
				PrivilegeStateUtil.PRIVILEGE_STATE_REUQIRED));
	}

	/**
	 * 测试给指定权限状态去掉某种权限类型
	 * 
	 * @author zhangpeng
	 * @date 2012-12-7
	 */
	@Test
	public void testRemoveState() {
		Assert.assertEquals(0, PrivilegeStateUtil.removeState(15,
				PrivilegeStateUtil.PRIVILEGE_STATE_LIST));
	}

	/**
	 * 测试求第一个参数类型与指定状态按位或结果集
	 * 
	 * @author zhangpeng
	 * @date 2012-12-7
	 */
	@Test
	public void testGetCombination() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		Set<Integer> set = new HashSet<Integer>();
		set.add(3);
		set.add(2);
		Assert.assertEquals(set, PrivilegeStateUtil.getCombination(2, list));
	}

	/**
	 * 根据权限类型数组获取所有可以产生的权限状态组合
	 * 
	 * @author zhangpeng
	 * @date 2012-12-7
	 */
	@Test
	public void testGetStateCombinations() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(4);
		List<Integer> result = new ArrayList<Integer>();
		result.add(1);
		result.add(4);
		result.add(5);
		Assert.assertEquals(result,
				PrivilegeStateUtil.getStateCombinations(list));
	}

	/**
	 * 实例测试，求包含1和4的权限状态
	 * 
	 * @author zhangpeng
	 * @date 2012-12-7
	 */
	@Test
	public void testGetSetIntersections() {
		Set<Integer> set1 = new HashSet<Integer>();
		set1.add(1);
		set1.add(2);
		set1.add(3);
		set1.add(4);
		Set<Integer> set2 = new HashSet<Integer>();
		set2.add(2);
		set2.add(3);
		set2.add(4);
		set2.add(5);
		Set<Integer> set3 = new HashSet<Integer>();
		set3.add(3);
		set3.add(4);
		set3.add(5);
		set3.add(6);
		Set<Integer> set4 = new HashSet<Integer>();
		set4.add(4);
		set4.add(5);
		set4.add(6);
		set4.add(7);
		Set<Integer> result = PrivilegeStateUtil.getSetIntersections(new Set[] {
				set1, set3, set2, set4 });
		Set<Integer> checkSet = new HashSet<Integer>();
		checkSet.add(4);
		Assert.assertEquals(checkSet, result);
	}

	/**
	 * 测试数据库可能出现的状态state
	 * 
	 * @author zhangpeng
	 * @date 2012-12-7
	 */
	@Test
	public void testGetDBFuzzyState() {
		Assert.assertEquals(
				64,
				PrivilegeStateUtil.getDBFuzzyState(
						PrivilegeStateUtil.PRIVILEGE_STATE_REUQIRED).size());
	}

	/**
	 * 测试获取过滤指定权限类型的状态列表
	 * 
	 * @author zhangpeng
	 * @date 2012-12-7
	 */
	@Test
	public void testGetStateByFilter() {
		Set<Integer> set = PrivilegeStateUtil
				.getStateByFilter(PrivilegeStateUtil.PRIVILEGE_STATE_REUQIRED);
		Iterator<Integer> it = set.iterator();
		while (it.hasNext()) {
			Integer i = it.next();
			Assert.assertFalse(PrivilegeStateUtil.checkState(i, 1));
		}
	}

}
