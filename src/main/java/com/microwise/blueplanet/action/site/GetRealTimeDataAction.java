package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 获取站点实时数据  没有监测指标过滤
 *
 * @author Wang yunlong
 * @date 13-1-23 下午4:02
 * @check @gaohui 2013-01-29 #1297
 * @check @gaohui 2013-02-25 #1316
 */
@Beans.Action
@Blueplanet
public class GetRealTimeDataAction {
    @Autowired
    private SiteService siteService;

    //output
    /**
     * 站点实时数据
     */
    private List<RealtimeDataVO> data;

    @Route("/blueplanet/site/realtime-data.json")
    public String execute() {
        String siteId = SiteRealTimeDataAction.getSiteId();
        data = siteService.findRealtimeDataLocation(siteId);
        return Results.json().root("data").done();
    }

    public List<RealtimeDataVO> getData() {
        return data;
    }

    public void setData(List<RealtimeDataVO> data) {
        this.data = data;
    }
}
