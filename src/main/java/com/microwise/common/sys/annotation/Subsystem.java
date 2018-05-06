package com.microwise.common.sys.annotation;

/**
 * 用于在 spring 中区分不同业务系统中 bean name 的名称空间注解.
 * 此注解配合 @code{com.microwise.common.sys.annotation.PrefixBeanNameGenerator} 使用, 例如:
 * <pre>
 * <context:component-scan base-package="com.microwise.alice"
 *     name-generator="com.microwise.common.sys.annotation.PrefixBeanNameGenerator" />
 * </pre>
 *
 *
 * @author bastengao
 * @date 12-10-23 Time: 下午9:53
 * @check @wang.yunlong & li.jianfei   #38   2012-12-18
 */
@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE })
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Subsystem {

	/**
	 * 子系统名称
	 * 
	 * @return
	 */
	String value() default "";

}
