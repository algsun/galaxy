package com.microwise.api.blueplanet;

import com.microwise.api.bean.ApiResult;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ZoneService;
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
 * @author xiedeng
 * @date 14-1-8
 */
@Controller
public class RealtimeDataAction {
    public static final Logger log = LoggerFactory.getLogger(RealtimeDataAction.class);

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LocationService locationService;



    /**
     * 查询区域下的设备和实时数据
     */
    private List<ZoneVO> getZoneRealTimeData(List<ZoneVO> zoneVOs) {
        List<ZoneVO> zoneVOList = new ArrayList<ZoneVO>();
        for (ZoneVO zoneVO : zoneVOs) {
            List<LocationVO> locations = locationService.findLocationsByZoneId(zoneVO.getZoneId(), false);
            if (locations != null && !locations.isEmpty()) {
                for (LocationVO location : locations) {
                    RealtimeDataVO realtimeDataVO = locationService.findLocationData(location.getId());
                    location.setRealtimeData(realtimeDataVO);
                }
                zoneVO.setLocations(locations);
                zoneVOList.add(zoneVO);
            }
        }
        return zoneVOList;
    }

    @RequestMapping(value = "/sites/{siteId}/realtimeData", method = RequestMethod.GET)
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
        List<ZoneVO> zoneVOList = new ArrayList<ZoneVO>();
        try {
            List<ZoneVO> zoneVOs = zoneService.findZoneList(siteId, null);
            for (ZoneVO zoneVO : zoneVOs) {
                zoneVOList.add(zoneVO);
                findAllZoneList(zoneVO, zoneVOList);
            }
            //查询区域下的设备和实时数据
            zoneVOList = getZoneRealTimeData(zoneVOList);
            result.setSuccess(true);
            result.setMessage("获取实时数据记录成功");
            result.setData(zoneVOList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取实时数据记录失败");
            log.error("根据站点编号获取实时数据记录失败", e);
        }
        return result;
    }

    @RequestMapping(value = "/zones/{zoneId}/realtimeData", method = RequestMethod.GET)
    @ApiOperation(value = "获取实时数据", position = 2, httpMethod = "GET",
            notes = "获取实时数据"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<ZoneVO>> getRealTimeDataByZoneId(@PathVariable String zoneId) {
        ApiResult<List<ZoneVO>> result = new ApiResult<List<ZoneVO>>();
        try {
            List<ZoneVO> zoneVOList = new ArrayList<ZoneVO>();
            ZoneVO zoneVO = zoneService.findZoneById(zoneId);
            zoneVOList.add(zoneVO);
            findAllZoneList(zoneVO, zoneVOList);
            //查询区域下的站点和实时数据
            zoneVOList = getZoneRealTimeData(zoneVOList);
            result.setSuccess(true);
            result.setMessage("获取实时数据记录成功");
            result.setData(zoneVOList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取实时数据记录失败");
            log.error("根据区域编号获取实时数据记录失败", e);
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

    @RequestMapping(value = "/devices/{deviceId}/realtimeData", method = RequestMethod.GET)
    @ApiOperation(value = "获取实时数据", position = 2, httpMethod = "GET",
            notes = "获取实时数据"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<RealtimeDataVO> getRealTimeDataByDeviceId(@PathVariable String deviceId) {
        ApiResult<RealtimeDataVO> result = new ApiResult<RealtimeDataVO>();
        try {
            String locationId = locationService.findLocationByNodeId(deviceId).getId();
            RealtimeDataVO realtimeDataVO = locationService.findLocationData(locationId);
            result.setSuccess(true);
            result.setMessage("获取实时数据记录成功");
            result.setData(realtimeDataVO);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取实时数据记录失败");
            log.error("根据设备编号获取实时数据记录失败", e);
        }
        return result;
    }

}
