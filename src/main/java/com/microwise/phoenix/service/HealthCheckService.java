package com.microwise.phoenix.service;

import com.microwise.orion.bean.InventoryError;
import com.microwise.phoenix.bean.vo.HealthCheckItem;
import com.microwise.phoenix.bean.vo.healthCheck.Device;
import com.microwise.phoenix.bean.vo.healthCheck.FTP;
import com.microwise.uma.bean.PersonBean;

import java.util.List;

/**
 * 系统 健康检查service
 *
 * @author xu.baoji
 * @date 2013-7-24
 * @check @duan.qixin 2013年7月29日 #4688
 */
public interface HealthCheckService {

    /****
     * 查询系统健康检测项
     *
     * @return List<HealthCheckItem> 系统健康检测项列表
     */
    public List<HealthCheckItem> findHealthCheckItem();

    /***
     * 查询系统健康检测项数量
     *
     * @return Integer 健康检测项数量
     */
    public int findHealthCheckItemCount();


    /***
     * 环境监控 ：设备检测
     * 返回数据list  不为null ，有两个元素。第一个元素为应减去的分数，第二个元素为异常设备数量。
     * 如果没有异常设备两个参数都为0
     *
     * @param siteId 站点编号
     * @param number 当前检测项 总分
     * @return List<Integer>  返回检测数据list
     * @author xu.baoji
     * @date 2013-7-24
     */
    public List<Integer> blueplanetDeviceCheck(String siteId, int number);

    /***
     * 环境监控 ：查询异常设备列表
     *
     * @param siteId 站点编号
     * @return List<device>  返回异常设备列表
     * @author xu.baoji
     * @date 2013-7-24
     */
    public List<Device> blueplanetFindErrorDevices(String siteId);

    /***
     * 本体监测：ftp 服务器检测
     * 返回数据list  不为null ，有两个元素。第一个元素为应减去的分数，第二个元素为异常ftp数量。
     * 如果没有异常ftp两个参数都为0
     *
     * @param siteId 站点编号
     * @param number 当前检测项 总分
     * @return List<Integer>  返回检测数据list
     * @author xu.baoji
     * @date 2013-7-24
     */
    public List<Integer> proximaFtpCheck(String siteId, int number);

    /***
     * 本体监测：查询异常ftp 列表
     *
     * @param siteId 站点编号
     * @return List<FTP> 返回异常ftp 服务器列表
     * @author xu.baoji
     * @date 2013-7-24
     */
    public List<FTP> proximaFindErrorFtps(String siteId);

    /***
     * 藏品管理：最后一次盘点检测
     * 返回数据list  不为null ，有两个元素。第一个元素为应减去的分数，第二个元素为异常文物数量。
     * 如果没有异常文物两个参数都为0
     *
     * @param siteId 站点编号
     * @param number 当前检测项 总分
     * @return List<Integer> 返回检测参数
     * @author xu.baoji
     * @date 2013-7-24
     */
    public List<Integer> orionLastInventoryCheck(String siteId, int number);

    /***
     * 藏品管理：查询最后一次盘点异常文物
     *
     * @param siteId 站点编号
     * @return List<Object> 返回最后一次盘点异常文物
     * @author xu.baoji
     * @date 2013-7-24
     */
    public List<InventoryError> orionFindLastInventoryErrorRelics(String siteId);

    /***
     * 人员管理：人员卡电量是否低电检测
     * 返回数据list  不为null ，有两个元素。第一个元素为应减去的分数，第二个元素为低电电子卡数量。
     * 如果没有低电电子卡两个参数都为0
     *
     * @param siteId 站点编号
     * @param number 当前检测项 总分
     * @return List<Integer> 返回检测数据
     * @author xu.baoji
     * @date 2013-7-24
     */
    public List<Integer> umaUserCardCheck(String siteId, int number);

    /***
     * 人员管理：查询异常人员卡
     *
     * @param siteId 站点编号
     * @return List<PersonBean>
     * @author xu.baoji
     * @date 2013-7-25
     */
    public List<PersonBean> umaFindErrorUserCard(String siteId);


}
