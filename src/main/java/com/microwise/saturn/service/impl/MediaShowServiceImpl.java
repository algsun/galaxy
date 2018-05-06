package com.microwise.saturn.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.MediaShowPO;
import com.microwise.saturn.dao.MediaShowDao;
import com.microwise.saturn.service.MediaShowService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 展示类别service实现
 *
 * @author 王耕
 * @date 2015-3-16
 */
@Beans.Service
@Saturn
@Transactional
public class MediaShowServiceImpl implements MediaShowService {

    @Autowired
    private MediaShowDao mediaShowDao;

    @Override
    public void save(MediaShowPO mediaShow) {
        mediaShowDao.save(mediaShow);
    }

    @Override
    public void delete(int mediaShowId) {
        mediaShowDao.delete(mediaShowId);
    }

    @Override
    public List<MediaShowPO> findAll(){
        return mediaShowDao.findAll();
    }

    @Override
    public List<MediaShowPO> findAllByPage(int pageIndex,int pageSize) {
        return mediaShowDao.findAllByPage(pageIndex,pageSize);
    }

    @Override
    public List<MediaShowPO> findByCondition(MediaShowPO mediaShow,int pageIndex,int pageSize) {
        return mediaShowDao.findByCondition(mediaShow,pageIndex,pageSize);
    }

    @Override
    public int findMediaShowCount(MediaShowPO mediaShow) {
        if (mediaShow == null) {
            return mediaShowDao.findAll().size();
        } else {
            return mediaShowDao.findByConditionSize(mediaShow);
        }
    }

    @Override
    public void update(MediaShowPO mediaShow) {
        mediaShowDao.update(mediaShow);
    }
}
