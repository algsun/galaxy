package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.vo.TopoViewVO;

import java.util.Date;
import java.util.List;

/**
 * @author xiedeng
 * @date 13-9-27
 * @check liuzhu 2013-10-11 #5894
 */
public interface TopoDao {

    /**
     * 获取设备信息
     *
     * @param siteId 站点编号
     * @return List<TopoViewVO> 设备信息
     * @author xiedeng
     * @date 2013-10-08
     */
    public List<TopoViewVO> getDevices(String siteId);

    /**
     * 获取拓扑图的信息
     *
     * @param parentId    父节点编号
     * @param siteId      站点编号
     * @param size        数据库站点编号字段截取的长度
     * @param nodeVersion 协议版本号
     * @return List<TopoViewVO> 设备信息
     * @author xiedeng
     * @date 2013-10-08
     */
    public List<TopoViewVO> getTopos(int parentId, String siteId, int size, int nodeVersion);

    /**
     * 获取设备信息
     *
     * @param nodeId 设备编号
     * @return TopoViewVO 设备信息
     * @author xiedeng
     * @date 2013-10-08
     */
    public TopoViewVO getNodeInfoByNodeId(String nodeId);

    /**
     * 获取站点下所有的超时设备
     *
     * @param siteId 站点编号
     * @return List<TopoViewVO> 所有的超时设备
     * @author xiedeng
     * @date 2013-10-08
     */
    public List<TopoViewVO> getTimeoutDevice(String siteId);

    /**
     * 获取设备的子设备数量
     *
     * @param parentId    父节点编号
     * @param siteId      站点编号
     * @param size        数据库站点编号字段截取的长度
     * @param nodeVersion 协议版本号
     * @return Integer 子设备数量
     * @author xiedeng
     * @date 2013-10-08
     */
    public Integer getChildrenCount(int parentId, String siteId, int size, int nodeVersion);

    /**
     * 获取需要统计负载量的设备信息
     *
     * @param siteId  站点编号
     * @param anomaly 设备状态
     * @return List<TopoViewVO> 设备负载量信息
     * @author xiedeng
     * @date 2013-10-08
     */
    public List<TopoViewVO> getCountDevices(String siteId, int anomaly);

    /**
     * 设备信号质量
     *
     * @param siteId  站点编号
     * @param anomaly 设备状态
     * @return List<TopoViewVO> 设备信号质量
     * @author liuzhu
     * @date 2013-10-15
     */
    public List<TopoViewVO> getRssi(String siteId, int anomaly);

    /**
     * 获取数据包的数量
     *
     * @param nodeId    节点编号
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return Integer   数据包数量
     * @author xiedeng
     * @date 2013-10-08
     */
    public Integer getNodeCount(String nodeId, Date startDate, Date endDate);

    /**
     * 获取最小时间
     *
     * @param nodeId 节点编号
     * @return Date   获取最小时间
     */
    public Date getMinTime(String nodeId);

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
