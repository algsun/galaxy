package com.microwise.halley.action.exhibition;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.vo.PackingListVO;
import com.microwise.halley.service.CarService;
import com.microwise.halley.service.PackingListService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 删除车辆
 *
 * @author wang.geng
 * @date 13-10-8  下午4:53
 * @check @li.jianfei #5842 2013-10-10
 */
@Beans.Action
@Halley
public class DeleteCarAction {

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 车辆信息配置服务层对象
     */
    @Autowired
    private CarService carService;

    /**
     * 装箱单服务对象
     */
    @Autowired
    private PackingListService packingListService;

    //input
    /**
     * 外展ID
     */
    private int exhibitionId;
    /**
     * 车辆ID
     */
    private int carId;

    /**
     * 删除车辆信息
     *
     * @return string
     */
    @Route("/halley/exhibition/deleteCar/{exhibitionId}/{carId}")
    public String deleteCar() {
        try {
            //在删除车辆前需要判断是否有关联的装箱单，如果有，则不能删除
            //需要判断某个车是否有装箱单是否能被删除
            Map<CarPO, List<PackingListVO>> packingMap = packingListService.findPackingListByExhibitionId(exhibitionId);
            for (CarPO carPO : packingMap.keySet()) {
                if (carPO.getId() == carId) {
                    List<PackingListVO> pkList = packingMap.get(carPO);
                    if (pkList.size() == 0) {
                        carService.deleteDeviceByCarId(carId);
                        carService.deleteCarByCarId(carId);
                        ActionMessage.createByAction().success("车辆信息删除成功");
                    } else if (pkList.size() > 0) {
                        ActionMessage.createByAction().fail("该车辆信息不能被删除");
                    }
                }
            }
            halleyLogger.log("外展配置", "车辆删除");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("车辆信息删除失败");
            halleyLogger.logFailed("外展配置", "车辆删除");
            e.printStackTrace();
        }
        return Results.redirect(String.format("/halley/exhibition/carConfig/%s", exhibitionId));
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
}
