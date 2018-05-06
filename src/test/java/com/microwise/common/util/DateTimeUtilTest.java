package com.microwise.common.util;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author gaohui
 * @date 13-4-27 10:58
 */
public class DateTimeUtilTest {
    @Test
    public void testStartOfDay() {
        Date startOfDay = DateTimeUtil.startOfDay(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(startOfDay);

        Assert.assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, c.get(Calendar.MINUTE));
        Assert.assertEquals(0, c.get(Calendar.SECOND));
        Assert.assertEquals(0, c.get(Calendar.MILLISECOND));
    }

    @Test
    public void testEndOfDay() {
        Date endOfDay = DateTimeUtil.endOfDay(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(endOfDay);

        Assert.assertEquals(23, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(59, c.get(Calendar.MINUTE));
        Assert.assertEquals(59, c.get(Calendar.SECOND));
        Assert.assertEquals(999, c.get(Calendar.MILLISECOND));
    }

    @Test
    public void testStartOfMonth() {
        Date startOfMonth = DateTimeUtil.startOfMonth(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(startOfMonth);

        Assert.assertEquals(1, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, c.get(Calendar.MINUTE));
        Assert.assertEquals(0, c.get(Calendar.SECOND));
        Assert.assertEquals(0, c.get(Calendar.MILLISECOND));
    }

    @Test
    public void testEndOfMonth() {
        Date endOfMonth = DateTimeUtil.endOfMonth(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(endOfMonth);
        int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        Assert.assertEquals(daysInMonth, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(23, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(59, c.get(Calendar.MINUTE));
        Assert.assertEquals(59, c.get(Calendar.SECOND));
        Assert.assertEquals(999, c.get(Calendar.MILLISECOND));
    }

    @Test
    public void testStartOfYear() {
        Date startOfYear = DateTimeUtil.startOfYear(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(startOfYear);
        Assert.assertEquals(0, c.get(Calendar.MONTH));
        Assert.assertEquals(1, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, c.get(Calendar.MINUTE));
        Assert.assertEquals(0, c.get(Calendar.SECOND));
        Assert.assertEquals(0, c.get(Calendar.MILLISECOND));
    }

    @Test
    public void testEndOfYear() {
        Date date = new Date();

        Date endOfYear = DateTimeUtil.endOfYear(date);

        Calendar c = Calendar.getInstance();
        c.setTime(endOfYear);

        int monthsInYear = c.getActualMaximum(Calendar.MONTH);
        int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        Assert.assertEquals(monthsInYear, c.get(Calendar.MONTH));
        Assert.assertEquals(daysInMonth, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(23, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(59, c.get(Calendar.MINUTE));
        Assert.assertEquals(59, c.get(Calendar.SECOND));
        Assert.assertEquals(999, c.get(Calendar.MILLISECOND));
    }

}

