package com.microwise.uma.service.impl;

import com.microwise.uma.bean.ZoneBean;
import com.microwise.uma.dao.ZoneDao;
import com.microwise.uma.service.ZoneService;
import com.microwise.uma.sys.Uma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域 Service 实现
 *
 * @author li.jianfei
 * @date 2013-4-18
 * @check @wang yunlong 2013-04-28 #3038
 * @check @li.jianfei 2013-06-04 #4019
 */
@Service
@Uma
@Transactional
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneDao zoneDao;

    @Override
    public List<ZoneBean> findZonesHasDevice(String siteId) {
        return zoneDao.findZonesHasDevice(siteId);
    }

    @Override
    public void findZoneTree(List<ZoneBean> sortList, String siteId,
                             String parentId, int level, int hourInterval,
                             List<String> closedZones) {
        ZoneBean zone;

        // 根据父区域ID查询子区域列表
        List<ZoneBean> zoneList = zoneDao.findZoneListByParentId(siteId,
                parentId, hourInterval);

        if (zoneList == null || zoneList.size() <= 0) {
            level--;
        } else {
            for (ZoneBean zoneTemp : zoneList) {
                zone = zoneTemp;
                // 设置区域级别
                zone.setLevel(level + 1);

                // 将符合条件的区域加入统计列表
                sortList.add(zone);

                // 获取当前区域子区域列表
                zone.setSubZoneList(zoneDao.findZoneListByParentId(siteId,
                        zone.getZoneId(), hourInterval));

                findZoneTree(sortList, siteId, zone.getZoneId(), level + 1,
                        hourInterval, closedZones);

                // 设置是否有子区域
                zone.setHasChildren(zone.getSubZoneList().size() > 0);

                // 更新区域人数、时间、人员信息
                updateZoneInfo(zone);

                // 设置区域是否显示，是否展开子区域
                zone.setHide(false);

                // 设置是否显示子区域
                zone.setShowSub(true);

                // 设置区域是否隐藏
                if (closedZones != null) {
                    if (closedZones.contains(zone.getParentId())) {
                        zone.setHide(true);
                    }
                    if (closedZones.contains(zone.getZoneId())) {
                        zone.setShowSub(false);
                    }
                }

                // 去除父区域人数为0的统计信息
                if (zone.getNumberOfPeople() == 0) {
                    sortList.remove(zone);
                }
            }
        }
    }

    @Override
    public List<ZoneBean> findPeopleInZone(String siteId, String zoneId,
                                           int hourInterval) {
        ZoneBean zone;
        List<ZoneBean> peopleList;

        // 根据父区域ID查找子区域列表
        List<ZoneBean> subZoneList = zoneDao.findZoneListByParentId(siteId,
                zoneId, hourInterval);

        if (subZoneList != null && subZoneList.size() > 0) {
            peopleList = new ArrayList<ZoneBean>();
            for (ZoneBean subZone : subZoneList) {
                zone = subZone;
                peopleList.addAll(findPeopleInZone(siteId, zone.getZoneId(),
                        hourInterval));
            }
        } else {

            peopleList = zoneDao.findPeoplesInZone(zoneId, hourInterval);
        }
        return peopleList;
    }

    /**
     * 更新区域人数、人员、时间信息 父区域人数为子区域人数组合 时间和人员为区域下所有子区域中最后一次发生时间和人员
     *
     * @param zone 区域
     */
    private void updateZoneInfo(ZoneBean zone) {
        int count = 0;
        List<ZoneBean> subZoneList = zone.getSubZoneList();
        if (subZoneList == null) {
            return;
        }
        for (ZoneBean temp : subZoneList) {
            if (temp.getOccurrenceTime() != null) {
                if (zone.getOccurrenceTime() == null
                        || temp.getOccurrenceTime().after(
                        zone.getOccurrenceTime())) {
                    zone.setUserId(temp.getUserId());
                    zone.setUserName(temp.getUserName());
                    zone.setOccurrenceTime(temp.getOccurrenceTime());
                }
            }
            if (temp.isHasChildren()) {// 递归
                updateZoneInfo(temp);
            }
            count = count + temp.getNumberOfPeople();
            zone.setNumberOfPeople(count);
        }
    }

    @Override
    public List<ZoneBean> countGoTimesWithZoneId(String siteId, int level,
                                                 List<String> closedZones) {
        // 获取区域人数统计(有人员活动的区域)
        List<ZoneBean> zoneList = zoneDao.countGoTimesOfZone(siteId);
        Map<String, ZoneBean> countMap = new HashMap<String, ZoneBean>();
        if (zoneList != null) {
            for (ZoneBean zone : zoneList) {
                countMap.put(zone.getZoneId(), zone);
            }
        }
        // 查询父区域，然后递归查询子区域
        List<ZoneBean> zoneTree = findZoneList(siteId, null, level, closedZones);
        for (ZoneBean zone : zoneTree) {
            // 统计最新人员和区域人员总数
            countZoneGoTimes(zone, countMap);
        }

        List<ZoneBean> res = new ArrayList<ZoneBean>();
        checkZoneTree(zoneTree, res);
        return res;
    }

    /**
     * 从数据库中迭代的查询出区域树
     *
     * @param siteId      站点ID
     * @param parentId    区域父ID
     * @param level       层级
     * @param closedZones 关闭的区域
     * @return List<ZoneBean>
     */
    private List<ZoneBean> findZoneList(String siteId, String parentId,
                                        int level, List<String> closedZones) {
        List<ZoneBean> subList = zoneDao.findZoneList(siteId, parentId);
        for (ZoneBean aSubList : subList) {

            aSubList.setLevel(level);
            // 设置区域是否显示，是否展开子区域
            aSubList.setHide(false);
            aSubList.setShowSub(true);
            if (closedZones != null) {
                if (closedZones.contains(aSubList.getParentId())) {
                    aSubList.setHide(true);
                }
                if (closedZones.contains(aSubList.getZoneId())) {
                    aSubList.setShowSub(false);
                }
            }

            List<ZoneBean> subBeans = findZoneList(siteId, aSubList.getZoneId(),
                    level + 1, closedZones);
            if (subBeans == null || subBeans.size() < 1) {
                aSubList.setHasChildren(false);
            } else {
                aSubList.setHasChildren(true);
                aSubList.setSubZoneList(subBeans);
            }

        }
        return subList;
    }

    /**
     * 统计区域下的总人数并返回当前区域下的最后一个进入的人员信息
     *
     * @param zone     区域
     * @param countMap 统计完成的区域Map
     * @return 最后进入区域的人员
     */
    private ZoneBean countZoneGoTimes(ZoneBean zone,
                                      Map<String, ZoneBean> countMap) {
        ZoneBean lastUser = new ZoneBean();
        // 先判断该区域是否有活动人数
        ZoneBean countZone = countMap.get(zone.getZoneId());
        if (countZone != null) {
            // 设置本区域直查人数
            zone.simpleClone(countZone);
            // 最新出现在区域的人员
            lastUser.simpleClone(zone);
        }
        int total = zone.getNumberOfPeople();
        // 统计子区域的人数总和
        if (zone.isHasChildren()) {
            for (ZoneBean sub : zone.getSubZoneList()) {
                ZoneBean subLast = countZoneGoTimes(sub, countMap);
                // 如果当前区域还没有统计到人员数 或者
                // 当前区域统计到的最新人员活动时间比子区域活动人员的时间早，则替换为子区域活动人员
                if ((lastUser.getOccurrenceTime() == null && subLast
                        .getOccurrenceTime() != null)
                        || (subLast.getOccurrenceTime() != null && subLast
                        .getOccurrenceTime().getTime() > lastUser
                        .getOccurrenceTime().getTime())) {
                    lastUser.simpleClone(subLast);
                }
                total += sub.getNumberOfPeople();

            }
        }

        // 如果子区域统计人数大于0，说明有子区域，并且子区域下有人员活动，需要把子区域的人数加到父区域并判断最后活动的人员
        if (total > 0) {
            zone.simpleClone(lastUser);
            zone.setNumberOfPeople(total);
        } else {
            // 如果子区域统计人数为0 则不添加子区域
            zone.setSubZoneList(null);
            zone.setHasChildren(false);
        }

        return lastUser;
    }

    /**
     * 去掉统计结果中人数为空或者子区域内无人员的节点
     *
     * @param tree 区域树
     * @param res  处理结果
     */
    private void checkZoneTree(List<ZoneBean> tree, List<ZoneBean> res) {

        if (tree != null && tree.size() > 0) {
            for (ZoneBean node : tree) {
                if (node != null && node.getNumberOfPeople() > 0) {
                    res.add(node);
                    if (node.getSubZoneList() != null
                            && node.getSubZoneList().size() > 0) {
                        checkZoneTree(node.getSubZoneList(), res);
                    }
                }
            }
        }
    }

    @Override
    public List<ZoneBean> findPeopleInOneZone(String siteId, String zoneId) {

        List<ZoneBean> res = new ArrayList<ZoneBean>();

        // 查询父区域，然后递归查询子区域
        List<ZoneBean> zoneTree = findZoneList(siteId, zoneId, 0,
                new ArrayList<String>());

        List<ZoneBean> zones = zoneDao.findPeopleInZone(siteId, zoneId);
        if (zones != null) {
            res.addAll(zones);
        }
        for (ZoneBean zone : zoneTree) {
            findPeopleInAZone(res, zone, siteId);
        }
        return res;
    }

    @Override
    public ZoneBean findZoneById(String zoneId) {
        ZoneBean zone = zoneDao.findZoneById(zoneId);
        zone.setSubZoneList(zoneDao.findSubZoneById(zoneId));
        return zone;
    }

    /**
     * 查询给定区域id下的人员
     *
     * @param res  查询结果集
     * @param bean 区域Bean
     */
    private void findPeopleInAZone(List<ZoneBean> res, ZoneBean bean, String siteId) {
        List<ZoneBean> zones = zoneDao.findPeopleInZone(siteId, bean.getZoneId());
        if (zones != null && zones.size() > 0) {
            res.addAll(zones);
        }
        if (bean.getSubZoneList() != null && bean.getSubZoneList().size() > 0) {
            for (ZoneBean sub : bean.getSubZoneList()) {
                findPeopleInAZone(res, sub, siteId);
            }
        }
    }
}
