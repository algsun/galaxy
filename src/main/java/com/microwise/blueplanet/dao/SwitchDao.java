package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.AliasPO;
import com.microwise.blueplanet.bean.po.SwitchChange;
import com.microwise.blueplanet.bean.po.Switches;

import java.util.Date;
import java.util.List;

/**
 * 控制模块端口开关状态
 *
 * @author gaohui
 * @date 14-2-20 09:33
 */
public interface SwitchDao {

    /**
     * 返回站点下所有控制模块的最后一次开关状态
     *
     * @param siteId
     * @return
     */
    public List<Switches> findSwitchBySiteId(String siteId);

    /**
     * 查询控制模块开关状态总数
     *
     * @param deviceId
     * @return
     */
    int findSwitchesCountByDeviceId(String deviceId);

    /**
     * 分页查询控制模块开关状态
     *
     * @param deviceId
     * @param start
     * @param pageSize
     * @return
     */
    List<Switches> findSwitchesByDeviceId(String deviceId, int start, int pageSize);

    /**
     * 返回最后一次控制模块的开关状态
     *
     * @param deviceId
     * @return
     */
    public Switches findLastSwitchByDeviceId(String deviceId);

    List<SwitchChange> findRecentSwitchChangesByDeviceId(String deviceId, int count);

    int findSwitchChangeCountByDeviceId(String deviceId, Date startTime, Date endTime, int route, int action);

    List<SwitchChange> findSwitchChangeByDeviceId(String deviceId, Date startTime, Date endTime, int route, int action, int start, int pageSize);

    /**
     * 添加备注
     *
     * @param deviceId
     * @param route
     * @param alias
     */
    public void insertAlias(String id,String deviceId, int route, String alias);

    /**
     * 删除备注
     *
     * @param device
     */
    public void deleteAlias(String device,int route);

    /**
     * 该路是否有备注
     *
     * @param deviceId
     * @param route
     * @return
     */
    public boolean aliasIsExist(String deviceId, int route);

    /**
     * 查询站点下的所有备注
     * @param siteId
     * @return
     */
    public List<AliasPO> findAliasBySite(String siteId,int type);

    /**
     * 查询设备的所有备注
     * @param deviceId
     * @return
     */
    public List<AliasPO> findAliasByDeviceId(String deviceId);
}
