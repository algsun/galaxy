package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.AliasPO;
import com.microwise.blueplanet.bean.po.SwitchChange;
import com.microwise.blueplanet.bean.po.Switches;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author gaohui
 * @date 14-2-20 09:32
 */
public interface SwitchService {

    /**
     * 返回站点下控制模块的最后一次开关状态
     *
     * nodeId => switches
     *
     * @param siteId
     * @return
     */
    Map<String, Switches> findSwitchesBySiteId(String siteId);

    int findSwitchesCountByDeviceId(String deviceId);

    List<Switches> findSwitchesByDeviceId(String deviceId, int page, int pageSize);

    /**
     * 返回最后一次控制模块的开关状态
     *
     * @param deviceId
     * @return
     */
    Switches findLastSwitchByDeviceId(String deviceId);

    List<SwitchChange> findRecentSwitchChangesByDeviceId(String deviceId, int count);

    int findSwitchChangeCountByDeviceId(String deviceId, Date startTime, Date endTime,int route, int action);

    List<SwitchChange> findSwitchChangeByDeviceId(String deviceId, Date startTime, Date endTime,int route, int action, int page, int pageSize);



    /**
     * 更新备注
     * @param deviceId
     * @param route
     * @param alias
     */
    public void updateAlias(String deviceId,int route,String alias);

    /**
     * 查询站点下的所有备注
     * @param siteId
     * @return
     */
    public Map<String,List<AliasPO>> findAliasBySite(String siteId,int type);

    /**
     * 查询设备的所有备注
     * @param deviceId
     * @return
     */
    public List<AliasPO>  findAliasByDeviceId(String deviceId);
}
