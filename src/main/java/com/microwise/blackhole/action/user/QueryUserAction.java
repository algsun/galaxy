package com.microwise.blackhole.action.user;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.ConfigFactory;
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
 * 查询用户列表
 *
 * @author li.jianfei
 * @date 2012-11-21
 * @check wang.yunlong #389 12-11-29 09:49
 * @check @gaohui #488 12-11-29 15:55
 */
@Beans.Action
@Blackhole
public class QueryUserAction {
    private static final Logger log = LoggerFactory.getLogger(QueryUserAction.class);

    /**
     * 用户信息 service
     */
    @Autowired
    private UserService userService;

    @Autowired
    private BlackholeLoggerUtil logger;

    //Input
    /**
     * 站点id
     */
    private int logicGroupId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 当前页
     */
    private int index;

    //Output
    /**
     * 用户列表
     */
    private List<User> userList;

    /**
     * 当前用户信息
     */
    private User currentUser;

    //Input && Output
    /**
     * 总页数
     */
    private int pageCount;

    private String customize;

    /**
     * 分页查询方法
     *
     * @return
     */
    public String execute() {
        try {
            Sessions sessions = new Sessions(ActionContext.getContext());

            // 首次进入时站点id为0，站点id为0时默认取当前站点id
            logicGroupId = sessions.currentLogicGroup().getId();
            pageCount= PagingUtil.pagesCount(userService.findUserListCount(logicGroupId, userName), Constants.SIZE_PER_PAGE);            ConfigFactory.Configs appConfig = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
            customize = appConfig.get(Constants.Config.GALAXY_CUSTOMIZE);
            index = (index == 0 ? 1 : index);
            userList = userService.findUserList(logicGroupId, userName, index, Constants.SIZE_PER_PAGE);
            logger.log("用户管理", "查询用户");
            ActionMessage.createByAction().consume();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getCustomize() {
        return customize;
    }

    public void setCustomize(String customize) {
        this.customize = customize;
    }
}
