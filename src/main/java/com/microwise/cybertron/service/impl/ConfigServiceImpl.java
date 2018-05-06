package com.microwise.cybertron.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Config;
import com.microwise.cybertron.dao.ConfigDao;
import com.microwise.cybertron.service.ConfigService;
import com.microwise.cybertron.sys.Cybertron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统配置 Service 实现
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
@Beans.Service
@Transactional
@Cybertron
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigDao configDao;

    @Override
    public Config findConfig(String siteId) {
        return configDao.findConfig(siteId);
    }

    @Override
    public void updateConfig(Config config) {
        Config configData = configDao.findConfig(config.getSiteId());
        if (configData != null) {
            configData.setOfficialId(config.getOfficialId());
            configDao.updateConfig(configData);
        } else {
            configDao.saveConfig(config);
        }
    }
}
