package com.microwise.common.sys.freemarker.shiro;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 访客权限判断扩展
 *
 * @author gaohui
 * @date 12-11-28 16:42
 */
public class GuestTools {

    /**
     * 判断访客是否某权限
     *
     * @author gaohui
     * @date 2012-11-28
     */
    public static class IsPermittedTool implements TemplateMethodModelEx {

        @Override
        public Object exec(List list) throws TemplateModelException {
            if (list.size() < 1) {
                throw new TemplateModelException("没有传递权限参数");
            }

            TemplateScalarModel permission = (TemplateScalarModel) list.get(0);
            Subject currentUser = SecurityUtils.getSubject();
            return Helper.isGuestPermitted(currentUser,permission.getAsString());
        }
    }

}
