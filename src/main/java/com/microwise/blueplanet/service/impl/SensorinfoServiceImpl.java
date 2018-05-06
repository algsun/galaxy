package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.dao.SensorinfoDao;
import com.microwise.blueplanet.service.SensorinfoService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 监测指标Service实现
 *
 * @author zhangpeng
 * @date 2013-1-17
 * @check 2013-02-25 xubaoji svn:1750
 */
@Service
@Transactional
@Blueplanet
public class SensorinfoServiceImpl implements SensorinfoService {

    /**
     * 用户Dao
     */
    @Autowired
    private SensorinfoDao sensorinfoDao;

    @Override
    public List<SensorinfoVO> findSensorinfo() {
        return sensorinfoDao.findSensorinfo();
    }

    @Override
    public SensorinfoVO findByPhysicalid(Integer sensorPhysicalid) {
        return sensorinfoDao.findByPhysicalid(sensorPhysicalid);
    }

}
