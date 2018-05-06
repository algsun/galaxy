package com.microwise.phoenix.sys;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.LoggerAction;

/**
 * 数据分析 操作日志 action
 *
 * @author liuzhu
 * @date 13-08-20
 */
public class PhoenixLoggerAction extends LoggerAction {

    @Override
    protected void logCurrentInSubsystem(String logName, String logContent, boolean state) {
        logAtCurrentLogicGroup(logName, logContent, Constants.Subsystems.Phoenix, state);
    }
}
