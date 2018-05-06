package com.microwise.blackhole.action.logicgroup;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 查询站点组
 *
 * @author li.jianfei
 * @date 2012-11-30
 * @check @wang.yunlong #620 2012-12-05
 */
@Beans.Action
@Blackhole
public class QueryLogicGroupAction {

    public static final Logger log = LoggerFactory.getLogger(QueryLogicGroupAction.class);

    /**
     * 站点组信息 service
     */
    @Autowired
    private LogicGroupService logicGroupService;

    @Autowired
    private BlackholeLoggerUtil logger;

    // Input
    /**
     * 站点组名称
     */
    private String logicGroupName;

    /**
     * 当前页
     */
    private int index;


    // Output
    /**
     * 站点组列表
     */
    private List<LogicGroup> logicGroupList;


    // Input && Output
    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 查询站点列表
     *
     * @return 操作标识
     */
    public String view() {

        try {
            Sessions sessions = new Sessions(ActionContext.getContext());
            LogicGroup currentLogicGroup = sessions.currentLogicGroup();

            //超级管理员 登录可以直接进入站点管理页面 这时候当前站点为空
            Integer currentLogicGroupId = null;
            if (currentLogicGroup != null) {
                currentLogicGroupId = currentLogicGroup.getId();
            }
            AddLogicGroupAction.initIsAtSupermanPage(ActionContext.getContext());
            pageCount = PagingUtil.pagesCount(logicGroupService.findSubLogicGroupListCount(currentLogicGroupId, logicGroupName),Constants.SIZE_PER_PAGE);
            index = index == 0 ? 1 : index;
            logicGroupList = logicGroupService.findSubLogicGroupList(currentLogicGroupId, logicGroupName, index, Constants.SIZE_PER_PAGE);
            ActionMessage.createByAction().consume();
            logger.log("站点管理", "查询站点");

        } catch (Exception e) {
            log.error("", e);
        }

        return Action.SUCCESS;
    }


    public String getLogicGroupName() {
        return logicGroupName;
    }

    public void setLogicGroupName(String logicGroupName) {
        this.logicGroupName = logicGroupName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<LogicGroup> getLogicGroupList() {
        return logicGroupList;
    }

    public void setLogicGroupList(List<LogicGroup> logicGroupList) {
        this.logicGroupList = logicGroupList;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

}
