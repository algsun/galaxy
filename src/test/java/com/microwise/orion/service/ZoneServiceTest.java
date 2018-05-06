package com.microwise.orion.service;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.proxima.bean.Zone;

/**
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3746
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZoneServiceTest extends CleanDBTest {

	/** 区域service */
	@Autowired
	private ZoneService zoneService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/orion/ZoneServiceTest.xml");
	}

	/**
	 * 查询 有文物的区域测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 */
	@Test
	public void testFindHasRelicZone() {
		List<Zone> zoneList = zoneService.findHasRelicZone("31010101");
		Assert.assertNotNull(zoneList);
		Assert.assertEquals(2, zoneList.size());
	}

}
