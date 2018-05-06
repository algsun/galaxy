package com.microwise.uma.service;

import com.microwise.uma.bean.ZoneBean;

import java.util.List;

/**
 * 区域 Service 接口
 *
 * @author li.jianfei
 * @date 2013-4-18
 */
public interface ZoneService {

    /**
     * 查询当前站点下拥有激发器的区域列表
     *
     * @param siteId 当前站点id
     * @return 区域集合
     */
    public List<ZoneBean> findZonesHasDevice(String siteId);

    /**
     * 查询区域树
     *
     * @param sortList     排序列表
     * @param siteId       当前站点ID
     * @param parentId     父区域ID
     * @param level        区域级别
     * @param hourInterval 时间间隔
     * @param closedZones  关闭的区域
     * @return 区域集合
     */
    public void findZoneTree(List<ZoneBean> sortList, String siteId,
                             String parentId, int level, int hourInterval,
                             List<String> closedZones);

    /**
     * 查询区域人员列表
     *
     * @param siteId       站点ID
     * @param zoneId       区域ID
     * @param hourInterval 时间间隔
     * @return 区域人员列表
     */
    public List<ZoneBean> findPeopleInZone(String siteId, String zoneId,
                                           int hourInterval);

    /**
     * 获取某个时间间隔内区域实时人数统计树
     *
     * @param siteId      站点id
     * @param level       区域级别
     * @param closedZones 关闭的区域
     * @return List<ZoneBean>
     */
    public List<ZoneBean> countGoTimesWithZoneId(String siteId, int level, List<String> closedZones);

    /**
     * 查询某个区域下实时人员列表
     *
     * @param siteId 站点ID
     * @param zoneId 区域ID
     * @return List<ZoneBean>
     */
    public List<ZoneBean> findPeopleInOneZone(String siteId, String zoneId);

    /**
     * 根据区域ID查找区域
     *
     * @param zoneId 区域ID
     * @return ZoneBean
     */
    public ZoneBean findZoneById(String zoneId);

}
