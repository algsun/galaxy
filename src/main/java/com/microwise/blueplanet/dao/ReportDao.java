package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.vo.ReportVO;

import java.util.Date;
import java.util.List;

/**
 * 报表 订阅dao
 *
 * @author xubaoji
 * @date 2013-6-13
 * @check li.jianfei 2013-06-20 #4188
 */
public interface ReportDao {

    /**
     * 查询一个站点下日报表 统计数据
     *
     * @param siteId 站点编号
     * @param date   日期
     * @return List<ReportVO> 报表实体列表
     * @author 许保吉
     * @date 2013-6-13
     */
    public List<ReportVO> findDayReportInfo(String siteId, String date);

    /**
     * 查询一个时间段内 报表数据
     *
     * @param siteId    站点编号
     * @param startDate 开始 时间
     * @param endDate   结束时间
     * @return List<ReportVO> 报表实体列表
     * @author 许保吉
     * @date 2013-6-13
     */
    public List<ReportVO> findReportInfo(String siteId, Date startDate,
                                         Date endDate);

}
