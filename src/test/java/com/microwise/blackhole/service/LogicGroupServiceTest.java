package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Site;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 测试 logicGroupSerice
 *
 * @author xubaoji
 * @date 2012-11-20
 * @check 2012-12-19 zhangpeng svn:885
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogicGroupServiceTest extends CleanDBTest {

    /**
     * 注入 logicGroupService
     */
    @Autowired
    private LogicGroupService logicGroupService;

    @Before
    public void setbefor() throws Exception {
        xmlInsert2("common/dbxml/blackhole/LogicGroupServiceSaveTest.xml");
    }

    /**
     * 根据地区行政编码查询该地区下所有站点列表，地区不限制级别
     *
     * @author 许保吉
     * @date 2012-11-20
     */
    @Test
    public void testFindSiteListByAreaCode() {
        List<Site> logicGroups = logicGroupService
                .findSiteListByAreaCode(130000);
        Assert.assertNotNull("获得130000下的所有站点列表成功", logicGroups);
    }

    /**
     * 修改logicGroup信息
     *
     * @author 许保吉
     * @date 2012-11-21
     */
    @Test
    public void testUpdateLogicGroup() {
        LogicGroup logicGroup = new LogicGroup();
        logicGroup.setId(2);
        logicGroup.setLogicGroupName("修改测试");
        logicGroup.setOrgAddress("丈八六路");
        logicGroup.setOrgCode("orgCode");
        logicGroup.setOrgTel("0521-1654789");
        logicGroup.setOrgFax("052212345678");
        logicGroup.setOrgWebsite("http://www.microwise-system.com/");
        logicGroup.setLogicGroupType(2);
        logicGroupService.updateLogicGroup(logicGroup);
        Assert.assertTrue("修改站点编号为1 的站点失败", logicGroupService
                .findLogicGroupById(2).getLogicGroupName().equals("修改测试"));

    }

    /**
     * 根据logicGroupId分页查询直接子站点列表（无分页）
     *
     * @author 许保吉
     * @date 2012-11-21
     */
    @Test
    public void testFindSubLogicGroupList() {
        List<LogicGroup> logicGroups = logicGroupService
                .findSubLogicGroupList(1);
        Assert.assertTrue("获得站点编号为1的站点下的子站点失败", logicGroups.size() > 0);
    }

    /**
     * 根据logicGroupId分页查询直接子站点列表（无分页）,有site的携带site
     *
     * @author zhangpeng
     * @date 2013-4-3
     */
    @Test
    public void testFindSubLogicGroupsCarrySite() {
        List<LogicGroup> logicGroups = logicGroupService
                .findSubLogicGroupsCarrySite(1);
        for (LogicGroup l : logicGroups) {
            System.out.println(l.getSite());
        }
        Assert.assertTrue("获得站点编号为1的站点下的子站点失败", logicGroups.size() > 0);
    }

    /**
     * 根据logicGroupId分页查询直接子站点列表（有分页）
     *
     * @author 许保吉
     * @date 2012-11-21
     */
    @Test
    public void testFindSubLogicGroupListByPage() {
        List<LogicGroup> logicGroups = logicGroupService.findSubLogicGroupList(
                1, null, 1, 10);
        Assert.assertTrue("分页获得站点编号为1的站点下的子站点失败", logicGroups.size() > 0);
        List<LogicGroup> logicGroups1 = logicGroupService
                .findSubLogicGroupList(null, null, 1, 10);
        System.out.println(logicGroups.size());
        Assert.assertTrue("获得所有顶级站点失败", logicGroups1.size() > 0);
        List<LogicGroup> logicGroups2 = logicGroupService
                .findSubLogicGroupList(1, "aaaaaaaa", 1, 10);
        Assert.assertTrue("分页模糊查询站点编号为1下的子站点失败", logicGroups2 == null ? true
                : logicGroups2.size() == 0);

    }

    /**
     * 根据logicGroupId和logicGroupName查询直接子站点数量
     *
     * @author 许保吉
     * @date 2012-11-21
     */
    @Test
    public void testFindSubLogicGroupListCount() {
        Integer count1 = logicGroupService.findSubLogicGroupListCount(null,
                null);
        Assert.assertTrue("获得顶级站点数量失败", count1 > 0);
        Integer count2 = logicGroupService.findSubLogicGroupListCount(1, null);
        Assert.assertTrue("获得站点编号为1的站点下的子站点数量失败", count2 > 0);
        Integer count = logicGroupService.findSubLogicGroupListCount(1,
                "adfdsfsdf");
        Assert.assertTrue("获得站点编号为1的站点下的模糊查询数量失败", count == 0);
    }

    /**
     * 调整Site对应关系
     *
     * @author 许保吉
     * @date 2012-11-21
     */
    @Test
    public void testChangeSiteOfLogicGroup() {
        logicGroupService.changeSiteOfLogicGroup(3, "11010106");
        LogicGroup logicGroup = logicGroupService.findLogicGroupById(3);
        Assert.assertTrue("调整logicGroup编号为3的站点site 失败", logicGroup.getSite()
                .getSiteId().equals("11010106"));

    }

    /**
     * 调整父LogicGroup
     *
     * @author 许保吉
     * @date 2012-11-21
     */
    @Test
    public void testChangeParentLogicGroup() {
        logicGroupService.changeParentLogicGroup(3, null);
        Assert.assertNull("将站点编号为3的站点的父站点设为null失败", logicGroupService
                .findLogicGroupById(3).getParent());
        logicGroupService.changeParentLogicGroup(2, 1);
        Assert.assertTrue("将站点编号为2的站点的父站点设为站点编号为1的站点失败", logicGroupService
                .findLogicGroupById(2).getParent().getId() == 1);

    }

    /**
     * 获得所有顶级站点列表
     *
     * @author 许保吉
     * @date 2012-11-22
     */
    @Test
    public void testFindTopLogicGroupList() {
        List<LogicGroup> logicGroups = logicGroupService
                .findTopLogicGroupList();
        Assert.assertTrue("获得所有顶级站点失败", logicGroups.size() > 0);
    }

    /**
     * 通过 logicGroup 的id编号 获得一个logicGroup 实体对象
     *
     * @author 许保吉
     * @date 2012-11-22
     */
    @Test
    public void testFidLogicGroupById() {
        LogicGroup logicGroup = logicGroupService.findLogicGroupById(1);
        Assert.assertNotNull("获得logicGroup编号为1的站点失败", logicGroup);
        LogicGroup logicGroup2 = logicGroupService.findLogicGroupById(12312312);
        Assert.assertNull("通过logicGroup编号获得一个不存在的logicGroup实体出错", logicGroup2);
    }

    /**
     * 通过用户编号获得 用户拥有查询权限的站点组
     *
     * @author 许保吉
     * @date 2012-11-26
     */
    @Test
    public void testFindUserLogicGroups() {
        List<LogicGroup> logicGroups = logicGroupService.findUserLogicGroups(2);
        Assert.assertTrue("获得一个用户拥有查询权限的站点组出错", logicGroups == null ? true
                : logicGroups.size() == 0);
        List<LogicGroup> logicGroups1 = logicGroupService
                .findUserLogicGroups(1);
        Assert.assertTrue("获得一个用户拥有查询权限的站点组出错", logicGroups1 != null
                && logicGroups1.size() > 0);
    }

    /**
     * 测试根据用户id查询用户归属站点
     *
     * @author 许保吉
     * @date 2012-11-26
     */
    @Test
    public void testFindLogicGroupBuUserId() {
        LogicGroup logicGroup = logicGroupService.findLogicGroupByUserId(2);
        Assert.assertTrue("获得一个用户的归属站点出错", logicGroup.getId() == 2);

    }

    /**
     * 获得一个站点下 直接行政站点
     *
     * @author 许保吉
     * @date 2012-12-4
     */
    @Test
    public void testFindAdmimLogicGroups() {
        List<LogicGroup> logicGroups = logicGroupService
                .findAdmimLogicGroups(null);
        Assert.assertTrue("获得所有顶级行政站点失败", logicGroups.size() > 0);
        List<LogicGroup> logicGroups1 = logicGroupService
                .findAdmimLogicGroups(1);
        Assert.assertTrue("获得站点编号为1的站点下的行政站点失败", logicGroups1.size() > 0);
    }

    /**
     * 通过logicGroup 编号获得 logicGroup 对象 并且 带有site 对象
     *
     * @author 许保吉
     * @date 2012-12-4
     */
    @Test
    public void testFindLogicGroupCarrySite() {
        LogicGroup logicGroup = logicGroupService.findLogicGroupCarrySite(3);
        Assert.assertNotNull("通过logicGroup 编号为3 logicGroup对象失败", logicGroup);
    }

    /**
     * 根据logicGroupId删除站点
     *
     * @author 许保吉
     * @date 2012-11-21
     */
    @Test
    public void testDeleteLogicGroup() {
        logicGroupService.deleteLogicGroup(2);
        Assert.assertNull("删除站点编号为2的站点失败",
                logicGroupService.findLogicGroupById(2));
    }

}
