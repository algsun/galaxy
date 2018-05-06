package com.microwise.biela.action;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.biela.service.EvaluateService;
import com.microwise.biela.sys.Biela;
import com.microwise.blackhole.bean.AreaCodeCN;
import com.microwise.blackhole.service.AreaCodeService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 综合评估.
 */
@Beans.Action
@Biela
@Route("/biela/")
public class ComprehensiveAction {

    /**
     * 页面
     */
    public static final String _pagePath = "../evaluate/comprehensive.ftl";

    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private AreaCodeService areaCodeService;

    /**
     * 被选择的省份名称
     */
    private String name;

    private String siteId;

    private int sensorId;

    @Route("comprehensive")
    public String evaluate() {
        return Results.ftl("/biela/pages/index/layout.ftl");
    }

    @Route("findSiteComplianceRate")
    public String findSiteComplianceRate() {
        int siteLogicId = Sessions.createByAction().currentLogicGroup().getId();
        List<SiteVO> siteComplianceRateList = dataCenterService.findSiteVOByLogicGroupId(siteLogicId);

        List<AreaCodeCN> areaCodes = areaCodeService.findAllArea();
        List<SiteVO> siteVOList = new ArrayList<SiteVO>();
        for (SiteVO comRate : siteComplianceRateList) {
            String provinceName = getAreaName(comRate.getAreaCode(), areaCodes, "");
            if (provinceName.contains(name)) {
                Date date = DateTime.now().minusDays(1).toDate();
                comRate.setComplianceRateVOs(evaluateService.findComplianceRates(comRate.getSiteId(), date));
                siteVOList.add(comRate);
            }
        }
        return Results.json().asRoot(siteVOList).done();
    }

    /**
     * 柱状图
     *
     * @return
     */
    @Route("findSiteAvgPeak")
    public String findSiteAvgPeak() {
        Date date = DateTime.now().minusDays(8).toDate();
        SiteVO siteVO = new SiteVO();
        siteVO.setSiteId(siteId);
        siteVO.setLocationSensorVOs(evaluateService.findAvgPeak(siteId, date, sensorId));
        return Results.json().asRoot(siteVO).done();
    }

    /**
     * 根据areaCode递归获得省/直辖市名称(省/直辖市没有父级areaCode)
     *
     * @param areaCode  当前areaCode
     * @param areaCodes 所有的区域集合
     * @return 省/直辖市名称
     */
    private String getAreaName(int areaCode, List<AreaCodeCN> areaCodes, String name) {
        AreaCodeCN parentArea = getParentArea(areaCode, areaCodes);
        if (parentArea != null) {
            name = getAreaName(parentArea.getAreaCode(), areaCodes, name);
        } else {
            for (AreaCodeCN a : areaCodes) {
                if (a.getAreaCode() == areaCode) {
                    name = a.getAreaName();
                }
            }
        }
        return name;
    }

    private AreaCodeCN getParentArea(int areaCode, List<AreaCodeCN> areaCodes) {
        AreaCodeCN area = new AreaCodeCN();
        for (AreaCodeCN areaCodeCN : areaCodes) {
            if (areaCode == areaCodeCN.getAreaCode()) {
                area = areaCodeCN.getParentAreaCodeCN();
            }
        }
        return area;
    }


    public static String get_pagePath() {
        return _pagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }
}
