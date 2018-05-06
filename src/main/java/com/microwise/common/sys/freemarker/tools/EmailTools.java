package com.microwise.common.sys.freemarker.tools;

import com.microwise.common.util.EmailUtil;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

import java.util.List;

/**
 * @author gaohui
 * @date 12-11-30 14:42
 */
public class EmailTools {

    /**
     * 混淆Email
     */
    public static class EmailObscuredTool implements TemplateMethodModelEx {

        @Override
        public Object exec(List list) throws TemplateModelException {
            if (list.size() < 1) {
                throw new TemplateModelException("没有传递权限参数");
            }
            TemplateScalarModel model = (TemplateScalarModel) list.get(0);
            String email = model.getAsString();
            String obscuredEmail = EmailUtil.obscured(email);
            if (obscuredEmail == null) {
                return "...";
            } else {
                return obscuredEmail;
            }
        }
    }
}
