package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.dao.SensorinfoDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监测指标Dao实现
 *
 * @author zhangpeng
 * @date 2013-1-17
 * @check 2013-02-25 xubaoji svn:1750
 */
@Dao
@Blueplanet
public class SensorinfoDaoImpl extends BlueplanetBaseDao implements
        SensorinfoDao {

    @Override
    public List<SensorinfoVO> findSensorinfo() {
        Map paramMap = new HashMap<String, Object>();
        paramMap.put("language", LocaleBundleTools.appLocale());
        return getSqlSession().selectList(
                "blueplanet.mybatis.SensorinfoDao.findSensorinfo", paramMap);
    }

    @Override
    public SensorinfoVO findByPhysicalid(Integer sensorPhysicalid) {
        return getSqlSession().selectOne("blueplanet.mybatis.SensorinfoDao.findByPhysicalid", sensorPhysicalid);
    }

}
