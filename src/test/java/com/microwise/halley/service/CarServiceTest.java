package com.microwise.halley.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.po.ConfigPO;
import com.microwise.halley.bean.po.DevicePO;
import com.microwise.halley.bean.po.UserPO;
import com.microwise.halley.bean.vo.CarVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆信息查询服务单元测试类
 *
 * @author wanggeng
 * @date 13-9-26 下午2:58
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarServiceTest extends CleanDBTest {

    @Autowired
    private CarService carService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/halley/CarServiceTest.xml");
    }

    @Test
    public void testFindCarsWithDeviceByExhibitionId() {
        List<CarVO> carVOList = carService.findCarsWithDeviceByExhibitionId(2);
        Assert.assertEquals(2, carVOList.size());
        CarVO carVO = carVOList.get(0);
        Assert.assertEquals("gerik", carVO.getDirector());
        Assert.assertEquals("12345678912", carVO.getDirectorPhone());
        Assert.assertEquals("陕A 95Q09", carVO.getPlateNumber());
    }

//    @Test
//    public void testSaveCarPO() {
//        CarPO carPO = new CarPO();
//        carPO.setId(4);
//        carPO.setDirector("gerik");
//        carPO.setDirectorPhone("12345678912");
//        carPO.setExhibitionId(1);
//        carPO.setPlateNumber("11111111");
//        carService.saveCarPO(carPO);
//        List<CarVO> carVOList = carService.findCarsWithDeviceByExhibitionId(1);
//        Assert.assertEquals(4, carVOList.size());
//        CarVO carVO = carVOList.get(3);
//        Assert.assertEquals("gerik", carVO.getDirector());
//        Assert.assertEquals("12345678912", carVO.getDirectorPhone());
//        Assert.assertEquals("11111111", carVO.getPlateNumber());
//    }

    /**
     * 为了减少测试数量saveCarPO和UpdateCarPO两个测试写在一起
     *
     * @author xu.yuexi
     * @date 2013-10-16
     */
    @Test
    public void testUpdateCarPOAndSaveCarPO() {
        CarPO carPO = new CarPO();
        carPO.setId(4);
        carPO.setDirector("gerik");
        carPO.setDirectorPhone("12345678912");
        carPO.setExhibitionId(3);
        carPO.setPlateNumber("2222");
        carService.saveCarPO(carPO);
        carPO.setPlateNumber("3333");
        carService.updateCarPO(carPO);
        List<CarVO> carVOList = carService.findCarsWithDeviceByExhibitionId(3);
        Assert.assertEquals(1, carVOList.size());
        CarVO carVO = carVOList.get(0);
        Assert.assertEquals("gerik", carVO.getDirector());
        Assert.assertEquals("12345678912", carVO.getDirectorPhone());
        Assert.assertEquals("3333", carVO.getPlateNumber());
    }

    /**
     * 因为设备和车辆是绑定在一起的，所以DeleteCarByCarId和DeleteDeviceByCarId两个测试写在一起
     *上面的测试添加了一辆车，所以这里最后一行改成3  s
     * @author xu.yuexi
     * @date 2013-10-16
     */
    @Test
    public void testDeleteCarByCarIdAndDeleteDeviceByCarId() {
        carService.deleteDeviceByCarId(1);
        List<CarVO> carVOList = carService.findCarsWithDeviceByExhibitionId(1);
        CarVO carVO = carVOList.get(0);
        List<DevicePO> devicePOList = carVO.getDevicePOList();
        Assert.assertEquals(0, devicePOList.size());
        carService.deleteCarByCarId(1);

        List<CarVO> carVOList1 = carService.findCarsWithDeviceByExhibitionId(1);
        Assert.assertEquals(2, carVOList1.size());
    }

    @Test
    public void testSaveDeviceList() {
        List<DevicePO> devicePOList = new ArrayList<DevicePO>();
        for (int i = 0; i < 3; i++) {
            DevicePO devicePO = new DevicePO();
            devicePO.setId(i + 7);
            devicePO.setDeviceId("aaa" + i);
            devicePO.setDeviceType(1);
            devicePO.setDvName("aaaaa" + i);
            devicePO.setCarId(4);
            devicePOList.add(devicePO);
        }
        carService.saveDeviceList(devicePOList);
        List<CarVO> carVOList = carService.findCarsWithDeviceByExhibitionId(2);
        CarVO carVO = carVOList.get(0);
        List<DevicePO> devicePOList1 = carVO.getDevicePOList();
        Assert.assertEquals(3, devicePOList1.size());
    }

    @Test
    public void testUpdateDeviceList() {
        List<DevicePO> devicePOList = new ArrayList<DevicePO>();
        for (int i = 0; i < 3; i++) {
            DevicePO devicePO = new DevicePO();
            devicePO.setDeviceId("aaa");
            devicePO.setDeviceType(1);
            devicePO.setCarId(5);
            devicePOList.add(devicePO);
        }
        carService.updateDeviceList(devicePOList, 5);
        List<CarVO> carVOList = carService.findCarsWithDeviceByExhibitionId(2);
        CarVO carVO = carVOList.get(1);
        Assert.assertEquals(3, carVO.getDevicePOList().size());
    }

    /**
     * 在验证saveUserPO的时候要用到FindUserPO，所以两个测试写在一起
     *
     * @author xu.yuexi
     * @date 2013-10-16
     */
    @Test
    public void testSaveUserPOAndFindUserPO() {
        List<UserPO> userPOList = carService.findUserPO(1);
        Assert.assertEquals(4, userPOList.size());
        UserPO userPO = new UserPO();
        userPO.setUserId(5);
        userPO.setExhibitionId(1);
        carService.saveUserPO(userPO);
        List<UserPO> userPOList1 = carService.findUserPO(1);
        Assert.assertEquals(5, userPOList1.size());
    }

    @Test
    public void testDeleteAllUser() {
        carService.deleteAllUser(1);
        List<UserPO> userPOList = carService.findUserPO(1);
        Assert.assertEquals(0, userPOList.size());
    }

    /**
     * 在验证SaveConfigPO的时候要用到dFindConfigByExhibitionId，所以两个测试写在一起
     *  SaveConfigPO的业务是保存的时候把原config覆盖掉，所以有了以下测试
     * @author xu.yuexi
     * @date 2013-10-16
     */
    @Test
    public void testSaveConfigPOAndFindConfigByExhibitionId() {
        List<ConfigPO> configPOList = carService.findConfigByExhibitionId(1);
        Assert.assertEquals(3, configPOList.size());
        Assert.assertEquals(10, configPOList.get(0).getAlarmRange());
        Assert.assertEquals(15, configPOList.get(1).getAlarmRange());
        Assert.assertEquals(20, configPOList.get(2).getAlarmRange());

        ConfigPO configPO = new ConfigPO();
        configPO.setExhibitionId(1);
        configPO.setAlarmRange(25);
        carService.saveConfigPO(configPO);

        List<ConfigPO> configPOList1 = carService.findConfigByExhibitionId(1);
        Assert.assertEquals(1, configPOList1.size());
        Assert.assertEquals(25, configPOList1.get(0).getAlarmRange());
    }
}
