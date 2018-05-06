package com.microwise.api.blueplanet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.microwise.api.bean.ApiResult;
import com.microwise.blueplanet.bean.po.AvgdataPO;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.proxima.proxy.ZoneProxy;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by lijianfei on 2017/7/11.
 *
 * @author li.jianfei
 * @since 2017/7/11
 */
@Controller
@Api(value = "Blueplanet", description = "环境监控", position = 12)
public class ZoneController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int OUT_SIDE = 2;


    @Autowired
    private ZoneManagerProxy zoneManagerProxy;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AppCacheHolder appCacheHolder;


    @Autowired
    private ZoneProxy zoneProxy;

    @RequestMapping(value = "sites/{siteId}/zones", method = RequestMethod.POST)
    @ApiOperation(value = "添加区域", position = 1, httpMethod = "POST", notes = "为指定站点添加区域")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<ZoneVO> save(@PathVariable String siteId, @RequestParam String name) {
        ApiResult<ZoneVO> result = new ApiResult<ZoneVO>();
        ZoneVO zone = new ZoneVO();
        zone.setSiteId(siteId);
        zone.setZoneName(name);
        zone.setPosition(OUT_SIDE);
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            if (zoneManagerProxy.containsName(siteId, null, name)) {
                result.setSuccess(false);
                result.setMessage("已存在同名区域");
            } else {
                String zoneId = zoneManagerProxy.saveZone(zone);
                ZoneVO newZone = zoneManagerProxy.findZoneById(zoneId);
                result.setData(newZone);
                //清除缓存
                appCacheHolder.evictZoneDeviceTree(siteId);
                result.setSuccess(true);
            }
        } catch (Exception e) {
            logger.error("添加区域异常", e);
            result.setSuccess(false);
            result.setMessage("服务端出错，请联系接口供应商");
        }
        return result;
    }

    @RequestMapping(value = "sites/{siteId}/zones/{zoneId}", method = RequestMethod.PATCH)
    @ApiOperation(value = "编辑区域", position = 2, httpMethod = "PATCH", notes = "提供修改区域名称功能")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult update(@PathVariable String siteId, @PathVariable String zoneId, ZoneVO zone) {
        ApiResult result = new ApiResult();
        try {
            if (!zoneManagerProxy.isNameAvailable(siteId, null, zoneId, zone.getZoneName())) {
                result.setSuccess(false);
                result.setMessage("已存在同名区域");
            }
            ZoneVO tempZone = zoneManagerProxy.findZoneById(zoneId);
            zoneManagerProxy.updateZone(zoneId, zone.getZoneName(), tempZone.getPlanImage(), tempZone.getPosition());
            //清除缓存
            appCacheHolder.evictZoneDeviceTree(siteId);
            appCacheHolder.evictZone(zoneId);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("服务端出错，请联系接口供应商");
        }
        return result;
    }


    @RequestMapping(value = "sites/{siteId}/zones/{zoneId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除区域", position = 3, httpMethod = "DELETE", notes = "根据区域ID删除区域信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult delete(@PathVariable String siteId, @PathVariable String zoneId) {
        ApiResult result = new ApiResult();
        try {
            if (zoneManagerProxy.isEmpty(zoneId) && (!zoneProxy.hasDV(zoneId))) {
                zoneManagerProxy.deleteZone(zoneId);
                appCacheHolder.evictZoneDeviceTree(siteId);
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("该区域已绑定设备或摄像机，不能被删除");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("服务端出错，请联系接口供应商");
        }
        return result;
    }

    @ApiOperation(value = "查询区域指定位置点历史数据", position = 4, httpMethod = "GET", notes = "根据区域ID和设备ID白名单查询节点历史数据")
    @RequestMapping(value = "zones/{zoneId}/history-data", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<List<Map<String, Object>>> historyData(@PathVariable String zoneId, @RequestParam String allow,
                                                            @RequestParam("begin_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                                            @RequestParam("end_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        ApiResult<List<Map<String, Object>>> result = new ApiResult<List<Map<String, Object>>>();
        try {
            List<LocationVO> locations = zoneService.findLocations(zoneId);

            List<String> allowed = Arrays.asList(allow.split(","));

            // 过滤白名单
            Iterator<LocationVO> iterator = locations.iterator();
            while (iterator.hasNext()) {
                LocationVO location = iterator.next();
                if (!allowed.contains(location.getId())) {
                    iterator.remove();
                }
            }

            // 组装数据
            List<Map<String, Object>> data = Lists.newArrayList();
            // 查询历史数据
            Map<String, Object> recentDataMap = null;
            for (LocationVO location : locations) {
                DeviceVO device = deviceService.findDeviceById(location.getNodeId());
                List<RecentDataVO> recentDataList = locationService.findRecentDataList(location.getId(), beginTime, endTime);

                List<Map<String, Object>> recentDatas = Lists.newArrayList();

                for (RecentDataVO recentData : recentDataList) {
                    recentDataMap = Maps.newHashMap();
                    if (recentData.getSensorInfoMap().containsKey(32)) {
                        recentDataMap.put("humidity", recentData.getSensorInfoMap().get(32).getSensorPhysicalValue());
                        recentDataMap.put("timestamp", recentData.getSensorInfoMap().get(32).getStamp());
                    }
                    if (recentData.getSensorInfoMap().containsKey(33)) {
                        recentDataMap.put("temperature", recentData.getSensorInfoMap().get(33).getSensorPhysicalValue());
                        recentDataMap.put("timestamp", recentData.getSensorInfoMap().get(33).getStamp());
                    }
                    recentDatas.add(recentDataMap);
                }


                // 组织设备数据
                Map<String, Object> locationData = Maps.newHashMap();
                locationData.put("nodeId", device.getNodeId());
                locationData.put("locationId", location.getId());
                locationData.put("interval", device.getInterval());
                locationData.put("history_data", recentDatas);


                data.add(locationData);
            }

            result.setSuccess(true);
            result.setData(data);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取区域位置点历史数据失败");
            logger.error("获取区域位置点历史数据失败", e);
        }

        return result;
    }

    @ApiOperation(value = "查询区域指定位置点均峰值数据", position = 4, httpMethod = "GET", notes = "根据区域ID和设备ID白名单查询节点均峰值数据")
    @RequestMapping(value = "zones/{zoneId}/average-data", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<List<Map<String, Object>>> averageData(@PathVariable String zoneId, @RequestParam String allow,
                                                            @RequestParam("begin_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                                            @RequestParam("end_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        ApiResult<List<Map<String, Object>>> result = new ApiResult<List<Map<String, Object>>>();
        try {
            List<LocationVO> locations = zoneService.findLocations(zoneId);

            List<String> allowed = Arrays.asList(allow.split(","));

            // 过滤白名单
            Iterator<LocationVO> iterator = locations.iterator();

            while (iterator.hasNext()) {
                LocationVO location = iterator.next();
                if (!allowed.contains(location.getId())) {
                    iterator.remove();
                }

            }

            // 组装数据
            List<Map<String, Object>> data = Lists.newArrayList();
            Map<String, Object> averageDataMap = null;
            for (LocationVO location : locations) {
                DeviceVO device = deviceService.findDeviceById(location.getNodeId());
                List<Map<String, Object>> averageDatas = Lists.newArrayList();
                List<AvgdataPO> avgdataList = locationService.findAverageAndPeakValue(location.getId(), beginTime, endTime);
                for (AvgdataPO avgdata : avgdataList) {
                    averageDataMap = Maps.newHashMap();
                    averageDataMap.put("average", avgdata.getAvgValue());
                    averageDataMap.put("date", avgdata.getMsDate());
                    averageDataMap.put("max", avgdata.getMaxValue());
                    averageDataMap.put("max_time", avgdata.getMaxTime());
                    averageDataMap.put("min", avgdata.getMinValue());
                    averageDataMap.put("min_time", avgdata.getMinTime());
                    averageDataMap.put("sensorId", avgdata.getSensorPhysicalid());

                    averageDatas.add(averageDataMap);
                }
                // 组织设备数据
                Map<String, Object> locationData = Maps.newHashMap();
                locationData.put("nodeId", device.getNodeId());
                locationData.put("locationId", location.getId());
                locationData.put("average_data", averageDatas);

                data.add(locationData);
            }

            result.setSuccess(true);
            result.setData(data);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取区域位置点均峰值数据失败");
            logger.error("获取区域位置点均峰值数据失败", e);
        }

        return result;
    }


}
