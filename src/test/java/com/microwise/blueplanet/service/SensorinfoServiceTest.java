package com.microwise.blueplanet.service;


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.common.sys.test.BaseTest;

/**
 * 监测指标Service测试
 * 
 * @author zhangpeng
 * @date 2013-1-18
 * 
 * @check 2013-02-25 xubaoji svn:1750
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SensorinfoServiceTest extends BaseTest {

	@Autowired
	private SensorinfoService SensorinfoService;

	@Test
	public void testFindSensorinfo(){
		Assert.assertEquals(120, SensorinfoService.findSensorinfo().size());
	}

	@Test
	public void testFindByPhysicalid() {
		SensorinfoVO sensorinfo = SensorinfoService.findByPhysicalid(32);
		Assert.assertNotNull(sensorinfo);
		Assert.assertEquals("湿度", sensorinfo.getCnName());
	}

}
