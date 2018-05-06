package com.microwise.orion.sys;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.LoggerAction;

/**
 * 藏品管理 操作日志 action
 *
 * @author gaohui
 * @date 13-6-3 13:21
 */
public class OrionLoggerAction extends LoggerAction {

    @Override
    protected void logCurrentInSubsystem(String logName, String logContent, boolean state) {
        logAtCurrentLogicGroup(logName, logContent, Constants.Subsystems.ORION, state);
    }
}
