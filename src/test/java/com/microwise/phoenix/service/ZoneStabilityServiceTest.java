package com.microwise.phoenix.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.vo.ZoneStability;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 区域稳定性Service测试
 *
 * @author zhangpeng
 * @date 13-8-7
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZoneStabilityServiceTest extends CleanDBTest {

    /** 区域稳定性统计Service */
    @Autowired
    private ZoneStabilityService zoneStabilityService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/phoenix/ZoneStabilityServiceTest.xml");
    }

    /**
     * 区域稳定性对比
     */
    @Test
    @Ignore
    public void testFindZoneStability() {
        List<ZoneStability> zoneStabilitiesList = zoneStabilityService.findZoneStability("31010101", 32, new Date(), 1);
        Assert.assertNotNull(zoneStabilitiesList);
        Assert.assertEquals(2, zoneStabilitiesList);
    }
}
