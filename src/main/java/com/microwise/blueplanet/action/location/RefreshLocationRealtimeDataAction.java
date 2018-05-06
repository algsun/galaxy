package com.microwise.blueplanet.action.location;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.RecentDataVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 实时刷新位置点实时数据 (ajax)
 * </pre>
 *
 * @author xuyuexi
 * @time 14-7-1
 */
@Beans.Action
@Blueplanet
public class RefreshLocationRealtimeDataAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(RefreshLocationRealtimeDataAction.class);


    @Autowired
    private LocationService locationService;


    //input
    /**
     * 设备id
     */
    private String locationId;

    /**
     * 获取的数据数量
     */
    private int count;
    /**
     * 开始时间
     */
    private long startTime;
    //output
    /**
     * 设备实时数据
     */
    private List<Map<String, Object>> data;
    private List<RecentDataVO> recentDataList;

    @Route("/blueplanet/location/{locationId}/realtime-data.json")
    public String queryRealtimeData() {
        try {
            recentDataList = locationService.findRecentDataList(locationId, count, startTime > 0 ? new Date(startTime) : null);
            log("位置点", "实时数据");
        } catch (Exception e) {
        logger.error("位置点实时数据",e);
        }
        return Results.json().root("recentDataList").done();
    }


    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RecentDataVO> getRecentDataList() {
        return recentDataList;
    }

    public void setRecentDataList(List<RecentDataVO> recentDataList) {
        this.recentDataList = recentDataList;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
