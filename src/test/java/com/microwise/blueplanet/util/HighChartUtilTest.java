package com.microwise.blueplanet.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.bean.vo.HighChartWindRoseVO;
import com.microwise.blueplanet.bean.vo.WindRoseVO;
import com.microwise.common.sys.Constants.ChartConstants;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * 原始数据转换为HighChart数据的工具类测试
 * 
 * @author zhangpeng
 * @date 2013-3-1
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HighChartUtilTest {

	public static void main(String args[]) {
		try {
			testPackageWindRose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testPackageBaseChart() {
		List<ChartVO> chartList = new ArrayList<ChartVO>();

		ChartVO chart = new ChartVO(32, "湿度", "HUM", "%", 2);
		List<Map<String, Object>> chartData = new ArrayList<Map<String, Object>>();
		for (int j = 0; j < 20; j++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(ChartConstants.CHARTDATA_KEY_TIME, new Date());
			map.put(ChartConstants.CHARTDATA_KEY_DATA,
					String.valueOf(new Random().nextInt()));
			chartData.add(map);
		}
		chart.setChartData(chartData);
		chartList.add(chart);

		System.out.println(chartList);
		Map<String, Object> map = HighChartUtil.packageBasic("3101010100010",
				"测试设备", chartList);
		Gson gson = new GsonBuilder().serializeNulls().create();
		// Gson gson = new Gson();
		System.out.println(gson.toJson(map));
	}

	public static void testPackageWindRose() throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		WindRoseVO wind = new WindRoseVO();
		wind.setoN(1.02);
		wind.setTime("2012年4月");
		HighChartWindRoseVO high = HighChartUtil.packageWindRose("1", "名字",
				wind);
		System.out.println(high);
		System.out.println(high.getText());
	}

}
