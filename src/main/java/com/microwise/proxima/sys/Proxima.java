package com.microwise.proxima.sys;

import com.microwise.common.sys.annotation.Subsystem;

/**
 * 本体监测命名空间注解
 *
 * @author bastengao
 * @date 12-10-23 Time: 下午11:18
 *
 * @check  @wang.yunlong & li.jianfei #17 2012-12-18
 */
@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE })
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Subsystem("proxima")
public @interface Proxima {
}
