package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域Service测试类
 *
 * @author zhangpeng
 * @date 2013-1-21
 * @check 2013-02-25 zhangpeng svn:1731
 * @check 2013-04-22 xubaoji svn:2627
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZoneServiceTest extends CleanDBTest {

    /**
     * 站点Dao
     */
    @Autowired
    private ZoneService zoneService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/blueplanet/ZoneServiceTest.xml");
    }

    /**
     * 测试查询区域的子孙区域列表
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testFindChildrenIdList() {
        List<String> idList = zoneService.findChildrenIdList("001");
        Assert.assertEquals(3, idList.size());
        Assert.assertTrue(idList.contains("001"));
        Assert.assertTrue(idList.contains("002"));
        Assert.assertTrue(idList.contains("003"));
    }

    /**
     * 测试查询区域的直接子区域
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testFindZones() {
        List<ZoneVO> zone = zoneService.findZones("001");
        Assert.assertEquals(2, zone.size());
    }

    /**
     * 测试判断区域下是否拥有该名称设备
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testContainsName() {
        Assert.assertTrue(zoneService.containsName("31010101", "001", "测试区域2"));
        Assert.assertTrue(zoneService.containsName("31010101", null, "测试区域1"));
    }

    /**
     * 测试判断区域是否为空（是否拥有子区域或设备）
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testIsEmpty() {
        Assert.assertTrue(zoneService.isEmpty("002"));
    }

    /**
     * 测试判断区域下是否拥有该名称设备
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testIsNameAvailable() {
        Assert.assertTrue(zoneService.isNameAvailable("31010101", "001", "002",
                "测试区域2"));
        Assert.assertFalse(zoneService.isNameAvailable("31010101", "001",
                "003", "测试区域2"));
    }

    /**
     * 测试查询区域数据版本号
     *
     * @author zhangpeng
     * @date 2013-1-21
     */
    @Ignore
    public void testFindDataVersion() {
        Assert.assertEquals(17, zoneService.findDataVersion("002"));
    }

    /**
     * 测试根据区域id查询区域对象
     *
     * @author zhangpeng
     * @date 2013-1-21
     */
    @Test
    public void testFindZoneById() {
        ZoneVO zone = zoneService.findZoneById("001");
        Assert.assertEquals("测试区域1", zone.getZoneName());
    }

    /**
     * 测试查询区域的直接子区域
     *
     * @author zhangpeng
     * @date 2013-1-21
     */
    @Test
    public void testFindZoneList() {
        List<ZoneVO> zoneList = zoneService.findZoneList("31010101", null);
        Assert.assertEquals(1, zoneList.size());
        Assert.assertEquals("测试区域1", zoneList.get(0).getZoneName());
        List<ZoneVO> zoneList2 = zoneService.findZoneList("31010101", "001");
        Assert.assertEquals(2, zoneList2.size());
    }

    /**
     * 测试查询区域的直接子区域
     *
     * @author zhangpeng
     * @date 2013-1-21
     */
    @Test
    public void testSaveZone() {
        ZoneVO zone = new ZoneVO();
        zone.setSiteId("11010101");
        zone.setParentId(null);
        zone.setZoneName("测试区域999");
        zone.setPlanImage(null);
        String zoneId = zoneService.saveZone(zone);
        ZoneVO rZone = zoneService.findZoneById(zoneId);
        Assert.assertEquals(zone.getZoneName(), rZone.getZoneName());
    }

    /**
     * 测试判断区域下是否拥有该名称设备
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testDeleteZone() {
        zoneService.deleteZone("003");
        Assert.assertNull(zoneService.findZoneById("003"));
    }

    /**
     * 测试判断区域下是否拥有该名称设备
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testUpdateZone() {
        zoneService.updateZone("001", "大傻", "新路径",1);
        ZoneVO zone = zoneService.findZoneById("001");
        Assert.assertEquals("大傻", zone.getZoneName());
        Assert.assertEquals("新路径", zone.getPlanImage());
        Assert.assertEquals(0, zoneService.findDataVersion("001"));
    }

    /**
     * 测试修改区域父区域
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testChangeParent() {
        zoneService.changeParent("002", null);
        ZoneVO zoneVO = zoneService.findZoneById("002");
        Assert.assertNull("测试修改区域父区域失败", zoneVO.getParentId());
    }

    /**
     * 根据站点查询区域
     *
     * @author liuzhu
     * @date 2014-6-24
     */
    @Test
    public void testFindZoneBySiteId() {
        List<ZoneVO> zoneList = zoneService.findZonesBySiteId("31010101");
        Assert.assertNotNull(zoneList);
        Assert.assertEquals(3, zoneList.size());
    }

}
