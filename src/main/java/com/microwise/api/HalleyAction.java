package com.microwise.api;

import com.microwise.api.bean.ApiResult;
import com.microwise.blueplanet.bean.vo.DeviceDataVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.proxy.DeviceProxy;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import com.microwise.halley.bean.vo.ExhibitionVO;
import com.microwise.halley.service.*;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Halley 系统 API for Encke
 *
 * @author li.jianfei
 * @date 2013-12-16
 */
@Controller
public class HalleyAction {

    @Autowired
    private ExhibitionService exhibitionService;

    @Autowired
    private ExhibitionStateService exhibitionStateService;

    @Autowired
    private CarService carService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DeviceProxy deviceProxy;

    @Autowired
    private PackingListService packingListService;

    /**
     * 设备检测指标ID:经度
     */
    public static final int SENSORPHYSICALID_LONGITUDE = 12287;
    /**
     * 设备检测指标ID:纬度
     */
    public static final int SENSORPHYSICALID_LATITUDE = 12286;

    /**
     * 设备检测指标ID:开关量
     */
    public static final int SENSORPHYSICALID_SWH = 89;

    /**
     * 设备检测指标ID:震动
     */
    public static final int SENSORPHYSICALID_SHAKE = 88;

    /**
     * 设备检测指标ID:加速度
     */
    public static final int SENSORPHYSICALID_ACCL = 87;


    @RequestMapping(value = "/exhibitions", method = RequestMethod.GET)
    @ApiOperation(value = "获取外展记录", position = 2, httpMethod = "GET",
            notes = "获取外展记录"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<ExhibitionVO>> getExhibitionList(@RequestParam String siteId) {

        List<ExhibitionVO> exhibitionList;

        ApiResult<List<ExhibitionVO>> result = new ApiResult<List<ExhibitionVO>>();
        try {
            exhibitionList = exhibitionService.findExhibitionListNotEnd(siteId);
            setExhibitionState(exhibitionList);
            result.setSuccess(true);
            result.setMessage("获取外展记录成功");
            result.setData(exhibitionList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("获取外展记录失败");
        }
        return result;
    }

    private void setExhibitionState(List<ExhibitionVO> exhibitionList) {
        for (ExhibitionVO exhibitionVO : exhibitionList) {
            //获取所有历史操作
            List<ExhibitionStateVO> exhibitions = exhibitionStateService.getHistoryState(exhibitionVO.getExhibitionId());
            // 设置外展当前状态
            ExhibitionStateVO exhibitionStateVO = exhibitionStateService.findCurrentState(exhibitionVO.getExhibitionId());
            for (ExhibitionStateVO ev : exhibitions) {
                if (ev.getState() == exhibitionStateVO.getState()) {
                    exhibitionVO.setHasPathPO(ev.getPathPO() != null);
                }
            }
        }
    }

    private ExhibitionVO getCurrentExhibitionState(int exhibitionId) {
        ExhibitionVO exhibitionVO = new ExhibitionVO();
        List<ExhibitionStateVO> exhibitions = exhibitionStateService.getHistoryState(exhibitionId);
        // 设置外展当前状态
        ExhibitionStateVO exhibitionStateVO = exhibitionStateService.findCurrentState(exhibitionId);
        for (ExhibitionStateVO ev : exhibitions) {
            if (ev.getState() == exhibitionStateVO.getState()) {
                exhibitionVO.setHasPathPO(ev.getPathPO() != null);
                exhibitionVO.setState(exhibitionStateVO.getState());
            }
        }
        return exhibitionVO;
    }

    @RequestMapping(value = "/exhibitions/{exhibitionId}", method = RequestMethod.GET)
    @ApiOperation(value = "更新外展状态", position = 2, httpMethod = "GET",
            notes = "更新外展状态"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<ExhibitionVO> addExhibitionState(@PathVariable int exhibitionId, @RequestParam int state, @RequestParam int operator) {

        ApiResult<ExhibitionVO> result = new ApiResult<ExhibitionVO>();
        try {
            if (state == 1) {   // 开始外展
                // 判段是否存在预设路线
                if (exhibitionStateService.getALLPathPO(exhibitionId).size() <= 0) {
                    result.setSuccess(false);
                    result.setMessage("本次外展尚未预设线路");
                }
                // 判段是否配置设备
                if (locationService.findLocationListByExhibitionId(exhibitionId).size() <= 0) {
                    result.setSuccess(false);
                    result.setMessage("本次外展尚未配置设备");
                }
                // 判段是否存在装箱单
                if (packingListService.findPackingList(exhibitionId).size() <= 0) {
                    result.setSuccess(false);
                    result.setMessage("本次外展尚无装箱单");
                }
            } else if (state == 4) {
                result.setSuccess(true);
                result.setMessage("更新外展状态成功");
            } else {
                exhibitionStateService.addExhibitionState(exhibitionId, state, operator);
                result.setSuccess(true);
                result.setMessage("更新外展状态成功");
            }
            //获取修改后的状态
            result.setData(getCurrentExhibitionState(exhibitionId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("更新外展状态失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/exhibitions/{exhibitionId}/cars", method = RequestMethod.GET)
    @ApiOperation(value = "获取外展车辆", position = 2, httpMethod = "GET",
            notes = "获取外展车辆"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<CarVO>> getExhibitionCarsWithDevices(@PathVariable int exhibitionId) {

        ApiResult<List<CarVO>> result = new ApiResult<List<CarVO>>();
        try {
            List<CarVO> carList = carService.findCarsWithDeviceByExhibitionId(exhibitionId);

            result.setSuccess(true);
            result.setMessage("获取外展车辆成功");
            result.setData(carList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取外展车辆失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/exhibitions/{exhibitionId}/cars/realtimeDataList", method = RequestMethod.GET)
    @ApiOperation(value = "获取车辆相关实时数据", position = 2, httpMethod = "GET",
            notes = "获取车辆相关实时数据"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<RealtimeDataVO>> getRealtimeDataList(@PathVariable int exhibitionId) {
        ApiResult<List<RealtimeDataVO>> result = new ApiResult<List<RealtimeDataVO>>();
        try {
            //获取外展传感设备的ID集合
            List<LocationVO> locationList = locationService.findLocationListByExhibitionId(exhibitionId);
            List<String> deviceIds = new ArrayList<String>();
            for (LocationVO locationInfo : locationList) {
                deviceIds.add(locationInfo.getId());
            }
            List<RealtimeDataVO> realTimeDataList = deviceProxy.findRealtimeData(deviceIds);

            Iterator iterator = realTimeDataList.iterator();
            while (iterator.hasNext()) {
                RealtimeDataVO data = (RealtimeDataVO) iterator.next();
                Map<Integer, DeviceDataVO> sensorinfoMap = data.getSensorinfoMap();

                if (!(sensorinfoMap.containsKey(SENSORPHYSICALID_LONGITUDE) ||
                        sensorinfoMap.containsKey(SENSORPHYSICALID_LATITUDE) ||
                        sensorinfoMap.containsKey(SENSORPHYSICALID_SWH) ||
                        sensorinfoMap.containsKey(SENSORPHYSICALID_SHAKE) ||
                        sensorinfoMap.containsKey(SENSORPHYSICALID_ACCL))) {
                    iterator.remove();
                }
            }
            result.setSuccess(true);
            result.setData(realTimeDataList);
            result.setMessage("获取车辆相关实时数据成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取车辆相关实时数据失败");
            e.printStackTrace();
        }
        return result;
    }

}
