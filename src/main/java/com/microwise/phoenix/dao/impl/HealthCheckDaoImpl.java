package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.bean.po.CheckItem;
import com.microwise.phoenix.bean.vo.HealthCheckItem;
import com.microwise.phoenix.bean.vo.healthCheck.Device;
import com.microwise.phoenix.dao.HealthCheckDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;
import com.microwise.uma.bean.PersonBean;

import java.util.List;

/**
 * 系统健康检测dao 实现
 *
 * @author xu.baoji
 * @date 2013-7-25
 * @check @duan.qixin 2013年7月29日 #4687
 */
@Dao
@Phoenix
public class HealthCheckDaoImpl extends PhoenixBaseDao implements HealthCheckDao {

    @Override
    public List<HealthCheckItem> findHealthCheckItem() {
        return getSqlSession().selectList("phoenix.mybatis.HealthCheckDao.findHealthCheckItem");
    }

    @Override
    public List<CheckItem> findCheckItem(int subsystemId) {
        return getSqlSession().selectList("phoenix.mybatis.HealthCheckDao.findCheckItem",
                subsystemId);
    }

    @Override
    public Integer findHealthCheckItemCount() {
        return getSqlSession().selectOne("phoenix.mybatis.HealthCheckDao.findHealthCheckItemCount");
    }

    @Override
    public Integer blueplanetFindDeviceCount(String siteId) {

        return getSqlSession().selectOne(
                "phoenix.mybatis.HealthCheckDao.blueplanetFindDeviceCount", siteId);
    }

    @Override
    public Integer blueplanetFindErrorDeviceCount(String siteId) {
        return getSqlSession().selectOne(
                "phoenix.mybatis.HealthCheckDao.blueplanetFindErrorDeviceCount", siteId);
    }

    @Override
    public List<Device> blueplanetFindErrorDevices(String siteId) {
        return getSqlSession().selectList(
                "phoenix.mybatis.HealthCheckDao.blueplanetFindErrorDevices", siteId);
    }

    @Override
    public Integer umaFindErrorUserCardCount(String siteId) {
        return getSqlSession().selectOne(
                "phoenix.mybatis.HealthCheckDao.umaFindErrorUserCardCount", siteId);
    }

    @Override
    public List<PersonBean> umaFindErrorUserCards(String siteId) {
        return getSqlSession().selectList(
                "phoenix.mybatis.HealthCheckDao.umaFindErrorUserCards", siteId);
    }

    @Override
    public Integer umaFindUserCardCount(String siteId) {
        return getSqlSession().selectOne(
                "phoenix.mybatis.HealthCheckDao.umaFindUserCardCount", siteId);
    }

}
