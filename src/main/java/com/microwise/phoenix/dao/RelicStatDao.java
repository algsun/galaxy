package com.microwise.phoenix.dao;

import java.util.List;
import java.util.Map;

/**
 * 藏品管理：文物统计dao 接口
 *
 * @author xu.baoji
 * @date 2013-7-16
 * @check @duan.qixin 2013-7-18 @4556
 */
public interface RelicStatDao {

    /**
     * 查询 站点下文物总数量
     *
     * @param siteId 站点编号
     * @return integer 站点下文物总数量
     * @author xu.baoji
     * @date 2013-7-16
     */
    public Integer findRelicSum(String siteId);

    /***
     * 查询系统文物级别统计 % 返回值 每个map为一种 级别的百分比统计 建‘key’ 级别名称 ，建‘value’ 该级别的 百分比值
     *
     * @param siteId   站点编号
     * @param relicSum 文物数量
     * @return List<Map<String, Object>>
     * @author xu.baoji
     * @date 2013-7-16
     */
    public List<Map<String, Object>> findLevelStat(String siteId, int relicSum);

    /***
     * 查询系统文物年代统计 % 返回值 每个map为一种 年代的百分比统计 建‘key’ 年代名称 ，建‘value’ 该年代的 百分比值
     *
     * @param siteId   站点编号
     * @param relicSum 文物数量
     * @return List<Map<String, Object>>
     * @author xu.baoji
     * @date 2013-7-16
     */
    public List<Map<String, Object>> findEraStat(String siteId, int relicSum);

    /***
     * 查询系统文物质地统计 % 返回值 每个map为一种 质地的百分比统计 建‘key’ 质地名称 ，建‘value’ 该质地的 百分比值
     *
     * @param siteId   站点编号
     * @param relicSum 文物数量
     * @return List<Map<String, Object>>
     * @author xu.baoji
     * @date 2013-7-16
     */
    public List<Map<String, Object>> findTextureStat(String siteId, int relicSum);

    /***
     * 查询系统文物区域统计 % 返回值 每个map为一种 区域的百分比统计 建‘key’ 区域名称 ，建‘value’ 该区域的 百分比值
     *
     * @param siteId   站点编号
     * @param relicSum 文物数量
     * @return List<Map<String, Object>>
     * @author xu.baoji
     * @date 2013-7-16
     */
    public List<Map<String, Object>> findZoneStat(String siteId, int relicSum);

}
