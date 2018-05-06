package com.microwise.cybertron.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Config;
import com.microwise.cybertron.dao.ConfigDao;
import com.microwise.cybertron.sys.Cybertron;
import com.microwise.cybertron.sys.CybertronBaseDao;
import org.hibernate.Query;

/**
 * 系统配置Dao 实现
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
@Beans.Dao
@Cybertron
public class ConfigDaoImpl extends CybertronBaseDao<Config> implements ConfigDao {
    public ConfigDaoImpl() {
        super(Config.class);
    }

    @Override
    public Config findConfig(String siteId) {
        String hql = "From Config c Where c.siteId = :siteId";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        return (Config) query.uniqueResult();
    }

    @Override
    public void saveConfig(Config config) {
        save(config);
    }

    @Override
    public void updateConfig(Config config) {
        update(config);
    }


}
