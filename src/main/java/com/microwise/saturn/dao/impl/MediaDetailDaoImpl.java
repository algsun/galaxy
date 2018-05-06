package com.microwise.saturn.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.MediaDetailPO;
import com.microwise.saturn.dao.MediaDetailDao;
import com.microwise.saturn.sys.Saturn;
import com.microwise.saturn.sys.SaturnBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 展示内容Dao实现
 *
 * @author 王耕
 * @date 2015-3-16
 */
@Beans.Dao
@Saturn
public class MediaDetailDaoImpl extends SaturnBaseDao implements MediaDetailDao {
    @Override
    public void save(MediaDetailPO mediaDetail) {
        getSqlSession().insert("saturn.mybatis.MediaDetailDao.save", mediaDetail);
    }

    @Override
    public void delete(int mediaDetailId) {
        getSqlSession().delete("saturn.mybatis.MediaDetailDao.deleteById", mediaDetailId);
    }

    @Override
    public List<MediaDetailPO> findAll() {
        return getSqlSession().selectList("saturn.mybatis.MediaDetailDao.findAll");
    }

    @Override
    public List<MediaDetailPO> findByConditions(MediaDetailPO mediaDetail, int pageIndex, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", mediaDetail.getId());
        paramMap.put("detailTitle", mediaDetail.getDetailTitle());
        paramMap.put("detailSubTitle", mediaDetail.getDetailSubTitle());
        paramMap.put("detailContent", mediaDetail.getDetailContent());
        paramMap.put("detailSubDesc", mediaDetail.getDetailSubDesc());
        paramMap.put("detailImage", mediaDetail.getDetailImage());
        paramMap.put("detailSubImage", mediaDetail.getDetailSubImage());
        paramMap.put("createBy", mediaDetail.getCreateBy());
        paramMap.put("createOn", mediaDetail.getCreateOn());
        paramMap.put("enable", mediaDetail.getEnable());
        paramMap.put("line", mediaDetail.getLine());
        paramMap.put("material", mediaDetail.getMaterial());
        paramMap.put("pubDate", mediaDetail.getPubDate());
        int begin = (pageIndex - 1) * pageSize;
        paramMap.put("begin", begin);
        paramMap.put("pageSize", pageSize);
        return getSqlSession().selectList("saturn.mybatis.MediaDetailDao.findByCondition", paramMap);
    }

    @Override
    public List<MediaDetailPO> findByConditions(String desc, Integer year, Integer quarterNum, Integer textureId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("createOn", year);
        paramMap.put("material", textureId);
        paramMap.put("desc", desc);
        paramMap.put("quarterNum", quarterNum);
        return getSqlSession().selectList("saturn.mybatis.MediaDetailDao.findByYearAndTextureId", paramMap);
    }

    @Override
    public MediaDetailPO findMediaDetailById(int id) {
        return getSqlSession().selectOne("saturn.mybatis.MediaDetailDao.findMediaDetailById", id);
    }

    @Override
    public int findByConditionSize(MediaDetailPO mediaDetail) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", mediaDetail.getId());
        paramMap.put("detailTitle", mediaDetail.getDetailTitle());
        paramMap.put("detailSubTitle", mediaDetail.getDetailSubTitle());
        paramMap.put("detailContent", mediaDetail.getDetailContent());
        paramMap.put("detailSubDesc", mediaDetail.getDetailSubDesc());
        paramMap.put("detailImage", mediaDetail.getDetailImage());
        paramMap.put("detailSubImage", mediaDetail.getDetailSubImage());
        paramMap.put("createBy", mediaDetail.getCreateBy());
        paramMap.put("createOn", mediaDetail.getCreateOn());
        paramMap.put("enable", mediaDetail.getEnable());
        paramMap.put("line", mediaDetail.getLine());
        paramMap.put("material", mediaDetail.getMaterial());
        paramMap.put("pubDate", mediaDetail.getPubDate());

        List<MediaDetailPO> mediaDetails = getSqlSession().selectList("saturn.mybatis.MediaDetailDao.findByConditionSize", paramMap);

        return mediaDetails.size();
    }

    @Override
    public List<MediaDetailPO> findAllByPage(int pageIndex, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        int begin = (pageIndex - 1) * pageSize;
        paramMap.put("begin", begin);
        paramMap.put("pageSize", pageSize);
        return getSqlSession().selectList("saturn.mybatis.MediaDetailDao.findAllByPage", paramMap);
    }

    @Override
    public void update(MediaDetailPO mediaDetail) {
        getSqlSession().update("saturn.mybatis.MediaDetailDao.update", mediaDetail);
    }

    @Override
    public void enable(int mediaDetailId, int enable) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("enable", enable);
        paramMap.put("mediaDetailId", mediaDetailId);
        getSqlSession().update("saturn.mybatis.MediaDetailDao.enable", paramMap);
    }
}
