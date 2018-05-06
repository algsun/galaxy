package com.microwise.blueplanet.action.location;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 历史数据导出等待页面
 *
 * @author gaohui
 * @date 13-2-18 14:31
 * @check @wang yunlong 2013-02-25 #1566
 */
@Beans.Action
@Blueplanet
public class LocationHistoryDataExportWaitingAction extends BlueplanetLoggerAction {

    private static final Logger logger = LoggerFactory.getLogger(LocationHistoryDataExportWaitingAction.class);

    @Autowired
    private LocationService locationService;

    //input, output
    // 位置点ID
    private String locationId;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 数据条数
     */
    private int count;

    /**
     * 等待页面
     */
    @Route("/blueplanet/location/{locationId}/waiting-for-export")
    public String viewExport() {
        try {
            count = locationService.findRecentDataListCount(locationId, startTime, endTime);
            log("位置点历史数据", "历史数据导出");
        } catch (Exception e) {
            logger.error("位置点历史数据导出", e);
        }
        return Results.ftl("/blueplanet/pages/location/history-data-export");
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
