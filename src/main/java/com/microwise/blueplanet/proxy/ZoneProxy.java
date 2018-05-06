package com.microwise.blueplanet.proxy;

import java.util.List;

/**
 * 区域代理层
 *
 * @author xubaoji
 * @date 2013-6-19
 */
public interface ZoneProxy {

    /**
     * 查询一个区域的所有子孙区域
     *
     * @param zoneId 区域id
     * @return List<String> 区域id 列表
     * @author 许保吉
     * @date 2013-6-19
     */
    public List<String> findChildrenIdList(String zoneId);

}
