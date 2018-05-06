package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.bean.vo.SubsystemOperate;
import com.microwise.phoenix.bean.vo.UserOperate;
import com.microwise.phoenix.dao.UserOperateDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 操作记录统计 dao 实现
 *
 * @author xu.baoji
 * @date 2013-8-19
 * @check @li.jianfei 2013.09.02 #5218
 */
@Phoenix
@Dao
public class UserOperateDaoImpl extends PhoenixBaseDao implements UserOperateDao {

    @Override
    public List<UserOperate> findUserFunctions(String email, Date startDate, Date endDate, int size) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("email", email);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("size", size);
        return getSqlSession().selectList("phoenix.mybatis.UserOperateDao.findUserFunctions",
                paramMap);
    }

    @Override
    public List<Map<String, Object>> findUserOperate(String email, String functionName,
                                                     Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("email", email);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("functionName", functionName);
        return getSqlSession().selectList("phoenix.mybatis.UserOperateDao.findUserOperate",
                paramMap);
    }

    @Override
    public List<SubsystemOperate> findSubsystem() {

        return getSqlSession().selectList("phoenix.mybatis.UserOperateDao.findSubsystem");
    }

    @Override
    public List<Map<String, Object>> findSubsystemOperate(int subsystemId, int logicGroupId,
                                                          Date startDate, Date endDate, int size) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("subsystemId", subsystemId);
        paramMap.put("logicGroupId", logicGroupId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("size", size);
        return getSqlSession().selectList("phoenix.mybatis.UserOperateDao.findSubsystemOperate",
                paramMap);
    }
}
