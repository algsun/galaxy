package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.ZonePeakValue;
import com.microwise.phoenix.dao.ZonePeakValueDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域稳定性Dao
 *
 * @author xuyuexi
 * @date 14-9-26
 */
@Beans.Dao
@Phoenix
public class ZonePeakValueDaoImpl extends PhoenixBaseDao implements ZonePeakValueDao {

    @Override
    public ZonePeakValue findZonePeakValues(String siteId, int sensorPhysicalId, Date start, Date end, int month, int type) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sensorPhysicalId", sensorPhysicalId);
        paramMap.put("siteId", siteId);
        paramMap.put("start", start);
        paramMap.put("end", end);
        paramMap.put("month", month + 1);
        paramMap.put("type", type);
        List<ZonePeakValue> zonePeakValues = getSqlSession().selectList("phoenix.mybatis.ZonePeakValueDao.findZonePeakValues", paramMap);
        if (zonePeakValues.size() > 0) {
            return zonePeakValues.get(0);
        }
        return null;
    }

}
