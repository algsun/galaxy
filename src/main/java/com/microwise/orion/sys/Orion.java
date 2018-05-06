package com.microwise.orion.sys;

import com.microwise.common.sys.annotation.Subsystem;

/**
 * 文物管理命名空间注解
 *
 * @author Wang yunlong
 * @time 13-5-8 下午3:48
 */
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Subsystem("orion")
public @interface Orion {
}
