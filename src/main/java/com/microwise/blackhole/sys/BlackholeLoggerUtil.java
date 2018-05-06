package com.microwise.blackhole.sys;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.LoggerAction;
import com.microwise.common.sys.annotation.Beans;

/**
 * 系统管理 操作日志 action
 *
 * @author liuzhu
 * @date 2013-09-10
 */

@Beans.Service
@Blackhole
public class BlackholeLoggerUtil extends LoggerAction {

    @Override
    public void logCurrentInSubsystem(String logName, String logContent, boolean state) {
        logAtCurrentLogicGroup(logName, logContent, Constants.Subsystems.BLACK_HOLE, state);
    }
}
