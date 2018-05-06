package com.microwise.common.sys.freemarker.shiro;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 判断用户是否是访客(用户在浏览非归属站点时，对于此站点用户为访客)
 *
 * @author gaohui
 * @date 12-11-28 17:18
 */
public class IsGuestTool implements TemplateMethodModelEx {

    @Override
    public Object exec(List list) throws TemplateModelException {
        Subject currentUser = SecurityUtils.getSubject();
        return Helper.isGuest(currentUser);
    }
}
