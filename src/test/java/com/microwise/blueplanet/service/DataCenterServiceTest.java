package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.DCSlidePO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 图库幻灯片功能测试用例
 *
 * @author liuzhu
 * @date 14-3-4
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataCenterServiceTest extends CleanDBTest {

    @Autowired
    private DataCenterService dataCenterService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/blueplanet/DataCenterServiceTest.xml");
    }

    /**
     * 根据站点组id获取所有子孙基层站点
     */
    @Test
    public void findSiteVOByLogicGroupId() {
        List<SiteVO> siteVOList = dataCenterService.findSiteVOByLogicGroupId(1);
        Assert.assertNotNull(siteVOList);
        Assert.assertEquals(1, siteVOList.size());
    }

    /**
     * 根据幻灯片id获取幻灯片
     */
    @Test
    public void findSlideShowById() {
        DCSlidePO dcSlidePO = dataCenterService.findSlideShowById(1);
        Assert.assertEquals("测试1", dcSlidePO.getTitle());
    }

    /**
     * 根据layoutId，itemId获取幻灯片list
     */
    @Test
    public void findSlideList() {
        List<DCSlidePO> dcSlidePOList = dataCenterService.findSlideList("bbb", "222");
        Assert.assertEquals(0, dcSlidePOList.size());
    }

    /**
     * 添加幻灯片
     */
    @Test
    public void saveSlide() {
        DCSlidePO dcSlidePO = new DCSlidePO();
        dcSlidePO.setId(2);
        dcSlidePO.setDetail("随便");
        dcSlidePO.setLocationId("22222");
        dcSlidePO.setTitle("he");
        dcSlidePO.setRefresh(10);
        dcSlidePO.setRelatedItemId("222");
        dcSlidePO.setRelatedLayoutId("aaa");
        dcSlidePO.setUrl("suibian");
        dataCenterService.saveSlide(dcSlidePO);
        List<DCSlidePO> dcSlidePOList = dataCenterService.findSlideList("aaa", "222");
        Assert.assertEquals(1, dcSlidePOList.size());
    }

    /**
     * 修改幻灯片vo
     */
    @Test
    public void update() {
        DCSlidePO dcSlidePO = new DCSlidePO();
        dcSlidePO.setId(3);
        dcSlidePO.setDetail("随便");
        dcSlidePO.setLocationId("22222");
        dcSlidePO.setTitle("呵呵");
        dcSlidePO.setRefresh(10);
        dcSlidePO.setRelatedItemId("222");
        dcSlidePO.setRelatedLayoutId("aaa");
        dcSlidePO.setUrl("suibian");
        dataCenterService.updateSlide(dcSlidePO);
        DCSlidePO slidePO = dataCenterService.findSlideShowById(3);
        Assert.assertEquals("22222", slidePO.getLocationId());
    }

    /**
     * 删除幻灯片
     */
    @Test
    public void deleteSlideById() {
        dataCenterService.deleteSlideById(2);
        DCSlidePO dcSlidePO = dataCenterService.findSlideShowById(2);
        Assert.assertNull(dcSlidePO);
    }

    /**
     * 删除所有幻灯片
     */
    @Test
    public void deleteItemAllSlidesByIds() {
        dataCenterService.deleteItemAllSlidesByIds("aaa", "222");
        List<DCSlidePO> dcSlidePOList = dataCenterService.findSlideList("aaa", "222");
        Assert.assertEquals(0, dcSlidePOList.size());
    }

}
