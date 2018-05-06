package com.microwise.orion.service;

import com.microwise.orion.bean.EventZone;

import java.util.List;

/**
 * 出库单文物分单确认service
 *
 * @author xubaoji
 * @date 2013-6-19
 * @check li.jianfei 2013-06-20 #4288
 */
public interface EventZoneService {

    /**
     * 查询 出库单文物分单数据
     *
     * @param eventId 事件id
     * @return List<EventZone>
     * @author 许保吉
     * @date 2013-6-19
     */
    public List<EventZone> findEventZones(String eventId);

    /**
     * 更新库房确认状态和确认人
     *
     * @param eventId 事件id
     * @param userId  确认人id
     * @param state   状态
     * @param zoneId  区域编号   没有区域管理的文物确认该 字段传null
     * @return void
     * @author 许保吉
     * @date 2013-6-19
     */
    public void updateEventZoneUserAndState(String eventId, Integer userId,
                                            int state, String zoneId);

}
