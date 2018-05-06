package com.microwise.common.sys.freemarker.shiro;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 是否拥有某个角色
 *
 * @author bastengao
 * @date 12-11-20 14:51
 * @check wang.yunlong & li.jianfei #310 2012-12-18
 */
public class HasRoleTool implements TemplateMethodModelEx {

    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list.size() < 1) {
            throw new TemplateModelException("没有传递角色参数");
        }

        Subject currentUser = SecurityUtils.getSubject();
        TemplateScalarModel role = (TemplateScalarModel) list.get(0);
        return currentUser.hasRole(role.getAsString());
    }
}
