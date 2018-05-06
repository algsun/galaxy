package com.microwise.halley.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.vo.ExhibitionVO;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 路径 Service 测试类
 * User: xu.yuexi
 * Date: 13-10-17
 * Time: 上午11:26
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PathServiceTest extends CleanDBTest {
    @Autowired
    private PathService pathService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/halley/PathServiceTest.xml");
    }

    @Test
    public void testFindPathPOByExhibitionId() {
        List<PathPO> pathPOList = pathService.findPathByExhibitionId(1);
        Assert.assertEquals(4, pathPOList.size());
        Assert.assertEquals(0, pathPOList.get(0).getDataType());
    }

    @Test
    public void testSavePathPOList() {
        List<PathPO> pathPOList = new ArrayList<PathPO>();
        for (int i = 0; i < 3; i++) {
            PathPO pathPO = new PathPO();
            pathPO.setExhibitionId(1);
            pathPO.setDataType(1);
            pathPO.setLatitude(22.1 + i);
            pathPO.setLongitude(33.1 + i);
            pathPOList.add(pathPO);
        }
        pathService.savePathList(pathPOList);
        List<PathPO> pathPOList1 = pathService.findPathByExhibitionId(1);
        Assert.assertEquals(7, pathPOList1.size());

    }

    @Test
    public void testDeleteByExhibitionId() {
        List<PathPO> pathPOList = pathService.findPathByExhibitionId(1);
        Assert.assertEquals(4, pathPOList.size());
        pathService.deleteByExhibitionId(1);
        List<PathPO> pathPOList1 = pathService.findPathByExhibitionId(1);
        Assert.assertEquals(0, pathPOList1.size());

    }

}
