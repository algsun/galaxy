package com.microwise.halley.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.halley.bean.po.OpticsDVPlacePO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * C光学摄像机点位 Service测试类
 * User: xu.yuexi
 * Date: 13-10-16
 * Time: 下午5:08
 */
public class OpticsDVPlaceServiceTest extends CleanDBTest {
    @Autowired
    private OpticsDVPlaceService opticsDVPlaceService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/halley/OpticsDVPlaceForHalleyServiceTest.xml");
    }

    @Test
    public void testFindAllOpticsDV() {
        List<OpticsDVPlacePO> opticsDVPlaceList = opticsDVPlaceService.findAllOpticsDV("1");
        Assert.assertEquals(1, opticsDVPlaceList.size());
    }

    @Test
    public void testFindOpticsDVByZoneId() {
        List<OpticsDVPlacePO> opticsDVPlaceList = opticsDVPlaceService.findOpticsDVByZoneId("1", "1");
        Assert.assertEquals(1, opticsDVPlaceList.size());
    }
}
