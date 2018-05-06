package com.microwise.common.sys.freemarker.shiro;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.shiro.SecurityUtils;

import java.util.List;

/**
 * 返回当前 Subject
 *
 * @author gaohui
 * @date 12-11-28 15:26
 */
public class CurrentUserTool implements TemplateMethodModelEx {

    @Override
    public Object exec(List list) throws TemplateModelException {
        return SecurityUtils.getSubject();
    }
}
