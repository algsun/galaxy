package com.microwise.blackhole.action.logicgroup;

import com.microwise.blackhole.bean.Site;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  根据区域id获取站点
 * </pre>
 *
 * @author: Wang yunlong
 * @time: 12-11-30 下午4:08
 * @check @li.jianfei #543 2012-12-05
 */

@Beans.Action
@Blackhole
public class FindSiteAction {
    @Autowired
    private LogicGroupService logicGroupService;
    /**
     * 地区编码
     */
    private int areaCode;
    /**
     * site列表
     */
    private List<Map<String, Object>> sites;

    public String execute() {
        sites = new ArrayList<Map<String, Object>>();
        List<Site> siteList = logicGroupService.findSiteListByAreaCode(areaCode);
        for (Site s : siteList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("siteId", s.getSiteId());
            map.put("siteName", s.getSiteName());
            sites.add(map);
        }
        return Action.SUCCESS;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public List<Map<String, Object>> getSites() {
        return sites;
    }

    public void setSites(List<Map<String, Object>> sites) {
        this.sites = sites;
    }
}
