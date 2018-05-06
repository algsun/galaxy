package com.microwise.phoenix.service.impl;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.MathUtil;
import com.microwise.phoenix.bean.vo.MarkSegment;
import com.microwise.phoenix.bean.vo.MarkSegmentContrast;
import com.microwise.phoenix.dao.MarkSegmentDao;
import com.microwise.phoenix.service.MarkSegmentService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.uma.util.DateTypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 标记段Service实现
 *
 * @author zhangpeng
 * @date 13-8-6
 * @check @duan.qixin 2013年8月12日 #4938
 * @check @wang.geng 2013年8月14日 #5014
 */
@Phoenix
@Beans.Service
@Transactional
public class MarkSegmentServiceImpl implements MarkSegmentService {

    @Autowired
    private MarkSegmentDao markSegmentDao;

    @Override
    public List<MarkSegmentContrast> contrast(String siteId, Date date, int type) {
        List<MarkSegmentContrast> markSegmentContrastList = new ArrayList<MarkSegmentContrast>();
        // 如果查询条件为年或月
        if (type == Constants.FIND_TYPE_YEAR || type == Constants.FIND_TYPE_MONTH) {
            Date start = DateTypeGenerator.start(type, date);
            Date end = DateTypeGenerator.end(type, date);
            markSegmentContrastList = markSegmentDao.findScale(siteId, start, end);
            for (MarkSegmentContrast mark : markSegmentContrastList) {
                if (mark != null) {
                    List<Float> floats = markSegmentDao.findFirstAndLastMarkLength(mark.getMarkId(),
                            start, end);
                    if (floats != null && floats.size() == 2) {
                        float lengthDelta = Math.abs(floats.get(1) - floats.get(0));
                        mark.setLengthDelta(lengthDelta);
                        mark.setLengthRealDelta(MathUtil.roundFloat(lengthDelta * mark.getScale(), 2));
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("日期类型不正确: " + type);
        }
        return markSegmentContrastList;
    }

    @Override
    public Map<Integer, Float> findMarkYearStat(String markId, int year) {
        Map<Integer, Float> yearStatData = new HashMap<Integer, Float>();
        // 查询标记段的信息 拿到计算像素 和 毫米 长度的比率
        MarkSegmentContrast markSegmentContrast = markSegmentDao.findScale(markId);
        // 查询上一年12 月份的最后一条数据
        Float oldYearLastData = markSegmentDao.findScaleYearLastData(markId, year - 1);
        // 查询 统计基本数据
        List<Map<String, Object>> basicData = markSegmentDao.findScaleYearStat(markId, year);
        disposeMarkData(yearStatData, basicData, markSegmentContrast.getScale(), oldYearLastData);
        return yearStatData;
    }

    @Override
    public List<MarkSegment> findAllMark(String siteId) {
        return markSegmentDao.findAllMark(siteId);
    }

    /**
     * 处理年统计数据
     *
     * @param yearStatData    要组装的年统计数据结果集
     * @param basicData       年统计数据基本数据
     * @param scale           比例 1像素代表多少毫米
     * @param oldYearLastData 用来计算本年 1月份的 对比量 (上一年12月份的最后数据)
     * @author xu.baoji
     * @date 2013-8-8
     */
    private void disposeMarkData(Map<Integer, Float> yearStatData,
                                 List<Map<String, Object>> basicData, float scale, Float oldYearLastData) {

        for (int i = 0; i < basicData.size(); i++) {
            // 获得 一个月份统计数据
            Map<String, Object> monthData = basicData.get(i);
            // 获得月份
            Integer month = (Integer) monthData.get("month");
            // 获得本月最后一个数据
            Float lastMarkLength = (Float) monthData.get("lastMarkLength");
            // 获得本月第一个数据
            Float fistMarkLength = (Float) monthData.get("fistMarkLength");
            // 本月无数据
            if (lastMarkLength == null) {
                yearStatData.put(month.intValue(), null);
            } else {
                // 本月裂隙增长长度
                Float addMarkLength = null;
                // 1月份数据
                if (i == 0) {
                    //本月最后长度 减去上一年12 月份长度 ，如果上一年12份无数据 减去本月第一次长度
                    addMarkLength = lastMarkLength
                            - (oldYearLastData == null ? fistMarkLength : oldYearLastData);
                } else {
                    //上一月数据
                    Map<String, Object> oldMonthData = basicData.get(i - 1);
                    Integer oldMonth = (Integer) oldMonthData.get("month");
                    Float oldLastMarkLength = (Float) oldMonthData.get("lastMarkLength");
                    // 判断上一月月份加一等于本月份 如果等于上一个月份有数据，不等于上一个月份无数据
                    addMarkLength = lastMarkLength
                            - (oldMonth + 1 == month ? oldLastMarkLength : fistMarkLength);
                }
                yearStatData.put(month.intValue(), MathUtil.roundFloat(addMarkLength * scale, 2));
            }
        }
    }
}
