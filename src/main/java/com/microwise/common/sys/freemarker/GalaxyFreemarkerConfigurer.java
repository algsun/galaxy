package com.microwise.common.sys.freemarker;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.microwise.common.sys.ConfigFactory;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.List;

/**
 * Created by lijianfei on 15-3-20.
 */
public class GalaxyFreemarkerConfigurer extends FreeMarkerConfigurer {

    private static final Logger log = LoggerFactory.getLogger(GalaxyFreemarkerConfigurer.class);

    private Configuration configuration;
    private ConfigFactory.Configs sharedVariablesConfig = null;

    @Override
    public Configuration createConfiguration() throws IOException, TemplateException {
        log.debug("初始化");

        configuration = super.createConfiguration();
        initShareVariables(configuration);

        return configuration;
    }

    /**
     * 初始化共享变量, 从 common/freemarker/shared-variables.properties 加载
     *
     * @param configuration
     * @throws freemarker.template.TemplateModelException
     */
    private void initShareVariables(Configuration configuration) throws TemplateModelException {
        sharedVariablesConfig = ConfigFactory.getInstance().getConfig("common/freemarker/share-variables.properties");
        List<String> names = sharedVariablesConfig.names();
        log.debug("变量:{}", names.toString());
        for (String name : names) {
            Splitter splitter = Splitter.on(".").omitEmptyStrings().trimResults();
            List<String> fullName = Lists.newArrayList(splitter.split(name));

            //如果长度是 1 则直接加变量；如果长度大于 1 则需要在最后一个段之前添加 SimpleHash 包装之
            if (fullName.size() == 1) {
                String value = sharedVariablesConfig.get(name);
                try {
                    configuration.setSharedVariable(name, Class.forName(value).newInstance());
                } catch (TemplateModelException e) {
                    throw e;
                } catch (Exception e) {
                    log.error(String.format("加载共享变量:[%s:%s]", name, value), e);
                }
            } else {
                putSharedVariable(name, fullName, null, 0);
            }
        }
    }

    /**
     * put 有命名空间的变量，如：a.b.c=123
     *
     * @param name
     * @param fullNames
     * @param parentHashWrapper
     * @param index
     * @throws TemplateModelException
     */
    private void putSharedVariable(String name, List<String> fullNames, SimpleHash parentHashWrapper, int index) throws TemplateModelException {
        //if last
        if (index == fullNames.size() - 1) {
            String value = sharedVariablesConfig.get(name);
            try {
                TemplateModel model = ((Class<TemplateModel>) Class.forName(value)).newInstance();

                String nameSegment = fullNames.get(index);
                parentHashWrapper.put(nameSegment, model);
            } catch (Exception e) {
                log.error(String.format("加载共享变量:[%s:%s]", name, value), e);
            }
        }
        //if first
        else if (index == 0) {
            String nameSegment = fullNames.get(index);

            SimpleHash hashWrapper = (SimpleHash) configuration.getSharedVariable(nameSegment);
            if (hashWrapper == null) {
                hashWrapper = new SimpleHash();
                configuration.setSharedVariable(nameSegment, hashWrapper);
            }

            putSharedVariable(name, fullNames, hashWrapper, index + 1);
        } else {
            String nameSegment = fullNames.get(index);
            SimpleHash hashWrapper = (SimpleHash) parentHashWrapper.get(nameSegment);
            if (hashWrapper == null) {
                hashWrapper = new SimpleHash();
                parentHashWrapper.put(nameSegment, hashWrapper);
            }

            putSharedVariable(name, fullNames, hashWrapper, index + 1);
        }
    }

}
