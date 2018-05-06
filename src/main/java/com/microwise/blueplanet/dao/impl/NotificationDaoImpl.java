package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.NotificationPO;
import com.microwise.blueplanet.dao.NotificationDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author xuyuexi
 * @date 14-4-24
 */
@Beans.Dao
@Blueplanet
public class NotificationDaoImpl extends BlueplanetBaseDao implements NotificationDao {

    @Override
    public void addNotification(NotificationPO notificationPO) {
        getSqlSession().insert("blueplanet.Notification.addNotification", notificationPO);
        List<String> deviceList = notificationPO.getDeviceIds();
        if (deviceList != null) {
            for (String deviceId : deviceList) {
                Map<String, Object> map = new HashMap<String, Object>();
                String uuid = UUID.randomUUID().toString();
                map.put("id", uuid);
                map.put("notifyId", notificationPO.getId());
                map.put("deviceId", deviceId);
                getSqlSession().insert("blueplanet.Notification.addNotificationDevice", map);
            }
        }
    }

    @Override
    public NotificationPO findNotificationByUserId(int userId,String siteId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("siteId", siteId);
        return getSqlSession().selectOne("blueplanet.Notification.findNotificationByUserId", map);
    }

    @Override
    public void deleteNotificationByUserId(int userId,String siteId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("siteId", siteId);
        getSqlSession().delete("blueplanet.Notification.deleteNotificationByUserId", map);
    }

    @Override
    public void deleteNotificationDeviceByNotifyId(String id) {
        getSqlSession().delete("blueplanet.Notification.deleteNotificationDeviceByNotifyId", id);
    }

    @Override
    public List findNotificationDevice(String notifyId) {
        return getSqlSession().selectList("blueplanet.Notification.findNotificationDevice", notifyId);
    }
}
