package com.microwise.halley.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.vo.PackingListVO;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 装箱单 Service 测试类
 * User: xu.yuexi
 * Date: 13-10-16
 * Time: 下午5:28
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PackingListServiceTest extends CleanDBTest {
    @Autowired
    private PackingListService packingListService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/halley/PackingListServiceTest.xml");
    }
    @Test
    public void testFindPackingListByExhibitionId(){
        Map<CarPO, List<PackingListVO>> poListMap= packingListService.findPackingListByExhibitionId(1);
        Assert.assertEquals(1, poListMap.size());
    }
}
