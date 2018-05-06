package com.microwise.orion.proxy.impl;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.proxy.RelicProxy;
import com.microwise.orion.service.PhotoService;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文物代理实现
 *
 * @author liuzhu
 * @date 2016-7-18
 */
@Component
@Scope("prototype")
@Orion
public class RelicProxyImpl implements RelicProxy {

    @Autowired
    private RelicService relicService;

    @Override
    public int findRelicsCount(String relicNameTotalCode, List<Integer> ids) {
        String siteId = Sessions.createByAction().currentSiteId();
        return relicService.findRelicsCount(siteId, relicNameTotalCode, ids);
    }

    @Override
    public List<Relic> findRelics(String relicNameTotalCode, int pageIndex, int pageSize, List<Integer> ids) {
        String siteId = Sessions.createByAction().currentSiteId();
        return relicService.findRelics(siteId, relicNameTotalCode, pageIndex, pageSize, ids);
    }

}
