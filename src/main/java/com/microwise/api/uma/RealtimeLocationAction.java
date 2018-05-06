package com.microwise.api.uma;

import com.microwise.api.bean.ApiResult;
import com.microwise.common.action.ActionMessage;
import com.microwise.uma.bean.ZoneBean;
import com.microwise.uma.service.ZoneService;
import com.microwise.uma.sys.UmaLoggerAction;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 实时定位
 *
 * @author xiedeng
 * @date 2014-2-27
 */
@Controller
public class RealtimeLocationAction extends UmaLoggerAction {

    /**
     * 区域 Service 接口
     */
    @Autowired
    private ZoneService zoneService;

    /**
     * 查询实时人员分布统计信息
     *
     * @return
     */

    @RequestMapping(value = "/location/{siteId}/{queryType}/{hourInterval}/realtimeLocation", method = RequestMethod.GET)
    @ApiOperation(value = "获取实时人员分布统计信息", position = 2, httpMethod = "GET",
            notes = "获取实时人员分布统计信息"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<ZoneBean>> getRealtimeLocation(@PathVariable int queryType, @PathVariable int hourInterval,
                                                         @PathVariable String siteId, @RequestParam String closeZoneIds) {
        ApiResult<List<ZoneBean>> result = new ApiResult<List<ZoneBean>>();
        try {
            List<ZoneBean> zoneList = zoneList(queryType, hourInterval, siteId, closeZoneIds);
            result.setSuccess(true);
            result.setMessage("获取实时人员分布统计信息成功");
            result.setData(zoneList);
            log("实时人员分布", "按规则统计");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务端出错").consume();
            logFailed("查询区域人员实时信息", "查询区域人员实时信息失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 区域人员详情
     *
     * @return
     */

    @RequestMapping(value = "/location/{siteId}/{zoneId}/{queryType}/{hourInterval}/userLocationDetail", method = RequestMethod.GET)
    @ApiOperation(value = "获取区域人员详情信息", position = 2, httpMethod = "GET",
            notes = "获取区域人员详情信息"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<ZoneBean>> getUserLocationDetail(@PathVariable int queryType, @PathVariable int hourInterval,
                                                           @PathVariable String siteId, @PathVariable String zoneId) {
        ApiResult<List<ZoneBean>> result = new ApiResult<List<ZoneBean>>();
        try {
            List<ZoneBean> zoneList = zoneDetail(queryType, siteId, zoneId, hourInterval);
            result.setSuccess(true);
            result.setMessage("获取区域人员详情信息成功");
            result.setData(zoneList);
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务端出错").consume();
            logFailed("查询区域人员详细信息", "查询区域人员详细信息失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取实时人员分布统计信息
     */
    private List<ZoneBean> zoneList(int queryType, int hourInterval, String siteId, String closeZoneIds) {
        List<ZoneBean> zoneList = new ArrayList<ZoneBean>();
        if (queryType == 0) {   // 按规则统计
            zoneList = zoneService.countGoTimesWithZoneId(siteId, 1, getClosedZoneIds(closeZoneIds));
        } else if (queryType == 1) {           // 按设备统计
            hourInterval = (hourInterval == 0 ? 1 : hourInterval);
            zoneService.findZoneTree(zoneList, siteId, null, 0, hourInterval, getClosedZoneIds(closeZoneIds));
        }
        List<ZoneBean> zones = new ArrayList<ZoneBean>();
        for (ZoneBean zoneBean : zoneList) {
            if (zoneBean.getLevel() == 1) {
                zones.add(zoneBean);
            }
        }
        zoneFilter(zones);
        return zones;
    }

    private void zoneFilter(List<ZoneBean> zones) {
        if (zones == null || zones.isEmpty()) {
            return;
        }
        Iterator<ZoneBean> zoneBeanIterator = zones.iterator();
        while (zoneBeanIterator.hasNext()) {
            ZoneBean zoneBean = zoneBeanIterator.next();
            if (zoneBean.getUserName() == null) {
                zoneBeanIterator.remove();
            }
            zoneFilter(zoneBean.getSubZoneList());
        }
    }

    /**
     * 获取区域详情信息(人员)
     */
    private List<ZoneBean> zoneDetail(int queryType, String siteId, String zoneId, int hourInterval) {
        List<ZoneBean> zoneList = new ArrayList<ZoneBean>();
        if (queryType == 0) {   // 按规则统计
            zoneList = zoneService.findPeopleInOneZone(siteId, zoneId);
        } else if (queryType == 1) {    // 按设备统计
            zoneList = zoneService.findPeopleInZone(siteId, zoneId, hourInterval);
        }
        return zoneList;
    }

    public List<String> getClosedZoneIds(String closedZoneIds) {
        List<String> closedList = new ArrayList<String>();
        if (closedZoneIds != null) {
            String temp[] = closedZoneIds.split(",");
            Collections.addAll(closedList, temp);
        }
        return closedList;
    }
}

