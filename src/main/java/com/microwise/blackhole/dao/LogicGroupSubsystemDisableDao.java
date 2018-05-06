package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.LogicGroupSubsystemDisable;
import com.microwise.blackhole.bean.Subsystem;

import java.util.List;

/**
 * 站点开关子系统dao
 *
 * @author liuzhu
 * @date 2014/3/24
 * @check 2014.03.27 xiedeng  svn: 8232
 */
public interface LogicGroupSubsystemDisableDao {

    /**
     * 修改子系统状态
     *
     * @param systemId 子系统id
     * @param state    状态
     * @author liuzhu
     * @date 2014-3-24
     */
    public int changeState(int systemId, int state);

    /**
     * 根据站点组id查找Subsystem对象
     *
     * @param logicGroupId 站点组id
     * @param subsystemId  站点组id
     * @return LogicGroupSubsystemDisable list对象
     * @author liuzhu
     * @date 2014-3-25
     */
    public LogicGroupSubsystemDisable findSubsystemStateByLogicGroupId(int logicGroupId, int subsystemId);

    /**
     * 获取所有业务系统
     *
     * @return List<Subsystem> 业务系统对象列表
     * @author liuzhu
     * @date 2014-03-25
     */
    public List<Subsystem> findSubsystems();

    /**
     * 获取所有开启业务系统
     *
     * @return List<Subsystem> 业务系统对象列表
     * @author liuzhu
     * @date 2014-03-25
     */
    public List<Subsystem> findAllSubsystems(String language);

    /**
     * 关闭一个业务系统
     *
     * @param logicGroupId 站点组id
     * @param subsystemId  业务系统id
     * @author liuzhu
     * @date 2014-3-25
     */
    public void able(int logicGroupId, int subsystemId);

    /**
     * 关闭一个业务系统
     *
     * @param id
     * @param logicGroupId 站点组id
     * @param subsystemId  业务系统id
     * @author liuzhu
     * @date 2014-3-25
     */
    public void disable(String id, int logicGroupId, int subsystemId);

    /**
     * 获取所有开启业务系统
     *
     * @return List<Subsystem> 业务系统对象列表
     * @author chenyaofei
     * @date 2016-03-02
     */
    public List<Subsystem> findAllSubsystemsFromHome();
}
