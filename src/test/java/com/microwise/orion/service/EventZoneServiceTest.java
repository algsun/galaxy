package com.microwise.orion.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.EventZone;

/**
 * 出库文物分单确认 service 测试
 * 
 * @author xubaoji
 * @date 2013-6-19
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventZoneServiceTest extends CleanDBTest{
	
	@Autowired
	private EventZoneService eventZoneService ;
	
    @Before
    public void setbefor() throws Exception {
        xmlInsert2("common/dbxml/orion/EventZoneServiceTest.xml");
    }
	
	@Test
	public void testFindEventZones(){
		List<EventZone> eventZones =eventZoneService.findEventZones("4101010113530205410");
		for (EventZone eventZone : eventZones) {
			System.out.println(eventZone.getUsers());
		}
		Assert.assertNotNull(eventZones);
	}
	
	@Test
	public void testUpdateEventZone(){
		eventZoneService.updateEventZoneUserAndState("4101010113530205410", 2, 1,"003");
	}

}
