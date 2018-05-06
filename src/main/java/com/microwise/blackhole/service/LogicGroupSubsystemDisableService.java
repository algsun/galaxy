package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.Subsystem;

import java.util.List;

/**
 * 站点开关子系统service
 *
 * @author liuzhu
 * @date 2014/3/24
 * @check 2014.03.27 xiedeng  #svn: 8232
 */
public interface LogicGroupSubsystemDisableService {

    /**
     * 查找Subsystem对象
     *
     * @return LogicGroupSubsystemDisable list对象
     * @author liuzhu
     * @date 2014-3-25
     */
    public List<Subsystem> findSubsystem();

    /**
     * 修改子系统状态
     *
     * @param systemId 子系统id
     * @param state    状态
     * @author liuzhu
     * @date 2014-3-24
     */
    public void changeState(int systemId, int state);

    /**
     * 根据站点组id查找Subsystem对象(已开启的业务系统)
     *
     * @param logicGroupId 站点组id
     * @return LogicGroupSubsystemDisable list对象
     * @author liuzhu
     * @date 2014-3-25
     */
    public List<Subsystem> findSubsystemOpen(int logicGroupId);

    /**
     * 根据站点组id查找Subsystem对象(不包含全局配置已关闭的业务系统)
     *
     * @param logicGroupId 站点组id
     * @return LogicGroupSubsystemDisable list对象
     * @author liuzhu
     * @date 2014-3-25
     */
    public List<Subsystem> findSubsystemByLogicGroupId(int logicGroupId);

    /**
     * 根据站点组id查找Subsystem对象(包含全局配置已关闭的业务系统)
     *
     * @param logicGroupId 站点组id
     * @return LogicGroupSubsystemDisable list对象
     * @author liuzhu
     * @date 2014-3-25
     */
    public List<Subsystem> findAllSubsystemByLogicGroupId(int logicGroupId);

    /**
     * 开启一个权限
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
     * 根据站点组id查找Subsystem对象(不包含全局配置已关闭的业务系统)
     *
     * @param logicGroupId 站点组id
     * @return LogicGroupSubsystemDisable list对象
     * @author chenyaofei
     * @date 2016-03-02
     */
    public List<Subsystem> findSubsystemByLogicGroupIdFormHome(int logicGroupId);

    /**
     * 根据站点组id查找Subsystem对象(已开启的业务系统)
     *
     * @param logicGroupId 站点组id
     * @return LogicGroupSubsystemDisable list对象
     * @author chenyaofei
     * @date 2016-03-02
     */
    public List<Subsystem> findSubsystemOpenFormHome(int logicGroupId);
}
