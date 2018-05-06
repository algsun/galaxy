package com.microwise.uma.dao;

import com.microwise.uma.bean.AbstractUserActionBean;
import com.microwise.uma.bean.UserActionBean;
import com.microwise.uma.bean.UserCensusActionBean;

import java.util.Date;
import java.util.List;

/**
 * 用户行为规则
 *
 * @author gaohui
 * @date 13-4-26 15:42
 * @check li.jianfei 2013-5-6 #3134
 */
public interface UserActionDao {

    /**
     * 查询用户某一时间段内的匹配的行为规则 (单程，往返)
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @param order     true 时间正序, false 时间倒序
     * @param start
     * @param size
     * @return
     */
    List<AbstractUserActionBean> findUserActions(int userId, long startTime, long endTime, boolean order, int start, int size);

    /**
     * 查询用户某一时间段内的匹配的行为规则数量 (单程，往，返)
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    int findCount(int userId, Date startTime, Date endTime);

    /**
     * 单程数量
     *
     * @param ruleId
     * @param startTime
     * @param endTime
     * @return
     */
    int findSingleCountByRuleId(int ruleId, long startTime, long endTime);


    /**
     * 往或者返的数量
     *
     * @param ruleId
     * @param startTime
     * @param endTime
     * @return
     */
    int findGoOrBackCountByRuleId(int ruleId, long startTime, long endTime);

    /**
     * 往返数量
     *
     * @param ruleId
     * @param startTime
     * @param endTime
     * @return
     */
    int findCensusCountByRuleId(int ruleId, long startTime, long endTime);

    /**
     * 根据行为规则ID分页查询匹配的单程记录
     *
     * @param ruleId
     * @param startTime
     * @param endTime
     * @param start
     * @param size
     * @param order
     * @param goBack
     * @return
     */
    List<UserActionBean> findSingleActionsByRuleId(int ruleId, long startTime, long endTime, int start, int size, boolean order, boolean goBack);

    /**
     * 根据行为规则ID分页查询匹配的往返记录
     *
     * @param ruleId
     * @param startTime
     * @param endTime
     * @param start
     * @param size
     * @param order
     * @return
     */
    List<UserCensusActionBean> findCensusActionsByRuleId(int ruleId, long startTime, long endTime, int start, int size, boolean order);

    /**
     * 查询用户某一时间段内的匹配的行为规则数量 (单程和往返)
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    int findCountByUserId(int userId, long startTime, long endTime);
}
