package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.bean.po.OutEventInfo;
import com.microwise.phoenix.bean.vo.OutEventStats;
import com.microwise.phoenix.bean.vo.OutEventStatsInfo;
import com.microwise.phoenix.bean.vo.RelicFrequency;
import com.microwise.phoenix.dao.OutEventStatsDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出库文物 统计 dao 实现类
 *
 * @author xu.baoji
 * @date 2013-7-9
 * @check @gaohui #4468 2013-07-12
 * @check @xu.baoji #4500 2013-07-12
 */
@Phoenix
@Dao
public class OutEventStatsDaoImpl extends PhoenixBaseDao implements OutEventStatsDao {

    @Override
    public OutEventStats findOutEventStat(String siteId, Date startDate, Date endDate, boolean isYear) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isYear", isYear);
        return getSqlSession().
                selectOne("phoenix.mybatis.OutEventStatDao.findOutEventStat", paramMap);
    }

    @Override
    public List<OutEventInfo> findOutEventInfo(String siteId, int type, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("type", type);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().
                selectList("phoenix.mybatis.OutEventStatDao.findOutEventInfo", paramMap);
    }

    @Override
    public OutEventStatsInfo findOutEventStatInfo(String siteId, int type, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("type", type);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().
                selectOne("phoenix.mybatis.OutEventStatDao.findOutEventStatInfo", paramMap);
    }

    @Override
    public List<Map<String, Object>> findEraStat(String siteId, int eventRelicSum, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("eventRelicSum", eventRelicSum);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().
                selectList("phoenix.mybatis.OutEventStatDao.findEraStat", paramMap);
    }

    @Override
    public List<Map<String, Object>> findLevelStat(String siteId, int eventRelicSum, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("eventRelicSum", eventRelicSum);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().
                selectList("phoenix.mybatis.OutEventStatDao.findLevelStat", paramMap);
    }

    @Override
    public List<Map<String, Object>> findTextureStat(String siteId, int eventRelicSum, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("eventRelicSum", eventRelicSum);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().
                selectList("phoenix.mybatis.OutEventStatDao.findTextureStat", paramMap);
    }

    @Override
    public List<Map<String, Object>> findZoneStats(String siteId, int eventRelicSum, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("eventRelicSum", eventRelicSum);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().
                selectList("phoenix.mybatis.OutEventStatDao.findZoneStat", paramMap);
    }

    @Override
    public Date findOldestOfOutedRelic(String siteId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("siteId", siteId);
        return getSqlSession().selectOne("phoenix.mybatis.OutEventStatDao.findOldestDateOfOutedRelic", param);
    }


    @Override
    public List<RelicFrequency> findRelicOutRanking(String siteId, Date startDate, Date endDate, int start, int max) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("siteId", siteId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("start", start);
        param.put("max", max);

        return getSqlSession().selectList("phoenix.mybatis.OutEventStatDao.findRelicOutRanking", param);
    }
}
