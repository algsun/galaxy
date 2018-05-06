package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.NotificationPO;

import java.util.List;

/**
 * 控制模块端口开关状态
 *
 * @author xuyuexi
 * @date 14-4-25
 */
public interface NotificationService {

    /**
     * 添加通知设置，一个用户一个
     * @param notificationPO
     */
    public void addNotification(NotificationPO notificationPO,String siteId);

    /**
     * 根据用户id查找通知设置
     * @param userId
     * @return
     */
    public NotificationPO findNotificationByUserId(int userId,String siteId);

    /**
     * 根据用户id删除通知设置
     * @param userId
     */
    public void deleteNotificationByUserId(int userId,String siteId);

    /**
     * 根据用户id删除该用户选择的设备
     * @param id
     */
    public void deleteNotificationDeviceByNotifyId(String id);

    /**
     * 根据通知设置Id查找选择的设备
     * @param notifyId
     * @return
     */
    public List findNotificationDevice(String notifyId);

}
