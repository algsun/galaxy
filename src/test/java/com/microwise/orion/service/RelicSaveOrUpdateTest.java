package com.microwise.orion.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Accident;
import com.microwise.orion.bean.Appraisal;
import com.microwise.orion.bean.Era;
import com.microwise.orion.bean.Level;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.Restore;
import com.microwise.orion.bean.StatusQuo;
import com.microwise.orion.bean.Texture;

/**
 * 文物service 测试类
 * 
 * @author xubaoji
 * @date 2013-5-13
 *
 * @check 2013-06-05 zhangpeng svn:4076
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RelicSaveOrUpdateTest extends CleanDBTest {

	/** 文物service */
	@Autowired
	private RelicService relicService;
	
	/** 文物年代service */
	@Autowired
	private EraService eraService;

	/** 文物级别 service */
	@Autowired
	private LevelService levelService;

	/** 文物质地service */
	@Autowired
	private TextureService textureService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/orion/RelicServiceTest.xml");
	}

	/**
	 * 文物 基本信息添加测试 (不添加文物字典信息)
	 * 
	 * @author 许保吉
	 * @date 2013-5-17
	 */
	@Test
	public void testSaveRelic() {

		// 不添加文物字典信息
		Relic relic = new Relic();
		relic.setTotalCode("1234");
		relic.setCatalogCode("121");
		relic.setTypeCode("123");
		relic.setName("ccc");
		relic.setSiteId("31010101");
		Era era = new Era();
		era.setId(1);
		Level level = new Level();
		level.setId(1);
		Texture texture = new Texture();
		texture.setId(1);
		relic.setEra(era);
		relic.setLevel(level);
		relic.setTexture(texture);
		relicService.addRelic(relic);
        Relic relic1 = relicService.findById(relic.getId());
        Assert.assertNotNull(relic1);

		// 同时添加文物字典信息
		Relic relic2 = new Relic();
		relic2.setTotalCode("1235");
		relic2.setCatalogCode("121");
		relic2.setTypeCode("123");
		relic2.setName("ccc");
		relic2.setSiteId("31010101");
		Era era1 = new Era();
		era1.setName("秦末");
		Level level1 = new Level();
		level1.setName("三级");
		Texture texture1 = new Texture();
		texture1.setName("青铜");
		relic2.setEra(era1);
		relic2.setLevel(level1);
		relic2.setTexture(texture1);
		relicService.addRelic(relic2);
        Relic relic3 = relicService.findById(relic2.getId());
        Assert.assertNotNull(relic3);

        // 清理 ear, level, texture
        relicService.delete(relic2);
        eraService.delete(era1);
        levelService.delete(level1);
        textureService.delete(texture1.getName());
	}

	/**
	 * 修改文物 基本信息测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-17
	 */
	@Test
	public void TestUpdateRelic() {
		Relic relic = new Relic();
		relic.setId(1);
		relic.setTotalCode("1230");
		relic.setName("gxcsin");
		relic.setCatalogCode("cc");
		relic.setTypeCode("2");
		relic.setSiteId("31010101");
		Era era = new Era();
		era.setId(1);
		Level level = new Level();
		level.setId(1);
		Texture texture = new Texture();
		texture.setId(1);
		relic.setEra(era);
		relic.setLevel(level);
		relic.setTexture(texture);
		relicService.updateRelic(relic);
		Relic relic2 = relicService.findRelicByRelicId(1, "31010101");
		Assert.assertEquals("gxcsin", relic2.getName());
		Assert.assertTrue(!Strings.isNullOrEmpty(relic2.getTypeCode()));
	}

	/**
	 * 添加 文物 记录 测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-20
	 */
	@Test
	public void TestAddRelicRecord() {
		Relic relic = new Relic();
		relic.setId(2);
		relic.setTotalCode("1231");
		// 添加文物事故记录
		Accident accident = new Accident();
		accident.setAccidentDate(new Date());
		accident.setAccidentInfo("添加测试事故");
		accident.setRelic(relic);
		relicService.addRelicRecord(accident);
		// 添加 现状记录
		StatusQuo statusQuo = new StatusQuo();
		statusQuo.setQuoDate(new Date());
		statusQuo.setQuoInfo("现状测试记录");
		statusQuo.setRelic(relic);
        statusQuo.setQuoPictures("test");
		relicService.addRelicRecord(statusQuo);
		// 添加修复记录
		Restore restore = new Restore();
		restore.setRelic(relic);
		restore.setRestoreDate(new Date());
		restore.setRestoreInfo("断臂修复");
		restore.setRestorers("陕历博工作组");
		relicService.addRelicRecord(restore);
		// 添加鉴定记录
		Appraisal appraisal = new Appraisal();
		appraisal.setAppraisalDate(new Date());
		appraisal.setExaminer("王教授");
		appraisal.setExpertOpinion("一级文物");
		appraisal.setRelic(relic);
		relicService.addRelicRecord(appraisal);

        Relic relic2 = relicService.findRelicByRelicId(2, "31010101");
        Assert.assertNotNull(relic2.getAccidents());
        Assert.assertEquals(1, relic2.getAccidents().size());
        Assert.assertNotNull(relic2.getStatusQuos());
        Assert.assertEquals(1, relic2.getStatusQuos().size());
        Assert.assertNotNull(relic2.getRestores());
        Assert.assertEquals(1, relic2.getRestores().size());
        Assert.assertNotNull(relic2.getAppraisals());
        Assert.assertEquals(1, relic2.getAppraisals().size());

	}

	/** 修改文物 库房状态测试 */
	@Test
	public void testUpdateRelicState() {
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(1);
		relicService.updateRelicState(idList, Relic.STATE_OUT);
		Relic relic = relicService.findById(1);
		Assert.assertEquals(Relic.STATE_OUT, relic.getState());
	}
	
	/**测试注销和取消注销文物*/
	@Test
	public void testUpdateRelicCanceledState(){
		relicService.updateRelicCanceledState("31010101", 1, true);
		Relic relic = relicService.findRelicByRelicId(1, "31010101");
		Assert.assertTrue(relic.getIscanceled());
	}

}
