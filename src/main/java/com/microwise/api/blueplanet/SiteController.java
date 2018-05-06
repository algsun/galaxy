package com.microwise.api.blueplanet;

import com.microwise.api.bean.ApiResult;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.AlarmService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ZoneService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijianfei on 2017/7/12.
 *
 * @author li.jianfei
 * @since 2017/7/12
 */
@Controller
@Api(value = "Blueplanet", description = "环境监控", position = 12)
public class SiteController {

    private static final Logger log = LoggerFactory.getLogger(SiteController.class);

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private AlarmService alarmService;


    @RequestMapping(value = "/sites/{siteId}/zones", method = RequestMethod.GET)
    @ApiOperation(value = "获取实时数据", position = 2, httpMethod = "GET",
            notes = "获取实时数据"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<ZoneVO>> getRealTimeDataBySiteId(@PathVariable String siteId) {
        ApiResult<List<ZoneVO>> result = new ApiResult<List<ZoneVO>>();
        List<ZoneVO> zones = new ArrayList<ZoneVO>();
        try {
            List<ZoneVO> zoneVOs = zoneService.findZoneList(siteId, null);
            for (ZoneVO zone : zoneVOs) {
                zones.add(zone);
                findAllZoneList(zone, zones);
            }
            //查询区域下的设备和实时数据
            refreshRealTimeData(zones);

            result.setSuccess(true);
            result.setMessage("获取实时数据记录成功");
            result.setData(zones);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取实时数据记录失败");
            log.error("根据站点编号获取实时数据记录失败", e);
        }
        return result;
    }


    private void findAllZoneList(ZoneVO parentZone, List<ZoneVO> zoneVOs) {
        List<ZoneVO> zoneVOList = zoneService.findZones(parentZone.getZoneId());
        for (ZoneVO zoneVO : zoneVOList) {
            zoneVO.setZoneName(parentZone.getZoneName().concat(">>").concat(zoneVO.getZoneName()));
            zoneVOs.add(zoneVO);
            findAllZoneList(zoneVO, zoneVOs);
        }
    }

    /**
     * 查询区域下的设备和实时数据
     */
    private void refreshRealTimeData(List<ZoneVO> zones) {
        for (ZoneVO zone : zones) {
            List<LocationVO> locations = locationService.findLocationsByZoneId(zone.getZoneId(), false);
            for (LocationVO location : locations) {
                RealtimeDataVO realtimeDataVO = locationService.findLocationData(location.getId());
                location.setRealtimeData(realtimeDataVO);
                location.setRecentAlarm(alarmService.findRecentAlarmRecord(location.getId()));
            }
            zone.setLocations(locations);
        }
    }

}
