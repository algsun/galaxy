package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.Subsystem;

import java.util.List;

/**
 * 业务系统Dao
 *
 * @author zhangpeng
 * @date 2012-11-21
 * @check 2012-11-27 xubaoji svn:381
 */
public interface SubsystemDao {

    /**
     * 获取所有业务系统Service
     *
     * @return List<Subsystem> 业务系统对象列表
     * @author zhangpeng
     * @date 2012-11-21
     */
    public List<Subsystem> findAllSubsystems();

    /**
     * 根据语言环境获取对应的数据库语言
     *
     * @return List<Subsystem> 业务系统对象列表
     * @author chenyaofei
     * @date 2016-03-01
     * */
    public List<Subsystem> findAllSubsystemsByLanguage(String language);

}
