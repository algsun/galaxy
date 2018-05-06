package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.po.uma.UserStat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人员管理：人员统计service 接口
 *
 * @author xu.baoji
 * @date 2013-7-16
 * @check @duan.qixin 2013-7-18 @4557
 * @check @li.jianfei 2013.09.02 #5215
 */
public interface UserStatService {

    /***
     * 查询人员活动频率统计
     * 按人员活动排序（倒序），如果没有数据返回空集合size为0
     *
     * @param LogicGroupId 站点编号
     * @param date         查询日期
     * @param type         查询类型
     * @param size         查询条数（前多少个 比如：前20）
     * @param isDesc       是否倒序   true ：查询活动最频繁的，false：查询活动最不频繁的
     * @return List<UserStat> 人员活动频率统计实体
     * @author xu.baoji
     * @date 2013-7-16
     */
    public List<UserStat> findUserFrequencyOfActivitiesStat(int LogicGroupId, Date date, int type,
                                                            int size, boolean isDesc);

    /***
     * 查询人员活动为n 的人员数量
     *
     * @param LogicGroupId  站点编号
     * @param date          查询日期
     * @param type          查询类型
     * @param activityCount 人员活动次数
     * @return integer 活动最不频繁的人的数量
     * @author xu.baoji
     * @date 2013-7-31
     */
    public Integer findUserCountByActivityCount(int LogicGroupId, Date date, int type, int activityCount);

    /***
     * 判断人员活动频率统计是否有数据 true 有。false 无
     *
     * @param LogicGroupId 站点编号
     * @param date         查询日期
     * @param type         查询类型
     * @return true /false
     * @author xu.baoji
     * @date 2013-7-31
     */
    public boolean hasData(int LogicGroupId, Date date, int type);

    /***
     * 查询人员 早晚 时刻 统计
     *
     * @param logicGroupId 站点id
     * @param date         查询日期
     * @param type         查询类型
     * @param isEvening    是否是  true 晚上最晚走的人员统计，false 早上最早来的人员统计
     * @param size         活动前size 个
     * @return Map<String, Integer> map key:人员名称  value 次数
     * @author xu.baoji
     * @date 2013-8-19
     */
    public Map<String, Integer> findUserMorningAndEveningStat(int logicGroupId, Date date, int type, boolean isEvening, int size);

    /**
     * 查询一段时间内 最早到的人员 和时刻，最晚走的人员和时刻
     *
     * @param logicGroupId 站点编号
     * @param date         查询条件
     * @param type         查询类型
     * @param isEvening    是否是  true 晚上最晚走的人员统计，false 早上最早来的人员统计
     * @return Map<String, Object>  key 人员名称  value  时刻 date 类型
     * @author xu.baoji
     * @date 2013-8-19
     */
    public Map<String, Date> findMorningAndEveningUser(int logicGroupId, Date date, int type, boolean isEvening);
}
