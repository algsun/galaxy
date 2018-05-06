package com.microwise.phoenix.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.vo.RelicFrequency;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 出库统计 service 测试
 *
 * @author xu.baoji
 * @date 2013-7-4
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OutEventStatsService2Test extends CleanDBTest {

    @Autowired
    private OutEventStatsService outEventStatService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/phoenix/OutEventStatsServiceTest.xml");
    }

    @Test
    public void testFindOldestDateOfOutedRelic() {
        Date date = outEventStatService.findOldestOfOutedRelic("31010101");
        Assert.assertNotNull(date);
        DateTime date2 = DateTime.now().withMillis(date.getTime());
        Assert.assertEquals(2013, date2.getYear());
        Assert.assertEquals(5, date2.getMonthOfYear());
        Assert.assertEquals(1, date2.getDayOfMonth());
    }

    @Test
    public void testFindRelicOutRankingByYear() {
        Date date = new DateTime().withYear(2013).toDate();
        List<RelicFrequency> relicFrequencies = outEventStatService.findRelicOutRankingByYear("31010101", date, 10);
        Assert.assertEquals(2, relicFrequencies.size());

        for (RelicFrequency relicFrequency : relicFrequencies) {
            Assert.assertEquals(1, relicFrequency.getCount());
        }
    }

    @Test
    public void testFindRelicOutRanking() {
        List<RelicFrequency> relicFrequencies = outEventStatService.findRelicOutRanking("31010101", 10);
        Assert.assertEquals(2, relicFrequencies.size());

        for (RelicFrequency relicFrequency : relicFrequencies) {
            Assert.assertEquals(1, relicFrequency.getCount());
        }
    }
}
