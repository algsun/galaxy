package com.microwise.phoenix.service;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.util.DateTimeUtil;
import com.microwise.common.sys.test.BaseTest;
import com.microwise.phoenix.bean.vo.ZoneStatistics;
import com.microwise.common.sys.Constants;

/***
 * 统计分析 区域统计 service 测试
 * 
 * @author xu.baoji
 * @date 2013-7-4
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZoneStatsTestService extends BaseTest {

	@Autowired
	private ZoneStatisticsService zoneStatisticsService;

	@Test
	public void findZoneStatisticsTest() {
		List<ZoneStatistics> zoneStats = zoneStatisticsService
				.findZoneStatistics(
						"a3f919da-e737-4406-8084-a09e50025055",
                        DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD, "2013-03-10"),
						Constants.FIND_TYPE_MONTH);
		System.out.println(zoneStats);
		List<ZoneStatistics>  zoneStats1 = zoneStatisticsService.findZoneStatistics(
				"a3f919da-e737-4406-8084",
                DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD, "2013-03-10"),
				Constants.FIND_TYPE_YEAR);
		System.out.println(zoneStats1);
		zoneStatisticsService.findRangStatsOfZones("012311", new Date(), Constants.FIND_TYPE_YEAR);
	}
}
