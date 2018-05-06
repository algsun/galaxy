package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.UserPO;
import com.microwise.halley.service.CarService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 报警人员配置
 *
 * @author wanggeng
 * @date 13-9-29 下午3:31
 * @check @li.jianfei #5842 2013-10-10
 */
@Beans.Action
@Halley
public class EditUserAction {
    /**
     * 页面layout路径
     */
    public static final String _pagePath = "../exhibition/edit-user.ftl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 车辆配置服务层对象
     */
    @Autowired
    private CarService carService;

    //input
    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 用户ID
     */
    private String userIds;

    /**
     * 报警人员列表更新
     *
     * @return string
     */
    @Route("/halley/exhibition/editUser/{exhibitionId}")
    public String execute() {

        List<UserPO> users = getUsers(userIds, exhibitionId);
        try {
            carService.deleteAllUser(exhibitionId);
            if (users.size() > 0) {
                for (UserPO user : users) {
                    carService.saveUserPO(user);
                }
            }
            ActionMessage.createByAction().success("报警人员配置成功");
            halleyLogger.log("外展配置", "人员配置");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("报警人员配置失败");
            halleyLogger.logFailed("外展配置", "人员配置");
            e.printStackTrace();
        }
        return Results.redirect(String.format("/halley/exhibition/carConfig/%s", exhibitionId));
    }

    /**
     * 获取要更新的用户列表
     *
     * @param userIds 用户ID
     * @return userPOs 报警人员列表
     */
    private List<UserPO> getUsers(String userIds, int exhibitionId) {
        List<UserPO> users = new ArrayList<UserPO>();
        if (!Strings.isNullOrEmpty(userIds)) {
            String ids[] = userIds.split(",");
            for (String id : ids) {
                int userId = Integer.parseInt(id.trim());
                UserPO user = new UserPO();
                user.setExhibitionId(exhibitionId);
                user.setUserId(userId);
                users.add(user);
            }
        }
        return users;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
}
