package com.microwise.common.action;

import org.apache.struts2.util.StrutsTypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 处理 struts java.util.Date 类型转换
 *
 * @author gaohui
 * @date 13-4-18 11:40
 */
public class DateConverter extends StrutsTypeConverter {

    // 支持的解析类型
    private static final List<SimpleDateFormat> formats;

    static {
        List<SimpleDateFormat> initFormats = new ArrayList<SimpleDateFormat>();
        // 越严格越靠前
        initFormats.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        initFormats.add(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        initFormats.add(new SimpleDateFormat("yyyy-MM-dd"));
        initFormats.add(new SimpleDateFormat("yyyy-MM"));
        initFormats.add(new SimpleDateFormat("HH:mm:ss"));
        initFormats.add(new SimpleDateFormat("HH:mm"));
        initFormats.add(new SimpleDateFormat("yyyy"));
        initFormats.add(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));

        formats = Collections.unmodifiableList(initFormats);
    }

    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values == null) {
            return null;
        }

        if (values.length < 1) {
            return null;
        }

        String value = values[0]; // 第一个值
        for (SimpleDateFormat df : formats) {
            try {
                // 一旦解析，直接返回
                return df.parse(value);
            } catch (ParseException e) {
                // do nothing
            }
        }

        return null;
    }

    @Override
    public String convertToString(Map context, Object o) {
        return o.toString();
    }
}
