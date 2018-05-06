package com.microwise.common.sys.freemarker.tools;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

import java.util.List;

/**
 * @author gaohui
 * @date 14-4-24 19:18
 */
public class StringFormatTool implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list.size() < 2) {
            throw new TemplateModelException("没有传递角色参数");
        }

        TemplateNumberModel length = (TemplateNumberModel) list.get(0);
        TemplateNumberModel value = (TemplateNumberModel) list.get(1);

        return String.format("%0" + length.getAsNumber() + "X", value.getAsNumber());
    }
}
