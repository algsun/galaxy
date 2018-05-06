package com.microwise.common.sys.freemarker.shiro;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 是否认证
 *
 * @author bastengao
 * @date 12-11-20 11:36
 * @check wang.yunlong & li.jianfei #310   2012-12-18
 */
public class IsAuthenticatedTool implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) throws TemplateModelException {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.isAuthenticated();
    }
}
