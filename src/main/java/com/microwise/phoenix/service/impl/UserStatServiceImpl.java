package com.microwise.phoenix.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.phoenix.bean.po.uma.UserStat;
import com.microwise.phoenix.dao.UserStatDao;
import com.microwise.phoenix.service.UserStatService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.uma.util.DateTypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 人员管理：人员统计service 实现
 *
 * @author xu.baoji
 * @date 2013-7-16
 * @check @duan.qixin 2013-7-18 #4557
 * @check @li.jianfei 2013.09.02 #5215
 */
@Service
@Phoenix
@Transactional
public class UserStatServiceImpl implements UserStatService {

    /**
     * 人员统计 dao
     */
    @Autowired
    private UserStatDao userStatDao;

    @Override
    public List<UserStat> findUserFrequencyOfActivitiesStat(int LogicGroupId, Date date, int type,
                                                            int size, boolean isDesc) {
        Date startDate = DateTypeGenerator.start(type, date);
        Date endDate = DateTypeGenerator.end(type, date);
        return userStatDao.findUserFrequencyOfActivitiesStat(LogicGroupId, startDate, endDate,
                size, isDesc);
    }

    @Override
    public Integer findUserCountByActivityCount(int LogicGroupId, Date date, int type,
                                                int activityCount) {
        Date startDate = DateTypeGenerator.start(type, date);
        Date endDate = DateTypeGenerator.end(type, date);
        return userStatDao.findUserCountByActivityCount(LogicGroupId, startDate, endDate,
                activityCount);
    }

    @Override
    public boolean hasData(int LogicGroupId, Date date, int type) {
        Date startDate = DateTypeGenerator.start(type, date);
        Date endDate = DateTypeGenerator.end(type, date);
        return userStatDao.hasData(LogicGroupId, startDate, endDate);
    }

    @Override
    public Map<String, Date> findMorningAndEveningUser(int logicGroupId, Date date, int type,
                                                       boolean isEvening) {
        Date startDate = DateTypeGenerator.start(type, date);
        Date endDate = DateTypeGenerator.end(type, date);
        Map<String, Object> morningAndEveningUser = userStatDao.findMorningAndEveningUser(
                logicGroupId, startDate, endDate, isEvening);
        Map<String, Date> map = new HashMap<String, Date>();
        if (morningAndEveningUser != null && morningAndEveningUser.size() > 0) {
            String key = (String) morningAndEveningUser.get("userName");
            String dateValue = (String) morningAndEveningUser.get("time");
            map.put(key, DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, dateValue));
        }
        return map;
    }

    @Override
    public Map<String, Integer> findUserMorningAndEveningStat(int logicGroupId, Date date,
                                                              int type, boolean isEvening, int size) {
        Date startDate = DateTypeGenerator.start(type, date);
        Date endDate = DateTypeGenerator.end(type, date);
        List<Map<String, Object>> userMorningAndEveningStat = userStatDao
                .findUserMorningAndEveningStat(logicGroupId, startDate, endDate, isEvening, size);
        return handleUserMorningAndEveningStat(userMorningAndEveningStat);
    }

    /***
     * 将人员早晚统计数据 list 处理成 map
     *
     * @param userMorningAndEveningStat
     * @return
     * @author xu.baoji
     * @date 2013-8-19
     */
    private Map<String, Integer> handleUserMorningAndEveningStat(
            List<Map<String, Object>> userMorningAndEveningStat) {
        Map<String, Integer> morningAndEveningStatMap = new LinkedHashMap<String, Integer>();
        for (Map<String, Object> map : userMorningAndEveningStat) {
            String key = (String) map.get("userName");
            Long value = (Long) map.get("number");
            morningAndEveningStatMap.put(key, value.intValue());
        }
        return morningAndEveningStatMap;
    }
}
