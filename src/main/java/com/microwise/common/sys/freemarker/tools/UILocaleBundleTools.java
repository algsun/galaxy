package com.microwise.common.sys.freemarker.tools;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 自定义UI属性读取工具类集合
 *
 * @author gaohui
 * @date 13-5-11 16:08
 */
public class UILocaleBundleTools {
    public static final Logger log = LoggerFactory.getLogger(UILocaleBundleTools.class);
    public static final String BASE_NAME = "custom/ui";

    public static class LocaleBundleGetString implements TemplateMethodModelEx {

        /**
         * 返回某个资源包中的某个值
         * 需要三个参数:
         * baseName: 基文件名
         * key: 键
         * [locale]: 本地名称, 可选
         *
         * @param list
         * @return
         * @throws TemplateModelException
         */
        @Override
        public Object exec(List list) throws TemplateModelException {
            if (list.size() < 2) {
                throw new IllegalArgumentException("缺少参数");
            }

            String baseName = ((TemplateScalarModel) list.get(0)).getAsString();
            String key = ((TemplateScalarModel) list.get(1)).getAsString();

            if (list.size() < 3) {
                return ResourceBundles.getStrAuto(baseName, key);
            } else { //如果有第三个参数, 则是 locale
                String locale = ((TemplateScalarModel) list.get(2)).getAsString();
                return ResourceBundles.getStr(baseName, key, locale);
            }
        }
    }

    /**
     * params: key:String
     */
    public static class UIBundleGetString implements TemplateMethodModelEx {

        @Override
        public Object exec(List list) throws TemplateModelException {
            if (list.size() < 1) {
                throw new IllegalArgumentException("缺少参数");
            }

            String key = ((TemplateScalarModel) list.get(0)).getAsString();

            return ResourceBundles.getStrAuto(BASE_NAME, key);
        }
    }

    /**
     * params: key:String
     */
    public static class UIBundleGetBool implements TemplateMethodModelEx {

        @Override
        public Object exec(List list) throws TemplateModelException {
            if (list.size() < 1) {
                throw new IllegalArgumentException("缺少参数");
            }
            String key = ((TemplateScalarModel) list.get(0)).getAsString();

            return ResourceBundles.getBoolAuto(BASE_NAME, key);
        }
    }

    /**
     * params: key:String
     */
    public static class UIBundleGetInt implements TemplateMethodModelEx {

        @Override
        public Object exec(List list) throws TemplateModelException {
            if (list.size() < 1) {
                throw new IllegalArgumentException("缺少参数");
            }
            String key = ((TemplateScalarModel) list.get(0)).getAsString();
            return ResourceBundles.getIntAuto(BASE_NAME, key);
        }
    }

    /**
     * params: key:String
     */
    public static class UIBundleGetStrings implements TemplateMethodModelEx {

        @Override
        public Object exec(List list) throws TemplateModelException {
            if (list.size() < 1) {
                throw new IllegalArgumentException("缺少参数");
            }
            String key = ((TemplateScalarModel) list.get(0)).getAsString();

            return ResourceBundles.getStrsAuto(BASE_NAME, key);
        }
    }

    /**
     * params: key:String
     */
    public static class UIBundleContains implements TemplateMethodModelEx {

        @Override
        public Object exec(List list) throws TemplateModelException {
            if (list.size() < 1) {
                throw new IllegalArgumentException("缺少参数");
            }

            String key = ((TemplateScalarModel) list.get(0)).getAsString();

            return ResourceBundles.containsKeyAuto(BASE_NAME, key);
        }
    }

    /**
     * 提供获取 ResourceBundle 更丰富的类型接口
     *
     * @author gaohui
     * @date 2013-05-15
     */
    public static class ResourceBundles {

        public static boolean containsKeyAuto(String baseName, String key) {
            return containsKey(baseName, key, appLocale());
        }

        public static boolean containsKey(String baseName, String key, String locale) {
            ResourceBundle resourceBundle = getRes(baseName, locale);

            return resourceBundle.containsKey(key);
        }

        /**
         * 根据 key 将值做为 String 返回.
         * 如果有 locale 配置，则应用其 locale；无则默认
         *
         * @param baseName
         * @param key
         * @return
         */
        public static String getStrAuto(String baseName, String key) {
            return getStr(baseName, key, appLocale());
        }

        public static String getStr(String baseName, String key, String locale) {
            ResourceBundle resourceBundle = getRes(baseName, locale);
            //   如果存在
            if (resourceBundle.containsKey(key)) {
                return resourceBundle.getString(key);
            } else {
                return null;
            }
        }

        /**
         * 根据 key 将值做为 boolean 返回.
         * 如果有 locale 配置，则应用其 locale；无则默认
         *
         * @param baseName
         * @param key
         * @return
         */
        public static boolean getBoolAuto(String baseName, String key) {
            return getBool(baseName, key, appLocale());
        }

        public static boolean getBool(String baseName, String key, String locale) {
            ResourceBundle resourceBundle = getRes(baseName, locale);

            if (!resourceBundle.containsKey(key)) {
                Exception e = new IllegalArgumentException("配置文件中没有对应的 key");
                log.error("", e);
                return false;
            }

            return Boolean.parseBoolean(resourceBundle.getString(key));
        }

        /**
         * 根据 key 将值做为 int 返回
         * 如果有 locale 配置，则应用其 locale；无则默认
         *
         * @param baseName
         * @param key
         * @return
         */
        public static int getIntAuto(String baseName, String key) {
            return getInt(baseName, key, appLocale());
        }

        public static int getInt(String baseName, String key, String locale) {
            ResourceBundle resourceBundle = getRes(baseName, locale);

            if (!resourceBundle.containsKey(key)) {
                Exception e = new IllegalArgumentException("配置文件中没有对应的 key");
                log.error("", e);
            }

            String intStr = resourceBundle.getString(key);
            try {
                return Integer.parseInt(intStr);
            } catch (NumberFormatException e) {
                log.error("本地资源非整形格式", e);
            }


            return 0;
        }

        /**
         * <pre>
         *     根据 key 将值做为 List<String>, 多个值之间使用 ","  隔开
         *     </pre>
         *
         * @param baseName
         * @param key
         * @return
         */
        public static List<String> getStrsAuto(String baseName, String key) {
            return getStrs(baseName, key, appLocale());
        }

        public static List<String> getStrs(String baseName, String key, String locale) {
            ResourceBundle resourceBundle = getRes(baseName, locale);

            if (!resourceBundle.containsKey(key)) {
                return Collections.emptyList();
            }

            String s = resourceBundle.getString(key);
            Iterable<String> ss = Splitter.on(",").omitEmptyStrings().trimResults().split(s);
            return Lists.newArrayList(ss);
        }

        /**
         * 根据 baseName 获取资源，如果 locale 为 null 使用默认
         *
         * @param baseName
         * @param locale
         * @return
         */
        public static ResourceBundle getRes(String baseName, String locale) {
            ResourceBundle resourceBundle;
            if (Strings.isNullOrEmpty(locale)) {
                resourceBundle = ResourceBundle.getBundle(baseName);
            } else {
                resourceBundle = ResourceBundle.getBundle(baseName, new Locale(locale));
            }

            return resourceBundle;
        }
    }

    @VisibleForTesting
    public static String appLocale() {
        ConfigFactory.Configs appConfig = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        return appConfig.get("app.locale");
    }
}
