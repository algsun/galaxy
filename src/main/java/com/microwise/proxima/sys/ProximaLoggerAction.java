package com.microwise.proxima.sys;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.LoggerAction;

/**
 * 本体检测 操作日志 action
 *
 * @author liuzhu
 * @date 13-08-20
 */
public class ProximaLoggerAction extends LoggerAction {

    @Override
    protected void logCurrentInSubsystem(String logName, String logContent, boolean state) {
        logAtCurrentLogicGroup(logName, logContent, Constants.Subsystems.PROXIMA, state);
    }
}
