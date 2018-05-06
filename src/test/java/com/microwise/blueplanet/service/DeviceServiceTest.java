package com.microwise.blueplanet.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.util.DateTimeUtil;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.HistoryDataVO;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.common.sys.test.CleanDBTest;

/**
 * 设备测试
 *
 * @author xubaoji
 * @date 2013-1-24
 * @check 2013-04-22 xubaoji svn:2564
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceServiceTest extends CleanDBTest {

    /**
     * 设备service
     */
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private LocationService locationService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/blueplanet/DeviceServiceTest.xml");
    }

    /**
     * 根据主模块id查询从模块列表
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testFindSlaveModule() {
        Assert.assertEquals(2, deviceService.findSlaveModule("3101010100260")
                .size());
    }

    /**
     * 查询绑定设备列表
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testFindBindList() {
        List<DeviceVO> list = deviceService.findDeviceList("31010101", "0",
                1, 1, 10);
        Assert.assertEquals(3, list.size());
    }

    /**
     * 查询绑定设备数量
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testFindBindListCount() {
        Assert.assertEquals(3,
                deviceService.findDeviceListCount("31010101", "0", 1));
    }

    /**
     * 根据id查询设备详情
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testFindDeviceById() {
        DeviceVO device = deviceService.findDeviceById("3101010100256");
        Assert.assertNotNull(device);
        Assert.assertEquals("3101010100256", device.getNodeId());
    }

    /**
     * 查询设备实时数据
     *
     * @return
     * @author 许保吉
     * @date 2013-1-24
     */
    @Test
    public void testFindRealtimeData1() {
        System.out.println(deviceService);
        RealtimeDataVO realtimeDataVO = deviceService
                .findRealtimeData("3101010110251");
        Assert.assertNotNull("查询设备实时数据出错！", realtimeDataVO);
        Assert.assertTrue("查询设备监测指标实时数据出错！", realtimeDataVO.getSensorinfoMap()
                .size() == 10);
    }

    /**
     * 分页查询设备历史数据
     *
     * @return
     * @author 许保吉
     * @date 2013-1-24
     */
    @Test
    @Ignore
    public void testFindHistoryData() {
        try {
            List<HistoryDataVO> historyDataList = deviceService
                    .findHistoryData("0000000000256", DateTimeUtil
                            .parse(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,
                                    "2013-01-22 17:00:43"), new Date(), 1, 5);
            Assert.assertNotNull("分页查询设备历史数据出错", historyDataList);
            Assert.assertTrue("查询设备历史数据出错", historyDataList.size() == 5);
            Assert.assertTrue("查询设备监测指标历史数据出错！", historyDataList.get(0)
                    .getSensorinfoMap().size() > 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页查询设备历史数据
     *
     * @return
     * @author 许保吉
     * @date 2013-1-24
     */
    @Test
    @Ignore
    public void testFindHistoryDataCount() {
        try {
            int count = deviceService.findHistoryDataCount("0000000000256",
                    DateTimeUtil.parse(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,
                            "2013-01-22 17:00:43"), new Date());
            Assert.assertTrue("查询历史数据数量出错！", count == 9);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id查询设备拥有的激活的监测指标集合
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testFindSensorinfo() {
        List<SensorinfoVO> sensorinfoList = deviceService
                .findSensorinfo("3101010100256","zh-CN");
        Assert.assertEquals(10, sensorinfoList.size());
    }

    /**
     * 根据id查询设备拥有的激活的监测指标集合
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testFindSensorPhysicalid() {
        List<Integer> sensorPhysicalidList = deviceService
                .findSensorPhysicalid("3101010100256", 1);
        Assert.assertEquals(10, sensorPhysicalidList.size());
    }

    /**
     * 根据id查询设备拥有的所有监测指标集合
     *
     * @author zhangpeng
     * @date 2013-1-24
     */
    @Test
    public void testFindAllSensorinfo() {
        List<SensorinfoVO> sensorinfoList = deviceService
                .findAllSensorinfo("3101010100256","zh-CN");
        Assert.assertEquals(10, sensorinfoList.size());
    }

    /**
     * 修改工作周期
     *
     * @author liuzhu
     * @date 2013-10-22
     */
    @Test
    public void testUpdateDeviceCycle() {
        DeviceVO deviceVO = new DeviceVO();
        deviceVO.setNodeId("3101010100258");
        deviceVO.setInterval(700);
        deviceService.updateDeviceInterval(deviceVO);
        DeviceVO deviceVO1 = deviceService.findDeviceById("3101010100258");
        Assert.assertEquals(700, deviceVO1.getInterval().intValue());
    }

    /**
     * 查询站点下设备集合
     *
     * @author liuzhu
     * @date 2014-6-24
     */
    @Test
    public void testFindDevicesBySiteId() {
        List<DeviceVO> deviceVOList = deviceService.findDevicesBySiteId("31010101");
        Assert.assertNotNull(deviceVOList);
        Assert.assertEquals(7, deviceVOList.size());
    }
}
