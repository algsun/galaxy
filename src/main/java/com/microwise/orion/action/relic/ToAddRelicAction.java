package com.microwise.orion.action.relic;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Era;
import com.microwise.orion.bean.Level;
import com.microwise.orion.bean.Property;
import com.microwise.orion.bean.Texture;
import com.microwise.orion.service.EraService;
import com.microwise.orion.service.LevelService;
import com.microwise.orion.service.PropertyService;
import com.microwise.orion.service.TextureService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.microwise.proxima.bean.Zone;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 跳转到文物入藏页面
 *
 * @author Wang rensong
 * @time 13-5-11
 */
@Beans.Action
@Orion
public class ToAddRelicAction extends OrionLoggerAction {

    /**
     * 时代
     */
    @Autowired
    private EraService eraService;

    /**
     * 质地
     */
    @Autowired
    private TextureService textureService;

    /**
     * 级别
     */
    @Autowired
    private LevelService levelService;

    /**
     * 文物属性
     */
    @Autowired
    private PropertyService propertyService;

    //Output
    /**
     * 页面是否展开全部藏品卡信息
     */
    private boolean inputView;

    /**
     * 查询时代列表
     */
    private List<Era> listEra;

    /**
     * 查询级别列表
     */
    private List<Level> listLevel;

    /**
     * 查询质地列表
     */
    private List<Texture> listTexture;

    /**
     * 查询区域列表
     */
    private List<Zone> listZone;

    /**
     * 查询文物扩展信息
     */
    private List<Property> proList;

    /**
     * 入藏页面跳转
     *
     * @return
     */
    public String execute() {
        try {
            //质地，级别，时代信息初始化
            listEra = eraService.findAllEra();
            listLevel = levelService.findAllLevel();
            listTexture = textureService.findAllTexture();
            //扩展属性初始化
            proList = new ArrayList<Property>(propertyService.findAllProperty());
        } catch (Exception e) {
            logFailed("进入文物入藏页面", "进入文物入藏页面属性获取失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public boolean isInputView() {
        return inputView;
    }

    public void setInputView(boolean inputView) {
        this.inputView = inputView;
    }

    public List<Era> getListEra() {
        return listEra;
    }

    public void setListEra(List<Era> listEra) {
        this.listEra = listEra;
    }

    public List<Level> getListLevel() {
        return listLevel;
    }

    public void setListLevel(List<Level> listLevel) {
        this.listLevel = listLevel;
    }

    public List<Texture> getListTexture() {
        return listTexture;
    }

    public void setListTexture(List<Texture> listTexture) {
        this.listTexture = listTexture;
    }

    public List<Zone> getListZone() {
        return listZone;
    }

    public void setListZone(List<Zone> listZone) {
        this.listZone = listZone;
    }

    public List<Property> getProList() {
        return proList;
    }

    public void setProList(List<Property> proList) {
        this.proList = proList;
    }

}

