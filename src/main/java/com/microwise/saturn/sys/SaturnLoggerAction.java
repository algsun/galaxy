package com.microwise.saturn.sys;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.LoggerAction;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wang yunlong
 * @time 13-2-5 下午3:07
 */
public class SaturnLoggerAction extends LoggerAction {
    /**
     * 子系统为 环境监控,
     * 用户信息来自当前登录用户,
     * 站点信息来自用户当前站点
     *
     * @param logName
     * @param logContent
     * @param state
     */
    protected void logCurrentInSubsystem(String logName, String logContent, boolean state) {
        logAtCurrentLogicGroup(logName, logContent, Constants.Subsystems.BLUE_PLANET, state);
    }
}
