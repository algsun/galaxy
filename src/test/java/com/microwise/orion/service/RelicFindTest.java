package com.microwise.orion.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.RelicCard;
import com.microwise.orion.vo.RelicVo;

/**
 * 文物service 测试类
 * 
 * @author xubaoji
 * @date 2013-5-13
 *
 * @check 2013-06-05 zhangpeng svn:4076
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RelicFindTest extends CleanDBTest {

	/** 文物service */
	@Autowired
	private RelicService relicService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/orion/RelicServiceTest.xml");
	}

	/**
	 * 分页查询文物基本信息测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-15
	 */
	@Test
	public void TestFindRelic() {

		List<Relic> relics = relicService.findRelics(null, null, null, null,
				null, -1, -1, -1, "31010101", -1, 1, 3,false);
		Assert.assertNotNull(relics);
		Assert.assertEquals(3, relics.size());
		Assert.assertNotNull(relics.get(0).getEra());
		Assert.assertNotNull(relics.get(0).getZone());

		relics = relicService.findRelics("dsff", "sdf", "df", "cd", "basdf", 5,
				4, 2, "31010101", -1, 1, 3,false);
		Assert.assertEquals(0, relics.size());

		relics = relicService.findRelics(null, null, "001", null, null, -1,
				-1, -1, "31010101", -1, 1, 3,false);

		Assert.assertEquals(3, relics.size());
	}

//    /**
//     * 获取导出excle的数据
//     *
//     * @author liuzhu
//     * @date 2013-07-31
//     */
//    @Test
//    public void TestFindRelicExcle() {
//        List<Relic> relics = relicService.findRelics(null, null, null, null,
//                null, -1, -1, -1, "31010101", -1,false);
//        Assert.assertNotNull(relics);
//        Assert.assertEquals(4,relics.size());
//    }

	/**
	 * 查询文物数量测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-15
	 */
	@Test
	public void TestFindRelicCount() {
		Integer count = relicService.findRelicCount(null, null, null, null,
				null, -1, -1, -1, "31010101", -1,false);
		Assert.assertEquals(4, count.intValue());
		count = relicService.findRelicCount("dsff", "sdf", "df", "cd", "basdf",
				5, 4, 2, "31010101", -1,false);
		Assert.assertEquals(0, count.intValue());
		count = relicService.findRelicCount(null, null, "001", null, null,
				-1, -1, -1, "31010101", -1,false);
		Assert.assertEquals(3, count.intValue());
	}

	/**
	 * 查询文物总件数测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-16
	 */
	@Test
	public void TestFindRelicAllCount() {
		Integer allCount = relicService.findRelicAllCount(null, null, null,
				null, null, -1, -1, -1, "31010101", -1,false);
		Assert.assertEquals(8, allCount.intValue());
		allCount = relicService.findRelicAllCount("dsff", "sdf", "df", "cd",
				"basdf", 5, 4, 2, "31010101", -1,false);
		Assert.assertEquals(0, allCount.intValue());
		allCount = relicService.findRelicAllCount(null, null, "001", null,
				null, -1, -1, -1, "31010101", -1,false);
		Assert.assertEquals(6, allCount.intValue());
	}

	/***
	 * 查询文物档案测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-17
	 */
	@Test
	public void TestFindRelicCarchives() {
		Relic relic = relicService.findRelicArchives("1230", "31010101");
		Assert.assertNotNull(relic);
		Assert.assertNotNull(relic.getEra());
		Assert.assertNotNull(relic.getPhotos());
		Assert.assertEquals(2, relic.getPhotos().size());
		Assert.assertNotNull(relic.getRelicPropertyMap().get("weight"));
		Assert.assertEquals(2, relic.getRelicPropertyMap().size());
		Assert.assertTrue(relic.getHasTag());
		Assert.assertNotNull(relic.getZone());
		Assert.assertEquals("001", relic.getZone().getId());
		Relic relic1 = relicService.findRelicArchives("1235", "31010101");
		Assert.assertNull(relic1);
	}

	/**
	 * 查询文物藏品卡测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-17
	 */
	@Test
	public void TestFindRelicCard() {

		RelicCard relicCard = relicService.findRelicCard("1230", "31010101");
		Assert.assertNotNull(relicCard);
		Assert.assertNotNull(relicCard.getEra());
		Assert.assertNotNull(relicCard.getAccident());
		Assert.assertEquals(2, relicCard.getAccident().getId().intValue());
		Assert.assertNotNull(relicCard.getPhotos());
		Assert.assertEquals(2, relicCard.getPhotos().size());
		Assert.assertEquals(2, relicCard.getRelicPropertyMap().size());
		Assert.assertNotNull(relicCard.getZone());
		Assert.assertEquals("001", relicCard.getZone().getId());

		RelicCard relicCard1 = relicService.findRelicCard("1235", "31010101");
		Assert.assertNull(relicCard1);

	}

	/**
	 * 通过文物总登记号查询文物所有信息测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-17
	 */
	@Test
	public void TestFindRelicByTotalCode() {

		Relic relic = relicService.findRelicByRelicId(1, "31010101");
		Assert.assertNotNull(relic);
		Assert.assertNotNull(relic.getEra());
		Assert.assertNotNull(relic.getPhotos());
		Assert.assertEquals(2, relic.getPhotos().size());
		Assert.assertNotNull(relic.getRelicPropertyMap().get("weight"));
		Assert.assertEquals(3, relic.getRelicPropertyMap().size());

		Relic relic1 = relicService.findRelicArchives("1235", "31010101");
		Assert.assertNull(relic1);
	}

	/**
	 * 查询上一个 和下一个文物总登记号测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-17
	 */
	@Test
	public void TestFindPreAndNextRelicTotalCode() {

		List<String> totalCode = relicService.findPreAndNextRelicTotalCode(
				"1230", "31010101");
		Assert.assertNull(totalCode.get(0));
		Assert.assertEquals("1231", totalCode.get(1));
		totalCode = relicService.findPreAndNextRelicTotalCode("1233",
				"31010101");
		Assert.assertNull(totalCode.get(1));
		Assert.assertEquals("1232", totalCode.get(0));

	}

	/**
	 * 为 http 服务查询所有文物信息测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-22
	 */
	@Test
	public void testFindHasTagRelics() {
		List<RelicVo> relicVoList = relicService.findHasTagRelics("31010101");
		Assert.assertNotNull(relicVoList);
		Assert.assertEquals(4, relicVoList.size());
		Assert.assertNotNull(relicVoList.get(0).getZoneId());
	}

	/**
	 * 测试修改文物 是否有标为 有标签
	 * 
	 * @author 许保吉
	 * @date 2013-5-23
	 */
	@Test
	public void testUpdateRelicTag() {
		String[] totalCode = { "1230", "1231", "1232", "1233", "1234", "1235",
				"1236", "1237", "1238", "1239" };
		List<String> totalCodeList = Arrays.asList(totalCode);
		relicService.updateRelicTag("31010101", totalCodeList);
        Relic relic = relicService.findRelicCard("1230","31010101");
        Assert.assertTrue(relic.getHasTag());
	}

	/**
	 * 测试修改文物状态
	 */
	@Test
	public void testUpdateRelicState() {
		List<Integer> relicIds = new ArrayList<Integer>();
		relicIds.add(1);
		relicService.updateRelicState(relicIds, 2);
		Relic relic = relicService.findById(1);
		Assert.assertEquals(2, relic.getState());
	}

}
