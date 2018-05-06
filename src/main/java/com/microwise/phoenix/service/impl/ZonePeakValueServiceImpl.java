package com.microwise.phoenix.service.impl;

import com.microwise.blueplanet.dao.LocationDao;
import com.microwise.blueplanet.dao.ZoneDao;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.phoenix.bean.vo.ZonePeakValue;
import com.microwise.phoenix.dao.ZonePeakValueDao;
import com.microwise.phoenix.service.ZonePeakValueService;
import com.microwise.phoenix.sys.Phoenix;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 区域稳定性Service实现
 *
 * @author xuyuexi
 * @date 14-9-26
 */
@Phoenix
@Beans.Service
@Transactional
public class ZonePeakValueServiceImpl implements ZonePeakValueService {

    @Autowired
    public ZonePeakValueDao zonePeakValueDao;
    @Autowired
    public ZoneDao zoneDao;
    @Autowired
    public LocationDao locationDao;

    @Override
    public List<ZonePeakValue> findZonePeakValue(String siteId, int sensorPhysicalid, Date date, int type) {
        List<ZonePeakValue> zonePeakValues = new ArrayList<ZonePeakValue>();
        int year = new DateTime(date).getYear();
        for (int i = 0; i < 12; i++) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, i, 1);
            Date start = DateTimeUtil.startOfMonth(cal.getTime());
            Date end = DateTimeUtil.endOfMonth(cal.getTime());
            ZonePeakValue zonePeakValue = zonePeakValueDao.findZonePeakValues(siteId, sensorPhysicalid, start, end, i, type);
            if (zonePeakValue != null) {
                zonePeakValues.add(zonePeakValue);
            }
        }
        return packageZonePeakValue(zonePeakValues);
    }

    public List<ZonePeakValue> packageZonePeakValue(List<ZonePeakValue> list) {
        for (ZonePeakValue zonePeakValue : list) {
            zonePeakValue.setZoneName(zoneDao.findZoneById(zonePeakValue.getZoneId()).getZoneName());
        }
        return list;
    }
}
