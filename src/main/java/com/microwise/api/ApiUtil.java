package com.microwise.api;

import com.microwise.blackhole.bean.User;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一些公共方法
 *
 * @author wang.geng
 * @date 14-6-27 下午2:41
 */
public class ApiUtil {

    public static Map<String, Object> getZone(List<Map<String, Object>> zoneList, ZoneVO zone) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("zones", zoneList);
        data.put("zoneId", zone.getZoneId());
        data.put("zoneName", zone.getZoneName());
        data.put("parentId", zone.getParentId());
        return data;
    }

    public static List<Map<String, Object>> getZone(List<Map<String, Object>> zones) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("zones", zones);
        dataList.add(data);
        return dataList;
    }

    /**
     * 封装用户站点信息
     *
     * @param user 用户信息
     * @param data 站点信息
     * @return 封装信息
     */
    public static List<Map<String, Object>> getUser(User user, Map<String, Object> data) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        if (user != null && data != null) {
            data.put("userId", user.getId());
            data.put("username", user.getUserName());
        }
        dataList.add(data);
        return dataList;
    }

    public static Map<String, Object> getDevice(DeviceVO device) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nodeId", device.getNodeId());
        data.put("nodeType", device.getNodeType());
        data.put("createTime", device.getCreateTime());
        data.put("sn", device.getSn());
        data.put("stamp", device.getStamp());
        data.put("rssi", device.getRssi());
        data.put("lqi", device.getLqi());
        data.put("interval_i", device.getInterval());
        data.put("lowvoltage", device.getLowvoltage());
        data.put("anomaly", device.getAnomaly());
        return data;
    }

    public static Map<String, Object> getDevice(List<Map<String, Object>> deviceList) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("devices", deviceList);
        return data;
    }

}
