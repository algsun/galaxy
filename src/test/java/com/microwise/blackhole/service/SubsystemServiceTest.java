package com.microwise.blackhole.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blackhole.bean.Subsystem;
import com.microwise.common.sys.test.BaseTest;

/**
 * 测试业务系统Service
 *
 * @author zhangpeng
 * @date 2012-11-21
 * @check 2012-12-19 xubaoji svn:877
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubsystemServiceTest extends BaseTest {

    /**
     * 业务系统Service
     */
    @Autowired
    private SubsystemService subsystemService;

    /**
     * 测试查询所有业务系统列表
     *
     * @author zhangpeng
     * @date 2012-11-21
     */
    @Test
    public void testFindAllSubsystems() {
        List<Subsystem> list = subsystemService.findAllSubsystemsByLanguage("zh_CN");
        Assert.assertEquals(10, list.size());
    }

}
