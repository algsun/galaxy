/**
 *
 */
package com.microwise.proxima.service.impl;

import com.google.common.base.Strings;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.dao.DVPlaceDao;
import com.microwise.proxima.service.DVPlaceService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * 摄像机点位信息服务层实现
 * </pre>
 *
 * @author zhangpeng
 * @date 2012-7-10
 * @check @li.jianfei 2013.09.02 #5293
 * @check li.jianfei liu.zhu 2014-4-15 # 8281
 */
@Service
@Transactional
@Proxima
public class DVPlaceServiceImpl implements DVPlaceService {

    /**
     * 摄像机Dao层
     */
    @Autowired
    private DVPlaceDao dvPlaceDao;

    @Override
    public DVPlaceBean findById(String id) {
        return dvPlaceDao.findById(id);
    }

    @Override
    public void update(DVPlaceBean dvPlace) {
        dvPlaceDao.update(dvPlace);
    }

    @Override
    public void changeEnable(String dvPlaceId) {
        boolean enable = dvPlaceDao.findDvEnable(dvPlaceId);
        dvPlaceDao.updateDvEnable(dvPlaceId, !enable);
    }

    @Override
    public void updateDvIP(int dvPlaceId, String dvIp) {
        this.dvPlaceDao.updateDvIP(dvPlaceId, dvIp);

    }

    @Override
    public List<DVPlaceBean> findByMonitorPointId(int monitorPointId) {
        return this.dvPlaceDao.findByMonitorPointId(monitorPointId);
    }

    @Override
    public List<DVPlaceBean> findAllDVPlace() {
        return dvPlaceDao.findAllDVPlace();
    }

    @Override
    public DVPlaceBean findByName(String dvPlaceName) {
        return dvPlaceDao.findByName(dvPlaceName);
    }

    @Override
    public boolean hasSameNameByAdd(String zoneId, String dvPlaceName) {
        return dvPlaceDao.hasSameNameByAdd(zoneId, dvPlaceName);
    }

    @Override
    public boolean hasSameNameByUpdate(String zoneId, String dvPlaceName,
                                       String dvPlaceId) {
        return dvPlaceDao.hasSameNameByUpdate(zoneId, dvPlaceName, dvPlaceId);
    }

    @Override
    public boolean hasSameName(String zoneId, String dvPlaceName,
                               String dvPlaceId) {
        if (Strings.isNullOrEmpty(dvPlaceId)) {
            return dvPlaceDao.hasSameNameByAdd(zoneId, dvPlaceName);
        } else {
            return dvPlaceDao.hasSameNameByUpdate(zoneId, dvPlaceName,
                    dvPlaceId);
        }
    }

    @Override
    public boolean findDvEnable(String dvPlaceId) {
        return dvPlaceDao.findDvEnable(dvPlaceId);
    }

    @Override
    public void updateRealmap(String dvId, String realmap) {
        dvPlaceDao.updateRealmap(dvId, realmap);
    }

    @Override
    public List<DVPlaceBean> findDvPlacesByZoneId(String zoneId) {
        return dvPlaceDao.findDvPlacesByZoneId(zoneId);
    }

    @Override
    public List<DVPlaceBean> findAll(String siteId, int pageNmb, int pageSize) {
        List<DVPlaceBean> dvPlaceBeans = dvPlaceDao.findAll(siteId, pageNmb, pageSize);
        for (DVPlaceBean o:dvPlaceBeans) {
            if (o instanceof OpticsDVPlaceBean) {
                o.setDvType(1);
            }else{
                o.setDvType(2);
            }
        }
        return dvPlaceBeans;
    }

    @Override
    public List<DVPlaceBean> findDvPlacesByZoneId(String zoneId, int pageNmb, int pageSize) {
        List<DVPlaceBean>   dvPlaceBeans= dvPlaceDao.findDvPlacesByZoneId(zoneId, pageNmb, pageSize);
        for (DVPlaceBean o:dvPlaceBeans) {
            if (o instanceof OpticsDVPlaceBean) {
                o.setDvType(1);
            }else{
                o.setDvType(2);
            }
        }
        return dvPlaceBeans;
    }

    @Override
    public List<DVPlaceBean> findAll(String siteId) {
        return dvPlaceDao.findAll(siteId);
    }
}
