package com.microwise.blueplanet.action.historyDataExport;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 历史数据导出等待页面
 *
 * @author bai.weixing
 * @since 2018/01/16
 */
@Beans.Action
@Blueplanet
public class HistoryDataExportWaitingAction extends BlueplanetLoggerAction {

    private static final Logger logger = LoggerFactory.getLogger(HistoryDataExportWaitingAction.class);

    @Autowired
    private LocationService locationService;

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
     * 区域id
     */
    private String zoneId;
    /**
     * 位置点id
     */
    private String locationId;

    private String siteId;

    private List<LocationVO> locationVOList = new ArrayList<>();

    int locationCount;

    private String websocketServerURL = "";

    {
        websocketServerURL = ConfigFactory.getInstance().getConfig("config.properties").get("websocketServerURL");
    }


    @Route("/blueplanet/history-data-export-wait-begin")
    public String viewExport() {
        return Results.ftl("/blueplanet/pages/historyDataExport/history-data-export-wait-begin");
    }

    @Route("/blueplanet/history-data-export-wait.json")
    public String prepareData() {
        try {
            if (!"全部".equals(locationId)) {
                locationVOList.add(locationService.findLocationById(locationId));
            } else if (!"全部".equals(zoneId)) {
                locationVOList = locationService.findLocationsByZoneId(zoneId, false);
            } else {
                locationVOList = locationService.findLocationsBySiteId(siteId);
            }
            for (LocationVO locationVO : locationVOList) {
                int tempCount = locationService.findRecentDataListCount(locationVO.getId(), startTime, endTime);
                count = count + tempCount;
                if (tempCount > 0) {
                    locationCount++;
                }
            }
            log("位置点历史数据", "历史数据导出");
        } catch (Exception e) {
            logger.error("位置点历史数据导出", e);
        }
        Map map = new HashMap();
        map.put("total", count);
        map.put("locationCount", locationCount);
        return Results.json().asRoot(map).done();
    }


    /**
     * 等待
     */
    @Route("/blueplanet/history-data-export-wait")
    public String viewExportProgress() {
        return Results.ftl("/blueplanet/pages/historyDataExport/history-data-export-wait");
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

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<LocationVO> getLocationVOList() {
        return locationVOList;
    }

    public void setLocationVOList(List<LocationVO> locationVOList) {
        this.locationVOList = locationVOList;
    }

    public int getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(int locationCount) {
        this.locationCount = locationCount;
    }

    public String getWebsocketServerURL() {
        return websocketServerURL;
    }

    public void setWebsocketServerURL(String websocketServerURL) {
        this.websocketServerURL = websocketServerURL;
    }
}
