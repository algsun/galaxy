package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.po.NotificationPO;
import com.microwise.blueplanet.dao.NotificationDao;
import com.microwise.blueplanet.service.NotificationService;
import com.microwise.blueplanet.sys.Blueplanet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xuyuexi
 * @date 14-4-25
 */
@Service
@Transactional
@Blueplanet
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Override
    public void addNotification(NotificationPO notificationPO,String siteId) {
        //获取当前用户
        int userId = notificationPO.getUserId();
        //获取当前用户的通知设置对象
        NotificationPO oldNotification = notificationDao.findNotificationByUserId(userId,siteId);
        //不为空删除该用户的所有通知设置记录
        if (oldNotification!=null) {
            notificationDao.deleteNotificationDeviceByNotifyId(oldNotification.getId());
            notificationDao.deleteNotificationByUserId(userId,siteId);
        }
        //选择全部设备，清空存储的设备列表
        if(notificationPO.getSubscribeType()==1){
            notificationPO.setDeviceIds(null);
        }
        notificationDao.addNotification(notificationPO);
    }

    @Override
    public NotificationPO findNotificationByUserId(int userId,String siteId) {
        //获取通知设置对象
        NotificationPO notificationPO = notificationDao.findNotificationByUserId(userId,siteId);
        //获取通知设置的设备
        notificationPO.setDeviceIds(notificationDao.findNotificationDevice(notificationPO.getId()));
        return notificationPO;
    }

    @Override
    public void deleteNotificationByUserId(int userId,String siteId) {
        notificationDao.findNotificationByUserId(userId,siteId);
    }

    @Override
    public void deleteNotificationDeviceByNotifyId(String id) {
        notificationDao.deleteNotificationDeviceByNotifyId(id);
    }

    @Override
    public List findNotificationDevice(String notifyId) {
        return notificationDao.findNotificationDevice(notifyId);
    }
}
