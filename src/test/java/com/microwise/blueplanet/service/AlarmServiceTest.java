package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.MeasureVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.common.sys.test.CleanDBTest;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 监测预警测试用例
 *
 * @author liuzhu
 * @date 14-3-17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlarmServiceTest extends CleanDBTest {

    /**
     * 监测预警service
     */
    @Autowired
    private AlarmService alarmService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/blueplanet/AlarmServiceTest.xml");
    }

    /**
     * 根据站点查询措施
     */
    @Test
    public void findMeasureList() {
        List<MeasureVO> measureVOs = alarmService.findMeasureList("31010101");
        Assert.assertEquals(3, measureVOs.size());
    }

    /**
     * 根据措施id查找措施
     */
    @Test
    public void findMeasureVOById() {
        MeasureVO measureVO = alarmService.findMeasureVOById("1");
        Assert.assertEquals("测试一", measureVO.getDescription());
    }

    /**
     * 更新措施
     */
    @Test
    public void updateMeasure() {
        MeasureVO measureVO = alarmService.findMeasureVOById("1");
        measureVO.setDescription("修改测试");
        alarmService.updateMeasure(measureVO);
        Assert.assertEquals("修改测试", measureVO.getDescription());
    }

    /**
     * 删除措施
     */
    @Test
    public void deleteMeasure() {
        alarmService.deleteMeasure("4");
        Assert.assertNull(alarmService.findMeasureVOById("4"));
    }

    /**
     * 添加措施
     */
    @Test
    public void addMeasure() {
        MeasureVO measureVO = new MeasureVO();
        measureVO.setId("123465");
        measureVO.setSiteId("31010101");
        measureVO.setCreateTime(new Date());
        measureVO.setDescription("添加测试");
        alarmService.addMeasure(measureVO);
        List<MeasureVO> measureVOs = alarmService.findMeasureList("31010101");
        Assert.assertEquals(4, measureVOs.size());
    }

    /**
     * 根据站点，区域id查询区域绑定的措施
     */
    @Test
    public void findZoneMeasure() {
        List<ZoneVO> zoneVOList = alarmService.findZoneMeasure("31010101", null);
        Assert.assertEquals(4, zoneVOList.size());
    }
}
