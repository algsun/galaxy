package com.microwise.orion.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blackhole.bean.User;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.EventRelic;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.bean.OutEventAttachment;

/**
 * Created by xiedeng
 * User: xiedeng
 * Date: 13-5-29
 * Time: 下午6:03
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OutEventServiceTest extends CleanDBTest {

    @Autowired
    private OutEventService outEventService;

    @Before
    public void setBefore() throws Exception {
        xmlInsert2("common/dbxml/orion/OutEventServiceTest.xml");
    }

    /**
     * 保存文物事件
     */
    @Test
    public void testSaveOutEventInfo() {
        OutEvent outEvent = new OutEvent();
        outEvent.setSiteId("11010119");
        outEvent.setUseFor("ck");
        outEvent.setBeginDate(new Date());
        outEvent.setEndDate(new Date());
        outEvent.setApplicant("Michael");
        outEvent.setOutBound(true);
        outEvent.setState(OutEvent.STATE_APPLYING);
        User user = new User();
        user.setId(33);
        outEvent.setUser(user);
        List<Integer> relicIds = new ArrayList<Integer>();
        relicIds.add(1);
        relicIds.add(2);
        outEventService.saveOutEventInfo(outEvent, relicIds);
        List<OutEvent> outEvents = outEventService.findOutEventsBySiteId("11010119", 1, 1);
        OutEvent outEvent1 = outEvents.get(0);
        Assert.assertEquals("Michael", outEvent1.getApplicant());
        Assert.assertEquals("ck", outEvent1.getUseFor());
        Assert.assertEquals("11010119", outEvent1.getSiteId());
        Assert.assertEquals(33, outEvent1.getUser().getId());
    }

    @Test
    public void testFindById() {
        OutEvent outEvent = outEventService.findById("4101010113530205410");
        Assert.assertEquals("Jackson", outEvent.getApplicant());
        Assert.assertEquals("ck", outEvent.getUseFor());
        Assert.assertEquals(2, outEvent.getEventType());
        Assert.assertEquals(false, outEvent.isOutBound());
        Assert.assertEquals("31010101", outEvent.getSiteId());
        Assert.assertEquals("2013-05-20", DateTimeUtil.format("yyyy-MM-dd", outEvent.getBeginDate()));
        Assert.assertEquals("2013-05-30", DateTimeUtil.format("yyyy-MM-dd", outEvent.getEndDate()));
        Assert.assertEquals("0", outEvent.getRemark());
    }

    @Test
    public void testUpdateOutEvent() {
        List<Integer> relicIds = new ArrayList<Integer>();
        relicIds.add(1);
        OutEvent outEvent = outEventService.findById("4101010113530205410");
        outEvent.setApplicant("Kobe Bryant");
        outEvent.setEventRelics(null);
        outEventService.updateOutEvent(outEvent, relicIds);
        OutEvent outEvent1 = outEventService.findById("4101010113530205410");
        int relictId = 0;
        for (EventRelic e : outEvent1.getEventRelics()) {
            relictId = e.getRelic().getId();
        }
        Assert.assertEquals("Kobe Bryant", outEvent1.getApplicant());
        Assert.assertEquals(1, outEvent1.getEventRelics().size());
        Assert.assertEquals(1, relictId);
    }

    @Test
    public void testUpdateRelicState() {
        outEventService.updateRelicState("4101010113530205410", 2);
        OutEvent outEvent = outEventService.findById("4101010113530205410");
        for (EventRelic eventRelic : outEvent.getEventRelics()) {
            Assert.assertEquals(2, eventRelic.getRelic().getState());
        }
        Assert.assertEquals(2,outEvent.getEventRelics().size());
    }
    
    @Test
    public void testAddOutEventAttachment(){
    	outEventService.addOutEventAttachment("4101010113530205410", 100, "cccc", new Date());
    	List<OutEventAttachment> outEventAttachments = outEventService.findOutEventAttachmentByEventId("4101010113530205410");
    	Assert.assertTrue(outEventAttachments.size() == 2);
        outEventService.deleteOutEventAttachment(1);
        outEventAttachments = outEventService.findOutEventAttachmentByEventId("4101010113530205410");
        Assert.assertTrue(outEventAttachments.size() == 1);
    	
    }

}
