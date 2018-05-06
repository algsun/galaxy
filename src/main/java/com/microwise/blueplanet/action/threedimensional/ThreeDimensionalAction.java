package com.microwise.blueplanet.action.threedimensional;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DimensionalLocationPO;
import com.microwise.blueplanet.bean.po.ThreeDimensionalPO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.ThreeDimensionalVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ThreeDimensionalService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王耕
 * @date 15-6-10
 */
@Beans.Action
@Blueplanet
public class ThreeDimensionalAction extends BlueplanetLoggerAction{
    public static final Logger log = LoggerFactory.getLogger(BlueplanetLoggerAction.class);

    // 内容页面
    private static final String _pagePath = "three-dimensional.ftl";

    @Autowired
    private ThreeDimensionalService threeDimensionalService;

    @Autowired
    private LocationService locationService;

    /**
     * 3d模型文档id
     */
    private int dimensionalId;

    /**
     * 3d模型文件实体集合
     */
    private List<ThreeDimensionalVO> threeDimensionals;

    @Route("/blueplanet/three-dimensional")
    public String view(){
        ActionMessage.createByAction().consume();
        String siteId = Sessions.createByAction().currentLogicGroup().getSite().getSiteId();
        try{
            List<ThreeDimensionalPO> tDimensionals = threeDimensionalService.findThreeDimensionals(siteId);
            threeDimensionals = dealWithDimensionalLocaitonRelations(tDimensionals,siteId);
            log("3d模型管理", "模型列表");
        }catch (Exception e){
            log.error("模型添加失败,模型添加失败", e);
        }
        return Results.ftl("/blueplanet/pages/threedimensional/layout");
    }

    @Route("/blueplanet/three-dimensional/deleteFile")
    public String deleteDimensionalFile(){
        try{
            ThreeDimensionalPO threeDimensionalPO = threeDimensionalService.findThreeDimenById(dimensionalId);
            threeDimensionalService.deleteDimensionalById(dimensionalId,threeDimensionalPO.getPath());
            ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.threeDimensional.deleteSuccess"));
            log("3d模型管理", "模型删除");
        }catch (Exception e){
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blueplanet.threeDimensional.delteFail"));
            log.error("3d模型管理,模型删除失败", e);
        }
        return "redirect:/blueplanet/three-dimensional";
    }

    /**
     * 查询设置每个模型的关联的位置点信息
     * @param tDimensionals 所有的模型
     * @param siteId 站点编号
     * @return 处理后的集合
     */
    private List<ThreeDimensionalVO> dealWithDimensionalLocaitonRelations(List<ThreeDimensionalPO> tDimensionals,String siteId){
        List<ThreeDimensionalVO> returnList = new ArrayList<ThreeDimensionalVO>();
        for(ThreeDimensionalPO threeDimensional : tDimensionals){
            ThreeDimensionalVO threeDimensionalVO = new ThreeDimensionalVO();
            threeDimensionalVO.setId(threeDimensional.getId());
              String Path =threeDimensional.getPath().substring(0, threeDimensional.getPath().lastIndexOf("."));
            threeDimensionalVO.setPath(Path);
            threeDimensionalVO.setUploadtime(threeDimensional.getUploadtime());
            threeDimensionalVO.setSiteId(threeDimensional.getSiteId());
            threeDimensionalVO.setRemark(threeDimensional.getRemark());
            List<DimensionalLocationPO> dimensionalLocationPOs = threeDimensionalService.findDimensionalLocationRelations(siteId, threeDimensional.getId());
            List<LocationVO> locations = new ArrayList<LocationVO>();
            for(DimensionalLocationPO dimensionalLocationPO : dimensionalLocationPOs){
                LocationVO locationVO = locationService.findLocationById(dimensionalLocationPO.getLocationId());
                locations.add(locationVO);
            }
            threeDimensionalVO.setLocationVOs(locations);
            returnList.add(threeDimensionalVO);
        }
        return returnList;
    }

    public static String get_pagePath() {
        return _pagePath;
    }


    public List<ThreeDimensionalVO> getThreeDimensionals() {
        return threeDimensionals;
    }

    public void setThreeDimensionals(List<ThreeDimensionalVO> threeDimensionals) {
        this.threeDimensionals = threeDimensionals;
    }

    public int getDimensionalId() {
        return dimensionalId;
    }

    public void setDimensionalId(int dimensionalId) {
        this.dimensionalId = dimensionalId;
    }
}
