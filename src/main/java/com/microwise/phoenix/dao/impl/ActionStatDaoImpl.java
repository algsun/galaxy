package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.bean.po.uma.ActionStat;
import com.microwise.phoenix.dao.ActionStatDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人员管理：规则统计dao 实现类
 *
 * @author xu.baoji
 * @check @duan.qixin 2013-7-18 #4567
 * @date 2013-7-17
 */
@Dao
@Phoenix
public class ActionStatDaoImpl extends PhoenixBaseDao implements ActionStatDao {

    @Override
    public List<ActionStat> findActionStat(String siteId, long startDate, long endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().selectList("phoenix.mybatis.ActionStatDao.findActionStat", paramMap);
    }
}
