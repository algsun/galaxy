package com.microwise.blackhole.sys;

import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author liuzhu
 * @date 13-9-23
 */
public class ResourcePaths {
    public static final Logger log = LoggerFactory.getLogger(ResourcePaths.class);

    /**
     * 获取资源路径
     *
     * @param dir 相对 galaxy-resources 目录, 路径前“/”可选
     * @return 相对路径或绝对路径
     */
    public static String galaxyResourcesDir(String dir) {
        ConfigFactory.Configs config = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);

        String baseDir = config.get(Constants.Config.RESOURCES_DIR);
        int dirRelativeMode = Integer.parseInt(config.get(Constants.Config.RESOURCES_DIR_RELATIVE));
        //相对路径
        if (dirRelativeMode == 1) {
            log.debug("allProperties:{}", System.getProperties());
            String tomcatPath = System.getProperty("catalina.home") + "/webapps/";

            return new File(tomcatPath + baseDir, dir).getPath();
        }
        // 绝对地址
        else if (dirRelativeMode == 0) {

            return new File(baseDir, dir).getPath();
        } else {
            throw new IllegalArgumentException("未知参数" + dirRelativeMode);
        }
    }

}
