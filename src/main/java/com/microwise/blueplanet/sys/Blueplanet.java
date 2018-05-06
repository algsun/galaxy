package com.microwise.blueplanet.sys;

import com.microwise.common.sys.annotation.Subsystem;

/**
 * 环境监控 spring name 命名空间注解
 *
 * @author bastengao
 * @date 12-10-23 Time: 下午11:18
 * @check @wang.yunlong & li.jianfei   #71    2012-12-18
 */
@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE })
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Subsystem("blueplanet")
public @interface Blueplanet {
}
