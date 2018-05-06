package com.microwise.proxima.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.proxima.bean.PictureBean;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 摄像机图片Service
 * 
 * @author zhangpeng
 * @date 2013-3-25
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PictureServiceTest extends CleanDBTest {

	/** 摄像机Service */
	@Autowired
	private PictureService pictureService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/proxima/PictureService.xml");
	}

	/**
	 * 测试查询摄像机的最近N包数据
	 * 
	 * @author zhangpeng
	 * @date 2013-3-25
	 */
	@Test
	public void testFindRecentPictures() {
		List<PictureBean> list = pictureService.findRecentPictures("123", 20);
		Assert.assertEquals(6, list.size());
	}

	/**
	 * 测试查询摄像机的最近N包数据
	 * 
	 * @author zhangpeng
	 * @date 2013-3-25
	 */
	@Test
	public void testFindByTime() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 1999);
		Date startTime = c.getTime();
		c.set(Calendar.YEAR, 2020);
		Date endTime = c.getTime();
		List<PictureBean> list = pictureService.findByTime("123", startTime,
				endTime);
		Assert.assertEquals(6, list.size());
	}

}
