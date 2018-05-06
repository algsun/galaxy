package com.microwise.uma.util;

import com.microwise.common.sys.Constants;
import com.microwise.common.util.DateTimeUtil;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * 日期类型查询时间转化工具
 *
 * @author gaohui
 * @date 13-4-27 17:34
 */
public class DateTypeGenerator {
    /**
     * 符合此类型的开始时间
     *
     * @param dateType
     * @param date
     * @return
     */
    public static Date start(int dateType, Date date) {
        Date startTime = null;
        if (dateType == Constants.Uma.DATE_TYPE_YEAR) {
            startTime = DateTimeUtil.startOfYear(date);
        } else if (dateType == Constants.Uma.DATE_TYPE_MONTH) {
            startTime = DateTimeUtil.startOfMonth(date);
        } else if (dateType == Constants.Uma.DATE_TYPE_DAY) {
            startTime = DateTimeUtil.startOfDay(date);
        } else {
            throw new IllegalArgumentException("日期类型不正确: " + dateType);
        }

        return startTime;
    }

    /**
     * 符合此类型的结束时间
     *
     * @param dateType
     * @param date
     * @return
     */
    public static Date end(int dateType, Date date) {
        Date endTime = null;
        if (dateType == Constants.Uma.DATE_TYPE_YEAR) {
            endTime = DateTimeUtil.endOfYear(date);
        } else if (dateType == Constants.Uma.DATE_TYPE_MONTH) {
            endTime = DateTimeUtil.endOfMonth(date);
        } else if (dateType == Constants.Uma.DATE_TYPE_DAY) {
            endTime = DateTimeUtil.endOfDay(date);
        } else {
            throw new IllegalArgumentException("日期类型不正确: " + dateType);
        }

        return endTime;
    }

    /**
     * 年开始时间
     *
     * @param year
     * @return
     * @author xu.baoji
     * @date 2013-7-25
     */
    public static Date start(int year) {
        Date date = new DateTime(year, 1, 1, 1, 0).toDate();
        return start(Constants.Uma.DATE_TYPE_YEAR, date);
    }

    /**
     * 年结束时间
     *
     * @param year
     * @return
     * @author xu.baoji
     * @date 2013-7-25
     */
    public static Date end(int year) {
        Date date = new DateTime(year, 1, 1, 1, 0).toDate();
        return end(Constants.Uma.DATE_TYPE_YEAR, date);
    }

    /**
     * 当前时间向前推一个月(30天)的时间
     *
     * @return Date
     * @author wang.geng
     * @date 2014-4-17
     */
    public static Date monthStart() {
        //一天毫秒数
        long dayTimeMillis = 86400000;
        return new Date(System.currentTimeMillis() - dayTimeMillis * 30);
    }
}
