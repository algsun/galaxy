package com.microwise.common.sys.freemarker.shiro;

import com.microwise.common.sys.Constants;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 是否是站点管理员
 *
 * @author gaohui
 * @date 12-11-28 16:16
 */
public class IsSiteManagerTool implements TemplateMethodModelEx {

    @Override
    public Object exec(List list) throws TemplateModelException {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.hasRole(Constants.Roles.SITE_MANAGER);
    }
}
