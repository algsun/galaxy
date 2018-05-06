package com.microwise.biela.sys;

import com.microwise.common.sys.annotation.Subsystem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 地图/GIS站点概览系统命名空间.
 *
 * @author wang.geng
 * @date 13-12-31  上午10:20
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Subsystem("biela")
public @interface Biela {
}
