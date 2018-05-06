package com.microwise.blueplanet.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.microwise.common.util.DateTimeUtil;
import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.bean.vo.WindRoseVO;
import com.microwise.common.sys.Constants;

/**
 * @author xubaoji
 * @date 2013-2-28
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChartServiceTest {

	private static ChartService chartService;

	static {
		ApplicationContext act = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		chartService = act.getBean(ChartService.class);

	}

	/**
	 * @param args
	 * 
	 * @author 许保吉
	 * @date 2013-2-28
	 * 
	 * @return
	 */
	public static void main(String[] args) {
		//testFindBasicChart();

		// testChartDaoFindCurveDataForDayRainfallChart();
		//testFindRainfall();
		//testFindLightShadow();
		testFindWindRose();
	}

	/**
	 * 获得基础曲线图 测试
	 * 
	 * @author 许保吉
	 * @date 2013-3-1
	 * 
	 * @return
	 */
	public static void testFindBasicChart() {

		List<Integer> sensorinfoList = new ArrayList<Integer>();
		sensorinfoList.add(32);
		sensorinfoList.add(33);
		List<ChartVO> chartDataList = chartService.findBasicChart(
				"3101010100020", sensorinfoList,
                DateTimeUtil.parseFull("2013-02-17 15:23:31"),
                DateTimeUtil.parseFull("2013-02-21 15:30:20"));
		for (ChartVO chartVO : chartDataList) {
			System.out.println(chartVO);
		}
	}

	/**
	 * 获得 降雨量图表数据 测试
	 * 
	 * @author 许保吉
	 * @date 2013-3-1
	 * 
	 * @return
	 */
	public static void testFindRainfall() {
		List<Integer> sensorinfoList = new ArrayList<Integer>();
		sensorinfoList.add(47);
		sensorinfoList.add(32);
		List<ChartVO> chartDataList = null;
		try {
			chartDataList = chartService.findRainfall("3101010100256",
					sensorinfoList, Constants.FIND_TYPE_DAY,
                    DateTimeUtil.parse(DateTimeUtil.YYYY_MM_DD, "2013-02-17"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(chartDataList);
	}

	/**
	 * 获得光照图表数据测试
	 * 
	 * @author 许保吉
	 * @date 2013-3-4
	 * 
	 * @return
	 */
	public static void testFindLightShadow() {
		ChartVO chartVO = chartService.findLight("0000131300256",
                Constants.FIND_TYPE_YEAR,
                DateTimeUtil.parseFull("2013-02-17 13:00:00"));
		System.out.println(chartVO);

	}

	/**
	 * 风向 图表数据 接口 测试
	 * 
	 * @author 许保吉
	 * @date 2013-3-6
	 * 
	 * @return
	 */
	public static void testFindWindRose() {

		WindRoseVO windRoseVO = chartService.findWindRose("3101010100256",
                Constants.FIND_TYPE_DAY,
                DateTimeUtil.parseFull("2013-03-06 13:00:00"), DateTimeUtil.parseFull("2013-03-06 13:00:00"), DateTimeUtil.parseFull("2013-03-06 13:00:00"));

		System.out.println(windRoseVO);

	}

}
