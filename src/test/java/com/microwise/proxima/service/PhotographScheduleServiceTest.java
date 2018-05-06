package com.microwise.proxima.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.proxima.bean.PhotographScheduleBean;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 摄像机拍照计划service测试
 * 
 * @author zhangpeng
 * @date 2013-3-26
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhotographScheduleServiceTest extends CleanDBTest {

	/** 摄像机拍照计划service */
	@Autowired
	private PhotographScheduleService photographScheduleService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/proxima/PhotographScheduleServiceTest.xml");
	}

	/**
	 * 查询摄像机的所有拍照计划设置
	 * 
	 * @author zhangpeng
	 * @date 2013-3-26
	 */
	@Test
	public void testFindAllOfDVPlace() {
		List<PhotographScheduleBean> list = photographScheduleService
				.findAllOfDVPlace("1113");
		Assert.assertEquals(2, list.size());
	}

}
