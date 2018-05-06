package com.microwise.orion.proxy.impl;

import com.microwise.orion.bean.Texture;
import com.microwise.orion.proxy.TextureProxy;
import com.microwise.orion.service.InventoryService;
import com.microwise.orion.service.TextureService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liuzhu
 * @date 2016-7-14
 */
@Component
@Scope("prototype")
@Orion
public class TextureProxyImpl implements TextureProxy {

    /**
     * 盘点service
     */
    @Autowired
    private TextureService textureService;


    @Override
    public List<Texture> findTextures() {
        return textureService.findAllTexture();
    }
}
