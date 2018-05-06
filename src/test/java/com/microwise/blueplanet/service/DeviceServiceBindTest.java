package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 设备测试
 *
 * @author xubaoji
 * @date 2013-1-24
 * @check 2013-02-25 xubaoji svn:1750
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceServiceBindTest extends CleanDBTest {

    /**
     * 设备service
     */
    @Autowired
    private DeviceService deviceService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/blueplanet/DeviceServiceBindTest.xml");
    }

    /**
     * 根据id查询设备拥有的监测指标集合
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testUpdateDevice() {
        DeviceVO device = new DeviceVO();
        String id = "3101010100260";
        String img = "新图片";
        Integer nodeType = 3;
        device.setNodeType(nodeType);
        device.setNodeId(id);
        device.setDeviceImage(img);
        deviceService.updateDevice(device);
        DeviceVO dev = deviceService.findDeviceById(id);
        Assert.assertEquals(img, dev.getDeviceImage());
    }
}
