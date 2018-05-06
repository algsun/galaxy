package com.microwise.blackhole.action.user;

import com.google.common.primitives.Ints;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
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
 * 分配站点组
 *
 * @author li.jianfei
 * @date 2012-11-13
 * @check @wang.yunlong #434 12-11-29 09:49
 * @check @gaohui #488 12-11-29 15:55
 */
@Beans.Action
@Blackhole
public class AssignLogicGroupAction {
    private static final Logger log = LoggerFactory.getLogger(AssignLogicGroupAction.class);

    /**
     * 用户信息 Service
     */
    @Autowired
    private UserService userService;

    /**
     * 站点组信息 Service
     */
    @Autowired
    private LogicGroupService logicGroupService;

    @Autowired
    private BlackholeLoggerUtil logger;

    // Input
    /**
     * 用户id
     */
    private int userId;

    /**
     * 站点 id
     */
    private int logicGroupId;

    /**
     * 用户分配的站点组
     */
    private int[] logicGroupIds;

    // Output
    /**
     * 用户站点组列表
     */
    private List<Map<String, Object>> logicGroupMap;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 反馈消息
     */
    private String message;


    /**
     * 分配站点组页面初始化
     *
     * @return 操作标识
     */
    public String view() {
        try {
            Sessions sessions = new Sessions(ActionContext.getContext());
            LogicGroup currentUserLogicGroup = sessions.currentUserLogicGroup();
            LogicGroup currentLogicGroup = sessions.currentLogicGroup();

            // 首次进入时站点id为0，站点id为0时默认取当前站点id
            logicGroupId = logicGroupId == 0 ? currentUserLogicGroup.getId() : logicGroupId;

            // 获取当前站点子站点和当前用户以拥有的站点
            List<LogicGroup> subLogicGroupList = logicGroupService.findSubLogicGroupList(logicGroupId);
            List<LogicGroup> userLogicGroupList = logicGroupService.findUserLogicGroups(userId);

            // 处理 zTree 识别的数据及其选中状态
            logicGroupMap = new ArrayList<Map<String, Object>>();
            for (LogicGroup subLogicGroup : subLogicGroupList) {
                //排除当前站点
                if (subLogicGroup.getId() == currentLogicGroup.getId()) {
                    continue;
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", subLogicGroup.getId());
                map.put("name", subLogicGroup.getLogicGroupName());
                map.put("iconSkin", "site");
                map.put("isParent", true);
                map.put("checked", false);
                for (LogicGroup userLogicGroup : userLogicGroupList) {
                    //选中已有站点组
                    if (subLogicGroup.getId() == userLogicGroup.getId()) {
                        map.put("checked", true);
                    }
                }
                logicGroupMap.add(map);
            }
        } catch (Exception e) {
            log.error("查询用户站点组", e);
        }
        return Action.SUCCESS;
    }


    /**
     * 分配站点组
     *
     * @return 操作标识
     */
    public String execute() {

        try {
            // 处理选中的站点组列表
            List<Integer> logicGroupIdList = new ArrayList<Integer>();
            // 可能 logicGroupIds 为 null (如果没有任何站点时) @gaohui 2013-05-13
            if (logicGroupIds != null) {
                logicGroupIdList = Ints.asList(logicGroupIds);
            }

            userService.assignLogicGroupToUser(userId, logicGroupIdList);
            success = true;
            message = "分配站点组成功";
            logger.log("用户管理", "分配站点组");
        } catch (Exception ex) {
            log.error("分配用户站点组", ex);
            success = false;
            message = "分配站点组失败";
            logger.logFailed("用户管理", "分配站点组");
        }

        return Action.SUCCESS;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int[] getLogicGroupIds() {
        return logicGroupIds;
    }

    public void setLogicGroupIds(int[] logicGroupIds) {
        this.logicGroupIds = logicGroupIds;
    }
}
