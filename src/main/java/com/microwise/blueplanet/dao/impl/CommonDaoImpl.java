package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.dao.CommonDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;

/**
 * 公共Dao的实现
 *
 * @author zhangpeng
 * @date 2013-1-28
 * @check 2013-02-25 xubaoji svn:1750
 */
@Dao
@Blueplanet
public class CommonDaoImpl extends BlueplanetBaseDao implements CommonDao {

    @Override
    public Integer findDataVersion(String tableName) {
        return getSqlSession().selectOne(
                "blueplanet.mybatis.CommonDao.findDataVersion", tableName);
    }

}
