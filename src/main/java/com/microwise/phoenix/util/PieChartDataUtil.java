package com.microwise.phoenix.util;

import com.microwise.phoenix.bean.vo.healthCheck.RelicPieData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 饼图数据处理工具类
 *
 * @author xu.baoji
 * @date 2013-7-17
 */
public class PieChartDataUtil {

    private static final int PIE_SUM = 90;

    private static final int VALUE_INDEX = 1;

    /***
     * 将小于 10 的饼图数据 处理为 其他
     *
     * @return
     * @author xu.baoji
     * @date 2013-8-5
     */
    private static RelicPieData disposePieChartDataTo(List<List<Object>> pieChartDatas) {
        // 用来统计饼图数据和的变量
        double sum = 0;
        // 数据数量
        int size = pieChartDatas.size();

        RelicPieData relicPieData = new RelicPieData();

        // 使用新的列表存放 饼图数据
        List<List<Object>> pieChartData = new ArrayList<List<Object>>();

        // 其他数据列表
        List<List<Object>> otherData = new ArrayList<List<Object>>();

        if (size > 0) {// 如果有数据 处理数据
            relicPieData.setHasData(true);
            for (int i = 0; i < size; i++) {
                // 获得饼图的单个数据
                List<Object> pie = pieChartDatas.get(i);

                // 判断该数据是否为第一个其他数据
                if (sum >= PIE_SUM && (size - i) > 1 && !relicPieData.isHasOtherData()) {
                    // 有其他数据
                    relicPieData.setHasOtherData(true);
                    // 组装最后一个图表数据
                    List<Object> lastPie = new ArrayList<Object>();
                    lastPie.add("其他");
                    lastPie.add(100 - sum);
                    pieChartData.add(lastPie);
                }

                // 如果relicPieData 无其他数据，则该数据为标准数据
                if (!relicPieData.isHasOtherData()) {
                    pieChartData.add(pie);
                } else {
                    // 为其他数据列表添加数据
                    otherData.add(pie);
                }
                // 计算饼图数据 和
                sum = sum + ((BigDecimal) pie.get(VALUE_INDEX)).doubleValue();
            }
        }
        relicPieData.setPieData(pieChartData);
        relicPieData.setOtherData(otherData);
        return relicPieData;
    }

    /****
     * 处理 饼状图数据
     *
     * @param basicDatas 饼状图 基本数据
     * @return List<List<Object>> 处理后的 饼状图数据
     * @author xu.baoji
     * @date 2013-7-10
     */
    private static List<List<Object>> disposePieChartDataToList(List<Map<String, Object>> basicDatas) {
        List<List<Object>> pieChartDatas = new ArrayList<List<Object>>();
        for (Map<String, Object> basicData : basicDatas) {
            List<Object> pieChartData = new ArrayList<Object>();
            pieChartData.add(basicData.get("key"));
            pieChartData.add(basicData.get("value"));
            pieChartData.add(basicData.get("number"));
            pieChartDatas.add(pieChartData);
        }
        return pieChartDatas;
    }

    /***
     * 处理饼图数据
     *
     * @param basicDatas
     * @return
     * @author xu.baoji
     * @date 2013-8-5
     */
    public static RelicPieData disposePieChartDataToRelicData(List<Map<String, Object>> basicDatas) {
        return disposePieChartDataTo(disposePieChartDataToList(basicDatas));
    }

}
