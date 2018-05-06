package com.microwise.phoenix.sys;

import com.microwise.common.sys.annotation.Subsystem;

/**
 * 综合评估 spring 命名空间注解
 *
 * @author gaohui
 * @date 13-7-2 15:36
 */
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Subsystem("phoenix")
public @interface Phoenix {
}
