package com.microwise.halley.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.AlarmPO;
import com.microwise.halley.dao.AlarmDao;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报警记录 Dao 实现
 *
 * @author li.jianfei
 * @date 2014-05-19
 */
@Beans.Dao
@Halley
public class AlarmDaoImpl extends HalleyBaseDao implements AlarmDao {
    @Override
    public int findAlarmListCount(int exhibitionId, Date beginTime, Date endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("exhibitionId", exhibitionId);
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        return getSqlSession().<Integer>selectOne("halley.mybatis.AlarmDao.findAlarmListCount", params);
    }

    @Override
    public List<AlarmPO> findAlarmListByPage(int exhibitionId, Date beginTime, Date endTime, int startPage, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("exhibitionId", exhibitionId);
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        int begin = (startPage - 1) * pageSize;
        params.put("startPage", begin);
        params.put("pageSize", pageSize);
        return getSqlSession().selectList("halley.mybatis.AlarmDao.findAlarmListByPage", params);
    }
}
