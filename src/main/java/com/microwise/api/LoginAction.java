package com.microwise.api;

import com.microwise.api.bean.ApiResult;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.service.UserService;
import com.microwise.common.sys.shiro.CustomRealm;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiedeng
 * @date 13-12-6
 */
@Controller
public class LoginAction {

    @Autowired
    private UserService userService;

    @Autowired
    private LogicGroupService logicGroupService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "登录系统", position = 2, httpMethod = "GET",
            notes = "登录系统"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<Object> login(@RequestParam String username, @RequestParam String password) {
        if (StringUtils.isBlank(username)) {
            return getResult(false, "用户名不能为空", null);
        } else if (StringUtils.isBlank(password)) {
            return getResult(false, "密码不能为空", null);
        } else {
            //用户名就是邮箱
            String email = username.toLowerCase();
            Subject currentUser = SecurityUtils.getSubject();
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(email, password);
                currentUser.login(token);
            } catch (LockedAccountException lae) {
                //account for that username is locked - can't login.  Show them a message?
                return getResult(false, "此帐户被禁用", null);
            } catch (AuthenticationException ae) {
                //unexpected condition - error?
                return getResult(false, "用户名或密码错误", null);
            }
            //获取用户信息
            User user = userService.findUserByEmail(email);
            if (CustomRealm.isSuperman(user.getId())) {
                List<LogicGroup> logicGroups = logicGroupService.findTopLogicGroupList();
                List<Map<String, Object>> logicGroupList = new ArrayList<Map<String, Object>>();
                for (LogicGroup logicGroup : logicGroups) {
                    logicGroupList.add(getLogicGroup(logicGroup));
                }
                return getResult(true, "登录成功", getUser(user, getLogicGroup(logicGroupList)));
            }

            //获取站点信息
            LogicGroup logicGroup = logicGroupService.findLogicGroupByUserId(user.getId());
            if (logicGroup != null) {
                logicGroup = logicGroupService.findLogicGroupCarrySite(logicGroup.getId());
            }
            List<Map<String, Object>> logicGroups = new ArrayList<Map<String, Object>>();
            logicGroups.add(getLogicGroup(logicGroup));
            return getResult(true, "登录成功", getUser(user, getLogicGroup(logicGroups)));
        }
    }


    private Map<String, Object> getLogicGroup(LogicGroup logicGroup) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (logicGroup == null) {
            return data;
        }
        List<LogicGroup> subLogicGroupsOfCurrent = logicGroupService.findSubLogicGroupList(logicGroup.getId());
        List<Map<String, Object>> logicGroupList = new ArrayList<Map<String, Object>>();
        if (subLogicGroupsOfCurrent != null && !subLogicGroupsOfCurrent.isEmpty()) {
            for (LogicGroup lg : subLogicGroupsOfCurrent) {
                logicGroupList.add(getLogicGroup(lg));
            }
        }
        return getLogicGroup(logicGroupList, logicGroup);
    }

    private Map<String, Object> getLogicGroup(List<Map<String, Object>> logicGroups) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("logicGroups", logicGroups);
        return data;
    }

    private Map<String, Object> getLogicGroup(List<Map<String, Object>> logicGroups, LogicGroup logicGroup) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("logicGroups", logicGroups);
        data.put("logicGroupId", logicGroup.getId());
        data.put("logicGroupName", logicGroup.getLogicGroupName());
        data.put("logicGroupType", logicGroup.getLogicGroupType());
        data.put("siteId", logicGroup.getSite() == null ? null : logicGroup.getSite().getSiteId());
        return data;
    }

    /**
     * 封装用户站点信息
     *
     * @param user 用户信息
     * @param data 站点信息
     * @return 封装信息
     */
    private Map<String, Object> getUser(User user, Map<String, Object> data) {
        if (user != null && data != null) {
            data.put("userId", user.getId());
            data.put("username", user.getUserName());
            data.put("token", user.getToken());
            data.put("isActiveUser", user.isActive());
        }
        return data;
    }

    /**
     * 获取返回的json 数据
     *
     * @param success 是否成功 true 成功， false 失败
     * @param msg     返回的信息
     * @param data    返回的数据
     * @return json数据
     */
    private ApiResult<Object> getResult(boolean success, String msg, Object data) {
        ApiResult<Object> apiResult = new ApiResult<Object>();
        apiResult.setSuccess(success);
        apiResult.setMessage(msg);
        apiResult.setData(data);
        return apiResult;
    }

}
