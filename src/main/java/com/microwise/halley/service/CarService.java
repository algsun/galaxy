package com.microwise.halley.service;

import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.po.ConfigPO;
import com.microwise.halley.bean.po.DevicePO;
import com.microwise.halley.bean.po.UserPO;
import com.microwise.halley.bean.vo.CarVO;

import java.util.List;
import java.util.Map;

/**
 * 车辆信息查询服务接口
 *
 * @author wanggeng
 * @date 13-9-26 下午2:36
 * @check @li.jianfei #5834 2013-10-10
 */
public interface CarService {

    /**
     * 查询一次外展的所有车辆以及车辆内设备的信息
     *
     * @param exhibitionId 外展ID
     * @return Set<CarVO> 车辆信息业务实体
     * @author wang.geng
     * @date 2013-9-26
     */
    public List<CarVO> findCarsWithDeviceByExhibitionId(int exhibitionId);

    /**
     * 查询所有车辆信息
     *
     * @return Set<CarVO> 车辆信息业务实体
     * @author xu.yuexi
     * @date 2013-11-20
     */
    public List<CarPO> findAllCars();

    /**
     * 保存车辆信息=
     *
     * @param carPO 车辆信息
     * @author wang.geng
     * @date 2013-9-27
     */
    public void saveCarPO(CarPO carPO);

    /**
     * 更新车辆信息
     *
     * @param carPO 车辆信息
     * @author wang.geng
     * @2013-10-9
     */
    public void updateCarPO(CarPO carPO);

    /**
     * 根据车辆的ID删除车辆
     *
     * @param carId 车辆ID
     * @author wang.geng
     * @date 2013-10-8
     */
    public void deleteCarByCarId(int carId);

    /**
     * 根据车辆ID删除车辆下设备的信息
     *
     * @param carId 车辆ID
     * @author wang.geng
     * @date 2013-10-9
     */
    public void deleteDeviceByCarId(int carId);

    /**
     * 保存车辆信息中相关的设备信息
     *
     * @param devicePOList 设备信息
     * @author wang.geng
     * @date 2013-10-9
     */
    public void saveDeviceList(List<DevicePO> devicePOList);

    /**
     * 更新车辆信息中的设备信息
     *
     * @param devicePOList 设备信息列表
     * @author wang.geng
     * @date 2013-10-9
     */
    public void updateDeviceList(List<DevicePO> devicePOList, int carId);

    /**
     * 保存报警人员信息
     *
     * @param userPO 人员实体
     * @author wang.geng
     * @date 2013-9-27
     */
    public void saveUserPO(UserPO userPO);

    /**
     * 根据外展ID删除所有报警用户
     *
     * @param exhibitionId 外展ID
     * @author wang.geng
     * @date 2013-10-8
     */
    public void deleteAllUser(int exhibitionId);

    /**
     * 根据外展ID查询报警用户
     *
     * @param exhibitionId 外展ID
     * @return List<UserPO> 人员列表
     * @author wang.geng
     * @date 2013-9-29
     */
    public List<UserPO> findUserPO(int exhibitionId);

    /**
     * 保存其它配置信息
     *
     * @param configPO 配置实体
     * @author wang.geng
     * @date 2013-9-27
     */
    public void saveConfigPO(ConfigPO configPO);

    /**
     * 根据外展ID查询报警范围
     *
     * @param exhibitionId 外展ID
     * @return 外展报警范围配置
     * @author wang.geng
     * @date 2013-9-29
     */
    public List<ConfigPO> findConfigByExhibitionId(int exhibitionId);

    /**
     * 根据外展ID查询报警范围
     *
     * @param exhibitionId 外展ID
     * @return 外展报警范围配置
     * @author liu.zhu
     * @date 2015-8-5
     */
    public Integer findConfigExhibitionId(int exhibitionId);


    /**
     * 根据车id 查找车上所有的监测指标
     *
     * @param carId 车id
     * @return
     * @author liuzhu
     * @date 2015-7-9
     */
    public Map<String, String> findCarSensor(int carId);
}
