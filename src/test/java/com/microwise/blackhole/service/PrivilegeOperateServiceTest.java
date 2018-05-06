package com.microwise.blackhole.service;

import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhu
 * @date 2014/4/3
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrivilegeOperateServiceTest extends CleanDBTest {

    @Before
    public void before() throws Exception {
        xmlInsert2("common/dbxml/blackhole/PrivilegeOperateServiceTest.xml");
    }

    @Autowired
    private PrivilegeOperateService privilegeOperateService;

    /**
     * 禁用权限
     */
    @Test
    public void disable() {
        List<String> list = new ArrayList<String>();
        list.add("blackhole:logicGroup:delete");
        privilegeOperateService.disable(list, 1);
        Assert.assertEquals(true, privilegeOperateService.isDisableById("blackhole:logicGroup:delete", 1));
    }

    @Test
    public void findDisablePrivilegeId() {
        List<String> list = privilegeOperateService.findDisablePrivilegeId(1);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void clearDisable() {
        privilegeOperateService.clearDisable(1);
        Assert.assertEquals(false, privilegeOperateService.isDisableById("blackhole:role:delete", 1));
    }




}
