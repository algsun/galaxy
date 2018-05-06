package com.microwise.orion.service;

import com.microwise.common.sys.test.BaseTest;
import com.microwise.orion.bean.Level;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 级别service 测试
 * 
 * @author xubaoji
 * @date 2013-5-17
 *
 * @check 2013-06-04 zhangpeng svn:3510
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LevelServiceTest extends BaseTest {

	/** 级别service */
	@Autowired
	private LevelService levelService;

	/**
	 * 查询所有级别信息测试
	 * 
	 * @author 许保吉
	 * @date   2013-5-17 
	 */
	@Test
	public void TestFindAllLevel(){
		List<Level> levels = levelService.findAllLevel();
		Assert.assertNotNull(levels);
		Assert.assertEquals(5, levels.size());
	}

}
