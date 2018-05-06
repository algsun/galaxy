package com.microwise.biela.sys;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.LoggerAction;
import com.microwise.common.sys.annotation.Beans;

/**
 * 地图/GIS站点概览操作日志.
 *
 * @author wang.geng
 * @date 13-12-31  上午10:32
 */
@Beans.Service
@Biela
public class BielaLoggerAction extends LoggerAction {
    @Override
    protected void logCurrentInSubsystem(String logName, String logContent, boolean state) {
        logAtCurrentLogicGroup(logName, logContent, Constants.Subsystems.BIELA, state);
    }
}
