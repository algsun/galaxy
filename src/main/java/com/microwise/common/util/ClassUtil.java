package com.microwise.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 反射工具类
 *
 * @author zhangpeng
 * @date 13-7-30
 */
public class ClassUtil {

    /**
     * 根据类型和属性名称查询属性值
     *
     * @param obj       访问对象
     * @param filedname 对象的属性
     * @return 返回对象的属性值
     * @throws Exception
     */
    public static Object getFieldValue(Object obj, String filedname) {

        try{
            //反射出类型
            Class<?> cls = obj.getClass();
            //反射出类型字段
            Field field = cls.getDeclaredField(filedname);
            //获取属性时，压制Java对访问修饰符的检查
            field.setAccessible(true);
            //在对象obj上读取field属性的值
            return field.get(obj);
        }catch (Exception e){
            return  null;
        }
    }

    /**
     * 根据类型和属性名称查询属性类型
     *
     * @param obj       访问对象
     * @param filedname 对象的属性
     * @return 返回对象的属性值
     * @throws Exception
     */
    public static Object getFieldType(Object obj, String filedname){

        try{
            //反射出类型
            Class<?> cls = obj.getClass();
            //反射出类型字段
            Field field = cls.getDeclaredField(filedname);
            //获取属性时，压制Java对访问修饰符的检查
            field.setAccessible(true);
            //在对象obj上读取field属性类型
            return field.getType();
        }catch (Exception e){
            return  null;
        }
    }

    /**
     * 反射调用对象的方法
     *
     * @param obj        对象
     * @param methodName 方法名称
     * @param paramTypes 参数类型    new Class[]{int.class,double.class}
     * @param params     参数值     new Object[]{2,3.5}
     * @return 调用对象的方法
     * @throws Exception
     */
    public static Object readObjMethod(Object obj, String methodName, Class<?>[] paramTypes, Object[] params) throws Exception {
        //发现类型
        Class<?> cls = obj.getClass();
        //发现方法
        Method method = cls.getDeclaredMethod(methodName, paramTypes);
        //访问方法时,压制Java对访问修饰符的检查
        method.setAccessible(true);
        Object val = method.invoke(obj, params);
        return val;
    }

}
