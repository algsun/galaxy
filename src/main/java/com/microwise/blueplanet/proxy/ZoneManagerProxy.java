package com.microwise.blueplanet.proxy;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;

import java.util.List;

/**
 * @author liuzhu
 * @date 13-11-25
 */
public interface ZoneManagerProxy {

    /**
     * 更改区域父子关系
     *
     * @param zoneId       区域id
     * @param parentZoneId 调整后的父级区域id，为null时调整为顶级区域
     * @author zhangpeng
     * @date 2013-1-25
     */
    public void changeParent(String zoneId, String parentZoneId);

    /**
     * 判断指定站点的区域下是否拥有该名称区域
     *
     * @param siteId       站点id
     * @param parentZoneId 所属区域id
     * @param zoneName     区域名称
     * @return true 拥有 false 没有
     * @author zhangpeng
     * @date 2013-1-18
     */
    public boolean containsName(String siteId, String parentZoneId,
                                String zoneName);

    /**
     * 删除区域
     *
     * @param zoneId 区域id
     * @author zhangpeng
     * @date 2013-1-18
     */
    public void deleteZone(String zoneId);

    /**
     * 根据区域id获取所有子孙区域id列表
     *
     * @param zoneId 区域id（不能为null）
     * @return List<String> 区域的所有子孙的id集合. 如果无结果返回大小为零的集合，则不是 null
     * @author zhangpeng
     * @date 2013-2-5
     */
    public List<String> findChildrenIdList(String zoneId);

    /**
     * 获取区域数据版本号
     *
     * @param zoneId 区域id
     * @return 数据版本号
     * @author zhangpeng
     * @date 2013-1-23
     */
    public long findDataVersion(String zoneId);

    /**
     * 查询区域下设备所拥有的监测指标集合（激活的）
     *
     * @param zoneId 区域id
     * @return List<SensorinfoVO> 监测指标vo对象列表
     * @author zhangpeng
     * @date 2013-1-18
     */
    public List<SensorinfoVO> findSensorinfo(String zoneId);

    /**
     * 根据id查询区域对象
     *
     * @param zoneId 区域id
     * @return ZoneVO 区域vo对象
     * @author zhangpeng
     * @date 2013-1-18
     */
    public ZoneVO findZoneById(String zoneId);

    /**
     * 查询区域的直接子区域列表
     *
     * @param zoneId 区域id
     * @return List<ZoneVO> 子区域vo对象列表
     * @author zhangpeng
     * @date 2013-04-12
     */
    public List<ZoneVO> findZones(String zoneId);

    /**
     * 根据站点id及父级区域id查询区域列表
     *
     * @param siteId       站点id
     * @param parentZoneId 父区域id
     * @return List<ZoneVO> 区域vo对象列表
     * @author zhangpeng
     * @date 2013-1-18
     */
    public List<ZoneVO> findZoneList(String siteId, String parentZoneId);

    /**
     * 判断区域是否为空（是否拥有子区域或设备）
     *
     * @param zoneId 区域id
     * @return true 空 false 不为空
     * @author zhangpeng
     * @date 2013-1-18
     */
    public boolean isEmpty(String zoneId);

    /**
     * 能否在当前区域下用此名称（用于区域修改业务）
     *
     * @param siteId       站点id
     * @param parentZoneId 所属区域id
     * @param zoneId       当前修改区域id
     * @param zoneName     区域名称
     * @return true 能用 false 不能用
     * @author zhangpeng
     * @date 2013-1-18
     */
    public boolean isNameAvailable(String siteId, String parentZoneId,
                                   String zoneId, String zoneName);

    /**
     * 添加区域
     *
     * @param zone 区域vo对象
     * @return String 新添加的区域的id
     * @author zhangpeng
     * @date 2013-1-18
     */
    public String saveZone(ZoneVO zone);

    /**
     * 更新区域详细信息
     *
     * @param zoneId    区域id
     * @param zoneName  区域名称
     * @param planImage 区域实景图路径
     * @author zhangpeng
     * @date 2013-1-18
     */
    public void updateZone(String zoneId, String zoneName, String planImage,int position);

}
