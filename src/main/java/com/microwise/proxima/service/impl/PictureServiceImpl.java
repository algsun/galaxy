package com.microwise.proxima.service.impl;

import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.dao.PictureDao;
import com.microwise.proxima.service.PictureService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhang.licong
 * @date 2012-7-10
 *
 * @check li.jianfei liu.zhu 2014-4-15 # 8174
 */
@Service
@Transactional
@Proxima
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public List<PictureBean> findByTime(String dvPlaceId, Date startDate,
                                        Date endDate) {
        return pictureDao.findByTime(dvPlaceId, startDate, endDate);
    }

    @Override
    public DVPlaceBean findDVPlaceByPictureId(String picId) {
        return pictureDao.findDVPlaceByPictureId(picId);
    }

    @Override
    public PictureBean findById(String picId) {
        return pictureDao.findById(picId);
    }

    @Override
    public List<PictureBean> findPictures(String dvPlaceId) {
        return pictureDao.findPictures(dvPlaceId);
    }

    public List<PictureBean> findPicturesAfter(String dvPlaceId, Date dateAfter,
                                               int max) {
        return pictureDao.findPicturesAfter(dvPlaceId, dateAfter, max);
    }

    @Override
    public List<PictureBean> findRecentPictures(String dvPlaceId, int count) {
        return pictureDao.findRecentPictures(dvPlaceId, count);
    }

}
