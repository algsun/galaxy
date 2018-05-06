package com.microwise.blueplanet.action.texture;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.TextureThresholdVO;
import com.microwise.blueplanet.service.SensorinfoService;
import com.microwise.blueplanet.service.TextureThresholdService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Texture;
import com.microwise.orion.proxy.TextureProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 质地阈值action
 */
@Beans.Action
@Blueplanet
public class TextureThresholdAction extends BlueplanetLoggerAction {


    public static final Logger log = LoggerFactory.getLogger(TextureThresholdAction.class);
    /**
     * 内容页面
     */
    private static final String _pagePath = "index.ftl";


    /**
     * 质地代理
     */
    @Autowired
    private TextureProxy textureProxy;

    /**
     * 监测指标service
     */
    @Autowired
    private SensorinfoService sensorinfoService;

    /**
     * 地址阈值service
     */
    @Autowired
    private TextureThresholdService textureThresholdService;

    /**
     * 质地list
     */
    private List<Texture> textures;

    /**
     * 监测指标list
     */
    private List<SensorinfoVO> sensorinfos;

    /**
     * 质地阈值list
     */
    private List<TextureThresholdVO> textureThresholdVOs;

    /**
     * 质地阈值vo
     */
    private TextureThresholdVO textureThresholdVO;

    /**
     * 默认展开第一个阈值(table tab)
     */
    private int textureId = 1;

    /**
     * 阈值实体
     */
    private Texture texture;

    /**
     * m_texture_threshold表id
     */
    private int id;

    /**
     * 监测指标id
     */
    private int sensorId;


    @Route("/blueplanet/texture-threshold")
    public String view() {
        try {
            textures = textureProxy.findTextures();
            for (Texture t : textures) {
                //当前质地的vo 前端页面做展示用
                if (t.getId() == textureId) {
                    texture = t;
                }

                //标记已设置的质地
                List<TextureThresholdVO> textureThresholds = textureThresholdService.findTextureThresholds(t.getId());
                if (textureThresholds.size() != 0) {
                    t.setSetting(true);
                }
            }


            sensorinfos = sensorinfoService.findSensorinfo();
            ActionMessage.createByAction().consume();
        } catch (Exception e) {
            log.error("质地阈值", e);
        }
        return Results.ftl("/blueplanet/pages/texture/layout");
    }

    @Route("/blueplanet/findTextureThresholds")
    public String findTextureThresholds() {
        try {
            textureThresholdVOs = textureThresholdService.findTextureThresholds(textureId);
        } catch (Exception e) {
            log.error("质地阈值", e);
        }
        return Results.json().asRoot(textureThresholdVOs).done();
    }

    @Route("/blueplanet/insert-texture-threshold")
    public String insert() {
        try {
            textureThresholdService.insertTextureThreshold(textureThresholdVO);
            ActionMessage.createByAction().success("生成成功");
            return Results.json().asRoot(true).done();
        } catch (Exception e) {
            log.error("质地阈值", e);
            ActionMessage.createByAction().fail("生成失败");
            return Results.json().asRoot(false).done();
        }
    }

    @Route("/blueplanet/isExist-texture-threshold")
    public String isExist() {
        try {
            TextureThresholdVO textureThreshold = textureThresholdService.findTextureThreshold(textureThresholdVO);
            if (textureThreshold == null) {
                return Results.json().asRoot(true).done();
            } else {
                return Results.json().asRoot(false).done();
            }
        } catch (Exception e) {
            log.error("质地阈值", e);
            return Results.json().asRoot(false).done();
        }
    }

    @Route("/blueplanet/delete-texture-threshold")
    public String delete() {
        try {
            textureThresholdService.deleteTextureThreshold(id, textureId, sensorId);
            ActionMessage.createByAction().success("删除成功");
            return Results.json().asRoot(true).done();
        } catch (Exception e) {
            log.error("质地阈值", e);
            ActionMessage.createByAction().fail("删除失败");
            return Results.json().asRoot(false).done();
        }
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<Texture> getTextures() {
        return textures;
    }

    public void setTextures(List<Texture> textures) {
        this.textures = textures;
    }

    public List<SensorinfoVO> getSensorinfos() {
        return sensorinfos;
    }

    public void setSensorinfos(List<SensorinfoVO> sensorinfos) {
        this.sensorinfos = sensorinfos;
    }

    public TextureThresholdVO getTextureThresholdVO() {
        return textureThresholdVO;
    }

    public void setTextureThresholdVO(TextureThresholdVO textureThresholdVO) {
        this.textureThresholdVO = textureThresholdVO;
    }

    public List<TextureThresholdVO> getTextureThresholdVOs() {
        return textureThresholdVOs;
    }

    public void setTextureThresholdVOs(List<TextureThresholdVO> textureThresholdVOs) {
        this.textureThresholdVOs = textureThresholdVOs;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }
}
