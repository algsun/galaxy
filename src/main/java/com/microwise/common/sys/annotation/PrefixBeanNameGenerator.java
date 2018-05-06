package com.microwise.common.sys.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.lang.annotation.Annotation;

/**
 * 前缀命名 beanName 的 BeanNameGenerator 实现，配合 @Subsystem("xxxSubsystem") 使用
 *
 * @author bastengao
 * @date 12-10-23 Time: 下午10:21
 * @check  @wang.yunlong & li.jianfei   #17   2012-12-18
 */
public class PrefixBeanNameGenerator extends AnnotationBeanNameGenerator {

	private static final String SPLITTER = ":";

	@Override
	public String generateBeanName(BeanDefinition definition,
			BeanDefinitionRegistry registry) {

		// 使用父类默认策略
		String beanName = super.generateBeanName(definition, registry);

		try {
			// 获取此 bean 对应的 class
			Class<?> beanClass = Class.forName(definition.getBeanClassName());

			// 判断此 bean 上有没有 @Subsystem 注解
			String newBeanName = generateBeaNameIfSubsystemPresent(beanClass,
					beanName);
			if (newBeanName != null) {
				return newBeanName;
			}

			// 查看 bean 的所有注解上是否有 @Subsystem 注解
			for (Annotation annotation : beanClass.getAnnotations()) {
				Class<? extends Annotation> annotationClass = annotation
						.annotationType();
				String newBeanName2 = generateBeaNameIfSubsystemPresent(
						annotationClass, beanName);
				if (newBeanName2 != null) {
					return newBeanName2;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return beanName;
	}

    /**
     * 返回 beanName 如果有 @Subsystem 注释
     *
     * @param clazz
     * @param oldBeanName
     * @return
     */
	private String generateBeaNameIfSubsystemPresent(Class<?> clazz,
			String oldBeanName) {
		// 判断此 class 上有没有 @Subsystem 注解
		if (clazz.isAnnotationPresent(Subsystem.class)) {
			// 如果有则使用 "{subsystemName}:{beanName}" 的方式返回新的 beanName
			Subsystem subsystem = clazz.getAnnotation(Subsystem.class);
			if (!subsystem.value().isEmpty()) {
				return subsystem.value() + SPLITTER + oldBeanName;
			}
		}
		return null;
	}
}
