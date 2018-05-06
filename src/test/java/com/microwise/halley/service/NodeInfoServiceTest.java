package com.microwise.halley.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.halley.bean.po.DevicePO;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 哈雷子系统设备节点查询实现测试类
 * User: xu.yuexi
 * Date: 13-10-16
 * Time: 下午4:22
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NodeInfoServiceTest extends CleanDBTest {

    @Autowired
    private NodeInfoService nodeInfoService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/halley/NodeInfoServiceTest.xml");
    }

    @Test
    public void testFindDeviceByDeviceType(){
        List<DevicePO> devices1 = nodeInfoService.findDeviceByDeviceType(1,1);
       // List<DevicePO> devices2 = nodeInfoService.findDeviceByDeviceType(2,1);
        Assert.assertEquals(2, devices1.size());
     //   Assert.assertEquals(3, devices2.size());
    }
}
