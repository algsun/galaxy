package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.bean.po.uma.ZoneStat;
import com.microwise.phoenix.dao.UserStopOverDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人员管理：人员在区域活动时长统计dao 实现
 *
 * @author xu.baoji
 * @date 2013-7-15
 * @check @duan.qixin 2013-7-18 #4535
 */
@Dao
@Phoenix
public class UserStopOverDaoImpl extends PhoenixBaseDao implements UserStopOverDao {

    @Override
    public List<ZoneStat> findZoneStat(String siteId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().selectList("phoenix.mybatis.UserStopOverDao.findZoneStat", paramMap);
    }
}
