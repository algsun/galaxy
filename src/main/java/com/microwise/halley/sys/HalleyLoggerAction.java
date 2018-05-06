package com.microwise.halley.sys;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.LoggerAction;
import com.microwise.common.sys.annotation.Beans;

/**
 * 文物外展操作日志
 *
 * @author wanggeng
 * @date 13-9-24 上午10:46
 */
@Beans.Service
@Halley
public class HalleyLoggerAction extends LoggerAction {

    @Override
    protected void logCurrentInSubsystem(String logName, String logContent, boolean state) {
        logAtCurrentLogicGroup(logName, logContent, Constants.Subsystems.HALLEY, state);
    }
}
