package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.PrivilegeOperate;
import com.microwise.blackhole.dao.PrivilegeOperateDao;
import com.microwise.blackhole.service.PrivilegeOperateService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.GalaxyIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liuzhu
 * @date 2014/3/31
 */

@Beans.Service
@Transactional
@Blackhole
public class PrivilegeOperateServiceImpl implements PrivilegeOperateService {

    @Autowired
    private PrivilegeOperateDao privilegeOperateDao;

    @Override
    public void disable(List<String> privilegeList, int logicGroupId) {

        //清除之前禁用的权限
        privilegeOperateDao.cleanDisable(logicGroupId);

        for (String privilege : privilegeList) {
            PrivilegeOperate privilegeOperate = new PrivilegeOperate();
            privilegeOperate.setId(GalaxyIdUtil.get64UUID());
            privilegeOperate.setLogicGroupId(logicGroupId);
            privilegeOperate.setPrivilegeId(privilege);
            privilegeOperateDao.disable(privilegeOperate);
        }
    }

    @Override
    public boolean isDisableById(String privilegeId, int logicGroupId) {
        return privilegeOperateDao.isDisableById(privilegeId, logicGroupId);
    }

    @Override
    public void clearDisable(int logicGroupId) {
        privilegeOperateDao.cleanDisable(logicGroupId);
    }

    @Override
    public List<String> findDisableSubsystemId(int logicGroupId) {
        return privilegeOperateDao.findDisableSubsystemId(logicGroupId);
    }

    @Override
    public List<String> findDisablePrivilegeId(int logicGroupId) {
        return privilegeOperateDao.findDisablePrivilegeId(logicGroupId);
    }

}
