package com.microwise.saturn.service;

import com.microwise.saturn.bean.MediaShowPO;

import java.util.List;

/**
 * 展示类别service
 *
 * @author 王耕
 * @date 2015-3-16
 */
public interface MediaShowService {
    //增
    public void save(MediaShowPO mediaShow);

    //删
    public void delete(int mediaShowId);

    //查
    public List<MediaShowPO> findAll();
    public List<MediaShowPO> findAllByPage(int pageIndex,int pageSize);
    public List<MediaShowPO> findByCondition(MediaShowPO mediaShow,int pageIndex,int pageSize);
    public int findMediaShowCount(MediaShowPO mediaShow);

    //改
    public void update(MediaShowPO mediaShow);
}
