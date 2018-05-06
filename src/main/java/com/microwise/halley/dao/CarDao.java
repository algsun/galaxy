package com.microwise.halley.dao;

import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.po.ConfigPO;
import com.microwise.halley.bean.po.DevicePO;
import com.microwise.halley.bean.po.UserPO;
import com.microwise.halley.bean.vo.CarVO;

import java.util.List;

/**
 * 车辆信息查询Dao接口
 *
 * @author wanggeng
 * @date 13-9-26 下午1:29
 * @check @li.jianfei 5843 2013-10-10
 */
public interface CarDao {

    /**
     * 根据外展ID，查询此次外展的车辆
     *
     * @param exhibitionId 外展ID
     * @return Set<CarPO> 车辆集合
     * @author wang.geng
     * @date 2013-9-26
     */
    public List<CarVO> findCarsByExhibitionId(int exhibitionId);

    /**
     * 查询所有车辆信息
     *
     * @return Set<CarVO> 车辆信息业务实体
     * @author xu.yuexi
     * @date 2013-11-20
     */
    public List<CarPO> findAllCars();

    /**
     * 根据车辆ID，查询车辆上装的设备
     *
     * @param carId 车辆ID
     * @return Set<DevicePO> 设备集合
     * @author wang.geng
     * @date 2013-9-26
     */
    public List<DevicePO> findDevicesByCarId(int carId);

    /**
     * 根据车辆ID，查询车辆上装的设备
     *
     * @param carId 车辆ID
     */
    public List<LocationVO> findLocationsByCarId(int carId);

    /**
     * 添加车辆
     *
     * @param carPO 车辆实体类
     * @author wang.geng
     * @date 2013-9-27
     */
    public void saveCarPO(CarPO carPO);

    /**
     * 更新车辆信息
     *
     * @param carPO 车辆实体类
     * @author wang.geng
     * @date 2013-10-9
     */
    public void updateCarPO(CarPO carPO);

    /**
     * 添加设备
     *
     * @param device 设备实体类
     * @author wang.geng
     * @date 2013-9-27
     */
    public void saveDevice(DevicePO device);

    /**
     * 添加报警通知人员
     *
     * @param userPO 人员实体类
     * @author wang.geng
     * @date 2013-9-27
     */
    public void saveUserPO(UserPO userPO);

    /**
     * 根据外展ID查询报警人员
     *
     * @param exhibitionId 外展ID
     * @return List<UserPO> 用户列表
     * @author wang.geng
     * @date 2013-9-29
     */
    public List<UserPO> findUserPO(int exhibitionId);

    /**
     * 根据ID删除所有报警人员
     *
     * @param exhibitionId 外展ID
     * @author wang.geng
     * @date 2013-9-30
     */
    public void deleteAllUserPO(int exhibitionId);

    /**
     * 添加其它配置项
     *
     * @param configPO 配置项实体类
     * @author wang.geng
     * @date 2013-9-27
     */
    public void saveConfigPO(ConfigPO configPO);

    /**
     * 根据外展ID查询其它配置
     *
     * @param exhibitionId 外展ID
     * @return List<ConfigPO> 其它配置
     * @author wang.geng
     * @date 2013-9-29
     */
    public List<ConfigPO> findConfigPO(int exhibitionId);

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
     * 根据外展ID删除报警范围配置
     *
     * @param exhibitionId 外展ID
     * @author wang.geng
     * @date 2013-9-29
     */
    public void deleteConfigPO(int exhibitionId);

    /**
     * 根据车辆ID删除车辆的所有设备
     *
     * @param carId 车辆ID
     * @author wang.geng
     * @date 2013-10-8
     */
    public void deleteDeviceByCarId(int carId);

    /**
     * 根据车辆ID删除车辆
     *
     * @param carId 车辆
     * @author wang.geng
     * @date 2013-10-8
     */
    public void deleteCarByCarId(int carId);

    /**
     * 根据车id 查找车上所有的监测指标
     *
     * @param carId 车id
     * @return
     * @author liuzhu
     * @date 2015-7-9
     */
    public String findLocationIds(int carId);
}
