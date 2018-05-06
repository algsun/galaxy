package com.microwise.orion.service;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Relic;

/**
 * 文物service 测试类
 *
 * @author xubaoji
 * @date 2013-5-13
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeleteRelicPropertyTest extends CleanDBTest {

    /**
     * 文物service
     */
    @Autowired
    private RelicService relicService;

    /**
     * 文物属性service
     */
    @Autowired
    private RelicPropertyService relicPropertyService;

    @Before
    public void setbefor() throws Exception {
        xmlInsert2("common/dbxml/orion/RelicServiceTest.xml");
    }

    /**
     * 文物 基本信息添加测试 (不添加文物字典信息)
     *
     * @author 许保吉
     * @date 2013-5-17
     */
    @Test
    public void testDeleteRelicProperty() {
        relicPropertyService.deleteRelicProperty(1);
        Relic relic = relicService.findRelicByRelicId(1, "31010101");
        Assert.assertEquals(2, relic.getRelicPropertyMap().size());
        relicPropertyService.deleteRelicProperty(5);
    }

}
