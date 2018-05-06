package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.ChartService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.HighChartUtil;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 光照图
 *
 * @author xuyuexi
 * @date 14-10-28
 * @check wang.geng xu.yuexi #9753 2014-10-30
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class DayLightAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(DayLightAction.class);


    @Autowired
    private LocationService locationService;

    @Autowired
    private ChartService chartService;


    // Input
    /**
     * 设备ID
     */
    private String locationId;

    /**
     * 时间类型 默认 ：天
     */
    private int dateType;

    // Output
    /**
     * HighChartData
     */
    private Map<String, Object> highChartData;

    @Route(value = "summarize/day-light.json", params = "dateType")
    public String view() {
        try {
            //当天累计光照
            Date date = new Date();
            String siteId = Sessions.createByAction().currentSiteId();
            //找出最新的活着的设备的位置点
            locationId = locationService.findLocationIdBySensorIdAndSiteId(Constants.ChartConstants.SENSORINFO_LX, siteId);
            if (!Strings.isNullOrEmpty(locationId)) {
                ChartVO chart = chartService.findLight(locationId, dateType, date);
                // 将原始数据转换为 HighChart 格式数据
                LocationVO location = locationService.findLocationById(locationId);
                highChartData = HighChartUtil.packageLight(locationId, location.getLocationName() != null ? location.getLocationName() : locationId, chart);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                highChartData.put("date", formatter.format(date));
            }
            log("站点总览", "累积光照");
        } catch (Exception e) {
            log.error("站点总览累积光照", e);
        }
        return Results.json().asRoot(highChartData).done();
    }


    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public Map<String, Object> getHighChartData() {
        return highChartData;
    }

    public void setHighChartData(Map<String, Object> highChartData) {
        this.highChartData = highChartData;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
