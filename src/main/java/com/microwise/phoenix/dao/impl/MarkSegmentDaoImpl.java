package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.MarkSegment;
import com.microwise.phoenix.bean.vo.MarkSegmentContrast;
import com.microwise.phoenix.dao.MarkSegmentDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.*;

/**
 * 标记段Dao实现
 *
 * @author zhangpeng
 * @date 13-8-6
 * @check @duan.qixin 2013年8月12日 #4938
 * @check @wang.geng 2013年8月14日 #5014
 */
@Beans.Dao
@Phoenix
public class MarkSegmentDaoImpl extends PhoenixBaseDao implements MarkSegmentDao {

    @Override
    public List<MarkSegmentContrast> findScale(String siteId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().selectList("phoenix.mybatis.MarkSegmentDao.findScale", paramMap);
    }

    @Override
    public List<Float> findFirstAndLastMarkLength(String markId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("markId", markId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().selectList(
                "phoenix.mybatis.MarkSegmentDao.findFirstAndLastMarkLength", paramMap);
    }

    @Override
    public List<Map<String, Object>> findScaleYearStat(String markId, int year) {
        List<Integer> months = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("months", months);
        paramMap.put("year", year);
        paramMap.put("markId", markId);
        return getSqlSession().selectList("phoenix.mybatis.MarkSegmentDao.findScaleYearStat",
                paramMap);
    }

    @Override
    public MarkSegmentContrast findScale(String markId) {
        return getSqlSession().selectOne("phoenix.mybatis.MarkSegmentDao.findOneScale", markId);
    }

    @Override
    public Float findScaleYearLastData(String markId, int year) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("year", year);
        paramMap.put("markId", markId);
        return getSqlSession().selectOne("phoenix.mybatis.MarkSegmentDao.findScaleYearLastData", paramMap);
    }

    @Override
    public List<MarkSegment> findAllMark(String siteId) {
        return getSqlSession().selectList("phoenix.mybatis.MarkSegmentDao.findAllMark", siteId);
    }

}
