package com.microwise.proxima.service.impl;

import com.microwise.proxima.bean.InfraredMarkRegionDataBean;
import com.microwise.proxima.bean.InfraredPictureDataBean;
import com.microwise.proxima.service.InfraredChartService;
import com.microwise.proxima.service.InfraredMarkRegionDataService;
import com.microwise.proxima.service.InfraredPictureDataService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 温度变化趋势图数据处理 sevice 实现
 *
 * @author li.jianfei
 * @date 2012-9-12
 *
 * @check li.jianfei liu.zhu 2014-4-15 # 8323
 */
@Service
@Transactional
@Proxima
public class InfraredChartServiceImpl implements InfraredChartService {

    /**
     * 红外图片数据 service
     */
    @Autowired
    private InfraredPictureDataService infraredPictureDataService;

    /**
     * 红外图片标记区域 service
     */
    @Autowired
    private InfraredMarkRegionDataService infraredMarkRegionDataService;


    @Override
    public List<Map<String,Object>> getInfraredPictureDataSeries(String dvPlaceId, Date startDate,
                                               Date endDate) {

        String chartData = "";

        double minY = 0.0d; // Y 轴起始值
        double maxY = 0.0d; // Y 轴最大值

        List<Map<String, Object>> listSeries = null;

        Map<String, Object> mapHighTemperatureSeries = null; // 最高温度曲线信息
        Map<String, Object> mapLowTemperatureSeries = null; // 最低温度曲线信息
        Map<String, Object> mapAverageTemperatureSeries = null; // 平均温度曲线信息

        List<List<Object>> highTemperatureList = null; // 最高温度曲线数据
        List<List<Object>> lowTemperatureList = null; // 最低温度曲线数据
        List<List<Object>> averageTemperatureList = null; // 平均温度曲线数据

        List<Object> highTemperaturePoint = null; // 最高温度 时间-温度 数据
        List<Object> lowTemperaturePoint = null; // 最低温度 时间-温度 数据
        List<Object> averageTemperaturePoint = null; // 平均温度 时间-温度 数据

        // 未选择点位 id 不能查询
        if (dvPlaceId == null) {
            return null;
        }

        // 获取 Y 轴最高温度和最低温度
        minY = infraredPictureDataService.findMinLowTemperature(dvPlaceId,
                startDate, endDate);
        maxY = infraredPictureDataService.findMaxHighTemperature(dvPlaceId,
                startDate, endDate);

        // Y 轴最大值和最小值都为0时表示无数据
        if (maxY == 0 && minY == 0) {
            return null;
        }

        // 获取图片数据表数据集合
        List<InfraredPictureDataBean> list = infraredPictureDataService
                .findListForChart(dvPlaceId, startDate, endDate);
        if (list != null) {
            if (list.size() > 0) {

                // 初始化集合
                listSeries = new ArrayList<Map<String, Object>>();

                mapHighTemperatureSeries = new HashMap<String, Object>();
                mapLowTemperatureSeries = new HashMap<String, Object>();
                mapAverageTemperatureSeries = new HashMap<String, Object>();

                highTemperatureList = new ArrayList<List<Object>>();
                lowTemperatureList = new ArrayList<List<Object>>();
                averageTemperatureList = new ArrayList<List<Object>>();

                for (InfraredPictureDataBean infraredPictureDataBean : list) {

                    highTemperaturePoint = new ArrayList<Object>();
                    lowTemperaturePoint = new ArrayList<Object>();
                    averageTemperaturePoint = new ArrayList<Object>();

                    // 图片上传日期
                    Date date = infraredPictureDataBean.getPicture()
                            .getSaveTime();

                    // 最高温
                    highTemperaturePoint.add(date.getTime());
                    highTemperaturePoint.add(infraredPictureDataBean
                            .getHighTemperature());
                    highTemperatureList.add(highTemperaturePoint);

                    // 最低温
                    lowTemperaturePoint.add(date.getTime());
                    lowTemperaturePoint.add(infraredPictureDataBean
                            .getLowTemperature());
                    lowTemperatureList.add(lowTemperaturePoint);

                    // 平均温
                    averageTemperaturePoint.add(date.getTime());
                    averageTemperaturePoint.add(infraredPictureDataBean
                            .getAverageTemperature());
                    averageTemperatureList.add(averageTemperaturePoint);
                }

                // mapHighTemperatureSeries.put("yAxis", 0);
                // mapHighTemperatureSeries.put("color", "#4572A7");
                mapHighTemperatureSeries.put("name", "最高温度");
                mapHighTemperatureSeries.put("type", "spline");
                mapHighTemperatureSeries.put("data", highTemperatureList);

                // mapAverageTemperatureSeries.put("yAxis", 1);
                // mapAverageTemperatureSeries.put("color", "#6495ED");
                mapAverageTemperatureSeries.put("name", "平均温度");
                mapAverageTemperatureSeries.put("type", "spline");
                mapAverageTemperatureSeries.put("data", averageTemperatureList);

                // mapLowTemperatureSeries.put("yAxis", 2);
                // mapLowTemperatureSeries.put("color", "#4572A7");
                mapLowTemperatureSeries.put("name", "最低温度");
                mapLowTemperatureSeries.put("type", "spline");
                mapLowTemperatureSeries.put("data", lowTemperatureList);

                listSeries.add(mapHighTemperatureSeries);
                listSeries.add(mapAverageTemperatureSeries);
                listSeries.add(mapLowTemperatureSeries);
            }
        }

        return listSeries;
    }

    @Override
    public List<Map<String,Object>> getInfraredMarkRegionDataSeries(String dvPlaceId,
                                                  String markRegionId, Date startDate, Date endDate) {

        double minY; // Y 轴起始值
        double maxY; // Y 轴最大值

        List<Map<String, Object>> listSeries = null;

        Map<String, Object> mapHighTemperatureSeries = null; // 最高温度曲线信息
        Map<String, Object> mapLowTemperatureSeries = null; // 最低温度曲线信息
        Map<String, Object> mapAverageTemperatureSeries = null; // 平均温度曲线信息

        List<List<Object>> highTemperatureList = null; // 最高温度曲线数据
        List<List<Object>> lowTemperatureList = null; // 最低温度曲线数据
        List<List<Object>> averageTemperatureList = null; // 平均温度曲线数据

        List<Object> highTemperaturePoint = null; // 最高温度 时间-温度 数据
        List<Object> lowTemperaturePoint = null; // 最低温度 时间-温度 数据
        List<Object> averageTemperaturePoint = null; // 平均温度 时间-温度 数据

        // 未选择点位 id 不能查询
        if (dvPlaceId == null) {
            return null;
        }

        // 获取 Y 轴最高温度和最低温度
        minY = infraredMarkRegionDataService.findMaxHighTemperature(dvPlaceId,
                markRegionId, startDate, endDate);
        maxY = infraredMarkRegionDataService.findMinLowTemperature(dvPlaceId,
                markRegionId, startDate, endDate);

        // Y 轴最大值和最小值都为0时表示无数据
        if (maxY == 0 && minY == 0) {
            return null;
        }

        // 获取图片数据表数据集合
        List<InfraredMarkRegionDataBean> list = infraredMarkRegionDataService
                .findListForChart(dvPlaceId, markRegionId, startDate, endDate);

        if (list != null) {
            if (list.size() > 0) {

                // 初始化集合
                listSeries = new ArrayList<Map<String, Object>>();

                mapHighTemperatureSeries = new HashMap<String, Object>();
                mapLowTemperatureSeries = new HashMap<String, Object>();
                mapAverageTemperatureSeries = new HashMap<String, Object>();

                highTemperatureList = new ArrayList<List<Object>>();
                lowTemperatureList = new ArrayList<List<Object>>();
                averageTemperatureList = new ArrayList<List<Object>>();

                for (InfraredMarkRegionDataBean infraredMarkRegionDataBean : list) {

                    highTemperaturePoint = new ArrayList<Object>();
                    lowTemperaturePoint = new ArrayList<Object>();
                    averageTemperaturePoint = new ArrayList<Object>();

                    // 图片上传日期
                    Date date = infraredMarkRegionDataBean.getPicture()
                            .getSaveTime();

                    // 最高温
                    highTemperaturePoint.add(date.getTime());
                    highTemperaturePoint.add(infraredMarkRegionDataBean
                            .getHighTemperature());
                    highTemperatureList.add(highTemperaturePoint);

                    // 最低温
                    lowTemperaturePoint.add(date.getTime());
                    lowTemperaturePoint.add(infraredMarkRegionDataBean
                            .getLowTemperature());
                    lowTemperatureList.add(lowTemperaturePoint);

                    // 平均温
                    averageTemperaturePoint.add(date.getTime());
                    averageTemperaturePoint.add(infraredMarkRegionDataBean
                            .getAverageTemperature());
                    averageTemperatureList.add(averageTemperaturePoint);
                }

                // mapHighTemperatureSeries.put("yAxis", 0);
                // mapHighTemperatureSeries.put("color", "#4572A7");
                mapHighTemperatureSeries.put("name", "最高温度");
                mapHighTemperatureSeries.put("type", "spline");
                mapHighTemperatureSeries.put("data", highTemperatureList);

                // mapAverageTemperatureSeries.put("yAxis", 1);
                // mapAverageTemperatureSeries.put("color", "#6495ED");
                mapAverageTemperatureSeries.put("name", "平均温度");
                mapAverageTemperatureSeries.put("type", "spline");
                mapAverageTemperatureSeries.put("data", averageTemperatureList);

                // mapLowTemperatureSeries.put("yAxis", 2);
                // mapLowTemperatureSeries.put("color", "#4572A7");
                mapLowTemperatureSeries.put("name", "最低温度");
                mapLowTemperatureSeries.put("type", "spline");
                mapLowTemperatureSeries.put("data", lowTemperatureList);

                listSeries.add(mapHighTemperatureSeries);
                listSeries.add(mapAverageTemperatureSeries);
                listSeries.add(mapLowTemperatureSeries);
            }
        }
        return listSeries;
    }

}
