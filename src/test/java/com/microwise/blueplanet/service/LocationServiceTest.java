package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.LocationPO;
import com.microwise.blueplanet.bean.vo.LocationHistoryVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 位置点测试类
 *
 * @author liuzhu
 * @date 2014-6-24
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocationServiceTest extends CleanDBTest {

    @Autowired
    private LocationService locationService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/blueplanet/LocationServiceTest.xml");
    }

    /**
     * 根据站点查询所有位置点
     */
    @Test
    public void testFindLocationBySiteId() {
        List<LocationVO> locationVOs = locationService.findLocationsBySiteIdAndLocationName("31010101",null);
        Assert.assertNotNull(locationVOs);
        Assert.assertEquals(2, locationVOs.size());
    }

    /**
     * 根据站点分页查询所有位置点
     */
    @Test
    public void findLocationList() {
        List<LocationVO> locationVOs = locationService.findLocationsBySiteId("31010101", 1, 1);
        Assert.assertNotNull(locationVOs);
        Assert.assertEquals(1, locationVOs.size());
    }

    /**
     * 根据站点查询位置点数目
     */
    @Test
    public void findLocationListCount() {
        int i = locationService.findLocationListCount("31010101");
        Assert.assertNotNull(i);
        Assert.assertEquals(2, i);
    }

    /**
     * 分页根据位置点名称和区域Id查询位置点，全部为空查询全部
     */
    @Test
    public void findLocationByNameAndZone() {
        List<LocationVO> locationVOs = locationService.findLocationByNameAndZone("测试", "001", "31010101", 1, 10);
        Assert.assertNotNull(locationVOs);
        Assert.assertEquals(2, locationVOs.size());
    }

    /**
     * 根据位置点名称和区域Id查询位置点的数目，全部为空查询全部
     */
    @Test
    public void findLocationByNameAndZoneCount() {
        int i = locationService.findLocationByNameAndZoneCount("测试", "001", "31010101");
        Assert.assertNotNull(i);
        Assert.assertEquals(2, i);
    }

    /**
     * 添加删除位置点
     */
    @Test
    public void addLocationDeleteLocation() {
        LocationPO locationPO = new LocationPO();
        locationPO.setId("3101010170002");
        locationPO.setSiteId("31010101");
        locationPO.setLocationName("aaa");
        locationPO.setCreateTime(new Date());
        try {
            locationService.addLocation(locationPO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = locationService.findLocationListCount("31010101");
        Assert.assertNotNull(i);
        Assert.assertEquals(3, i);
        try {
            locationService.deleteLocation("3101010170002");
            locationService.dropTable("3101010170002");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int j = locationService.findLocationListCount("31010101");
        Assert.assertNotNull(j);
        Assert.assertEquals(2, j);
    }

    /**
     * 获得位置点id
     */
    @Test
    public void getNewLocationId() {
        String locationId = locationService.getNewLocationId("31010101");
        Assert.assertEquals("3101010170002", locationId);
    }

    /**
     * 通过位置点id获得设备最近N 包数据
     */
    @Ignore
    public void testFindRealTimeData() {
        List<RecentDataVO> recentDataList = locationService.findRecentDataList("3101010170012", 12,null);
    }

    /**
     * 根据位置点id查找位置点绑定历史
     */
    @Test
    public void findLocationHistoryLitByLocationId() {
        List<LocationHistoryVO> locationHistoryVOList = locationService.findLocationHistoryList("3101010170001");
        Assert.assertNotNull(locationHistoryVOList);
        Assert.assertEquals(2, locationHistoryVOList.size());
    }

}
