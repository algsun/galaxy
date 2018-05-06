package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.TopoViewVO;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.common.util.DateTimeUtil;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiedeng
 * @date 13-9-29
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TopoServiceTest extends CleanDBTest {

    /**
     * 网络拓扑图service接口
     */
    @Autowired
    private TopoService topoService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/blueplanet/TopoServiceTest.xml");
    }

    @Test
    public void testGetTopData1() {
        List<Object> topos = topoService.getTopoData("3101010100001");
        Assert.assertNotNull(topos);
        Assert.assertNotNull(topos.get(0));
        Assert.assertNotNull(topos.get(1));
    }

    @Test
    public void testGetTopData2() {
        List<Object> topos = topoService.getTopoData("3101010100001");
        List<TopoViewVO> topoViewVOList = (List<TopoViewVO>) topos.get(0);
        List<Map<String, String>> refs = (List<Map<String, String>>) topos.get(1);
        Assert.assertEquals(9, topoViewVOList.size());
        Assert.assertEquals(8, refs.size());
    }

    @Test
    public void testGetDevices1() {
        List<TopoViewVO> topoViewVOList = topoService.getDevices("31010101");
        Assert.assertNotNull(topoViewVOList);
    }

    @Test
    public void testGetDevices2() {
        List<TopoViewVO> topos = topoService.getDevices("31010101");
        Assert.assertEquals(9, topos.size());
    }

    @Test
    public void testGetDevices3() {
        List<TopoViewVO> topos = topoService.getDevices("31010102");
        Assert.assertEquals(0, topos.size());
    }

    @Test
    public void testGetLoadDevices1() {
        List<TopoViewVO> topos = topoService.getLoadDevices("31010101", 0);
        Assert.assertEquals(4, topos.size());
    }

    @Test
    public void testGetLoadDevices2() {
        List<TopoViewVO> topos = topoService.getLoadDevices("31010101", 1);
        Assert.assertEquals(3, topos.size());
    }

    @Test
    public void testGetRssi1() {
        List<TopoViewVO> topos = topoService.getRssi("31010101", 0);
        Assert.assertEquals(9, topos.size());
    }

    @Test
    public void testGetRssi2() {
        List<TopoViewVO> topos = topoService.getRssi("31010101", 1);
        Assert.assertEquals(6, topos.size());
    }
    @Ignore
    @Test
    public void testGetLoseRate1() {
        List<TopoViewVO> topos = topoService.getLoseRate(new Date(), new Date(), Constants.FIND_TYPE_DAY, "31010101");
        Assert.assertEquals(9, topos.size());
    }

    @Test
    public void testGetLoseRate2() {
        List<TopoViewVO> topos = topoService.getLoseRate(new Date(),new Date(), Constants.FIND_TYPE_DAY, "31010102");
        Assert.assertEquals(0, topos.size());
    }

    @Ignore
    @Test
    public void testGetHistoryRoute() {
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = DateTimeUtil.parse("yyyy-MM-dd HH:mm:ss", "2013-01-01 00:00:00");
            endDate = DateTimeUtil.parse("yyyy-MM-dd HH:mm:ss", "2013-11-19 23:59:59");
        } catch (Exception e) {

        }
        List<TopoViewVO> topos = topoService.getHistoryRoute("3101010100001", startDate, endDate, 0, 10);
        Assert.assertEquals(1, topos.size());
    }

    @Ignore
    @Test
    public void testGetHistoryRouteCount() {
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = DateTimeUtil.parse("yyyy-MM-dd HH:mm:ss", "2013-01-01 00:00:00");
            endDate = DateTimeUtil.parse("yyyy-MM-dd HH:mm:ss", "2013-10-15 23:59:59");
        } catch (Exception e) {

        }
        int count = topoService.getHistoryRouteCount("3101010100001", startDate, endDate);
        Assert.assertEquals(1, count);
    }

    @Test
    public void testTimeoutDevice() {
        List<TopoViewVO> topoViewVOList1 = topoService.getTimeoutDevice("31010101");
        List<TopoViewVO> topoViewVOList2 = topoService.getTimeoutDevice("31010102");
        Assert.assertNotNull(topoViewVOList1);
        Assert.assertEquals(3, topoViewVOList1.size());
        Assert.assertEquals(0, topoViewVOList2.size());
    }
}
