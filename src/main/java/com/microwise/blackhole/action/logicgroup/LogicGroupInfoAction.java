package com.microwise.blackhole.action.logicgroup;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <pre>
 *     logicGroup 信息
 * </pre>
 *
 * @author: Wang yunlong
 * @time: 12-12-3 下午4:16
 */
@Beans.Action
@Blackhole
public class LogicGroupInfoAction {
    private static final Logger log = LoggerFactory.getLogger(LogicGroupInfoAction.class);
    @Autowired
    private LogicGroupService logicGroupService;
    //input
    /**
     * 站点id
     */
    private int id;
    //output
    /**
     * 站点
     */
    private LogicGroup logicGroup;

    public String view() {
        try {
            logicGroup = logicGroupService.findLogicGroupById(id);
            AddLogicGroupAction.initIsAtSupermanPage(ActionContext.getContext());
        } catch (Exception e) {
            logicGroup = new LogicGroup();
            log.error("站点信息", e);
        }
        return Action.SUCCESS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LogicGroup getLogicGroup() {
        return logicGroup;
    }

    public void setLogicGroup(LogicGroup logicGroup) {
        this.logicGroup = logicGroup;
    }
}
