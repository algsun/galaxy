package com.microwise.orion.action.relic;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Texture;
import com.microwise.orion.service.TextureService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 编辑质地
 *
 * @author xuyuexi
 * @time 14-6-4
 */
@Beans.Action
@Orion
public class editTextureAction extends OrionLoggerAction {


    /**
     * 文物属性信息
     */
    @Autowired
    private TextureService textureService;

    private String textureName;

    @Route(value = "orion/saveTexture", params = "textureName")
    public String excute() {
        Map map = new HashMap();
        if (textureService.isTextureExist(textureName)) {
            map.put("success", null);
            return Results.json().asRoot(map).done();
        } else {
            Texture texture = new Texture();
            texture.setName(textureName);
            try {
                int i = textureService.save(texture);
                map.put("textureId", i);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("success", "error");
                return Results.json().asRoot(map).done();
            }
        }
        map.put("success", "success");
        return Results.json().asRoot(map).done();
    }


    @Route(value = "orion/deleteTexture", params = "textureName")
    public String delete() {
        Map map = new HashMap();
        try {
            int i = textureService.delete(textureName);
            map.put("textureId", i);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", "error");
            return Results.json().asRoot(map).done();
        }
        map.put("success", "success");
        return Results.json().asRoot(map).done();
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }
}

