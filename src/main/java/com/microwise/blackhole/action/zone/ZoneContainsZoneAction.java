package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <pre>
 * 判断一个区域是否属于另一个区域的子（孙）区域
 * </pre>
 *
 * @author Wang yunlong
 * @date 13-2-6 上午9:49
 * @check @gaohui 2013-02-25 #1485
 */
@Beans.Action
@Blackhole
public class ZoneContainsZoneAction {
    @Autowired
    private ZoneManagerProxy zoneManagerProxy;
    //input
    /**
     * 当前区域
     */
    private String zoneId;
    /**
     * 移动上级区域
     */
    private String newParentId;

    //output
    /**
     * 是否可以移动
     */
    private boolean contains;

    @Route("blackhole/zone/manage/contains.json")
    public String execute() {
        List<String> children = zoneManagerProxy.findChildrenIdList(zoneId);
        contains = !children.contains(newParentId);
        return Results.json().root("contains").done();
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getNewParentId() {
        return newParentId;
    }

    public void setNewParentId(String newParentId) {
        this.newParentId = newParentId;
    }

    public boolean isContains() {
        return contains;
    }

    public void setContains(boolean contains) {
        this.contains = contains;
    }
}
