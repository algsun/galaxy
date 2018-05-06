package com.microwise.phoenix.service;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.vo.OutEventStats;
import com.microwise.phoenix.bean.vo.OutEventStatsInfo;
import com.microwise.phoenix.bean.vo.RelicBasicStats;

/***
 * 出库统计 service 测试
 * 
 * @author xu.baoji
 * @date 2013-7-4
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OutEventStatsServiceTest extends CleanDBTest {

	@Autowired
	private OutEventStatsService outEventStatsService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/phoenix/OutEventStatsServiceTest.xml");
	}

	/** 出库统计查询测试 */
	@Test
	public void findOutEventStats() {
		List<OutEventStats> outEventStats = outEventStatsService.findOutEventStat("31010101", 2013);
		Assert.assertEquals(2, outEventStats.size());
		OutEventStats  outEventStats2 = outEventStats.get(0);
		Assert.assertEquals(2013, outEventStats2.getYear().intValue());
		Assert.assertEquals(1, outEventStats2.getYearCount().intValue());
		Assert.assertEquals(2, outEventStats2.getYearSum().intValue());
		outEventStats2 = outEventStats.get(1);
		Assert.assertNotNull(outEventStats2);
		Assert.assertEquals(0, outEventStats2.getYearCount().intValue());
	}

	/** 出库单统计信息 查询测试 */
	@Test
	public void findOutEventStatsInfo() {
		List<OutEventStatsInfo> outEventStatInfos = outEventStatsService.findOutEventStatInfo(
				"31010101", 2013);
		Assert.assertEquals(2, outEventStatInfos.size());
	}

	/** 出库文物 各种类型 % 数据查询测试 */
	@Test
	public void findPieChartData() {
		RelicBasicStats relicBasicStat = outEventStatsService.findPieChartData("31010101", 2013);
		Assert.assertNotNull(relicBasicStat);
	}
}
