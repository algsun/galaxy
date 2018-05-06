package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.vo.MarkSegment;
import com.microwise.phoenix.bean.vo.MarkSegmentContrast;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 标记段Service
 *
 * @author zhangpeng
 * @date 13-8-6
 * @check @duan.qixin 2013年8月12日 #4938
 * @check @wang.geng 2013年8月14日 #5014
 */
public interface MarkSegmentService {

    /**
     * 查询站点下年、或月的标记段变化对比
     *
     * @param siteId 站点编号
     * @param date   时间参数
     * @param type   类型（年/月）
     * @return List<MarkSegmentContrast> 标记段对比vo列表
     * @author zhangpeng
     * @date 13-8-6
     */
    public List<MarkSegmentContrast> contrast(String siteId, Date date, int type);

    /***
     * 查询一个标记段的年统计数据
     * 标记段12 个月变化统计数据列表  key 为 月份，value 为 变化幅度 ，如果某月没有数据则没有该建值对
     * 如果12 个月都没有数据map为 size为0 的集合
     *
     * @param markId 标记段id
     * @param year   要查询的数据年份
     * @return Map<Integer, Float>
     * @author xu.baoji
     * @date 2013-8-7
     */
    public Map<Integer, Float> findMarkYearStat(String markId, int year);

    /***
     * 查询一个站点下所以标记段信息
     *
     * @param siteId 站点编号
     * @return List<MarkSegmentt>
     * @author xu.baoji
     * @date 2013-8-8
     */
    public List<MarkSegment> findAllMark(String siteId);


}
