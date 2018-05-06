package com.microwise.blackhole.action.logicgroup.current;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询行政站点 ajax action
 *
 * @author gaohui
 * @date 12-12-4 16:44
 * @check @li.jianfei #630 2012-12-05
 */
@Beans.Action
@Blackhole
public class QueryAdmimLogicGroupAction {
    private static final Logger log = LoggerFactory.getLogger(QueryAdmimLogicGroupAction.class);

    @Autowired
    private LogicGroupService logicGroupService;

    // input
    private int logicGroupId;
    // output
    private List<Map<String, Object>> logicGroupMap;

    public String execute() {
        try {
            List<LogicGroup> logicGroupList;
            // 如果没有选择站点则获取顶级行政站点，否则获取子行政站点
            if (logicGroupId == 0) {
                logicGroupList = logicGroupService.findAdmimLogicGroups(null);
            } else {
                logicGroupList = logicGroupService.findAdmimLogicGroups(logicGroupId);
            }

            // 处理 zTree 数据
            logicGroupMap = new ArrayList<Map<String, Object>>();
            // 排除当前站点
            LogicGroup currentLogicGroup = new Sessions(ActionContext.getContext()).currentLogicGroup();

            for (LogicGroup logicGroup : logicGroupList) {
                if (currentLogicGroup.getId().equals(logicGroup.getId())) {
                    continue;
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", logicGroup.getId());
                map.put("name", logicGroup.getLogicGroupName());
                map.put("iconSkin", "area");
                map.put("isParent", true);

                logicGroupMap.add(map);
            }

        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }

    public List<Map<String, Object>> getLogicGroupMap() {
        return logicGroupMap;
    }

    public void setLogicGroupMap(List<Map<String, Object>> logicGroupMap) {
        this.logicGroupMap = logicGroupMap;
    }
}
