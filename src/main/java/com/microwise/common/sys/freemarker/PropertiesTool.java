package com.microwise.common.sys.freemarker;

import com.microwise.common.sys.ConfigFactory;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * 读取配置文件工具类
 *
 * @author lijianfei
 * @date 2015-10-30
 */
public class PropertiesTool implements TemplateMethodModel {
    public Object exec(List list) throws TemplateModelException {
        if (list.size() < 2) {
            throw new TemplateModelException("缺少参数");
        }
        return ConfigFactory.getInstance().getConfig(list.get(0).toString()).get(list.get(1).toString());
    }
}
