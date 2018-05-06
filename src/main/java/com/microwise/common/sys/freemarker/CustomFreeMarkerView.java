package com.microwise.common.sys.freemarker;

import freemarker.template.SimpleHash;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 将 request 对象添加到上下文中
 * @author 李建飞
 * @date 2014-09-09
 */
public class CustomFreeMarkerView extends FreeMarkerView {
    protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        SimpleHash fmModel = super.buildTemplateModel(model, request, response);
        fmModel.put("request", request);
        return fmModel;
    }
}