package com.microwise.saturn.service;

import com.microwise.saturn.bean.MediaDetailPO;

import java.util.List;

/**
 * 展示内容
 *
 * @author 王耕
 * @date 2015-3-16
 */
public interface MediaDetailService {
    //增
    public void save(MediaDetailPO mediaDetail);

    //删
    public void delete(int mediaDetailId);

    //查
    public List<MediaDetailPO> findAll();

    public List<MediaDetailPO> findAllByPage(int pageIndex, int pageSize);

    public List<MediaDetailPO> findByConditions(MediaDetailPO mediaDetail, int pageIndex, int pageSize);

    public List<MediaDetailPO> findByConditions(String desc, Integer year, Integer quarterNum, Integer textureId);

    public MediaDetailPO findMediaDetailById(int id);

    public int findMediaDetailCount(MediaDetailPO mediaDetail);

    //改
    public void update(MediaDetailPO mediaDetail);

    //启用,禁用
    public void enable(int mediaDetailId, int enable);
}
