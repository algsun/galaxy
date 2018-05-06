package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.vo.SubsystemOperate;
import com.microwise.phoenix.bean.vo.UserOperate;

import java.util.Date;
import java.util.List;

/**
 * 用户操作统计 service
 *
 * @author xu.baoji
 * @date 2013-8-16
 * @check @li.jianfei 2013.09.02 #5218
 */
public interface UserOperateService {

    /**
     * 查询一个用户一段时间段内的操作记录
     *
     * @param email    用户邮箱
     * @param date     日期
     * @param dateType 查询类型
     * @param size     要查询的数量
     * @return List<UserOperate> 用户操作记录统计实体
     * @author xu.baoji
     * @date 2013-8-17
     */
    List<UserOperate> findUserOperates(String email, Date date, int dateType, int size);

    /**
     * 按业务系统查询用户操作记录
     *
     * @param logicGroupId 站点id
     * @param date         查询日期
     * @param dateType     查询类型
     * @param size         查询的数量
     * @return List<SubsystemOperate> 业务系统操作记录
     * @author xu.baoji
     * @date 2013-8-21
     */
    List<SubsystemOperate> findSubsystemOperate(int logicGroupId, Date date, int dateType, int size);

}
