package com.microwise.blackhole.proxy.impl;

import com.microwise.blackhole.bean.SysLog;
import com.microwise.blackhole.proxy.LogProxy;
import com.microwise.blackhole.service.LogService;
import com.microwise.blackhole.sys.Blackhole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 日志代理实现
 *
 * @author zhangpeng
 * @date 2013-2-1
 */
@Component
@Scope("prototype")
@Blackhole
public class LogProxyImpl implements LogProxy {

    @Autowired
    private LogService logService;

    @Override
    public void saveLog(SysLog sysLog) {
        logService.saveLog(sysLog);
    }

}
