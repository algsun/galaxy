package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.EventZone;

import java.util.List;

/**
 * 出库事件文物分单处理dao
 *
 * @author xubaoji
 * @date 2013-6-19
 * @check li.jianfei 2013-06-20 #4283
 */
public interface EventZoneDao extends BaseDao<EventZone> {

    /**
     * 批量添加出库 文物按区域确认记录
     *
     * @param eventId    出库事件id
     * @param zoneIdList 区域id列表
     * @return void
     * @author 许保吉
     * @date 2013-6-19
     */
    public void addEventZone(String eventId, List<String> zoneIdList);

    /**
     * 查询出库事件下所有 区域确认实体 （不携带文物信息）
     *
     * @param eventId 出库事件id
     * @return List<EventZone> 出库单按区域确认实体列表
     * @author 许保吉
     * @date 2013-6-19
     */
    public List<EventZone> findEventZones(String eventId);

    /**
     * 查询出库事件下所有 区域确认实体 （携带所有集合和对象属性信息）
     *
     * @param eventId 出库事件id
     * @return List<EventZone> 出库单按区域确认实体列表
     * @author 许保吉
     * @date 2013-6-19
     */
    public List<EventZone> findEventZonesBeatchAll(String eventId);

    /**
     * 删除 出库单文物 按区域确认记录
     *
     * @param eventId 出库事件id
     * @return void
     * @author 许保吉
     * @date 2013-6-19
     */
    public void deleteEventZone(String eventId);

    /**
     * 添加出库单按区域分单确认 文物记录
     *
     * @param eventZoneId 出库单按区域分单确认实体id
     * @param relicIds    文物id 列表
     * @return void
     * @author 许保吉
     * @date 2013-6-19
     */
    public void addEventZoneRelic(int eventZoneId, List<Integer> relicIds);

    /**
     * 删除 出库文物分单记录
     *
     * @param eventId 出库事件id
     * @return void
     * @author 许保吉
     * @date 2013-6-19
     */
    public void deleteEventZoneRelic(String eventId);

    /**
     * 更新库房确认状态和确认人
     *
     * @param eventId 事件id
     * @param userId  确认人id
     * @param state   状态
     * @param zoneId  区域ID   没有区域管理的文物  ，该字段传null
     * @return void
     * @author 许保吉
     * @date 2013-6-19
     */
    public void updateEventZoneUserAndState(String eventId, Integer userId,
                                            int state, String zoneId);

}
