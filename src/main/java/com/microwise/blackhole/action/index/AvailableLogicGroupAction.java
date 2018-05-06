package com.microwise.blackhole.action.index;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Security;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 为《其他站点》加载数据
 *
 * @author li.jianfei
 * @date 2012-11-29
 * @check @gaohui #467 12-11-29 16:55
 */
@Beans.Action
@Blackhole
public class AvailableLogicGroupAction {

    /**
     * 站点组信息service
     */
    @Autowired
    private LogicGroupService logicGroupService;

    // Output
    /**
     * 站点组
     */
    private List<Map<String, Object>> logicGroupMap;

    /**
     * 获取《其他站点》初始化数据
     *
     * @return 操作标识
     */
    public String view() {

        List<LogicGroup> logicGroupList = null;

        // 判断是否超级管理员或者匿名用户
        if (Security.isSuperman() || Security.isAnonymity()) {
            // 查询所有顶级站点
            logicGroupList = logicGroupService.findTopLogicGroupList();
        } else {
            // 查询当前用户归属站点及用户站点组
            Sessions sessions = new Sessions(ActionContext.getContext());
            User user = sessions.currentUser();
            LogicGroup currentUserLogicGroup = sessions.currentUserLogicGroup();
            logicGroupList = logicGroupService.findUserLogicGroupsCarrySite(user.getId());
            logicGroupList.add(0, currentUserLogicGroup);
        }

        // 组织 zTree 数据
        logicGroupMap = new ArrayList<Map<String, Object>>();

        for (LogicGroup logicGroup : logicGroupList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", logicGroup.getId());
            map.put("name", logicGroup.getLogicGroupName());
            map.put("iconSkin", "site");
            map.put("isParent", true);
            // 站点类型
            if (logicGroup.getSite() != null) {
                map.put("type", "basicSite");
            }

            logicGroupMap.add(map);
        }

        return Action.SUCCESS;
    }

    public List<Map<String, Object>> getLogicGroupMap() {
        return logicGroupMap;
    }

    public void setLogicGroupMap(List<Map<String, Object>> logicGroupMap) {
        this.logicGroupMap = logicGroupMap;
    }
}
