package com.microwise.blackhole.proxy;

import com.microwise.blackhole.bean.SysLog;

/**
 * 日志代理层
 *
 * @author zhangpeng
 * @date 2013-2-1
 */
public interface LogProxy {

    /**
     * 添加日志
     *
     * @param sysLog 日志对象
     * @author zhangpeng
     * @date 2012-11-15
     */
    public void saveLog(SysLog sysLog);

}
