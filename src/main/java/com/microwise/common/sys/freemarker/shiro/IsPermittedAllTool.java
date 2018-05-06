package com.microwise.common.sys.freemarker.shiro;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * 是否用户在归属站点有权限
 *
 * @author bastengao
 * @date 12-11-20 15:51
 * @check @wang.yunlong & li.jianfei #460 2012-12-18
 */
public class IsPermittedAllTool implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list.isEmpty()) {
            throw new TemplateModelException("没有传递权限参数");
        }

        List<String> permissions = new ArrayList<String>();
        for (Object scalarModel : list) {
            permissions.add(((TemplateScalarModel) scalarModel).getAsString());
        }
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.isPermittedAll(permissions.toArray(new String[]{}));
    }
}
