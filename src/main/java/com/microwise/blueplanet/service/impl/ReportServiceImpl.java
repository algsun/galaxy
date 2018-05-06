package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.vo.ReportVO;
import com.microwise.blueplanet.dao.ReportDao;
import com.microwise.blueplanet.service.ReportService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.common.util.DateTimeUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 报表service 实现
 *
 * @author xubaoji
 * @date 2013-6-13
 * @check li.jianfei 2013-06-20 #4200
 */
@Service
@Blueplanet
@Transactional
public class ReportServiceImpl implements ReportService {

    /**
     * 报表 dao
     */
    @Autowired
    private ReportDao reportDao;

    @Override
    public List<ReportVO> findReportInfo(String siteId, Date date, Integer type) {
        List<ReportVO> reportVOs = null;
        DateTime dateTime = new DateTime(date);
        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        // 日期处理（月最大天，周的开始和结束日期）
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (type) {
            case Constants.FIND_TYPE_DAY:
                reportVOs = reportDao.findDayReportInfo(siteId,
                        DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, date));
                break;
            case Constants.FIND_TYPE_MONTH:
                reportVOs = reportDao.findReportInfo(siteId, new DateTime(year,
                        month, 1, 0, 0).toDate(), new DateTime(year, month,
                        calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0)
                        .toDate());
                break;
            case Constants.FIND_TYPE_WEEK:
                int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
                calendar.add(Calendar.DATE, -day_of_week);
                Date startDate = calendar.getTime();
                calendar.add(Calendar.DATE, 6);
                Date endDate = calendar.getTime();
                // 开始 时间和结束 时间要处理成 年月日类型
                reportVOs = reportDao.findReportInfo(
                        siteId,
                        DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD,
                                DateTimeUtil.formatFull(startDate)),
                        DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD,
                                DateTimeUtil.formatFull(endDate)));
                break;
            case Constants.FIND_TYPE_YEAR:
                reportVOs = reportDao.findReportInfo(siteId, new DateTime(year, 1,
                        1, 0, 0).toDate(), new DateTime(year, 12, 31, 0, 0)
                        .toDate());
                break;
        }
        return reportVOs;
    }
}
