package com.microwise.saturn.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.MediaDetailPO;
import com.microwise.saturn.dao.MediaDetailDao;
import com.microwise.saturn.service.MediaDetailService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 展示内容service实现
 *
 * @author 王耕
 * @date 2015-3-16
 */
@Beans.Service
@Saturn
@Transactional
public class MediaDetailServiceImpl implements MediaDetailService {

    @Autowired
    private MediaDetailDao mediaDetailDao;

    @Override
    public void save(MediaDetailPO mediaDetail) {
        mediaDetailDao.save(mediaDetail);
    }

    @Override
    public void delete(int mediaDetailId) {
        mediaDetailDao.delete(mediaDetailId);
    }

    @Override
    public List<MediaDetailPO> findAll() {
        return mediaDetailDao.findAll();
    }

    @Override
    public int findMediaDetailCount(MediaDetailPO mediaDetail) {
        if (mediaDetail == null) {
            return mediaDetailDao.findAll().size();
        } else {
            return mediaDetailDao.findByConditionSize(mediaDetail);
        }
    }

    @Override
    public List<MediaDetailPO> findAllByPage(int pageIndex, int pageSize) {
        return mediaDetailDao.findAllByPage(pageIndex, pageSize);
    }

    @Override
    public List<MediaDetailPO> findByConditions(MediaDetailPO mediaDetail, int pageIndex, int pageSize) {
        return mediaDetailDao.findByConditions(mediaDetail, pageIndex, pageSize);
    }

    @Override
    public List<MediaDetailPO> findByConditions(String desc, Integer year, Integer quarterNum, Integer textureId) {
        return mediaDetailDao.findByConditions(desc, year, quarterNum, textureId);
    }

    @Override
    public MediaDetailPO findMediaDetailById(int id) {
        return mediaDetailDao.findMediaDetailById(id);
    }

    @Override
    public void update(MediaDetailPO mediaDetail) {
        mediaDetailDao.update(mediaDetail);
    }

    @Override
    public void enable(int mediaDetailId, int enable) {
        mediaDetailDao.enable(mediaDetailId, enable);
    }
}
