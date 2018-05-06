package com.microwise.blackhole.proxy;

import com.microwise.blackhole.bean.LogicGroup;

import java.util.List;

/**
 * 站点service代理
 *
 * @author liuzhu
 * @date 14-1-7
 * @check @wang.geng 2014-1-17 #7658
 */
public interface LogicGroupProxy {
    /**
     * <pre>
     * 通过用户id获取用户归属站点及用户可查询的站点组包括子孙中所有基层站点，携带site信息
     *
     * @param logicGroupId 当前切换到的逻辑站点id
     * @param userId       用户id
     * @return List<logicGroup> logicGroup列表带有 site 对象
     * </pre>
     * @author zhangpeng
     * @date 2013-4-2
     */
    public List<LogicGroup> findLogicGroupForMap(Integer logicGroupId,
                                                 int userId);
}
