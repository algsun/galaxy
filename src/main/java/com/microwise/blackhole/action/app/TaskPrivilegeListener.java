package com.microwise.blackhole.action.app;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;

import java.util.Collection;
import java.util.Iterator;

/**
 * 我的任务权限管理监听
 *
 * @author: wanggeng
 * @date: 13-7-31 上午9:52
 */
@Beans.Bean
@Blackhole
public class TaskPrivilegeListener implements BeforeInitPrivilegesListener {
    @Override
    public void beforeInit(Collection<Privilege> privileges, LogicGroup logicGroup) {
        ConfigFactory.Configs appConfig = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        boolean taskPrivilegeEnable = Boolean.parseBoolean(appConfig.get(Constants.Config.MY_TASK_ENABLE));
        if (!taskPrivilegeEnable) {
            String taskPrivilege = appConfig.get(Constants.Config.MY_TASK_ENABLE_PRIVILEGE);
            Iterator<Privilege> iterator = privileges.iterator();
            Privilege privilege;
            while (iterator.hasNext()) {
                privilege = iterator.next();
                if (taskPrivilege.equals(privilege.getPrivilegeId())) {
                    iterator.remove();
                }
            }
        }
    }
}
