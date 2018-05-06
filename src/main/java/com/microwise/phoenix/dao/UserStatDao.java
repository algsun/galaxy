package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.po.uma.UserStat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人员管理：人员统计
 *
 * @author xu.baoji
 * @date 2013-7-16
 * @check @duan.qixin 2013-7-18 @4557
 * @check @li.jianfei 2013.09.02 #5215
 */
public interface UserStatDao {

    /***
     * 查询人员活动频率统计
     *
     * @param LogicGroupId 站点编号
     * @param startDate    查询日期
     * @param endDate      查询类型
     * @param size         查询条数（前多少个 比如：前20）
     * @param isDesc       排序方式  true ：倒序   false 正序
     * @return List<UserStat> 人员活动频率统计实体 （按人员活动排序）
     * @author xu.baoji
     * @date 2013-7-16
     */
    public List<UserStat> findUserFrequencyOfActivitiesStat(int LogicGroupId, Date startDate, Date endDate,
                                                            int size, boolean isDesc);

    /***
     * 查询 活动次数为 n 的人员数量
     *
     * @param LogicGroupId  站点编号
     * @param startDate     开始日期
     * @param endDate       结束日期
     * @param activityCount 人员活动次数
     * @return integer 活动最不频繁的人的数量
     * @author xu.baoji
     * @date 2013-7-31
     */
    public Integer findUserCountByActivityCount(int LogicGroupId, Date startDate, Date endDate, int activityCount);

    /***
     * 判断人员活动频率统计是否有数据 true 有。false 无
     *
     * @param LogicGroupId 站点编号
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return true /false
     * @author xu.baoji
     * @date 2013-7-31
     */
    public boolean hasData(int LogicGroupId, Date startDate, Date endDate);

    /***
     * 查询人员 早晚 时刻 统计
     *
     * @param logicGroupId 站点id
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @param isEvening    是否是  true 晚上最晚走的人员统计，false 早上最早来的人员统计
     * @param size         获得前 size 个
     * @return List<Map<String, Object>> map 有两个元素
     * 第一对  key：“userName” ，value ：具体人员名，第二对  key:number  value:次数
     * @author xu.baoji
     * @date 2013-8-19
     */
    public List<Map<String, Object>> findUserMorningAndEveningStat(int logicGroupId, Date startDate,
                                                                   Date endDate, boolean isEvening, int size);

    /**
     * 查询一段时间内 最早到的人员 和时刻，最晚走的人员和时刻
     *
     * @param logicGroupId 站点编号
     * @param startDate    开始 时间
     * @param endDate      结束时间
     * @param isEvening    是否是  true 晚上最晚走的人员统计，false 早上最早来的人员统计
     * @return Map<String, Object> 有两个元素
     * 第一对  key：“userName” ，value ：具体人员名，第二对  key:time  value:时刻
     * @author xu.baoji
     * @date 2013-8-19
     */
    public Map<String, Object> findMorningAndEveningUser(int logicGroupId, Date startDate, Date endDate, boolean isEvening);
}
