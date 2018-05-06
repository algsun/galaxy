package com.microwise.proxima.proxy;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.proxima.bean.Zone;

/**
 * 区域服务代理 测试
 * 
 * @author xubaoji
 * @date 2013-5-21
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZoneProxyTest extends CleanDBTest {

	/** 区域代理层 */
	@Autowired
	private ZoneProxy zoneProxy;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/proxima/ZoneServiceTest.xml");
	}

	/**
	 * 查询所有区域测试 携带有父区域
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 */
	@Test
	public void TestFindZoneList() {

		List<Zone> zoneList = zoneProxy.findZoneList("31010101");
		
		Assert.assertNotNull(zoneList);
		Assert.assertEquals(3, zoneList.size());
		

	}

}
