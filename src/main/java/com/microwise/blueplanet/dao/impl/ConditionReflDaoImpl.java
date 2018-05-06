package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.ConditionRefl;
import com.microwise.blueplanet.dao.ConditionReflDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.List;

/**
 * @author gaohui
 * @date 14-2-19 11:20
 */
@Beans.Dao
@Blueplanet
public class ConditionReflDaoImpl extends BlueplanetBaseDao implements ConditionReflDao {

    @Override
    public List<ConditionRefl> findByDeviceId(String deviceId){
       return getSqlSession().selectList("blueplanet.ConditionRefl.findByDeviceId", deviceId);
    }

}
