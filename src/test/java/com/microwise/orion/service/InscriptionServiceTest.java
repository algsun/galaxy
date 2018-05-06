package com.microwise.orion.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Inscription;
import com.microwise.orion.bean.Relic;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 铭文题跋 service 测试
 * 
 * @author xubaoji
 * @date 2013-5-17
 *
 * @check 2013-06-04 zhangpeng svn:3664
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InscriptionServiceTest extends CleanDBTest {

	/** 铭文题跋service */
	@Autowired
	private InscriptionService inscriptionService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/orion/InscriptionServiceTest.xml");
	}

	/**
	 * 添加铭文题跋测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 */
	@Test
	public void testAdd() {
		Inscription inscription = new Inscription();
		inscription.setInfo("cbc");
		inscription.setInfo("aa.jpg");
		Relic relic = new Relic();
		relic.setId(1);
		inscription.setRelic(relic);
		inscriptionService.addInscription(inscription);
        Inscription inscription1 = inscriptionService.findById(inscription.getId());
        Assert.assertEquals(inscription.getPath(),inscription1.getPath());
	}

	/**
	 * 铭文题跋删除测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 */
	@Test
	public void testDelete() {
		inscriptionService.deleteInscription(1);
        Inscription inscription = inscriptionService.findById(1);
        Assert.assertNull(inscription);
	}
}
