package com.microwise.blackhole.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blackhole.bean.SysLog;
import com.microwise.common.sys.test.CleanDBTest;

/**
 * 测试日志接口
 *
 * @author zhangpeng
 * @date 2012-11-16
 * @check 2012-12-19 xubaoji svn:877
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogServiceTest extends CleanDBTest {

    /**
     * 日志Service
     */
    @Autowired
    private LogService logService;

    /**
     * 添加日志
     *
     * @author zhangpeng
     * @date 2012-12-13
     */
    @Test
    public void testSaveLog() {
        SysLog sysLog = new SysLog();
        sysLog.setUserName("操作用户名称");
        sysLog.setUserEmail("操作用户Email");
        sysLog.setLogicGroupId(1);
        sysLog.setLogicGroupName("站点名称");
        sysLog.setSubsystemId(1);
        sysLog.setLogName("测试记录");
        sysLog.setLogContent("日志内容");
        sysLog.setLogTime(new Date());
        sysLog.setLogState(false);
        logService.saveLog(sysLog);
        int sysLogListSize = logService.findLogListCount(sysLog);
        Assert.assertTrue("testSaveLog测试失败", sysLogListSize == 1);
    }

    /**
     * 查询日志列表（无时间条件）
     *
     * @author zhangpeng
     * @date 2012-12-13
     */
    @Test
    public void testFindLogList() {
        SysLog sysLog = new SysLog();
        sysLog.setUserName("操作用户名称");
        sysLog.setUserEmail("操作用户Email");
        sysLog.setLogicGroupId(1);
        sysLog.setLogicGroupName("站点名称");
        sysLog.setSubsystemId(1);
        sysLog.setLogName("测试记录");
        sysLog.setLogContent("日志内容");
        sysLog.setLogTime(new Date());
        sysLog.setLogState(false);
        logService.saveLog(sysLog);
        sysLog = new SysLog();
        sysLog.setUserName("用");
        List<SysLog> sysLogList = logService.findLogList(sysLog, 1, 10);
        Assert.assertTrue("testFindLogList测试失败", sysLogList.size() == 1);
    }

    /**
     * 查询日志数量（无时间条件）
     *
     * @author zhangpeng
     * @date 2012-12-13
     */
    @Test
    public void testFindLogListCount() {
        SysLog sysLog = new SysLog();
        sysLog.setUserName("操作用户名称");
        sysLog.setUserEmail("操作用户Email");
        sysLog.setLogicGroupId(1);
        sysLog.setLogicGroupName("站点名称");
        sysLog.setSubsystemId(1);
        sysLog.setLogName("测试记录");
        sysLog.setLogContent("日志内容");
        sysLog.setLogTime(new Date());
        sysLog.setLogState(false);
        logService.saveLog(sysLog);
        sysLog = new SysLog();
        sysLog.setUserName("用");
        int sysLogListSize = logService.findLogListCount(sysLog);
        Assert.assertTrue("testFindLogListCount测试失败", sysLogListSize == 1);
    }

    /**
     * 查询日志列表（有时间条件）
     *
     * @author zhangpeng
     * @date 2012-12-13
     */
    @Test
    public void testFindLogList2() {
        SysLog sysLog = new SysLog();
        sysLog.setUserName("操作用户名称");
        sysLog.setUserEmail("操作用户Email");
        sysLog.setLogicGroupId(1);
        sysLog.setLogicGroupName("站点名称");
        sysLog.setSubsystemId(1);
        sysLog.setLogName("测试记录");
        sysLog.setLogContent("日志内容");
        sysLog.setLogTime(new Date());
        sysLog.setLogState(false);
        logService.saveLog(sysLog);
        sysLog = new SysLog();
        sysLog.setLogName("测试");
        List<SysLog> sysLogList = logService.findLogList(sysLog, DateTime.now()
                .minusHours(10).toDate(), new Date(), 1, 10);
        Assert.assertTrue("testFindLogList2测试失败", sysLogList.size() == 1);
    }

    /**
     * 查询日志数量（有时间条件）
     *
     * @author zhangpeng
     * @date 2012-12-13
     */
    @Test
    public void testFindLogListCount2() throws ParseException {
        SysLog sysLog = new SysLog();
        sysLog.setUserName("操作用户名称");
        sysLog.setUserEmail("操作用户Email");
        sysLog.setLogicGroupId(1);
        sysLog.setLogicGroupName("站点名称");
        sysLog.setSubsystemId(1);
        sysLog.setLogName("测试记录");
        sysLog.setLogContent("日志内容");
        sysLog.setLogTime(new Date());
        sysLog.setLogState(false);
        logService.saveLog(sysLog);
        sysLog = new SysLog();
        sysLog.setLogName("测试");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int sysLogListSize = logService.findLogListCount(sysLog,
                sdf.parse("2008-08-08 12:10:12"),
                sdf.parse("2999-11-28 12:10:12"));
        Assert.assertTrue("testFindLogListCount2测试失败", sysLogListSize == 1);
    }

}
