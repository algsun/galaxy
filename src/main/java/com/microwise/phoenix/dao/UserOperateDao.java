package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.vo.SubsystemOperate;
import com.microwise.phoenix.bean.vo.UserOperate;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户操作记录 dao
 *
 * @author xu.baoji
 * @date 2013-8-19
 * @check @li.jianfei 2013.09.02 #5218
 */
public interface UserOperateDao {

    /***
     * 查询 用户操作功能模块统计
     *
     * @param email     用户邮箱
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param size      查询数量
     * @return List<UserOperate> 用户操作功能模块统计 不携带有用户具体操作
     * @author xu.baoji
     * @date 2013-8-19
     */
    List<UserOperate> findUserFunctions(String email, Date startDate, Date endDate, int size);

    /***
     * 查询用户 操作功能模块 下的具体操作
     *
     * @param email        用户邮箱
     * @param functionName 用户操作过的功能模块
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @return List<Map<String, Object>> map size 为 2 ，
     * 第一对 key：“ operate” value：“具体操作”；第二对 key：“number” value：操作次数
     * @author xu.baoji
     * @date 2013-8-19
     */
    List<Map<String, Object>> findUserOperate(String email, String functionName, Date startDate, Date endDate);

    /***
     * 查询系统中已经激活 的业务系统
     *
     * @return List<SubsystemOperate> 业务系统操作统计实体 不携带有操作记录
     * @author xu.baoji
     * @date 2013-8-21
     */
    List<SubsystemOperate> findSubsystem();

    /***
     * 查询一个业务系统 在一个站点下的操作统计
     *
     * @param subsystemId  业务系统id
     * @param logicGroupId 逻辑站点编号
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @param size         要取的数量
     * @return List<Map<String, Object>> 人员操作该业务系统的统计数据  map size 为 2
     * 第一对 key userName  value ：具体用户名 ； 第二对  key：number value：操作次数
     * @author xu.baoji
     * @date 2013-8-21
     */
    List<Map<String, Object>> findSubsystemOperate(int subsystemId, int logicGroupId, Date startDate, Date endDate, int size);

}
