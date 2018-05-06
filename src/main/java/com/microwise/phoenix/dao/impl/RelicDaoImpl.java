package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.dao.RelicStatDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 藏品管理：系统文物统计dao 实现
 *
 * @author xu.baoji
 * @date 2013-7-16
 * @check @duan.qixin 2013-7-18 #4556
 */
@Dao
@Phoenix
public class RelicDaoImpl extends PhoenixBaseDao implements RelicStatDao {

    @Override
    public Integer findRelicSum(String siteId) {
        return getSqlSession().<Integer>selectOne("phoenix.mybatis.RelicStatDao.findRelicSum",
                siteId);
    }

    @Override
    public List<Map<String, Object>> findEraStat(String siteId, int relicSum) {
        return getSqlSession().selectList("phoenix.mybatis.RelicStatDao.findEraStat",
                disposeParamMap(siteId, relicSum));
    }

    @Override
    public List<Map<String, Object>> findLevelStat(String siteId, int relicSum) {
        return getSqlSession().selectList("phoenix.mybatis.RelicStatDao.findLevelStat",
                disposeParamMap(siteId, relicSum));
    }

    @Override
    public List<Map<String, Object>> findTextureStat(String siteId, int relicSum) {
        return getSqlSession().selectList("phoenix.mybatis.RelicStatDao.findTextureStat",
                disposeParamMap(siteId, relicSum));
    }

    @Override
    public List<Map<String, Object>> findZoneStat(String siteId, int relicSum) {
        return getSqlSession().selectList("phoenix.mybatis.RelicStatDao.findZoneStat",
                disposeParamMap(siteId, relicSum));
    }

    /***
     * 处理 mybatis参数 此处dao 方法参数完全相同 避免重复 统一使用该方法封装参数
     *
     * @param siteId   站点编号
     * @param relicSum 文物总数量
     * @return Map<String, Object> 参数 map 集合
     * @author xu.baoji
     * @date 2013-7-16
     */
    private Map<String, Object> disposeParamMap(String siteId, int relicSum) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("relicSum", relicSum);
        return paramMap;
    }

}
