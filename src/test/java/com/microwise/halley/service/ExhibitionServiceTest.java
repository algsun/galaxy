package com.microwise.halley.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.halley.bean.vo.ExhibitionVO;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 展览信息查询服务单元测试类
 * User: xu.yuexi
 * Date: 13-10-16
 * Time: 下午3:00
 */
public class ExhibitionServiceTest extends CleanDBTest {

    @Autowired
    private ExhibitionService exhibitionService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/halley/ExhibitionServiceTest.xml");
    }

    @Test
    public void testFindExhibitionList() {
        Date start = new DateTime().withDate(2013, 1, 1).toDate();
        List<ExhibitionVO> exhibitionList = exhibitionService.findExhibitionList("1", 1, 1, 20, start, DateTime.now().toDate());
        Assert.assertEquals(1, exhibitionList.size());
        ExhibitionVO exhibitionVO = exhibitionList.get(0);
        Assert.assertEquals(1, exhibitionVO.getExhibitionId());
        Assert.assertEquals("mw", exhibitionVO.getApplicant());
        Assert.assertEquals("Mon Jan 21 14:42:57 CST 2013", exhibitionVO.getBeginTime().toString());
        Assert.assertEquals("Thu Mar 21 14:42:57 CST 2013", exhibitionVO.getEndTime().toString());
        Assert.assertEquals("Sun Jan 20 00:00:00 CST 2013", exhibitionVO.getEstimatedBeginTime().toString());
        Assert.assertEquals("Fri Mar 22 00:00:00 CST 2013", exhibitionVO.getEstimatedEndTime().toString());
        Assert.assertEquals("1", exhibitionVO.getOutEventId());
        Assert.assertEquals("wz", exhibitionVO.getUseFor());
    }

    @Test
    public void testFinishExhibition() {
        int operatorId = 2;
        exhibitionService.finishExhibition(1, operatorId);
        Date beginTime = DateTime.now().withDate(2013, 1, 1).withMillisOfSecond(0).toDate();
        Date endTime = DateTime.now().withDate(2013, 12, 31).withMillisOfSecond(0).toDate();
        List<ExhibitionVO> exhibitionList = exhibitionService.findExhibitionList("1", 1, 1, 20, beginTime, endTime);
        Assert.assertEquals(1, exhibitionList.size());
        ExhibitionVO exhibitionVO = exhibitionList.get(0);
        Assert.assertEquals(1, exhibitionVO.getExhibitionId());
        Assert.assertEquals("mw", exhibitionVO.getApplicant());
        Assert.assertEquals(DateTime.now().withDate(2013, 01, 21).withTime(14, 42, 57, 0).toDate(), exhibitionVO.getBeginTime());
        Calendar c1 = Calendar.getInstance();
        c1.setTime(exhibitionVO.getEndTime());
        Calendar c2 = Calendar.getInstance();
        c2.setTime(DateTime.now().withSecondOfMinute(0).toDate());
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);
        Assert.assertEquals(c2 ,c1);  //时间就改为当前时间了
        Assert.assertEquals("Sun Jan 20 00:00:00 CST 2013", exhibitionVO.getEstimatedBeginTime().toString());
        Assert.assertEquals("Fri Mar 22 00:00:00 CST 2013", exhibitionVO.getEstimatedEndTime().toString());
        Assert.assertEquals("1", exhibitionVO.getOutEventId());
        Assert.assertEquals("wz", exhibitionVO.getUseFor());
    }
}
