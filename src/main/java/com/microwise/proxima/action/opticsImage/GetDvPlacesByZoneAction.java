package com.microwise.proxima.action.opticsImage;

import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.service.OpticsDVPlaceService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 根据区域获取光学摄像机点位
 * </pre>
 *
 * @author Wang yunlong
 * @time 13-3-26 下午2:53
 * @check li.jianfei 2013-3-29 #2361
 */
@Beans.Action
@Proxima
public class GetDvPlacesByZoneAction {
    private static final Logger log = LoggerFactory.getLogger(GetDvPlacesByZoneAction.class);
    @Autowired
    private OpticsDVPlaceService opticsDVPlaceService;
    /**
     * 区域
     */
    private String zoneId;

    /**
     * 光学摄像机点位
     */
    private List<Map<String, Object>> dvPlaces;

    public String execute() {
        try {
            List<OpticsDVPlaceBean> opticsDVPlaces;
            String siteId = Sessions.createByAction().currentLogicGroup().getSite().getSiteId();
            if (Strings.isNullOrEmpty(zoneId)) {
                opticsDVPlaces = opticsDVPlaceService.findAll(siteId);
            } else {
                opticsDVPlaces = opticsDVPlaceService.findByZoneId(zoneId);
            }
            dvPlaces = new ArrayList<Map<String, Object>>();
            for (OpticsDVPlaceBean o : opticsDVPlaces) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", o.getId());
                map.put("placeName", o.getPlaceName());
                dvPlaces.add(map);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<Map<String, Object>> getDvPlaces() {
        return dvPlaces;
    }

    public void setDvPlaces(List<Map<String, Object>> dvPlaces) {
        this.dvPlaces = dvPlaces;
    }
}
