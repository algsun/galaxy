package com.microwise.uma.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.AbstractUserActionBean;
import com.microwise.uma.bean.UserActionBean;
import com.microwise.uma.bean.UserCensusActionBean;
import com.microwise.uma.dao.UserActionDao;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户行为规则
 *
 * @author gaohui
 * @date 13-4-26 15:42
 * @check li.jianfei 2013-5-6 #3126
 */
@Beans.Dao
@Uma
public class UserActionDaoImpl extends UmaBaseDao implements UserActionDao {

    @Override
    public int findCount(int userId, Date startTime, Date endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("startTime", startTime.getTime());
        params.put("endTime", endTime.getTime());

        return getSqlSession().<Integer>selectOne("uma.mybatis.UserActionDao.findUserActionsCount", params);
    }

    @Override
    public int findCountByUserId(int userId, long startTime, long endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);

        return getSqlSession().<Integer>selectOne("uma.mybatis.UserActionDao.findUserActionsCount2", params);
    }

    @Override
    public List<AbstractUserActionBean> findUserActions(int userId,
                                                        long startTime, long endTime, boolean order, int start, int size) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("order", order);
        params.put("start", start);
        params.put("size", size);

        List<AbstractUserActionBean> userActions = getSqlSession().selectList("uma.mybatis.UserActionDao.findUserActionsByUserId", params);
        for (AbstractUserActionBean userAction : userActions) {
            if (userAction instanceof UserCensusActionBean) {
                computeDuration((UserCensusActionBean) userAction);
            }
        }

        return userActions;
    }

    @Override
    public int findSingleCountByRuleId(int ruleId, long startTime, long endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return getSqlSession().<Integer>selectOne("uma.mybatis.UserActionDao.findSingleActionCountByRoleId", params);
    }

    @Override
    public int findCensusCountByRuleId(int ruleId, long startTime, long endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return getSqlSession().<Integer>selectOne("uma.mybatis.UserActionDao.findCensusActionCountByRoleId", params);
    }

    @Override
    public List<UserCensusActionBean> findCensusActionsByRuleId(int ruleId,
                                                                long startTime, long endTime, int start, int size, boolean order) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("start", start);
        params.put("size", size);
        params.put("order", order);

        List<UserCensusActionBean> userActions = getSqlSession().selectList("uma.mybatis.UserActionDao.findCensusActionsByRuleId2", params);
        for (UserCensusActionBean userAction : userActions) {
            computeDuration(userAction);
        }

        return userActions;
    }

    @Override
    public List<UserActionBean> findSingleActionsByRuleId(int ruleId,
                                                          long startTime, long endTime, int start, int size, boolean order, boolean goBack) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("start", start);
        params.put("size", size);
        params.put("order", order);
        params.put("goBack", goBack);

        return getSqlSession().selectList("uma.mybatis.UserActionDao.findSingleActionsByRuleId2", params);
    }

    /**
     * 计算往返的停留时间
     *
     * @param userAction 用户行为对象
     */
    private static void computeDuration(UserCensusActionBean userAction) {
        long endTime2 = userAction.getBackAction().getOccurrenceTime().getTime();
        long startTime2 = userAction.getGoAction().getOccurrenceTime().getTime();
        long duration = (endTime2 - startTime2) / 1000; // 注意：此处将小数部分直接舍去
        userAction.setDuration(duration);
    }

    @Override
    public int findGoOrBackCountByRuleId(int ruleId, long startTime, long endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return getSqlSession().<Integer>selectOne("uma.mybatis.UserActionDao.findGoOrBackActionCountByRoleId", params);
    }
}
