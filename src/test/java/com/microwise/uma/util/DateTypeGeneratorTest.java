package com.microwise.uma.util;

import com.microwise.common.sys.Constants;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期枚举工具类
 *
 * @author gaohui
 * @date 13-4-28 11:22
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DateTypeGeneratorTest {
    @Test
    public void testStart() throws Exception {
        Date startTime = DateTypeGenerator.start(Constants.Uma.DATE_TYPE_YEAR, new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        Assert.assertEquals(0, c.get(Calendar.MONTH));
        Assert.assertEquals(1, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, c.get(Calendar.MINUTE));
        Assert.assertEquals(0, c.get(Calendar.SECOND));
        Assert.assertEquals(0, c.get(Calendar.MILLISECOND));
    }

    @Test
    public void testEnd() throws Exception {
        Date endTime = DateTypeGenerator.end(Constants.Uma.DATE_TYPE_YEAR, new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(endTime);

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
