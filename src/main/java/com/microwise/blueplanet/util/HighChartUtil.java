package com.microwise.blueplanet.util;

import com.microwise.blueplanet.bean.vo.*;
import com.microwise.common.sys.Constants.ChartConstants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 将系统数据转换为HighChart图表数据的工具类
 *
 * @author zhangpeng
 * @date 2013-2-28
 * @check 2013-03-11 xubaoji svn:2014
 */
public class HighChartUtil {

    /**
     * 查询类型：基础曲线图
     */
    private static final String TYPE_BASIC = "basic";
    /**
     * 查询类型：均值曲线图
     */
    private static final String TYPE_AVG = "avg";

    /**
     * 查询类型：降雨量图
     */
    private static final String TYPE_RAINFULL = "rainfull";

    /**
     * 查询类型：光照图
     */
    private static final String TYPE_LIGHT = "light";

    /**
     * 风向标识数组
     */
    public static final String[] directions = {"N", "Nnw", "Nw", "Wnw", "W", "Wsw", "Sw", "Ssw", "S", "Sse",
            "Se", "Ese", "E", "Ene", "Ne", "Nne"

    };

    /**
     * 风向标识List
     */
    public static final List<String> list = Arrays.asList(directions);

    /**
     * 数据库字段中o开头的用以标识风向
     */
    public static final String WIND_ROSE_O = "o";

    /**
     * 数据库字段中s开头的用以标识风速
     */
    public static final String WIND_ROSE_S = "s";

    /**
     * 转换基础曲线图原始数据为HighChart需要的格式
     *
     * @param locationId   位置点ID
     * @param locationName 设备名称
     * @param chartList    原始数据列表
     * @return Map
     * <p/>
     * <pre>
     *         key：hasBasicData是否有数据，boolean值
     *         key：locationId对应位置点Id
     *         key：locationName对应设备名称
     *         key：chartList对应单个设备返回图形数据对象ChartVO列表List<HighChartVO>
     *         注：Map的key在系统com.microwise.blueplanet.sys. Constants. ChartConstants下，见注释
     *         </pre>
     */
    public static Map<String, Object> packageBasic(String locationId,
                                                   String locationName, List<ChartVO> chartList) {
        return packageChartData(locationId, locationName, chartList, TYPE_BASIC);
    }

    /**
     * 转换均值曲线图原始数据为HighChart需要的格式
     *
     * @param deviceId   设备id
     * @param deviceName 设备名称
     * @param chartList  原始数据列表
     * @return Map
     * <p/>
     * <pre>
     *                                                                                                                                                                         key：hasBasicData是否有数据，boolean值
     *                                                                                                                                                                         key：deviceId对应设备id
     *                                                                                                                                                                         key：deviceName对应设备名称
     *                                                                                                                                                                         key：chartList对应单个设备返回图形数据对象ChartVO列表List<HighChartVO>
     *                                                                                                                                                                         注：Map的key在系统com.microwise.blueplanet.sys. Constants. ChartConstants下，见注释
     *                                                                                                                                                                         </pre>
     */
    public static Map<String, Object> packageAvg(String deviceId,
                                                 String deviceName, List<ChartVO> chartList) {
        return packageChartData(deviceId, deviceName, chartList, TYPE_AVG);
    }

    /**
     * 降雨量图原始数据为HighChart需要的格式
     *
     * @param locationId   位置点id
     * @param locationName 位置点名称
     * @param chartList    原始数据列表
     * @return Map
     * <p/>
     * <pre>
     *         key：hasRailfulData是否有降雨量数据，boolean值
     *         key：locationId 对应位置点id
     *         key：locationName 对应位置点名称
     *         key：chartList对应单个设备返回图形数据对象ChartVO列表List<HighChartVO>
     *         注：Map的key在系统com.microwise.blueplanet.sys. Constants. ChartConstants下，见注释
     *         </pre>
     */
    public static Map<String, Object> packageRainfull(String locationId,
                                                      String locationName, List<ChartVO> chartList) {
        return packageChartData(locationId, locationName, chartList, TYPE_RAINFULL);
    }

    /**
     * 累计光照原始数据为HighChart需要的格式
     *
     * @param locationId   位置点id
     * @param locationName 位置点名称
     * @param chart        原始数据列表
     * @return Map
     * <p/>
     * <pre>
     *         key：hasRailfulData是否有降雨量数据，boolean值
     *         key：locationId 对应位置点id
     *         key：locationName 对应位置点名称
     *         key：chartList对应单个设备返回图形数据对象ChartVO列表List<HighChartVO>
     *         注：Map的key在系统com.microwise.blueplanet.sys. Constants. ChartConstants下，见注释
     *         </pre>
     */
    public static Map<String, Object> packageLight(String locationId,
                                                   String locationName, ChartVO chart) {
        List<ChartVO> chartList = new ArrayList<ChartVO>();
        chartList.add(chart);
        return packageChartData(locationId, locationName, chartList, TYPE_LIGHT);
    }

    /**
     * 风向玫瑰图原始数据为HighChart需要的格式
     *
     * @param locationId   位置点id
     * @param locationName 位置点名称
     * @param windRose     风向玫瑰图原始数据
     * @return HighChartWindRoseVO highChart需要的风向玫瑰图数据vo对象
     */
    public static HighChartWindRoseVO packageWindRose(String locationId,
                                                      String locationName, WindRoseVO windRose) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        if (windRose == null) {
            return new HighChartWindRoseVO(false);
        } else {
            List<HighChartDirectionVO> resultList = new ArrayList<HighChartDirectionVO>();
            for (String field : list) {
                HighChartDirectionVO directionVO = new HighChartDirectionVO();
                directionVO.setDirectionId(field.toUpperCase());
                Method getWindFrequency = windRose.getClass().getSuperclass()
                        .getDeclaredMethod("get" + WIND_ROSE_O + field);
                Double frequency = (Double) getWindFrequency.invoke(windRose);
                directionVO.setWindFrequency(frequency);
                Method getWindSpeed = windRose.getClass().getSuperclass()
                        .getDeclaredMethod("get" + WIND_ROSE_S + field);
                Double windSpeed = (Double) getWindSpeed.invoke(windRose);
                directionVO.setWindSpeed(windSpeed);
                resultList.add(directionVO);
            }
            HighChartWindRoseVO high = new HighChartWindRoseVO(locationId,
                    locationName, windRose.getWindcalm(), resultList, true);
            high.setText((locationName != null ? locationName : locationId));
            return high;
        }
    }

    /**
     * 封装原始数据为HighChart格式
     *
     * @param locationId   位置点ID
     * @param locationName 位置点名称
     * @param chartList    原始数据列表
     * @param methodType   内部常量，根据调用方法类型不同，判断是否有数据会有差异
     * @return Map
     */
    private static Map<String, Object> packageChartData(String locationId,
                                                        String locationName, List<ChartVO> chartList, String methodType) {
        Map<String, Object> map = new HashMap<String, Object>();
        // TODO 目前为防止程序不报错这样写，需要调整
        if (TYPE_BASIC.equals(methodType)) {
            map.put(ChartConstants.LOCATION_ID, locationId);
            map.put(ChartConstants.LOCATION_NAME, locationName);
        } else {
            map.put(ChartConstants.DEVICE_ID, locationId);
            map.put(ChartConstants.DEVICE_NAME, locationName);
        }

        boolean hasData = hasData(chartList, methodType);
        if (TYPE_BASIC.equals(methodType)) {
            map.put(ChartConstants.HAS_DATA_BASIC, hasData);
        } else if (TYPE_RAINFULL.equals(methodType)) {
            map.put(ChartConstants.HAS_DATA_RAINFALL, hasData);
        } else if (TYPE_LIGHT.equals(methodType)) {
            map.put(ChartConstants.HAS_DATA_LIGHT, hasData);
        } else if (TYPE_AVG.equals(methodType)) {
            map.put(ChartConstants.HAS_DATA_AVG, hasData);
        }
        if (methodType.equals(TYPE_BASIC) || methodType.equals(TYPE_RAINFULL) || methodType.equals(TYPE_AVG)) {
            List<HighChartVO> highList = new ArrayList<HighChartVO>();
            // 封装单个HighChartVO
            for (int i = 0; i < chartList.size(); i++) {
                HighChartVO high = packageChartData(locationId, locationName,
                        chartList.get(i), methodType);
                high.setyAxis(i);
                highList.add(high);
            }
            map.put(ChartConstants.CHART_LIST, highList);
        } else if (methodType.equals(TYPE_LIGHT)) {
            HighChartVO highVO = packageChartData(locationId, locationName,
                    chartList.get(0), methodType);
            map.put(ChartConstants.CHART_DATA, highVO);
        }
        return map;
    }


    /**
     * 封装单个HighChartVO
     *
     * @param locationId   设备id
     * @param locationName 设备名称
     * @param chart        单个设备原始数据
     * @param methodType   内部常量，根据调用方法类型不同，判断是否有数据会有差异
     * @return HighChartVO
     */
    private static HighChartVO packageChartData(String locationId,
                                                String locationName, ChartVO chart, String methodType) {
        HighChartVO high = new HighChartSonVO(chart);
        high.setLocationId(locationId);
        if (TYPE_RAINFULL.equals(methodType)) {
            high.setName(chart.getCnName() + "(" + chart.getUnits() + ")");
        } else {
            high.setName((locationName != null ? locationName : locationId) + "-"
                    + chart.getCnName() + "(" + chart.getUnits() + ")");
        }
        if (TYPE_BASIC.equals(methodType) || TYPE_AVG.equals(methodType)) {
            high.setType(ChartConstants.CHART_TYPE_SPLINE);
        } else if ((TYPE_RAINFULL.equals(methodType) && ChartConstants.SENSORINFO_RB.equals(chart
                .getSensorPhysicalId())) || TYPE_LIGHT.equals(methodType)) {
            high.setType(ChartConstants.CHART_TYPE_COLUMN);
        }
        return high;
    }

    /**
     * 是否有数据（根据类型不同判断不同）
     *
     * @param chartList  原始数据列表
     * @param methodType 内部常量，根据调用方法类型不同，判断是否有数据会有差异
     * @return boolean true 有 false 无
     */
    private static boolean hasData(List<ChartVO> chartList, String methodType) {
        for (ChartVO chart : chartList) {
            // 基础曲线图常规查询时，只要有一个ChartVO有数据就返回有
            if (TYPE_BASIC.equals(methodType) && chart.isHasData()) {
                return true;
            }
            // 降雨量图查询时，只有降雨量指标有数据时，才表示有数据
            else if (TYPE_RAINFULL.equals(methodType)
                    && chart.getSensorPhysicalId().equals(ChartConstants.SENSORINFO_RB)
                    && chart.isHasData()) {
                return true;
            }
            // 光照
            else if (TYPE_LIGHT.equals(methodType)) {
                return chartList.get(0).isHasData();
            }
            // 均值
            else if (TYPE_AVG.equals(methodType)) {
                return chartList.get(0).isHasData();
            }
        }
        return false;
    }
}
