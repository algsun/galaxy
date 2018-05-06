package com.microwise.common.sys.freemarker.shiro;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.shiro.SecurityUtils;

import java.util.List;

/**
 * 判断用户的当前站点是否是归属站点
 *
 * @author gaohui
 * @date 12-11-29 11:33
 */
public class AtHomeLogicGroupTool implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) throws TemplateModelException {
        return Helper.atHomeLogicGroup(SecurityUtils.getSubject());
    }
}
