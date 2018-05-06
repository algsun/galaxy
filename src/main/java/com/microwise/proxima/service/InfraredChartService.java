package com.microwise.proxima.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 温度变化趋势图数据处理 sevice
 * <b>数据类型</b>：红外图片数据/红外区域数据
 * </pre>
 * 
 * @author li.jianfei
 * @date 2012-9-12
 */
public interface InfraredChartService {


	/**
	 * <pre>
	 * TODO 未验证
	 * </pre>
	 * 处理红外图片全图温度变化趋势图数据
	 * 
	 * @param dvPlaceId
	 *            点位 id
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return HighChart可接收的 Json 数据
	 * @author li.jianfei
	 * @date 2012-09-12
	 */
	public List<Map<String,Object>> getInfraredPictureDataSeries(String dvPlaceId, Date startDate,
			Date endDate);

	/**
	 * <pre>
	 * TODO 未验证
	 * </pre>
	 * 处理红外图片标记区域温度变化趋势图数据
	 * 
	 * @param dvPlaceId
	 *            点位id
	 * @param markRegionId
	 *            标记区域id
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return HighChart可接收的 Json 数据
	 * @author li.jianfei
	 * @date 2012-09-12
	 */
	public List<Map<String,Object>> getInfraredMarkRegionDataSeries(
			String dvPlaceId, String markRegionId, Date startDate, Date endDate);
}
