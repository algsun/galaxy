package com.microwise.uma.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.uma.bean.ZoneBean;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域 Service 测试
 *
 * @author li.jianfei
 * @date 2013-04-18
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZoneServiceTest extends CleanDBTest {

    /**
     * 区域 Service
     */
    @Autowired
    private ZoneService zoneService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/uma/ZoneServiceTest.xml");
    }

    /**
     * 查询有设备的区域列表
     */
    @Test
    public void testFindZonesHasDevice() {
        List<ZoneBean> zoneList = zoneService.findZonesHasDevice("31010101");
        Assert.assertEquals(1, zoneList.size());
    }

    /**
     * 查询设备树
     */
    @Test
    public void testFindZoneTree() {
        List<ZoneBean> zoneList = new ArrayList<ZoneBean>();
        zoneService.findZoneTree(zoneList,"31010101", null, 1, 1, null);
        Assert.assertEquals(0, zoneList.size());
    }

    /**
     * 查询区域下人员列表信息
     */
    @Test
    public void testFindPeoplesInZone() {
        List<ZoneBean> zoneList = zoneService.findPeopleInZone("31010101","001", 1);
        Assert.assertEquals(0, zoneList.size());

    }
}
