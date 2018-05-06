package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.ConfigPO;
import com.microwise.halley.bean.po.UserPO;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import com.microwise.halley.service.CarService;
import com.microwise.halley.service.ExhibitionStateService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 跳转到车辆设备信息配置页面
 *
 * @author wanggeng
 * @date 13-9-27 下午1:38
 * @check @li.jianfei #5842 2013-10-10
 */
@Beans.Action
@Halley
public class ToCarConfigAction {
    /**
     * 页面layout路径
     */
    public static final String _pagePath = "../exhibition/car-config.ftl";

    /**
     * 车辆配置服务
     */
    @Autowired
    private CarService carService;

    /**
     * 用户相关服务
     */
    @Autowired
    private UserService userService;
    /**
     * 外展状态
     */
    @Autowired
    private ExhibitionStateService exhibitionStateService;
    //input
    /**
     * 外展ID
     */
    private int exhibitionId;

    //output
    /**
     * 车辆信息列表
     */
    private List<CarVO> carVOList;

    /**
     * 报警用户列表
     */
    private List<User> alarmUserList = new ArrayList<User>();
    /**
     * 外展实体
     */

    private ExhibitionStateVO exhibitionStateVO;
    /**
     * 用户列表
     */
    private List<User> userList;

    /**
     * 报警范围
     */
    private int alarmRange;

    /**
     * 车辆配置页面信息展示
     *
     * @return string
     */
    @Route("/halley/exhibition/carConfig/{exhibitionId}")
    public String execute() {
        exhibitionStateVO = exhibitionStateService.findCurrentState(exhibitionId);
        try {
            //车辆信息列表查询
            carVOList = carService.findCarsWithDeviceByExhibitionId(exhibitionId);

            //系统人员列表查询
            userList = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());

            //报警人员列表查询
            List<UserPO> userPOList = carService.findUserPO(exhibitionId);

            //根据报警人员列表的ID获得人员实体类对象List
            for (UserPO u : userPOList) {
                User user = userService.findUserById(u.getUserId());
                alarmUserList.add(user);
            }

            //获取报警配置
            List<ConfigPO> configList = carService.findConfigByExhibitionId(exhibitionId);
            if (configList.size() > 0) {
                alarmRange = configList.get(0).getAlarmRange();
            }

            ActionMessage.createByAction().consume();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Results.ftl("/halley/pages/index/layout.ftl");
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

    public List<CarVO> getCarVOList() {
        return carVOList;
    }

    public void setCarVOList(List<CarVO> carVOList) {
        this.carVOList = carVOList;
    }

    public List<User> getAlarmUserList() {
        return alarmUserList;
    }

    public void setAlarmUserList(List<User> alarmUserList) {
        this.alarmUserList = alarmUserList;
    }

    public int getAlarmRange() {
        return alarmRange;
    }

    public void setAlarmRange(int alarmRange) {
        this.alarmRange = alarmRange;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public ExhibitionStateVO getExhibitionStateVO() {
        return exhibitionStateVO;
    }

    public void setExhibitionStateVO(ExhibitionStateVO exhibitionStateVO) {
        this.exhibitionStateVO = exhibitionStateVO;
    }
}
