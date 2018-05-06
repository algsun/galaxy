package com.microwise.uma.dao;

import com.microwise.uma.bean.ZoneBean;

import java.util.List;

/**
 * 区域DAO 接口
 *
 * @author li.jianfei
 * @date 2013-4-18
 */
public interface ZoneDao {

    /**
     * 查询当前站点下拥有激发器的区域列表
     *
     * @param siteId 当前站点id
     * @return 区域集合
     */
    public List<ZoneBean> findZonesHasDevice(String siteId);

    /**
     * 根据父区域ID查询子区域列表
     *
     * @param siteId       当前站点ID
     * @param parentId     父区域ID
     * @param hourInterval 时间间隔
     * @return 子区域集合
     */
    public List<ZoneBean> findZoneListByParentId(String siteId,
                                                 String parentId, int hourInterval);

    /**
     * 查询区域人员列表
     *
     * @param zoneId       区域ID
     * @param hourInterval 时间间隔
     * @return 区域人员列表
     */
    public List<ZoneBean> findPeoplesInZone(String zoneId, int hourInterval);

    /**
     * 该站点下的所有区域人员进出统计情况
     *
     * @param siteId 站点Id 不能为空
     * @return 站点下每个区域的最新人数
     */
    public List<ZoneBean> countGoTimesOfZone(String siteId);

    /**
     * 查询区域
     *
     * @param siteId 站点ID
     * @param parentId 父区域ID
     * @return 区域列表
     */
    public List<ZoneBean> findZoneList(String siteId, String parentId);


    /**
     * 查询区域内人员详细列表
     *
     * @param siteId 站点ID
     * @param zoneId 区域ID
     * @return List<ZoneBean>
     */
    public List<ZoneBean> findPeopleInZone(String siteId, String zoneId);

    /**
     * 根据区域ID查找区域
     *
     * @param zoneId 区域ID
     * @return ZoneBean
     */
    public ZoneBean findZoneById(String zoneId);

    /**
     * 根据区域ID查询子区域列表
     *
     * @param zoneId 区域ID
     * @return List<ZoneBean>
     */
    public List<ZoneBean> findSubZoneById(String zoneId);
}
