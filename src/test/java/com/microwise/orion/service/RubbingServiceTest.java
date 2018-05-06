package com.microwise.orion.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.Rubbing;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 拓片service 测试
 * 
 * @author xubaoji
 * @date 2013-5-17
 *
 * @check 2013-06-04 zhangpeng svn:3664
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RubbingServiceTest extends CleanDBTest {

	/** 拓片service */
	@Autowired
	private RubbingService rubbingService;

	@BeforeClass
	public static void setbefor() throws Exception {
		xmlInsert2("common/dbxml/orion/RubbingServiceTest.xml");
	}

	/**
	 * 添加拓片测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 */
	@Test
	public void testAddRubbing() {
		Rubbing rubbing = new Rubbing();
		rubbing.setPath("cc.jpg");
		rubbing.setRubbingDate(new Date());
		Relic relic = new Relic();
		relic.setId(1);
		rubbing.setRelic(relic);
		rubbingService.addRubbing(rubbing);
        Rubbing rubbing1 = rubbingService.findById(rubbing.getId());
        Assert.assertEquals(rubbing.getPath(),rubbing1.getPath());
	}

	/**
	 * 删除拓片测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 */
	@Test
	public void testDeleteRubbing() {
		rubbingService.deleteRubbing(1);
        Rubbing rubbing = rubbingService.findById(1);
        Assert.assertNull(rubbing);
	}
}
