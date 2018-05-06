package com.microwise.common.sys.freemarker.shiro;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.apache.shiro.SecurityUtils;

import java.util.List;

/**
 * 判断用户是否是匿名用户
 *
 * @author li.jianfei
 * @date 2012-11-27
 */
public class IsAnonymityTool implements TemplateMethodModel {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return Helper.isAnonymity(SecurityUtils.getSubject());
    }
}
