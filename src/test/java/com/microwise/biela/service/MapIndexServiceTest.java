package com.microwise.biela.service;

import com.microwise.biela.bean.po.LogicGroupPO;
import com.microwise.biela.bean.po.SensorInfoPO;
import com.microwise.biela.bean.vo.CustomizeVO;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 站点地图测试用例
 *
 * @author liuzhu
 * @date 2014-1-15
 * @check @wang.geng 2014-1-17 #7707
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MapIndexServiceTest extends CleanDBTest {

    @Autowired
    private MapIndexService mapIndexService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/biela/MapIndexService.xml");
    }

    /**
     * 查询站点下监测指标
     */
    @Test
    public void testFindSensorinfo() {
        List<SensorInfoPO> sensorinfos = mapIndexService.findSensorInfo("31010101");
        Assert.assertEquals(4, sensorinfos.size());
    }

    /**
     * 根据站点id获取站点组
     */
    @Test
    public void testFindLogicGroupBySiteId() {
        LogicGroupPO logicGroupPO = mapIndexService.findLogicGroupBySiteId("11010101");
        Assert.assertEquals("测试数据2", logicGroupPO.getLogicGroupName());
    }

    /**
     * 获取定制信息
     */
    @Test
    public void testFindCustomizeVOList() {
        List<CustomizeVO> customizeVOList = mapIndexService.findCustomizeVOList("11010101");
        Assert.assertEquals(1,customizeVOList.size());

    }

    /**
     * 添加定制
     */
    @Test
    public void saveCustomize() {
        mapIndexService.saveCustomize("11010101", "1101010100045", 32, "测试备注");
        List<CustomizeVO> customizeVOList = mapIndexService.findCustomizeVOList("11010101");
        Assert.assertEquals(2,customizeVOList.size());
    }

    /**
     * 删除定制
     */
    @Test
    public void deleteCustomize(){
        mapIndexService.saveCustomize("11010101", "1101010100023", 32, "测试备注1");
        mapIndexService.deleteCustomizeById(1);
        List<CustomizeVO> customizeVOList = mapIndexService.findCustomizeVOList("11010101");
        Assert.assertEquals(1,customizeVOList.size());
    }


}
