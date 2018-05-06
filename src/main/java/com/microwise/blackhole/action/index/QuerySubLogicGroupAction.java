package com.microwise.blackhole.action.index;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询子站点
 *
 * @author bastengao
 * @date 12-11-22 10:08
 * @check @wang.yunlong & li.jianfei    #263    2012-12-18
 */
@Beans.Action
@Blackhole
public class QuerySubLogicGroupAction {
    @Autowired
    private LogicGroupService logicGroupService;

    //input
    // 站点ID
    private int logicGroupId;
    //output
    // 直接子站点
    private List<Map<String, Object>> subLogicGroupsMap;

    public String execute() {
        List<LogicGroup> subLogicGroups = logicGroupService.findSubLogicGroupsCarrySite(logicGroupId);
        subLogicGroupsMap = new ArrayList<Map<String, Object>>();
        for (LogicGroup logicGroup : subLogicGroups) {
            Map<String, Object> logicGroupMap = new HashMap<String, Object>();
            logicGroupMap.put("id", logicGroup.getId());
            logicGroupMap.put("name", logicGroup.getLogicGroupName());
            logicGroupMap.put("iconSkin", "site");
            logicGroupMap.put("isParent", true);
            // 站点类型
            if (logicGroup.getSite() != null) {
                logicGroupMap.put("type", "basicSite");
            }

            subLogicGroupsMap.add(logicGroupMap);
        }
        return Action.SUCCESS;
    }

    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }

    public List<Map<String, Object>> getSubLogicGroupsMap() {
        return subLogicGroupsMap;
    }

    public void setSubLogicGroupsMap(List<Map<String, Object>> subLogicGroupsMap) {
        this.subLogicGroupsMap = subLogicGroupsMap;
    }
}
