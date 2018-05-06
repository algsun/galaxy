package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.po.CheckItem;
import com.microwise.phoenix.bean.vo.HealthCheckItem;
import com.microwise.phoenix.bean.vo.healthCheck.Device;
import com.microwise.uma.bean.PersonBean;

import java.util.List;

/**
 * 系统健康检测 dao
 *
 * @author xu.baoji
 * @date 2013-7-25
 * @check @duan.qixin 2013年7月29日 #4687
 */
public interface HealthCheckDao {

    /****
     * 查询系统健康检测项
     *
     * @return List<HealthCheckItem> 系统健康检测项 vo 列表
     * @author xu.baoji
     * @date 2013-7-25
     */
    public List<HealthCheckItem> findHealthCheckItem();

    /****
     * 查询子系统检测项
     *
     * @param subsystemId 子系统id
     * @return List<CheckItem> 系统健康检测项 po 列表
     * @author xu.baoji
     * @date 2013-7-25
     */
    public List<CheckItem> findCheckItem(int subsystemId);

    /***
     * 查询系统健康检测项数量
     *
     * @return Integer 健康检测项数量
     * @author xu.baoji
     * @date 2013-7-25
     */
    public Integer findHealthCheckItemCount();

    /***
     * 环境监控：查询 设备总数量
     *
     * @param siteId 站点编号
     * @return Integer 设备数量
     * @author xu.baoji
     * @date 2013-7-25
     */
    public Integer blueplanetFindDeviceCount(String siteId);

    /***
     * 环境监控：查询 异常设备总数量
     *
     * @param siteId 站点编号
     * @return Integer 设备数量
     * @author xu.baoji
     * @date 2013-7-25
     */
    public Integer blueplanetFindErrorDeviceCount(String siteId);

    /***
     * 环境监控：查询异常设备列表
     *
     * @param siteId 站点编号
     * @return List<Device> 异常设备列表
     * @author xu.baoji
     * @date 2013-7-25
     */
    public List<Device> blueplanetFindErrorDevices(String siteId);

    /***
     * 人员管理：查询电子卡总数量（已发放）
     *
     * @param siteId 站点编号
     * @return Integer 已发放电子卡总数量
     * @author xu.baoji
     * @date 2013-7-25
     */
    public Integer umaFindUserCardCount(String siteId);

    /***
     * 人员管理：查询低电的电子卡（已发放）
     *
     * @param siteId 站点编号
     * @return Integer 低电和无电电子卡数量
     * @author xu.baoji
     * @date 2013-7-25
     */
    public Integer umaFindErrorUserCardCount(String siteId);

    /***
     * 人员管理：查询低电和无电的电子卡和对应人员信息
     *
     * @param siteId 站点编号
     * @return List<PersonBean> 电子卡和人员关系信息实体
     * @author xu.baoji
     * @date 2013-7-25
     */
    public List<PersonBean> umaFindErrorUserCards(String siteId);


}
