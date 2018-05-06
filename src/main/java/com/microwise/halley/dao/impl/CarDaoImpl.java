package com.microwise.halley.dao.impl;

import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.po.ConfigPO;
import com.microwise.halley.bean.po.DevicePO;
import com.microwise.halley.bean.po.UserPO;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.dao.CarDao;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyBaseDao;

import java.util.List;

/**
 * 车辆信息查询Dao接口实现
 *
 * @author wanggeng
 * @date 13-9-26 下午1:29
 * @check @li.jianfei 5834 2013-10-10
 */
@Dao
@Halley
public class CarDaoImpl extends HalleyBaseDao implements CarDao {
    @Override
    public List<CarVO> findCarsByExhibitionId(int exhibitionId) {
        return getSqlSession().selectList(
                "halley.mybatis.CarDao.findCarsByExhibitionId", exhibitionId);
    }

    @Override
    public List<CarPO> findAllCars() {
        return getSqlSession().selectList(
                "halley.mybatis.CarDao.findAllCars");
    }

    @Override
    public List<DevicePO> findDevicesByCarId(int carId) {
        return getSqlSession().selectList(
                "halley.mybatis.CarDao.findDevicesByCarId", carId
        );
    }

    @Override
    public List<LocationVO> findLocationsByCarId(int carId) {
        return getSqlSession().selectList(
                "halley.mybatis.CarDao.findLocationsByCarId", carId
        );
    }

    @Override
    public void saveCarPO(CarPO carPO) {
        getSqlSession().insert("halley.mybatis.CarDao.saveCarPO", carPO);
    }

    @Override
    public void updateCarPO(CarPO carPO) {
        getSqlSession().update("halley.mybatis.CarDao.updateCarPO", carPO);
    }

    @Override
    public void saveDevice(DevicePO devicePO) {
        getSqlSession().insert("halley.mybatis.CarDao.saveDevicePO", devicePO);
    }

    @Override
    public void saveUserPO(UserPO userPO) {
        getSqlSession().insert("halley.mybatis.CarDao.saveUserPO", userPO);
    }

    @Override
    public List<UserPO> findUserPO(int exhibitionId) {
        return getSqlSession().selectList(
                "halley.mybatis.CarDao.findUserByExhibitionId", exhibitionId);
    }

    @Override
    public void deleteAllUserPO(int exhibitionId) {
        getSqlSession().delete("halley.mybatis.CarDao.deleteUserPOsByUserId", exhibitionId);
    }

    @Override
    public void saveConfigPO(ConfigPO configPO) {
        getSqlSession().insert("halley.mybatis.CarDao.saveConfigPO", configPO);
    }

    @Override
    public List<ConfigPO> findConfigPO(int exhibitionId) {
        return getSqlSession().selectList(
                "halley.mybatis.CarDao.findConfigByExhibitionId", exhibitionId);
    }

    @Override
    public Integer findConfigExhibitionId(int exhibitionId) {
        return getSqlSession().selectOne(
                "halley.mybatis.CarDao.findConfigExhibitionId", exhibitionId);
    }

    @Override
    public void deleteConfigPO(int exhibitionId) {
        getSqlSession().delete("halley.mybatis.CarDao.deleteConfigPOByExhibitionId", exhibitionId);
    }

    @Override
    public void deleteDeviceByCarId(int carId) {
        getSqlSession().delete("halley.mybatis.CarDao.deleteDeviceByCarId", carId);
    }

    @Override
    public void deleteCarByCarId(int carId) {
        getSqlSession().delete("halley.mybatis.CarDao.deleteCarByCarId", carId);
    }

    @Override
    public String findLocationIds(int carId) {
        return getSqlSession().selectOne("halley.mybatis.CarDao.findLocationIdByCarId", carId);
    }
}
