package com.microwise.blueplanet.proxy.impl;

import com.microwise.blueplanet.proxy.ZoneProxy;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xubaoji
 * @date 2013-6-19
 */
@Component
@Scope("prototype")
@Blueplanet
public class ZoneProxyImpl implements ZoneProxy {

    @Autowired
    private ZoneService zoneService;

    @Override
    public List<String> findChildrenIdList(String zoneId) {
        return zoneService.findChildrenIdList(zoneId);
    }

}
