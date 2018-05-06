package com.microwise.orion.service.impl;

import com.google.common.collect.Lists;
import com.microwise.blueplanet.proxy.ZoneProxy;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.orion.bean.*;
import com.microwise.orion.dao.*;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.vo.OutEventVo;
import com.microwise.orion.vo.RelicVo;
import com.microwise.proxima.bean.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 出库 记录（出库单）service 实现
 *
 * @author xubaoji
 * @date 2013-5-29
 * @check duan qixin svn:3998
 * @check li.jianfei 2013-06-20 #4269
 */
@Service
@Orion
@Transactional
public class OutEventServiceImpl implements OutEventService {

    /**
     * 出库 事件dao
     */
    @Autowired
    private OutEventDao outEventDao;

    /**
     * 出库文物dao
     */
    @Autowired
    private EventRelicDao eventRelicDao;

    /**
     * 文物dao
     */
    @Autowired
    private RelicDao relicDao;

    /**
     * 出库单文物分区域 确认dao
     */
    @Autowired
    private EventZoneDao eventZoneDao;

    /**
     * 用户区域dao
     */
    @Autowired
    private UserZoneDao userZoneDao;

    /**
     * 环境监控：区域代理服务
     */
    @Autowired
    private ZoneProxy zoneProxy;

    /**
     * 出库事件文档记录dao
     */
    @Autowired
    private OutEventAttachmentDao outEventAttachmentDao;

    @Override
    public void saveOutEventInfo(OutEvent outEvent, List<Integer> relicIdList) {
        if (outEvent != null) {
            outEvent.setId(DateTimeUtil.getOutEventId(outEvent.getSiteId()));
            outEventDao.saveOutEventInfo(outEvent);
            // TODO 重构代码
            for (Integer id : relicIdList) {
                EventRelic eventRelic = new EventRelic();
                Relic relic = new Relic();
                relic.setId(id);
                eventRelic.setRelic(relic);
                eventRelic.setOutEvent(outEvent);
                eventRelicDao.save(eventRelic);
            }
            // 将出库文物按区域分单存储
            saveEventZoneAndRelic(outEvent.getId(), outEvent.getSiteId(), relicIdList);
        }
    }

    @Override
    public List<OutEvent> findOutEventsBySiteId(String siteId, int start,
                                                int size) {
        return outEventDao.findOutEventsBySiteId(siteId, start, size);
    }

    @Override
    public int findOutEventCountBySiteId(String siteId) {
        return outEventDao.findOutEventCountBySiteId(siteId);
    }

    @Override
    public List<OutEvent> findExhibitions(String siteId, Date begin, Date end) {
        //查询所有外展出库单
        return outEventDao.findExhibitions(siteId, begin, end);
    }

    @Override
    public OutEvent findById(String id) {
        // 根据总登记号排序
        OutEvent outEvent = outEventDao.findById(id);
        List<EventRelic> eventRelics = Lists.newArrayList(outEvent
                .getEventRelics());
        Collections.sort(eventRelics, new Comparator<EventRelic>() {
            @Override
            public int compare(EventRelic o1, EventRelic o2) {
                return o1.getRelic().getTotalCode()
                        .compareTo(o2.getRelic().getTotalCode());
            }
        });
        Set<EventRelic> eventRelics1 = new LinkedHashSet<EventRelic>();
        eventRelics1.addAll(eventRelics);
        outEvent.setEventRelics(eventRelics1);
        return outEvent;
    }

    @Override
    public void updateOutEvent(OutEvent outEvent, List<Integer> relicIds) {
        // 修改 出库事件 表
        outEventDao.updateOutEvent(outEvent);
        // 删除和出库事件相关联的信息
        eventRelicDao.deleteEventRelicsByOutEventId(outEvent.getId());
        // 保存eventRelic 信息
        for (Integer id : relicIds) {
            EventRelic eventRelic1 = new EventRelic();
            Relic relic = new Relic();
            relic.setId(id);
            eventRelic1.setRelic(relic);
            eventRelic1.setOutEvent(outEvent);
            eventRelicDao.save(eventRelic1);
        }
        // 删除该出库事件 的 出库文物分单记录
        deleteEventZoneAndRelic(outEvent.getId());

        // 将出库文物按区域分单存储
        saveEventZoneAndRelic(outEvent.getId(), outEvent.getSiteId(), relicIds);
    }

    @Override
    public List<OutEventVo> findOutEventVo(Map<String, String> outEventIds) {

        List<String> eventIdList = new ArrayList<String>(outEventIds.keySet());
        // 查询出库单 信息
        List<OutEventVo> outEventVos = outEventDao.findOutEventVo(eventIdList);

        // 查询出库单文物信息
        if (outEventVos != null) {
            for (OutEventVo outEventVo : outEventVos) {
                outEventVo.setEventRelics(outEventDao.findOutEventRelicVo(outEventVo.getId()));
                outEventVo.setTaskId(outEventIds.get(outEventVo.getId()));
            }
        }
        return outEventVos;
    }

    @Override
    public List<OutEventVo> findOutEventVoByState(String siteId, int state) {
        // 查询出库单 信息
        List<OutEventVo> outEventVos = outEventDao.findOutEventVoByState(siteId, state);
        for (OutEventVo outEventVo : outEventVos) {
            outEventVo.setEventRelics(outEventDao.findOutEventRelicVo(outEventVo.getId()));
        }

        return outEventVos;
    }

    @Override
    public void deleteOutEvent(String outEventId) {
        // 删除出库事件 文物 分单记录信息
        deleteEventZoneAndRelic(outEventId);

        // 删除和出库事件相关联的信息
        eventRelicDao.deleteEventRelicsByOutEventId(outEventId);
        outEventAttachmentDao.deleteEventAttachmentByEventId(outEventId);
        // 删除 出库事件
        OutEvent outEvent = new OutEvent();
        outEvent.setId(outEventId);
        outEventDao.delete(outEvent);

    }

    @Override
    public void updateRelicState(String outEventId, int relicState) {
        OutEvent outEvent = outEventDao.findById(outEventId);
        List<Integer> relicIds = new ArrayList<Integer>();
        for (EventRelic eventRelic : outEvent.getEventRelics()) {
            relicIds.add(eventRelic.getRelic().getId());
        }
        relicDao.updateRelicState(relicIds, relicState);
    }

    @Override
    public void doStockOut(String outEventId) {
        OutEvent outEvent = outEventDao.findById(outEventId);
        outEvent.setState(OutEvent.STATE_OUT);
        outEventDao.update(outEvent);

        // 将文物状态改为 “待出库”
        updateRelicState(outEventId, Relic.STATE_TO_BE_OUT);
    }

    @Override
    public void backToStoreRoom(String outEventId) {
        OutEvent outEvent = outEventDao.findById(outEventId);
        outEvent.setState(OutEvent.STATE_BACK);
        outEventDao.update(outEvent);
        List<Integer> relicIds = new ArrayList<Integer>();
        for (EventRelic eventRelic : outEvent.getEventRelics()) {
            relicIds.add(eventRelic.getRelic().getId());
            eventRelic.setInDate(new Date());
            eventRelicDao.update(eventRelic);
        }
        relicDao.updateRelicState(relicIds, 0);
    }

    @Override
    public void addOutEventAttachment(String eventId, int userId, String path, Date date) {
        outEventAttachmentDao.addOutEventAttachment(eventId, userId, path, date);
    }

    @Override
    public void deleteOutEventAttachment(int attachmentId) {
        outEventAttachmentDao.deleteOutEventAttachment(attachmentId);
    }

    @Override
    public List<OutEventAttachment> findOutEventAttachmentByEventId(String eventId) {
        return outEventAttachmentDao.findByEventId(eventId);
    }

    /**
     * 将出库文物分单存储
     *
     * @param eventId  出库事件id
     * @param siteId   站点编号
     * @param relicIds 文物id 列表
     * @return void
     * @author 许保吉
     * @date 2013-6-19
     */
    private void saveEventZoneAndRelic(String eventId, String siteId,
                                       List<Integer> relicIds) {
        // 有出库文物的区域 （管理员区域）id 列表
        Set<String> hasRelicZoneIds = new HashSet<String>();
        // 用来存储 有管理员区域与 出库文物对应关系 key 区域id
        Map<String, List<Integer>> zoneRelicIds = new HashMap<String, List<Integer>>();

        // 用来存储 所有有管理员的区域 和他们的子区域 （查询没有管理员区域的出库文物）
        List<String> allZoneIds = new ArrayList<String>();

        // 查所有出库单文物信息携带有区域信息
        List<Relic> relics = relicDao.findRelicByIds(relicIds);
        // 查站点下所有 有管理员的区域
        List<Zone> zones = userZoneDao.findZones(siteId);
        for (Zone zone : zones) {
            // 查询区域下的子孙区域
            List<String> childrenIds = zoneProxy.findChildrenIdList(zone
                    .getId());
            if (childrenIds == null) {
                childrenIds = new ArrayList<String>();
            }
            childrenIds.add(zone.getId());
            allZoneIds.addAll(childrenIds);
            // 判断出库文物是否在该区域下
            for (Relic relic : relics) {
                if (relic.getZone() != null && childrenIds.indexOf(relic.getZone().getId()) != -1) {
                    // 出库单中文物 对应的 管理员区域 放进set 集合
                    hasRelicZoneIds.add(zone.getId());
                    // 将出库单中文物和区域关系 存放进map 集合
                    processingData(zoneRelicIds, zone.getId(), relic.getId());
                }
            }
        }
        // 处理没有人员管理的文物 。1、有区域 但没有对应的区域管理的文物 2 、没有区域的文物

        // 有区域 但没有对应的区域管理的文物
        List<Integer> noUserZoneRelicIds = null;
        if (!allZoneIds.isEmpty()) {
            noUserZoneRelicIds = eventRelicDao.findNoUserZoneEventRelic(
                    allZoneIds, eventId);
            if (noUserZoneRelicIds != null && !noUserZoneRelicIds.isEmpty()) {
                hasRelicZoneIds.add(null);
                processingData(zoneRelicIds, null,
                        noUserZoneRelicIds.toArray(new Integer[noUserZoneRelicIds.size()]));
            }
        }
        // 没有区域的出库文物
        List<Integer> noZoneRelicIds = eventRelicDao.findNoZoneEventRelic(eventId);
        if (noZoneRelicIds != null && !noZoneRelicIds.isEmpty()) {
            hasRelicZoneIds.add(null);
            processingData(zoneRelicIds, null, noZoneRelicIds.toArray(new Integer[noZoneRelicIds.size()]));
        }
        addData(hasRelicZoneIds, zoneRelicIds, eventId);
    }

    /**
     * 添加 数据
     *
     * @param zoneIds      区域ID列表
     * @param zoneRelicIds 区域文物ID列表
     * @param eventId      出库事件ID
     * @return
     * @author 许保吉
     * @date 2013-6-19
     */
    private void addData(Set<String> zoneIds,
                         Map<String, List<Integer>> zoneRelicIds, String eventId) {
        List<String> zoneIdList = new ArrayList<String>();
        zoneIdList.addAll(zoneIds);
        eventZoneDao.addEventZone(eventId, zoneIdList);

        List<EventZone> eventZones = eventZoneDao.findEventZones(eventId);
        for (EventZone eventZone : eventZones) {
            Zone zone = eventZone.getZone();
            eventZoneDao.addEventZoneRelic(eventZone.getId(),
                    zoneRelicIds.get(zone == null ? null : zone.getId()));
        }

    }

    /**
     * 处理 数据
     *
     * @param zoneRelicIds 区域文物ID列表
     * @param zoneId       区域ID
     * @param relicIds     文物ID列表
     * @return
     * @author 许保吉
     * @date 2013-6-19
     */
    private void processingData(Map<String, List<Integer>> zoneRelicIds,
                                String zoneId, Integer... relicIds) {
        List<Integer> zoneRelicId = zoneRelicIds.get(zoneId);
        if (zoneRelicId == null) {
            zoneRelicId = new ArrayList<Integer>();
            zoneRelicIds.put(zoneId, zoneRelicId);
        }
        zoneRelicId.addAll(Arrays.asList(relicIds));
    }

    /**
     * 删除出库文物 按区域分单记录
     *
     * @param eventId 出库事件id
     * @return
     * @author 许保吉
     * @date 2013-6-19
     */
    private void deleteEventZoneAndRelic(String eventId) {
        eventZoneDao.deleteEventZoneRelic(eventId);
        eventZoneDao.deleteEventZone(eventId);
    }

}
