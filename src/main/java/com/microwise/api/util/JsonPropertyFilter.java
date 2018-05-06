package com.microwise.api.util;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

/**
 *
 * 转json工具类
 *
 * @author liuzhu
 * @date 2014-10-9
 * @dependency fastjson
 */
public class JsonPropertyFilter implements PropertyPreFilter {

    /**
     * 解析属性
     */
    private String[] includes = new String[]{};

    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }

        for (String str : includes) {
            if (str.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String[] getIncludes() {
        return includes;
    }

    public void setIncludes(String[] includes) {
        this.includes = includes;
    }
}

