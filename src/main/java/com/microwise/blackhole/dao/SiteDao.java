package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.Site;

import java.util.List;

/**
 * 站点Dao
 *
 * @author li.jianfei
 * @date 2014-10-09
 */
public interface SiteDao {

    /**
     * 查询站点集合
     *
     * @return 站点集合
     */
    public List<Site> findAll();

}
