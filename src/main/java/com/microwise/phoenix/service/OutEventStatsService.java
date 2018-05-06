package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.vo.OutEventStats;
import com.microwise.phoenix.bean.vo.OutEventStatsInfo;
import com.microwise.phoenix.bean.vo.RelicBasicStats;
import com.microwise.phoenix.bean.vo.RelicFrequency;

import java.util.Date;
import java.util.List;

/**
 * 出库事件 统计service
 *
 * @author xu.baoji
 * @date 2013-7-9
 * @check @gaohui #4469 2013-07-12
 * @check @xu.baoji #4500 2013-07-12
 */
public interface OutEventStatsService {

    /***
     * 查询 出库统计 数据
     * <p/>
     * 每年出库统计数据实体列表,按年份倒序排序. 第0个为当前年，第1个为上一年
     * 如果无数据，集合的个数依然是 2,但对应的元素对象为null
     *
     * @param siteId 站点编号
     * @param year   要查询的年
     * @return List<OutEventStat>
     * @author xu.baoji
     * @date 2013-7-9
     */
    public List<OutEventStats> findOutEventStat(String siteId, int year);

    /***
     * 查询 出库统计 数据
     * <p/>
     * 如果是年类型：
     * 每年出库统计数据实体列表,按年份倒序排序. 第0个为当前年，第1个为上一年
     * 如果无数据，集合的个数依然是 2,但对应的元素对象为null
     * 如果是月类型：
     * 只返回当前月的出库统计数据，list 的size 为1
     *
     * @param siteId 站点编号
     * @param date   时间参数
     * @param type   类型
     * @return List<OutEventStat>
     * @author xu.baoji
     * @date 2013-7-9
     */
    public List<OutEventStats> findOutEventStat(String siteId, Date date, int type);

    /***
     * 查询 出库记录单统计信息
     * <p/>
     * list：第一个元素为外出借展类型 ，第二个元素为科研修复类型   。如果没有数据list 集合 size 依然为2  ，但对应实体为null、
     *
     * @param siteId 站点编号
     * @param year   要查询的年
     * @return List<OutEventStatInfo> 出库记录单统计信息
     * @author xu.baoji
     * @date 2013-7-9
     */
    public List<OutEventStatsInfo> findOutEventStatInfo(String siteId, int year);

    /***
     * 查询 出库文物 饼图数据
     *
     * @param siteId 站点编号
     * @param year   要查询的年
     * @return RelicBasicStat 文物统计基本饼状图实体
     * @author xu.baoji
     * @date 2013-7-9
     */
    public RelicBasicStats findPieChartData(String siteId, int year);

    /***
     * 查询 出库文物 饼图数据
     *
     * @param siteId 站点编号
     * @param date   时间参数
     * @param type   类型
     * @return RelicBasicStat 文物统计基本饼状图实体
     * @author xu.baoji
     * @date 2013-7-9
     */
    public RelicBasicStats findPieChartData(String siteId, Date date, int type);

    /**
     * 返回某个站点下，最早出库文物的时间
     *
     * @param siteId 站点ID
     * @return 时间
     * @author gahui
     * @date 2013-07-10
     */
    Date findOldestOfOutedRelic(String siteId);

    /**
     * 根据站点按年查文物出库次数的排名
     *
     * @param siteId 站点id
     * @param year   哪一年
     * @param max    最多文物数量
     * @return
     * @author
     * @date 2013-07-10
     */
    List<RelicFrequency> findRelicOutRankingByYear(String siteId, Date year, int max);

    /**
     * 查询文物出库次数的排名
     *
     * @param siteId 站点id
     * @param date   查询参数
     * @param type   查询类型
     * @param max    最多文物数量
     * @return
     * @author
     * @date 2013-07-10
     */
    List<RelicFrequency> findRelicOutRanking(String siteId, Date date, int type, int max);

    /**
     * 根据站点查文物出库次数总排名
     *
     * @param siteId
     * @param max
     * @return
     */
    List<RelicFrequency> findRelicOutRanking(String siteId, int max);
}
