package com.microwise.orion.service;

import java.util.Date;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.Rove;

/**
 * 文物流传经历service 测试类
 * 
 * @author xubaoji
 * @date 2013-5-13
 *
 * @check 2013-06-04 zhangpeng svn:4046
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoveServiceTest extends CleanDBTest {

	/** 文物service */
	@Autowired
	private RoveService roveService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/orion/RoveServiceTest.xml");
	}

	/** 添加文物流传经历 */
	@Test
	public void testAddRelicRove() {
		Rove rove = new Rove();
		Relic relic =new Relic() ;
		relic.setId(1);
		rove.setRelic(relic);
		rove.setRoveDate(new Date());
		rove.setRoveInfo("aaaaa");
		roveService.addRelicRove(rove);
		Rove rove2 = roveService.findById(rove.getId());
		Assert.assertEquals(rove.getRoveInfo(), rove2.getRoveInfo());
	}

}
