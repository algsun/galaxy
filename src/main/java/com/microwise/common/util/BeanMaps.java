package com.microwise.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <pre>
 *     把对象封装成map
 *     struts json 支持
 * </pre>
 *
 * @author Wang yunlong
 * @time 12-12-14 上午11:36
 */
public class BeanMaps {
    /**
     * 把一个实体list对象封装成Map list
     * 可以通过properties选择需要封装的属性
     * 对象属性名称和map键保持一致
     *
     * @param originalDataCollection 实体对象list
     * @param properties             需要的转换的数据名称数组
     * @return List<Map<String,Object>>对象
     */
    public static <T> List<Map<String, Object>> toMap(Collection<T> originalDataCollection, String[] properties) {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        if (originalDataCollection.size() != 0) {
            Map<String, Method> methods = getMethods(originalDataCollection.iterator().next(), properties);
            for (T originalData : originalDataCollection) {
                listMap.add(listStrings(originalData, properties, methods));
            }
        }
        return listMap;
    }

    /**
     * 把一个实体对象封装成Map
     * 可以通过properties选择需要封装的属性
     * 对象属性名称和map键保持一致
     *
     * @param originalData 实体对象
     * @param properties   属性数组
     * @return map
     */
    public static <T> Map<String, Object> toMap(T originalData, String[] properties) {
        if (originalData == null) {
            return null;
        }
        Map<String, Method> methods = getMethods(originalData, properties);
        return listStrings(originalData, properties, methods);
    }

    /**
     * 对象list 封装成map List
     * map的键和对象属性名称不一致
     * 可以通过properties选择需要封装的属性
     * 注意：对象的属性数组properties和map键数组names保持对应
     *
     * @param originalDataCollection
     * @param properties
     * @param names
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> toMap(Collection<T> originalDataCollection, String[] properties, String[] names) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (originalDataCollection.size() != 0) {
            Map<String, Method> methodMap = getMethods(originalDataCollection.iterator().next(), properties);
            for (T originalData : originalDataCollection) {
                mapList.add(propertyForNamePutMap(originalData, methodMap, properties, names));
            }
        }
        return mapList;
    }


    /**
     * 把一个对象装换成Map
     * map的键和对象的属性名称不一致
     * 可以通过properties选择需要封装的属性
     * 注意：对象的属性数组properties和map键数组names保持对应
     *
     * @param originalData
     * @param properties
     * @param names
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> toMap(T originalData, String[] properties, String[] names) {
        if (originalData == null) {
            return null;
        }
        Map<String, Method> methods = getMethods(originalData, properties);
        return propertyForNamePutMap(originalData, methods, properties, names);
    }

    /**
     * 对象list 转换成map list
     * map包含对象的所有属性值
     * map的键和对象属性名称一致
     *
     * @param originalDataCollection
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> toMap(Collection<T> originalDataCollection) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (originalDataCollection.size() != 0) {
            String[] propertiesArray = getPropertiesToArray(originalDataCollection.iterator().next());
            Map<String, Method> methodMap = getMethods(originalDataCollection.iterator().next(), propertiesArray);
            for (T originalData : originalDataCollection) {
                mapList.add(listStrings(originalData, propertiesArray, methodMap));
            }
        }
        return mapList;
    }

    /**
     * 对象转换成Map
     * map包含对象的所有属性值
     * map的键和对象属性名称一致
     *
     * @param originalData
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> toMap(T originalData) {
        if (originalData == null) {
            return null;
        }
        String[] propertiesArray = getPropertiesToArray(originalData);
        Map<String, Method> methodMap = getMethods(originalData, propertiesArray);
        return listStrings(originalData, propertiesArray, methodMap);
    }

    /**
     * 获取对象所有属性名称
     *
     * @param originalData
     * @param <T>
     * @return
     */
    private static <T> String[] getPropertiesToArray(T originalData) {
        Field[] fields = originalData.getClass().getDeclaredFields();
        List<String> properties = new ArrayList<String>();
        String[] propertiesArray = new String[0];
        for (Field property : fields) {
            properties.add(property.getName());
        }
        return properties.toArray(propertiesArray);
    }

    /**
     * 通过get方法获取响应属性值
     * 对象属性名称和map键保持一致
     *
     * @param originalData
     * @param properties
     * @param methods
     * @param <T>
     * @return
     */
    private static <T> Map<String, Object> listStrings(T originalData, String[] properties, Map<String, Method> methods) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (String property : properties) {
            try {
                map.put(property, methods.get(property).invoke(originalData));
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return map;
    }

    /**
     * 通过get方法获取响应属性值
     * 对象属性名称和map键不一致
     *
     * @param originalData
     * @param methodMap
     * @param properties
     * @param names
     * @param <T>
     * @return
     */
    private static <T> Map<String, Object> propertyForNamePutMap(T originalData, Map<String, Method> methodMap, String[] properties, String[] names) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++) {
            Method method = methodMap.get(properties[i]);
            try {
                map.put(names[i], method.invoke(originalData));
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return map;
    }

    /**
     * 获得t对象的方法
     *
     * @param originalData
     * @param properties   需要方法数组
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     */
    private static <T> Map<String, Method> getMethods(T originalData, String[] properties) {
        Map<String, Method> methodMap = new HashMap<String, Method>();
        for (String field : properties) {
            Method method;
            try {
                method = originalData.getClass().getMethod(getMethodName("get", field));
            } catch (NoSuchMethodException e) {
                try {
                    method = originalData.getClass().getMethod(getMethodName("is", field));
                } catch (NoSuchMethodException e1) {
                    throw new IllegalArgumentException(e1);
                }
            }
            methodMap.put(field, method);
        }
        return methodMap;
    }

    /**
     * 根据属性获得方法名称
     *
     * @param pre
     * @param field
     * @return
     */
    private static String getMethodName(String pre, String field) {
        return pre + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

}
