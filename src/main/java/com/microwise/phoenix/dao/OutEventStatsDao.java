package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.po.OutEventInfo;
import com.microwise.phoenix.bean.vo.OutEventStats;
import com.microwise.phoenix.bean.vo.OutEventStatsInfo;
import com.microwise.phoenix.bean.vo.RelicFrequency;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 出库文物统计 dao
 *
 * @author xu.baoji
 * @date 2013-7-9
 * @check @gaohui #4468 2013-07-12
 * @check @xu.baoji #4500 2013-07-12
 */
public interface OutEventStatsDao {

    /***
     * 查询 出库文物统计信息
     *
     * @param siteId    站点编号
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param isYear    是否是年
     * @return OutEventStat 出库文物统计实体
     * @author xu.baoji
     * @date 2013-7-9
     */
    public OutEventStats findOutEventStat(String siteId, Date startDate, Date endDate, boolean isYear);

    /***
     * 查询出库文物级别统计 %
     *
     * @param siteId        站点编号
     * @param eventRelicSum 当前出库文物中数量
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @return List<Map<String,Object>> Map 为一种级别的统计信息 key 字段名 ：name 级别名
     * 、levelCount该级别占有的 %
     * @author xu.baoji
     * @date 2013-7-9
     */
    public List<Map<String, Object>> findLevelStat(String siteId, int eventRelicSum, Date startDate, Date endDate);

    /***
     * 查询出库文物级别统计 %
     *
     * @param siteId        站点编号
     * @param eventRelicSum 当前出库文物中数量
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @return List<Map<String,Object>> Map 为一种年代的统计信息 key 字段名 ：name 年代名
     * 、levelCount该年代占有的 %
     * @author xu.baoji
     * @date 2013-7-9
     */
    public List<Map<String, Object>> findEraStat(String siteId, int eventRelicSum, Date startDate, Date endDate);

    /***
     * 查询出库文物级别统计 %
     *
     * @param siteId        站点编号
     * @param eventRelicSum 当前出库文物中数量
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @return List<Map<String,Object>> Map 为一种质地的统计信息 key 字段名 ：name 质地名
     * 、levelCount该质地占有的 %
     * @author xu.baoji
     * @date 2013-7-9
     */
    public List<Map<String, Object>> findTextureStat(String siteId, int eventRelicSum, Date startDate, Date endDate);

    /***
     * 查询出库文物 区域统计 %
     *
     * @param siteId        站点编号
     * @param eventRelicSum 当前出库文物中数量
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @return List<Map<String,Object>> Map 为一种区域的统计信息 key 字段名 ：key 区域名
     * 、value 该区域占有的 %
     * @author xu.baoji
     * @date 2013-7-9
     */
    public List<Map<String, Object>> findZoneStats(String siteId, int eventRelicSum, Date startDate, Date endDate);

    /**
     * 查询出库事件 记录信息
     *
     * @param siteId    站点编号
     * @param type      事件类型  1、外出借展  2、科研修复
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<OutEventInfo> 出库事件 信息
     * @author xu.baoji
     * @date 2013-7-9
     */
    public List<OutEventInfo> findOutEventInfo(String siteId, int type, Date startDate, Date endDate);

    /***
     * 查询出库事件 记录统计
     *
     * @param siteId    站点编号
     * @param type      查询类型  1、外出借展  2、科研修复
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return OutEventStatInfo 出库事件记录统计实体
     * @author xu.baoji
     * @date 2013-7-9
     */
    public OutEventStatsInfo findOutEventStatInfo(String siteId, int type, Date startDate, Date endDate);

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
     * 返回文物出库排名, 按次数倒序排
     *
     * @param siteId    站点id
     * @param startDate 开始时间
     * @param endDate   结果时间
     * @param start     开始
     * @param max       大小
     * @return
     */
    List<RelicFrequency> findRelicOutRanking(String siteId, Date startDate, Date endDate, int start, int max);
}
