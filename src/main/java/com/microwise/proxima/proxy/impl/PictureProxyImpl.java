package com.microwise.proxima.proxy.impl;

import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.proxy.PictureProxy;
import com.microwise.proxima.service.OpticsDVPlaceService;
import com.microwise.proxima.service.PictureService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * picture代理实现
 *
 * @author xu.yuexi
 * @date 2013-3-28
 */
@Component
@Scope("prototype")
@Proxima
public class PictureProxyImpl implements PictureProxy {

    @Autowired
    private PictureService pictureService;
    @Autowired
    private OpticsDVPlaceService opticsDVPlaceService;

    @Override
    public PictureBean findById(String picId) {
        return pictureService.findById(picId);
    }



    @Override
    public List<PictureBean> findByTime(String dvPlaceId, Date startDate, Date endDate) {
        return pictureService.findByTime(dvPlaceId, startDate, endDate);
    }

    @Override
    public List<OpticsDVPlaceBean> findAll(String siteId) {
        return opticsDVPlaceService.findAll(siteId);
    }

}
