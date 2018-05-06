package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.ReportVO;

import java.util.Date;
import java.util.List;

/**
 * 报表 service
 *
 * @author xubaoji
 * @date 2013-6-13
 * @check li.jianfei 2013-06-20 #4185
 */
public interface ReportService {

    /**
     * 查询一个站点下 报表 统计数据
     *
     * @param siteId 站点编号
     * @param date   日期
     * @param type   类型
     * @return List<ReportVO> 报表实体
     * @author 许保吉
     * @date 2013-6-13
     */
    public List<ReportVO> findReportInfo(String siteId, Date date,
                                         Integer type);


}
