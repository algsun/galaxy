package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.vo.MarkSegment;
import com.microwise.phoenix.bean.vo.MarkSegmentContrast;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 标记段Dao
 *
 * @author zhangpeng
 * @date 13-8-6
 * @check @duan.qixin 2013-8-12 #4938
 * @check @wang.geng 2013年8月14日 #5014
 */
public interface MarkSegmentDao {

    /**
     * 查询站点下所有标记段及其比例尺等计算信息
     *
     * @param siteId    站点编号
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<MarkSegmentContrast> 标记段对比vo列表
     * @author zhangpeng
     * @date 13-8-6
     */
    public List<MarkSegmentContrast> findScale(String siteId, Date startDate, Date endDate);

    /**
     * 查询标记段一段时间段的第一个及最后一个标记段长度
     *
     * @param markId    标记段id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<Float> 第一个元素为第一个标记段长度，第二个元素为最后一个
     * @author zhangpeng
     * @date 13-8-6
     */
    public List<Float> findFirstAndLastMarkLength(String markId, Date startDate, Date endDate);

    /***
     * 查询一个标记段的年统计数据
     * 包含12 个月的 第一和最后一个记录数据
     *
     * @param markId 标记段id
     * @param year   查询年
     * @return List<Map<String,Object>> 该标记段12 个月统计数据集合
     * @author xu.baoji
     * @date 2013-8-7
     */
    public List<Map<String, Object>> findScaleYearStat(String markId, int year);

    /***
     * 查询一个标记段 莫年12 月份的最后一条数据
     *
     * @param markId 标记段id
     * @param year   查 询年
     * @return Float 标记段长度
     * @author xu.baoji
     * @date 2013-8-8
     */
    public Float findScaleYearLastData(String markId, int year);

    /***
     * 查询一个标记段的信息
     *
     * @param markId 标记段id
     * @return MarkSegmentContrast 标记段信息
     * @author xu.baoji
     * @date 2013-8-8
     */
    public MarkSegmentContrast findScale(String markId);

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
