package com.microwise.uma.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.uma.bean.DeviceBean;

/**
 * 设备 Service 测试
 *
 * @author li.jianfei
 * @date 2013-04-18
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceServiceTest extends CleanDBTest {

    /**
     * 设备 Service
     */
    @Autowired
    private DeviceService deviceService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/uma/DeviceServiceTest.xml");
    }

    /**
     * 分页查询设备列表
     *
     * @author li.jianfei
     * @date 2013-04-18
     */
    @Test
    public void testFindDeviceList() {
        // 类型为激发器、无区域过滤条件
        List<DeviceBean> deviceList = deviceService.findDeviceList(2, "",true, 1, 15);
        Assert.assertEquals(2, deviceList.size());

        // 类型为激发器、有区域过滤条件
        deviceList = deviceService.findDeviceList(2, "001",true, 1, 15);
        Assert.assertEquals(1, deviceList.size());

        // 类型为读卡器、无区域过滤条件
        deviceList = deviceService.findDeviceList(1, "", true,1, 15);
        Assert.assertEquals(1, deviceList.size());

    }

    /**
     * 查询设备记录条数
     *
     * @author li.jianfei
     * @date 2013-04-18
     */
    @Test
    public void testFindDeviceListCount() {
        int count;
        // 类型为激发器，无区域过滤条件
        count = deviceService.findDeviceListCount(2, "" , false);
        Assert.assertEquals(2, count);

        // 类型为激发器，有区域过滤条件
        count = deviceService.findDeviceListCount(2, "001", false);
        Assert.assertEquals(1, count);

        // 类型为读卡器，无区域过滤条件
        count = deviceService.findDeviceListCount(1, "", false);
        Assert.assertEquals(1, count);
    }

    /**
     * 根据ID查询设备信息
     *
     * @author li.jianfei
     * @date 2013-04-18
     */
    @Test
    public void testFindDeviceById() {
        DeviceBean device = deviceService.findDeviceById(1);
        Assert.assertEquals("读卡器", device.getName());
    }

    /**
     * 更新读卡器信息
     *
     * @author li.jianfei
     * @date 2013-04-18
     */
    @Test
    public void testUpdateDevice() {
        String deviceName;
        DeviceBean device;
        // 修改读卡器名称，区域信息为空
        deviceName = "读卡器-update";
        deviceService.updateDevice(3, deviceName, "");
        device = deviceService.findDeviceById(3);
        Assert.assertEquals(deviceName, device.getName());

        //修改激发器名称，并修改区域信息
        deviceName = "激发器-update";
        String zoneId = "001";
        deviceService.updateDevice(2, deviceName, zoneId);
        device = deviceService.findDeviceById(2);
        Assert.assertEquals(deviceName, device.getName());
        Assert.assertEquals(zoneId, device.getZoneId());
        Assert.assertEquals("测试区域1", device.getZoneName());

    }

    /**
     * 停用启用设备
     *
     * @author li.jianfei
     * @date 2013-04-18
     */
    @Test
    public void testSetEnable() {
        deviceService.setEnable(1, false);
        DeviceBean device = deviceService.findDeviceById(1);
        Assert.assertEquals(false, device.isEnable());
    }

    /**
     * 查询所有激发器
     *
     * @author li.jianfei
     * @date 2013-04-18
     */
    @Test
    public void testFindAllExciter() {
        List<DeviceBean> exciterList = deviceService.findAllExciter();
        Assert.assertEquals(2, exciterList.size());
    }
    
    /**
     * 查询所有未绑定的电子卡
     *
     * @author 许保吉
     * @date   2013-4-22 
     * 
     */
    @Test
    public  void  testFindAllCard(){
    	List<DeviceBean> cardList=deviceService.findAllCard();
    	Assert.assertEquals(1, cardList.size());
    }

    /**
     * 设备名称是否已被使用
     *
     * @author li.jianfei
     * @date 2013-04-18
     */
    @Test
    public void testIsNameUsed() {
        boolean nameUsed = deviceService.isNameUsed(2, "读卡器");
        Assert.assertEquals(true, nameUsed);
    }
    
    /**
     * 是否可以将激发器禁用测试
     *
     * @author 许保吉
     * @date   2013-4-28 
     * 
     * @return
     */
    @Test
    public  void testIsCanDisableExciter(){
    	boolean  is = deviceService.isCanDisableExciter(2);
    	Assert.assertTrue(is);
    }

}
