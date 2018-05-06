package com.microwise.blackhole.action.logicgroup;

import com.microwise.blackhole.bean.AreaCodeCN;
import com.microwise.blackhole.service.AreaCodeService;
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
 *  根据区域id获取下级区域
 * </pre>
 *
 * @author: Wang yunlong
 * @time: 12-11-30 下午4:07
 * @check @li.jianfei #543 2012-12-05
 */
@Beans.Action
@Blackhole
public class FindAreaAction {

    @Autowired
    private AreaCodeService areaCodeService;
    //input
    /**
     * 地区id
     */
    private int areaCode;

    /**
     * 查询结果
     */
    private List<Map<String, Object>> areaCodeCNs;

    public String execute() {
        areaCodeCNs = new ArrayList<Map<String, Object>>();
        List<AreaCodeCN> areaCodes = areaCodeService.findAreaListByAreaCode(areaCode);
        for (AreaCodeCN a : areaCodes) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("areaCode", a.getAreaCode());
            map.put("areaName", a.getAreaName());
            areaCodeCNs.add(map);
        }
        return Action.SUCCESS;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public List<Map<String, Object>> getAreaCodeCNs() {
        return areaCodeCNs;
    }

    public void setAreaCodeCNs(List<Map<String, Object>> areaCodeCNs) {
        this.areaCodeCNs = areaCodeCNs;
    }
}
