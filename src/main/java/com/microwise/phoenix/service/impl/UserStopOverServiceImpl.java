package com.microwise.phoenix.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.phoenix.bean.po.uma.ZoneStat;
import com.microwise.phoenix.bean.vo.UserStopOver;
import com.microwise.phoenix.dao.UserStopOverDao;
import com.microwise.phoenix.service.UserStopOverService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.uma.util.DateTypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 人员管理：人员在区域活动时长统计service 实现
 *
 * @author xu.baoji
 * @date 2013-7-15
 * @check @duan.qixin 2013-7-18 #4538
 */
@Phoenix
@Service
@Transactional
public class UserStopOverServiceImpl implements UserStopOverService {

    /**
     * 人员在区域活动时长dao接口
     */
    @Autowired
    private UserStopOverDao userStopOverDao;

    @Override
    public UserStopOver findUserStopOver(String siteId, Date date, int type) {
        // 获得查询开始时间和结束时间
        Date startDate = DateTypeGenerator.start(type, date);
        Date endDate = DateTypeGenerator.end(type, date);
        List<ZoneStat> zoneStats = userStopOverDao.findZoneStat(siteId, startDate, endDate);

        UserStopOver userStopOver = new UserStopOver();
        // 如果有数据，将数据处理封装在 UserStopOver 对象中
        if (zoneStats != null && zoneStats.size() > 0) {
            userStopOver.setHasData(true);
            disposeData(userStopOver, zoneStats);
        }
        return userStopOver;
    }

    /***
     * 处理统计数据
     *
     * @param userStopOver
     * @param zoneStats
     * @author xu.baoji
     * @date 2013-7-15
     */
    private void disposeData(UserStopOver userStopOver, List<ZoneStat> zoneStats) {
        List<String> zoneNames = new ArrayList<String>();
        List<Float> avgTimes = new ArrayList<Float>();
        List<Float> inTimes = new ArrayList<Float>();
        String avgZoneName = "";
        Float maxAvgTime = 0f;
        String inZoneName = "";
        Float maxInTime = 0F;
        for (ZoneStat zoneStat : zoneStats) {
            String zoneName = zoneStat.getZoneName();
            Float avgTime = DateTimeUtil.millisecondToHour(zoneStat.getAvgTime());
            Float inTime = DateTimeUtil.millisecondToHour(zoneStat.getInTime());
            // 将对象数据存放进 list 列表中
            zoneNames.add(zoneName);
            avgTimes.add(avgTime);
            inTimes.add(inTime);
            // 处理平均 时长最大值
            if (avgTime > maxAvgTime) {
                maxAvgTime = avgTime;
                avgZoneName = zoneName;
            }
            // 处理总时长最大值
            if (inTime > maxInTime) {
                maxInTime = inTime;
                inZoneName = zoneName;
            }
        }
        // 将处理好的数据 存放在 UserStopOver 对象中
        userStopOver.setZoneNames(zoneNames);
        userStopOver.setAvgTime(avgTimes);
        userStopOver.setInTime(inTimes);
        userStopOver.setAvgZoneName(avgZoneName);
        userStopOver.setMaxAvgTime(maxAvgTime);
        userStopOver.setInZoneName(inZoneName);
        userStopOver.setMaxInTime(maxInTime);
    }

}
