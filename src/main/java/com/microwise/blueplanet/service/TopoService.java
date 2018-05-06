package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.TopoViewVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiedeng
 * @date 13-9-29
 * @check liuzhu 2013-10-11 #5894
 */
public interface TopoService {

    /**
     * 获取设备信息
     *
     * @param siteId 站点
     * @return List<TopoViewVO>  设备信息
     * @author xiedeng
     * @date 2013-09-29
     */
    public List<TopoViewVO> getDevices(String siteId);

    /**
     * 获取设备的数量
     *
     * @return Map<Integer, Integer> key 设备类型（5种类型）,value 设备数量
     * @author xiedeng
     * @date 2013-09-29
     */
    public Map<Integer, Integer> getDeviceCount();

    /**
     * 获取拓扑图
     *
     * @param nodeId 设备编号
     * @return List<Object> 第一个元素，拓扑图的点信息，
     * 第二个元素, 拓扑图的映射信息
     * @author xiedeng
     * @date 2013-09-29
     */
    public List<Object> getTopoData(String nodeId);

    /**
     * 根据nodeId获取设备信息
     *
     * @param nodeId 设备编号
     * @return TopoViewVO  设备信息
     * @author liuzhu
     * @date 2013-09-29
     */
    public TopoViewVO getNodeInfoByNodeId(String nodeId);

    /**
     * 获取站点下所有超时的设备
     *
     * @param siteId 站点id
     * @return List<TopoViewVO>  超时的设备信息
     * @author liuzhu
     * @date 2013-09-29
     */
    public List<TopoViewVO> getTimeoutDevice(String siteId);

    /**
     * 获取有负载量的设备
     *
     * @param siteId  站点id
     * @param anomaly 设备状态
     * @return List<TopoViewVO>  有负载量的设备信息
     * @author xiedeng
     * @date 2013-10-08
     */
    public List<TopoViewVO> getLoadDevices(String siteId, int anomaly);

    /**
     * 获取丢包率
     *
     * @param startDate 时间
     * @param endDate   结束时间
     * @param type      时间类型
     * @param siteId    站点编号
     * @return List<TopoViewVO>   设备丢包率信息
     * @author xiedeng
     * @date 2013-10-08
     */
    public List<TopoViewVO> getLoseRate(Date startDate, Date endDate, int type, String siteId);

    /**
     * 获得一个设备的丢包率
     *
     * @param startDate
     * @param endDate
     * @param nodeId
     * @return TopoViewVO 设备丢包率 vo 实体
     * @author xu。baoji
     * @date 2014.02.20
     */
    public TopoViewVO getLoseRate(Date startDate, Date endDate, String nodeId);

    /**
     * 获取信号质量
     *
     * @param siteId  站点编号
     * @param anomaly 设备状态
     * @return List<TopoViewVO>   设备信号质量信息
     * @author xiedeng
     * @date 2013-10-08
     */
    public List<TopoViewVO> getRssi(String siteId, int anomaly);

    /**
     * 获取设备历史链路信息
     *
     * @return List<TopoViewVO> 设备链路信息
     */
    public List<TopoViewVO> getHistoryRoute(String nodeId, Date startDate, Date endDate, int startIndex, int pageSize);

    /**
     * 获取设备历史链路信息总条数
     *
     * @param nodeId    节点编号
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return Integer           总条数
     */
    public Integer getHistoryRouteCount(String nodeId, Date startDate, Date endDate);
}
