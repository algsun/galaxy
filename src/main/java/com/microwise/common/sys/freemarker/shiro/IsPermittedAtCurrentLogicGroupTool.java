package com.microwise.common.sys.freemarker.shiro;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 是否在当前站点有权限.
 * <p/>
 * 根据用户当前站点的状态自动判断,
 * 如果当前站点是归属站点则使用归属站点权限判断；
 * 如果当前站点不是归属站点，则使用访客权限判断
 *
 * @author gaohui
 * @date 12-11-28 18:19
 */
public class IsPermittedAtCurrentLogicGroupTool implements TemplateMethodModelEx {


    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list.size() < 1) {
            throw new TemplateModelException("没有传递权限参数");
        }

        TemplateScalarModel permission = (TemplateScalarModel) list.get(0);
        String perm = permission.getAsString();

        Subject currentUser = SecurityUtils.getSubject();
        //如果在归属站点
        if (Helper.atHomeLogicGroup(currentUser)) {
            return currentUser.isPermitted(perm);
        }
        //如果在其他站点
        else {
            return Helper.isGuestPermitted(currentUser, perm);
        }
    }
}
