package com.microwise.blackhole.sys;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.service.SubsystemService;
import com.microwise.blueplanet.action.app.ZoneDeviceTreeAction;
import com.microwise.blueplanet.sys.AppCacheHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 所有的业务系统
 *
 * @author bastengao
 * @date 12-11-21 13:41
 * @check @wang.yunlong & li.jianfei    #230    2012-12-18
 */
public class Subsystems {
    public static final Logger log = LoggerFactory.getLogger(Subsystems.class);

    @Autowired
    private SubsystemService subsystemService;

    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;

    @Autowired
    private LogicGroupService logicGroupService;

    @Autowired
    private AppCacheHolder appCacheHolder;

    /**
     * 所有的业务系统
     */
    private static List<Subsystem> subsystems;

    /**
     * 返回所有业务系统
     * <p/>
     * 注意：请不要乱改对象的值
     *
     * @return
     */
    public static List<Subsystem> all() {
        return new ArrayList<Subsystem>(subsystems);
    }

    /**
     * 初始化所有的业务系统
     */
    public void init() {
        subsystems = subsystemService.findAllSubsystemsByLanguage("zh_CN");
        try {
            List<LogicGroup> logicGroups = logicGroupService.findAll();
            for (LogicGroup logicGroup : logicGroups) {
                if (logicGroup.getSite() == null) continue;
                log.info("加载" + logicGroup.getLogicGroupName() + "设备树缓存");
                appCacheHolder.loadZoneDeviceTree(logicGroup.getSite().getSiteId());
            }
        } catch (ExecutionException e) {
            log.error("加载设备树缓存失败", e);
        }
    }

    /**
     * 动态设置子系统名称的值
     */
    public static void setSubsystems(List<Subsystem> subsystems) {
        Subsystems.subsystems = subsystems;
    }
}
