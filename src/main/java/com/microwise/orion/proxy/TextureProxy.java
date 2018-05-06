package com.microwise.orion.proxy;

import com.microwise.orion.bean.Texture;

import java.util.List;

/**
 * 质地代理
 */
public interface TextureProxy {

    /**
     * 查询所有质地
     *
     * @return List<Texture> 所有质地
     * @author liuzhu
     * @date 2016-7-14
     */
    List<Texture> findTextures();
}
