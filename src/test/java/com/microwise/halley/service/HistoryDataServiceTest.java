package com.microwise.halley.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.po.ConfigPO;
import com.microwise.halley.bean.po.DevicePO;
import com.microwise.halley.bean.po.UserPO;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.bean.vo.TimeIntervalVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆信息查询服务单元测试类
 *
 * @author xu.yuexi
 * @date 13-11-11
 */
public class HistoryDataServiceTest extends CleanDBTest {

    @Autowired
    private HistoryDataService historyDataService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/halley/HistoryDataServiceTest.xml");
    }

    @Test
    public void testFindCarsWithDeviceByExhibitionId() {
        List<TimeIntervalVO> timeIntervalVOList = historyDataService.getTimeInterval(1);
        Assert.assertEquals(3, timeIntervalVOList.size());
        Assert.assertEquals("世家星城 - 历史博物馆", timeIntervalVOList.get(0).getName());
        Assert.assertEquals("历史博物馆 陈展中", timeIntervalVOList.get(1).getName());
        Assert.assertEquals("历史博物馆 - 世家星城", timeIntervalVOList.get(2).getName());
    }
}
