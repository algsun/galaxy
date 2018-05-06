package com.microwise.blueplanet.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.microwise.blueplanet.bean.po.AliasPO;
import com.microwise.blueplanet.bean.po.SwitchChange;
import com.microwise.blueplanet.bean.po.Switches;
import com.microwise.blueplanet.dao.SwitchDao;
import com.microwise.blueplanet.service.SwitchService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author gaohui
 * @date 14-2-20 09:33
 */
@Beans.Service
@Transactional
@Blueplanet
public class SwitchServiceImpl implements SwitchService {
    @Autowired
    private SwitchDao switchDao;

    /**
     * @param siteId
     * @return
     */
    @Override
    public Map<String, Switches> findSwitchesBySiteId(String siteId) {
        List<Switches> switchesList = switchDao.findSwitchBySiteId(siteId);
        return Maps.uniqueIndex(switchesList, new Function<Switches, String>() {
            @Override
            public String apply(Switches switches) {
                switches.parseToValues();
                return switches.getNodeId();
            }
        });
    }

    @Override
    public int findSwitchesCountByDeviceId(String deviceId) {
        return switchDao.findSwitchesCountByDeviceId(deviceId);
    }

    @Override
    public List<Switches> findSwitchesByDeviceId(String deviceId, int page, int pageSize) {
        return switchDao.findSwitchesByDeviceId(deviceId, (page - 1) * pageSize, pageSize);
    }

    @Override
    public Switches findLastSwitchByDeviceId(String deviceId) {
        Switches switches = switchDao.findLastSwitchByDeviceId(deviceId);
        switches.parseToValues();

        return switches;
    }

    @Override
    public List<SwitchChange> findRecentSwitchChangesByDeviceId(String deviceId, int count) {
        return switchDao.findRecentSwitchChangesByDeviceId(deviceId, count);
    }

    @Override
    public int findSwitchChangeCountByDeviceId(String deviceId, Date startTime, Date endTime, int route, int action) {
        return switchDao.findSwitchChangeCountByDeviceId(deviceId, startTime, endTime, route, action);
    }

    @Override
    public List<SwitchChange> findSwitchChangeByDeviceId(String deviceId, Date startTime, Date endTime, int route, int action, int page, int pageSize) {
        return switchDao.findSwitchChangeByDeviceId(deviceId, startTime, endTime, route, action, (page - 1) * pageSize, pageSize);
    }

    @Override
    public void updateAlias(String deviceId, int route, String alias) {
        if (switchDao.aliasIsExist(deviceId, route)) {
            switchDao.deleteAlias(deviceId, route);
        }
        String id = UUID.randomUUID().toString();
        switchDao.insertAlias(id, deviceId, route, alias);
    }

    @Override
    public Map<String,List<AliasPO>> findAliasBySite(String siteId, int type) {
        //查询站点下所有控制模块的备注
        List<AliasPO> aliasPOList = switchDao.findAliasBySite(siteId, type);
        Map<String,List<AliasPO>> listMap=new HashMap<String, List<AliasPO>>();
        //把备注封装成以设备id为键的map
        for(int i=0;i<aliasPOList.size();i++){
            //从第一个备注开始
           AliasPO aliasPO=aliasPOList.get(i);
            //拿到备注对象的设备id
           String nodeId=aliasPO.getDeviceId();
           List<AliasPO> aliasPOList1=new ArrayList<AliasPO>();
            //把list中该设备的备注封装进list1中
            for(AliasPO aliasPO1:aliasPOList){
             if(nodeId.equals(aliasPO1.getDeviceId())){
                 aliasPOList1.add(aliasPO1);
             }
            }
            //以设备id为key放进备注的列表
            listMap.put(nodeId,aliasPOList1);
        }
        return listMap;
    }

    @Override
    public List<AliasPO> findAliasByDeviceId(String deviceId) {
        return switchDao.findAliasByDeviceId(deviceId);
    }
}
