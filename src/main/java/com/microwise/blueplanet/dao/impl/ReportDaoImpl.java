package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.vo.ReportVO;
import com.microwise.blueplanet.dao.ReportDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表订阅 dao 实现
 *
 * @author xubaoji
 * @date 2013-6-13
 * @check li.jianfei 2013-06-20 #4188
 */
@Dao
@Blueplanet
public class ReportDaoImpl extends BlueplanetBaseDao implements ReportDao {


    @Override
    public List<ReportVO> findDayReportInfo(String siteId, String date) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("date", date);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ReportDao.findDayReportInfo", map);
    }

    @Override
    public List<ReportVO> findReportInfo(String siteId, Date startDate,
                                         Date endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ReportDao.findReportInfo", map);
    }
}
