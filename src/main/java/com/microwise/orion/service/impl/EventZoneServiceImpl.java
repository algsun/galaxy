package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.EventZone;
import com.microwise.orion.dao.EventZoneDao;
import com.microwise.orion.dao.UserZoneDao;
import com.microwise.orion.service.EventZoneService;
import com.microwise.orion.sys.Orion;
import com.microwise.proxima.bean.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 出库单 文物 分单确认 service 实现
 *
 * @author xubaoji
 * @date 2013-6-19
 * @check li.jianfei 2013-06-20 #4288
 */
@Orion
@Service
@Transactional
public class EventZoneServiceImpl implements EventZoneService {

    /**
     * 出库单文物分单确认 dao
     */
    @Autowired
    private EventZoneDao eventZoneDao;

    /**
     * 用户区域关系dao
     */
    @Autowired
    private UserZoneDao userZoneDao;

    @Override
    public List<EventZone> findEventZones(String eventId) {
        // 查询区域出库文物分单记录
        List<EventZone> eventZones = eventZoneDao.findEventZonesBeatchAll(eventId);

        // 查询区域管理员
        if (eventZones != null && !eventZones.isEmpty()) {
            for (EventZone eventZone : eventZones) {
                Zone zone = eventZone.getZone();
                if (zone != null) {
                    eventZone.setUsers(userZoneDao.findZoneUser(zone.getId()));
                }
            }
        }
        return eventZones;
    }

    @Override
    public void updateEventZoneUserAndState(String eventId, Integer userId,
                                            int state, String zoneId) {
        // 库房确认通过 修改 该库房记录
        if (state == EventZone.EVENT_ZONE_STATE_PASS) {
            eventZoneDao.updateEventZoneUserAndState(eventId, userId, state, zoneId);
        } else if (state == EventZone.EVENT_ZONE_STATE_NOT_PASS) {
            // 库房不通过 删除该 出库事件文物 分单记录
            eventZoneDao.deleteEventZoneRelic(eventId);
            eventZoneDao.deleteEventZone(eventId);
        }

    }
}
