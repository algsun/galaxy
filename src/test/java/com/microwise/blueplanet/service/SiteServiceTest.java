package com.microwise.blueplanet.service;

import java.util.ArrayList;
import java.util.List;


import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.common.sys.test.CleanDBTest;

/**
 * 站点测试
 *
 * @author zhangpeng
 * @date 2013-1-21
 * @check 2013-02-25 zhangpeng svn:1731
 * @check 2013-04-22 xubaoji svn:2568
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SiteServiceTest extends CleanDBTest {

    /**
     * 站点Dao
     */
    @Autowired
    private SiteService siteService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/blueplanet/SiteServiceTest.xml");
    }

    /**
     * 查询站点下的实时数据
     *
     * @author 许保吉
     * @date 2013-1-21
     */
    @Ignore
    public void testFindRealtimeData() {
        List<RealtimeDataVO> realtimeDataList = siteService
                .findRealtimeDataLocation("31010101");
        Assert.assertNotNull("查询站点实时数据出错", realtimeDataList);
        Assert.assertTrue("查询站点实时数据出错", realtimeDataList.size() > 0);
        Assert.assertTrue("查询站点实时数据出错", realtimeDataList.size() == 2);
    }

    /**
     * 查询站点下的实时数据 带有监测指标查询条件
     *
     * @author 许保吉
     * @date 2013-1-21
     */
    @Test
    public void testFindRealtimeData2() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(32);
        List<RealtimeDataVO> realtimeDataList = siteService.findDeviceRealTimeData(
                "31010101", list);
        Assert.assertTrue("查询实时数据出错", realtimeDataList.size() == 3);
    }

    /**
     * 查询站点下的所有设备所拥有的监测指标
     *
     * @author zhangpeng
     * @date 2013-1-18
     */
    @Ignore
    public void testFindSensorinfo() {
        List<SensorinfoVO> sensorinfos = siteService.findSensorinfo("31010101");
        Assert.assertEquals(4, sensorinfos.size());
    }

    /**
     * 根据id查询站点
     *
     * @author zhangpeng
     * @date 2013-1-18
     */
    @Test
    public void testFindSiteById() {
        SiteVO site = siteService.findSiteById("11010101");
        Assert.assertEquals("故宫博物院", site.getSiteName());
    }

}
