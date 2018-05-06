package com.microwise.phoenix.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.phoenix.bean.po.UserLogin;
import com.microwise.phoenix.bean.vo.UserLogLength;
import com.microwise.phoenix.bean.vo.UserLoginStat;
import com.microwise.phoenix.dao.UserLoginStatDao;
import com.microwise.phoenix.service.UserLoginStatService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.uma.util.DateTypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 系统管理：用户登录习惯 统计 service 接口实现类
 *
 * @author xu.baoji
 * @date 2013-7-22
 * @check @duan.qixin 2013年7月29日 #5004
 */
@Service
@Phoenix
@Transactional
public class UserLoginStatServiceImpl implements UserLoginStatService {

    /***
     * 用户登录 习惯统计dao
     */
    @Autowired
    private UserLoginStatDao userLoginStatDao;

    @Override
    public UserLoginStat findUserLoginStat(int logicGroupId, Date date, int type) {
        Date startDate = DateTypeGenerator.start(type, date);
        Date endDate = DateTypeGenerator.end(type, startDate);
        int loginCount = userLoginStatDao.findUserLoginCount(logicGroupId, startDate, endDate);
        UserLoginStat userLoginStat = new UserLoginStat();
        if (loginCount != 0) {
            userLoginStat.setHasData(true);
            // 组装 周统计数据
            Float[] weekData = new Float[7];
            disposeStatData(weekData, userLoginStatDao.findUserLoginWeekStat(logicGroupId,
                    loginCount, startDate, endDate), true);
            userLoginStat.setWeekStat(Arrays.asList(weekData));

            // 组装 日统计数据
            Float[] dayData = new Float[6];
            disposeStatData(dayData, userLoginStatDao.findUserLoginDayStat(logicGroupId,
                    loginCount, startDate, endDate), false);
            userLoginStat.setDayStat(Arrays.asList(dayData));
        }
        return userLoginStat;
    }

    /***
     * 处理 统计数据
     *
     * @param statDatas    要组装的统计数据
     * @param biseStatData 基本统计数据
     * @author xu.baoji
     * @date 2013-7-22
     */
    private void disposeStatData(Float[] statDatas, List<Map<String, Object>> biseStatData,
                                 boolean isInteger) {
        for (Map<String, Object> map : biseStatData) {
            int key = 0;
            if (isInteger) {
                key = (Integer) map.get("key");
            } else {
                key = ((Long) map.get("key")).intValue();
            }
            Float statData = ((BigDecimal) map.get("statData")).floatValue();
            statDatas[key] = statData;
        }
    }

    @Override
    public List<UserLogLength> findUserLonLength(int logicGroupId, Date date, int type, int size) {
        // 用户登录时长统计列表
        List<UserLogLength> userLogLengths = new ArrayList<UserLogLength>();

        // 查询站点下用户列表
        List<UserLogin> users = userLoginStatDao.findUserBySite(logicGroupId);
        if (users.size() > 0) {
            Date startDate = DateTypeGenerator.start(type, date);
            Date endDate = DateTypeGenerator.end(type, date);
            // 查询每个用户登录 和退出记录
            for (UserLogin user : users) {
                UserLogLength userLogLength = new UserLogLength();
                List<UserLogin> userLogins = userLoginStatDao.findUserLoginByEmail(logicGroupId,
                        user.getEmail(), startDate, endDate);
                userLogLength.setLogLength(disposeUserLogLength(userLogins));
                userLogLength.setUserName(user.getUserName());
                userLogLengths.add(userLogLength);
            }
        }
        // 将数据按降序排序
        dropUserLogLengths(userLogLengths);
        return copyOf(userLogLengths, size);
    }

    /****
     * 获得 一个list 中前 n 是元素
     *
     * @param userLogLengths 用户统计时长实体列表
     * @param size           要获取的元素个数
     * @return List<UserLogLength>
     * @author xu.baoji
     * @date 2013-8-9
     */
    private List<UserLogLength> copyOf(List<UserLogLength> userLogLengths, int size) {
        if (size == 0) {
            size = userLogLengths.size();
        }
        UserLogLength[] userLogLengthArrays = new UserLogLength[userLogLengths.size()];
        userLogLengths.toArray(userLogLengthArrays);
        return Arrays.asList(Arrays.copyOfRange(userLogLengthArrays, 0, size));

    }

    /***
     * 将list 对象降序排序
     *
     * @param userLogLengths 用户登录时长统计列表
     * @return List<UserLogLength> 排序处理后的用户登录时长列表
     * @author xu.baoji
     * @date 2013-8-9
     */
    private void dropUserLogLengths(List<UserLogLength> userLogLengths) {
        // 将 list 元素 按照指定的排序方式进行排序
        Collections.sort(userLogLengths, new Comparator<UserLogLength>() {
            @Override
            public int compare(UserLogLength o1, UserLogLength o2) {
                if (o1.getLogLength() < o2.getLogLength()) {
                    return 1;
                } else if (o1.getLogLength() > o2.getLogLength()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    // 该方法暂时为用到，为查询登录时长按升序排序 准备

    /***
     * 将list 对象升序排序
     *
     * @param userLogLengths 用户登录时长统计列表
     * @return List<UserLogLength> 排序处理后的用户登录时长列表
     * @author xu.baoji
     * @date 2013-8-9
     */
    @SuppressWarnings("unused")
    private void literUserLogLengths(List<UserLogLength> userLogLengths) {
        // 将 list 元素 按照指定的排序方式进行排序
        Collections.sort(userLogLengths, new Comparator<UserLogLength>() {
            @Override
            public int compare(UserLogLength o1, UserLogLength o2) {
                if (o1.getLogLength() > o2.getLogLength()) {
                    return 1;
                } else if (o1.getLogLength() < o2.getLogLength()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    /***
     * 处理用户登录时长
     *
     * @param userLogins 用户登录退出 记录列表
     * @return Float 用户登录时长
     * @author xu.baoji
     * @date 2013-8-9
     */
    private Float disposeUserLogLength(List<UserLogin> userLogins) {
        long logLength = 0;
        for (int i = 0; i < userLogins.size() - 1; i++) {
            UserLogin userLogin = userLogins.get(i);
            UserLogin nexUserLogin = userLogins.get(i + 1);
            if (userLogin.getLogName().equals("登录") && nexUserLogin.getLogName().equals("退出")) {
                logLength = logLength
                        + (nexUserLogin.getLogTime().getTime() - userLogin.getLogTime().getTime());
            }
        }
        return DateTimeUtil.millisecondToHour(logLength);
    }

}
