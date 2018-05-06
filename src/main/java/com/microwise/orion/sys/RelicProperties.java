package com.microwise.orion.sys;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.microwise.orion.bean.Property;
import com.microwise.orion.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * 缓存文物扩展属性
 *
 * @author gaohui
 * @date 13-5-22 11:05
 */
public class RelicProperties {
    private static List<Property> properties;

    /**文物属性信息*/
    @Autowired
    private PropertyService propertyService;

    public void init() {
        List<Property> properties = propertyService.findAllProperty();
        RelicProperties.properties = Collections.unmodifiableList(properties);

        Maps.uniqueIndex(properties, new Function<Property, String>() {
            @Override
            public String apply(com.microwise.orion.bean.Property property) {
                return property.getEnName();
            }
        });
    }


    /**
     * 返回文物的全部扩展属性
     *
     * @return
     */
    public static List<Property> allProperties() {
        return properties;
    }

}
