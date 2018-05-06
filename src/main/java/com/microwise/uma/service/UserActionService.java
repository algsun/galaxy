package com.microwise.uma.service;

import com.microwise.uma.bean.AbstractUserActionBean;
import com.microwise.uma.bean.UserActionBean;
import com.microwise.uma.bean.UserCensusActionBean;

import java.util.Date;
import java.util.List;

/**
 * 用户行为规则
 *
 * @author gaohui
 * @date 13-4-26 16:12
 * @check li.jianfei 2013-5-6 #3129
 */
public interface UserActionService {

    /**
     * 查询用户某一时间段内的匹配的行为规则 (单程，往返)
     * <p/>
     * 单程 type = 1, 且类型为 UserActionBean
     * 往返 type = 2, 且类型为 UserCensusActionBean
     *
     * @param userId    用户id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param order     true 时间正序, false 时间倒序
     * @param index     第几页, 从 1 开始
     * @param size      一页大小
     * @return
     */
    List<AbstractUserActionBean> findUserActions(int userId, Date startTime, Date endTime, boolean order, int index, int size);

    /**
     * 查询用户某一时间段内的匹配的行为规则数量 (单程，往，返)
     *
     * @param userId    用户id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    int findCount(int userId, Date startTime, Date endTime);

    /**
     * 查询用户某一时间段内的匹配的行为规则数量 (单程和往返)
     *
     * @param userId    用户id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    int findCountByUserId(int userId, Date startTime, Date endTime);

    /**
     * 根据行为规则id查找某一时间段的记录数量
     *
     * @param ruleId    规则id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    int findCountByRuleId(int ruleId, Date startTime, Date endTime);

    /**
     * 根据行为规则id查找某一时间段的记录数量
     *
     * @param ruleId   规则ID
     * @param dateType 1.年 2.月 3.日
     * @param date     日期
     * @return
     */
    int findCountByRuleId(int ruleId, int dateType, Date date);

    /**
     * 根据行为规则ID分页查询匹配的单程记录
     *
     * @param ruleId    规则ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param index     当前页, 从 1 开始
     * @param size      一页条数
     * @param order     true occurrenceTime 正序, false occurrenceTime 逆序
     * @return
     */
    List<UserActionBean> findSingleActionsByRuleId(int ruleId, Date startTime, Date endTime, int index, int size, boolean order);

    /**
     * 根据行为规则ID分页查询匹配的单程记录
     *
     * @param ruleId   规则ID
     * @param dateType 时间类型(1.年 2.月 3.日)
     * @param date     时间
     * @param index    当前页, 从 1 开始
     * @param size     一页条数
     * @param order    true occurrenceTime 正序, false occurrenceTime 逆序
     * @return
     */
    List<UserActionBean> findSingleActionsByRuleId(int ruleId, int dateType, Date date, int index, int size, boolean order);

    /**
     * 根据行为规则ID分页查询匹配的往返记录
     *
     * @param ruleId   规则ID
     * @param dateType 时间类型(1.年 2.月 3.日)
     * @param date     日期
     * @param index    当前页, 从 1 开始
     * @param size     一页条数
     * @param order    true occurrenceTime 正序, false occurrenceTime 逆序
     * @return
     */
    List<UserCensusActionBean> findCensusActionsByRuleId(int ruleId, int dateType, Date date, int index, int size, boolean order);

    /**
     * 根据行为规则ID分页查询匹配的往返记录
     *
     * @param ruleId    规则ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param index     当前页, 从 1 开始
     * @param size      一页条数
     * @param order     true occurrenceTime 正序, false occurrenceTime 逆序
     * @return
     */
    List<UserCensusActionBean> findCensusActionsByRuleId(int ruleId, Date startTime, Date endTime, int index, int size, boolean order);
}
