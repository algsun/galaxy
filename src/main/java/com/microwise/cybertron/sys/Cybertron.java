package com.microwise.cybertron.sys;

import com.microwise.common.sys.annotation.Subsystem;

/**
 * 档案管理命名空间注解
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Subsystem("cybertron")
public @interface Cybertron {
}
