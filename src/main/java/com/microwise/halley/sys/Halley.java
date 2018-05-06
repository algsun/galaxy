package com.microwise.halley.sys;

import com.microwise.common.sys.annotation.Subsystem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文物外展系统命名空间注释
 *
 * @author wanggeng
 * @date 13-9-24 上午10:35
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Subsystem("halley")
public @interface Halley {
}
