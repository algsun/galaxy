package com.microwise.blackhole.sys;

import com.microwise.common.sys.annotation.Subsystem;

/**
 * 系统管理命名空间注解
 *
 * @author bastengao
 * @date 12-10-23 Time: 下午10:44
 * @check @wang.yunlong & li.jianfei    #17   2012-12-18
 */
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Subsystem("blackhole")
public @interface Blackhole {
}
