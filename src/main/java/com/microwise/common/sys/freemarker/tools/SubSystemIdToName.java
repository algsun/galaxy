package com.microwise.common.sys.freemarker.tools;

import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.sys.Subsystems;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * freemarker扩展类 实现将业务系统的id转换成其名称
 *
 * @Author: Wang yunlong
 * @Date: 12-11-21
 * @Time: 上午11:01
 */
public class SubSystemIdToName implements TemplateMethodModel {
    @SuppressWarnings("unchecked")
    public Object exec(List args) throws TemplateModelException {
        if (args.size() < 1) {
            throw new TemplateModelException("没有传递角色参数");
        }
        List<Subsystem> subsystems = Subsystems.all();
        String name = "";
        for (Subsystem s : subsystems) {
            if (s.getSubsystemId() == Integer.parseInt((String) args.get(0))) {
                name = s.getSubsystemName();
            }
        }
        return name;
    }
}

