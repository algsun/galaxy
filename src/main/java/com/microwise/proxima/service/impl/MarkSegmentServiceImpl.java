package com.microwise.proxima.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.MarkSegmentBean;
import com.microwise.proxima.bean.MarkSegmentPositionBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.dao.DVPlaceDao;
import com.microwise.proxima.dao.MarkSegmentDao;
import com.microwise.proxima.dao.MarkSegmentPositionDao;
import com.microwise.proxima.dao.PictureDao;
import com.microwise.proxima.service.MarkSegmentService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.util.PositionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * MarkSegmentService implement
 *
 * @author gaohui
 * @date 2012-7-13
 */
@Service
@Transactional
@Proxima
public class MarkSegmentServiceImpl implements MarkSegmentService {
    @Autowired
    private MarkSegmentDao markSegmentDao;
    @Autowired
    private MarkSegmentPositionDao markSegmentPositionDao;
    @Autowired
    private DVPlaceDao dvPlaceDao;
    @Autowired
    private PictureDao pictureDao;

    @Override
    public List<MarkSegmentBean> findAllMarkSegmentByDvPlaceId(String dvPlaceId) {
        return markSegmentDao.findAllMarkSegmentByDvPlaceId(dvPlaceId);
    }

    @Override
    public MarkSegmentBean findMarkSegmentOfDVPlaceByName(int dvPlaceId,
                                                          String markSegmentName) {
        return markSegmentDao.findMarkSegmentOfDVPlaceByName(dvPlaceId,
                markSegmentName);
    }

    @Override
    public MarkSegmentPositionBean addMarkSegmentWidthPoistion(int dvPlaceId,
                                                               String markName, int picId, int positionX, int positionY,
                                                               int positionX2, int positionY2) {

        DVPlaceBean dvPlace = dvPlaceDao.findById(dvPlaceId);

        // 添加新的标记段
        MarkSegmentBean markSegment = new MarkSegmentBean();
        markSegment.setName(markName);
        markSegment.setDvPlace(dvPlace);
        markSegment.setCreateTime(new Date());

        markSegmentDao.save(markSegment);

        PictureBean pic = pictureDao.findById(picId);

        // 添加标记段坐标
        MarkSegmentPositionBean markSegmentPosition = new MarkSegmentPositionBean();
        markSegmentPosition.setPicture(pic);
        markSegmentPosition.setMarkSegment(markSegment);
        markSegmentPosition.setPositionX(positionX);
        markSegmentPosition.setPositionY(positionY);
        markSegmentPosition.setPositionX2(positionX2);
        markSegmentPosition.setPositionY2(positionY2);
        markSegmentPosition.setUpdateTime(new Date());

        // 计算 legnth 和 deltaLength
        markSegmentPosition.setLengthDelta(0);
        double lengthOfPixel = PositionUtil.length(positionX, positionY,
                positionX2, positionY2);
        float length = PositionUtil.realLength(lengthOfPixel,
                dvPlace.getImageRealWidth(), dvPlace.getImageWidth());
        markSegmentPosition.setMarkLength(length);

        markSegmentPositionDao.save(markSegmentPosition);

        return markSegmentPosition;
    }

    @Override
    public void cancelMarkSegment(int markSegmentId) {
        MarkSegmentBean markSegment = markSegmentDao.findById(markSegmentId);
        if (markSegment != null) {
            markSegment.setCancel(true);
            markSegment.setCancelTime(new Date());
        }
    }

    @Override
    public Map<Integer, List<MarkSegmentBean>> findAllMarkSegment() {
        Map<Integer, List<MarkSegmentBean>> mapSegements = null;
        List<MarkSegmentBean> listTemp = null;
        String dvPlaceID = null;
        List<MarkSegmentBean> listSegements = markSegmentDao.findAllMarkSegment();
        if (listSegements.size() > 0) {
            mapSegements = new HashMap<Integer, List<MarkSegmentBean>>();
            for (MarkSegmentBean markSegmentBean : listSegements) {
                if (!markSegmentBean.getDvPlace().getId().equals(dvPlaceID)) {
                    listTemp = new ArrayList<MarkSegmentBean>();
                    dvPlaceID = markSegmentBean.getDvPlace().getId();
                    // TODO 正确代码为下面第一行代码, 第二行代码为是让代码编译通过 @gaohui 2013-03-21
                    // TODO 需要统一将 Integer 转为 String
                    // mapSegements.put(dvPlaceID, listTemp);
                    mapSegements.put(1, listTemp);
                }
                listTemp.add(markSegmentBean);
            }
        }

        return mapSegements;
    }

    @Override
    public Map<DVPlaceBean, Collection<MarkSegmentBean>> findAllBySiteId(String siteId) {
        List<MarkSegmentBean> markSegments = markSegmentDao.findAllBySiteId(siteId);
        ListMultimap<DVPlaceBean, MarkSegmentBean> dvPlaceToMarkSegments = Multimaps.index(markSegments, new Function<MarkSegmentBean, DVPlaceBean>() {
            @Override
            public DVPlaceBean apply(MarkSegmentBean markSegmentBean) {
                return markSegmentBean.getDvPlace();
            }
        });

        return dvPlaceToMarkSegments.asMap();
    }

    @Override
    public DVPlaceBean findDVPlaceByMarkSegmentId(String markSegmentId) {
        return markSegmentDao.findDVPlaceByMarkSegmentId(markSegmentId);
    }

}
