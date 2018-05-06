package com.microwise.common.util;

import org.apache.struts2.ServletActionContext;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 国际化action中文
 *
 * @author chenyaofei
 * @date 16-2-24
 */

public class ResourceBundleUtil {

    public static ResourceBundle getBundle() {
        Locale locale = (Locale)ServletActionContext.getRequest().getSession().getAttribute("WW_TRANS_I18N_LOCALE");
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/language", locale);
        return bundle;
    }

//    public static String getLanguage() {
//        Locale locale = (Locale)ServletActionContext.getRequest().getSession().getAttribute("WW_TRANS_I18N_LOCALE");
//        return locale.getDefault().toString();
//    }
}
