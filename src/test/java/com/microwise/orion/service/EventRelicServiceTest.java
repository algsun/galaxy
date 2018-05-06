package com.microwise.orion.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.EventRelic;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created with xiedeng
 * User: xiedeng
 * Date: 13-6-1
 * Time: 下午12:13
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventRelicServiceTest extends CleanDBTest {

    @Autowired
    private EventRelicService eventRelicService;

    @Before
    public void setbefor() throws Exception {
        xmlInsert2("common/dbxml/orion/OutEventServiceTest.xml");
    }
    /**
     * 测试根据编号修改文物事件的状态
     */
    @Test
    public void testUpdateStateById(){
        eventRelicService.updateStateById(1,1);
        EventRelic  eventRelic = eventRelicService.findById(1);
        Assert.assertEquals(1,eventRelic.getState());
    }

    /**
     * 测试根据编号修改日期
     */
    @Test
    public void testUpdateOutDateById(){
        Date actDate = new Date();
        eventRelicService.updateOutDateById(1,actDate);
        EventRelic  eventRelic = eventRelicService.findById(1);
        Assert.assertNotNull(eventRelic.getOutDate());
    }

}
