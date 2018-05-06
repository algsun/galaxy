package com.microwise.cybertron.dao;

import com.microwise.cybertron.bean.Config;

/**
 * 系统配置 Dao
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
public interface ConfigDao {


    /**
     * 查询该站点档案全宗号
     *
     * @param siteId 站点ID
     * @return 档案全宗号
     */
    public Config findConfig(String siteId);

    /**
     * 添加配置信息
     *
     * @param config 配置信息
     */
    public void saveConfig(Config config);

    /**
     * 更新配置信息
     *
     * @param config 配置信息
     */
    public void updateConfig(Config config);

}
