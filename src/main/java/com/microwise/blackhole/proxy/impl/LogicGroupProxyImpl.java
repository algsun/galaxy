package com.microwise.blackhole.proxy.impl;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.dao.LogicGroupDao;
import com.microwise.blackhole.proxy.LogicGroupProxy;
import com.microwise.blackhole.sys.Blackhole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 站点组代理类
 *
 * @author liuzhu
 * @date 14-1-7
 * @check @wang.geng 2014-1-17 #7570
 */
@Component
@Scope("prototype")
@Blackhole
public class LogicGroupProxyImpl implements LogicGroupProxy {
    /**
     * 站点组Dao
     */
    @Autowired
    private LogicGroupDao logicGroupDao;

    @Override
    public List<LogicGroup> findLogicGroupForMap(Integer logicGroupId,
                                                 int userId) {
        // 获取当前逻辑站点id
        Set<Integer> set = new HashSet<Integer>();
        set.add(logicGroupId);
        LogicGroup logicGroup = logicGroupDao.findLogicGroupByUserId(userId);
        if (logicGroup != null) {
            set.add(logicGroup.getId());
        }
        // 查询用户站点组对应逻辑站点id列表
        set.addAll(logicGroupDao.findUserLogicGroupIds(userId));
        return logicGroupDao.findLogicGroupForMap(set);
    }
}
