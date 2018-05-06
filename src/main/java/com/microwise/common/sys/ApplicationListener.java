package com.microwise.common.sys;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.sys.Subsystems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.Map;

/**
 * 应用系统监听器
 *
 * @author gaohui
 * @date 12-11-27 14:29
 */
public class ApplicationListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(ApplicationListener.class);

    /**
     * 系统配置文件
     */
    public static final String APP_CONFIG_PATH = "config.properties";
    /** 应用根目录 */
    private static String webAppBasePath;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.debug("系统启动");

        ServletContext servletContext = servletContextEvent.getServletContext();
        webAppBasePath = servletContext.getRealPath("/");
        //系统启动时间
        servletContext.setAttribute("app.startTime", new Date());
        initAppConfig(servletContext);
//        initSubsystems(servletContext);
    }

//    /**
//     *todo application获取子系统名称，国际化以后改为session动态获取。
//     *
//     * 初始化业务系统     *
//     * @param servletContext
//     */
//    private void initSubsystems(ServletContext servletContext) {
//        Map<String, Subsystem> subsystemMap = Maps.uniqueIndex(Subsystems.all(), new Function<Subsystem, String>() {
//            @Override
//            public String apply(Subsystem subsystem) {
//                return subsystem.getSubsystemCode();
//            }
//        });
//        servletContext.setAttribute("app.subsystems", subsystemMap);
//    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.debug("系统停止");
    }

    /**
     * 初始化系统配置
     *
     * @param servletContext
     */
    private void initAppConfig(ServletContext servletContext) {
        ConfigFactory.Configs appConfig = ConfigFactory.getInstance().getConfig(APP_CONFIG_PATH);
        // 应用程序模式
        String appStage = null;
        // 是否是开发者
        String iAmHacker = System.getenv("I_AM_HACKER");
        if(Strings.isNullOrEmpty(iAmHacker)){
            appStage = appConfig.get("app.stage");
        }else{
            appStage = "develop";
        }
        if (Strings.isNullOrEmpty(appStage)) {
            // 如果没有配置，默认为 "develop"
            servletContext.setAttribute("app.stage.product", false);
            servletContext.setAttribute("app.stage.develop", true);
            servletContext.setAttribute("app.stage.test", false);
        } else {
            servletContext.setAttribute("app.stage.product", false);
            servletContext.setAttribute("app.stage.develop", false);
            servletContext.setAttribute("app.stage.test", false);
            if (appStage.equals("product")) {
                servletContext.setAttribute("app.stage.product", true);
            } else if (appStage.equals("develop")) {
                servletContext.setAttribute("app.stage.develop", true);
            } else if (appStage.equals("test")) {
                servletContext.setAttribute("app.stage.test", true);
            }
        }

        // 应用程序定制
        String appLocale = appConfig.get("app.locale");
        if (Strings.isNullOrEmpty(appLocale)) {
        } else {
            servletContext.setAttribute("app.locale", appLocale);
        }
    }

    public static String getWebAppBasePath() {
        return webAppBasePath;
    }
}
