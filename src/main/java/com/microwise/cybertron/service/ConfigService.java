package com.microwise.cybertron.service;

import com.microwise.cybertron.bean.Config;

/**
 * 系统配置 Service
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
public interface ConfigService {
    /**
     * 查询该站点档案全宗号
     *
     * @param siteId 站点ID
     * @return 档案全宗号
     */
    public Config findConfig(String siteId);

    /**
     * 更新配置信息
     *
     * @param config 配置信息
     */
    public void updateConfig(Config config);

}
