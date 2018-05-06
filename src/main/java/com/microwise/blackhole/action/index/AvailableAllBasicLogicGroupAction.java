package com.microwise.blackhole.action.index;

import com.microwise.biela.service.MapIndexService;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * <pre>
 * 获取所有可用到额基层站点
 * </pre>
 *
 * @author Wang yunlong
 * @time 13-4-2 下午4:24
 */
@Beans.Action
@Blackhole
public class AvailableAllBasicLogicGroupAction {
    @Autowired
    private LogicGroupService logicGroupService;
    /**
     * 站点地图服务
     */
    @Autowired
    private MapIndexService mapIndexService;

    /**
     * 站点组
     */
    private List<Map<String, Object>> logicGroupMap;


    public String execute() {
        logicGroupMap = new ArrayList<Map<String, Object>>();
        User user = Sessions.createByAction().currentUser();
        LogicGroup logicGroup = Sessions.createByAction().currentLogicGroup();
        List<LogicGroup> logicGroups = logicGroupService.findLogicGroupForMap(logicGroup.getId(), user.getId());
        for (LogicGroup l : logicGroups) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", l.getId());
            Map<String, Object> site = new HashMap<String, Object>();
            site.put("lngBaiDu", l.getSite().getLngBaiDu());
            site.put("latBaiDu", l.getSite().getLatBaiDu());
            site.put("siteName", l.getSite().getSiteName());
            map.put("site", site);
            logicGroupMap.add(map);
        }

        //站点所在区域名
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("areaNames", areaNames(logicGroups));
        logicGroupMap.add(map);

        //最大最小坐标
        Map<String, Object> latLngMap = getMaxLatMinLatAndMaxLngMinLng(logicGroups);
        logicGroupMap.add(latLngMap);

        //地图中心点坐标
        Map<String, Object> center = getCenter(latLngMap);
        logicGroupMap.add(center);
        return Action.SUCCESS;
    }

    //站点所在区域名去重
    private Set<String> areaNames(List<LogicGroup> logicGroupList) {
        Set<String> areaNames = new TreeSet<String>();
        for (LogicGroup logicGroup : logicGroupList) {
            String name = mapIndexService.findAreaCodePOBySiteId(logicGroup.getSite().getSiteId()).getAreaName();
            areaNames.add(name);
        }
        return areaNames;
    }

    //获取最大最小坐标值
    private Map<String, Object> getMaxLatMinLatAndMaxLngMinLng(List<LogicGroup> logicGroupList) {
        List<Double> lat = new ArrayList<Double>();
        List<Double> lng = new ArrayList<Double>();
        for (LogicGroup l : logicGroupList) {
            Double la = l.getSite().getLatBaiDu();
            Double ln = l.getSite().getLngBaiDu();
            if (ln != null) {
                lng.add(ln);
            } else {
                continue;
            }

            if (la != null) {
                lat.add(la);
            } else {
                continue;
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (lat.size() > 0) {
            Collections.sort(lat);
            map.put("minLat", lat.get(0));
            map.put("maxLat", lat.get(lat.size() - 1));
        }
        if (lng.size() > 0) {
            Collections.sort(lng);
            map.put("minLng", lng.get(0));
            map.put("maxLng", lng.get(lng.size() - 1));
        }
        return map;
    }

    //获取地图中心点
    private Map<String, Object> getCenter(Map<String, Object> LatAndLng) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (LatAndLng.size() > 0) {
            double lat = (Double.parseDouble(LatAndLng.get("minLat").toString()) + Double.parseDouble(LatAndLng.get("maxLat").toString())) / 2;
            double lng = (Double.parseDouble(LatAndLng.get("minLng").toString()) + Double.parseDouble(LatAndLng.get("maxLng").toString())) / 2;
            map.put("centerLat", lat);
            map.put("centerLng", lng);
        }
        return map;
    }

    public List<Map<String, Object>> getLogicGroupMap() {
        return logicGroupMap;
    }

    public void setLogicGroupMap(List<Map<String, Object>> logicGroupMap) {
        this.logicGroupMap = logicGroupMap;
    }
}
