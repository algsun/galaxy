package com.microwise.orion.util;

import com.microwise.common.sys.ApplicationListener;

/**
 * 处理 orion 资源路径工具类
 *
 * @author gaohui
 * @date 13-6-21 11:43
 */
public class ResourcePaths {

    /**
     * 返回应用根目录
     *
     * @return
     */
    public static String appBasePath() {
        return ApplicationListener.getWebAppBasePath();
    }

    /**
     * 文物图片目录
     *
     * @param siteId
     * @param relicId
     * @return
     */
    public static String imagesDirOfRelic(String siteId, int relicId) {
        return com.microwise.blackhole.sys.ResourcePaths.galaxyResourcesDir("orion") + "/images/" + siteId + "/" + relicId;
    }

}
