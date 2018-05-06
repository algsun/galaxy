package com.microwise.biela.action;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.collect.ArrayListMultimap;
import com.microwise.biela.bean.vo.ComplianceRateVO;
import com.microwise.biela.bean.vo.MixtureVO;
import com.microwise.biela.bean.vo.NodeSensorInfoVO;
import com.microwise.biela.service.EvaluateService;
import com.microwise.biela.service.MapIndexService;
import com.microwise.biela.sys.Biela;
import com.microwise.blackhole.bean.AreaCodeCN;
import com.microwise.blackhole.service.AreaCodeService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 实时评估 EventAction
 *
 * @author li.jianfei
 * @date 2014-10-29
 */
@Beans.Action
@Biela
@Route("/biela/")
public class EvaluateAction {

    private Logger logger = LoggerFactory.getLogger(EvaluateAction.class);

    /**
     * 页面
     */
    public static final String _pagePath = "../evaluate/evaluate.ftl";

    @Autowired
    private MapIndexService mapIndexService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private AreaCodeService areaCodeService;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 监测指标id
     */
    private int sensorId;

    @Route("evaluate")
    public String evaluate() {
        return Results.ftl("/biela/pages/index/layout.ftl");
    }

    @Route("evaluate/siteRealtimeAvgData.json")
    public String siteRealtimeAvgData() {
        Map<String, List<NodeSensorInfoVO>> nodeSensorInfos = null;
        try {
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            List<Integer> ids = new ArrayList<Integer>();
            ids.add(Constants.ChartConstants.SENSORINFO_TMT);
            ids.add(Constants.ChartConstants.SENSORINFO_HUM);
            ids.add(Constants.ChartConstants.SENSORINFO_LX);
            ids.add(Constants.ChartConstants.SENSORINFO_UV);
            ids.add(Constants.ChartConstants.SENSORINFO_CO2);
            ids.add(Constants.ChartConstants.SENSORINFO_VOC_HS);
            nodeSensorInfos = mapIndexService.findSiteRealtimeAvg(ids, logicGroupId);
        } catch (Exception e) {
            logger.error("获取实时数据失败", e);
        }
        return Results.json().asRoot(nodeSensorInfos).done();
    }

    @Route("findSite")
    public String findSite() {
        List<SiteVO> siteVOs = null;
        try {
            siteVOs = siteService.findSite();
        } catch (Exception e) {
            logger.error("获取站点失败", e);
        }
        return Results.json().asRoot(siteVOs).done();
    }

    @Route("findMixture")
    public String findMixture() {
        List<SiteVO> siteVOList = new ArrayList<SiteVO>();
        try {
            int logicId = Sessions.createByAction().currentLogicGroup().getId();
            List<SiteVO> sites = dataCenterService.findSiteVOByLogicGroupId(logicId);
            Date date = DateTime.now().minusDays(2).toDate();
            for (SiteVO siteVO : sites) {
                List<MixtureVO> mixtureVOs = evaluateService.findMixture(siteVO.getSiteId(), date);
                siteVO.setMixtureVOs(mixtureVOs);
            }

            List<AreaCodeCN> areaCodes = areaCodeService.findAllArea();
            ArrayListMultimap<String, SiteVO> siteVOMap = ArrayListMultimap.create();
            for (SiteVO siteVO : sites) {
                if (siteVO.getMixtureVOs().size() != 0) {
                    String provinceName = getAreaName(siteVO.getAreaCode(), areaCodes, "");
                    siteVOMap.put(provinceName, siteVO);
                }
            }

            for (String site : siteVOMap.keySet()) {
                List<SiteVO> siteVOs = siteVOMap.get(site);
                List<MixtureVO> mixtureVOList = new ArrayList<MixtureVO>();

                float comRate32 = 0F;
                float comRate33 = 0F;
                float waveValue32 = 0F;
                float waveValue33 = 0F;
                int number = 0;
                for (int i = 0; i < siteVOs.size(); i++) {
                    SiteVO siteVO = siteVOs.get(i);
                    List<MixtureVO> mixtureVOs = siteVO.getMixtureVOs();
                    for (MixtureVO mixtureVO : mixtureVOs) {
                        if (mixtureVO.getSensorId() == Constants.ChartConstants.SENSORINFO_HUM) {
                            comRate32 += mixtureVO.getComRate();
                            waveValue32 += mixtureVO.getWaveValue();
                        } else {
                            comRate33 += mixtureVO.getComRate();
                            waveValue33 += mixtureVO.getWaveValue();
                        }
                    }
                    number++;
                }
                MixtureVO mixtureVO32 = new MixtureVO();
                mixtureVO32.setSensorId(Constants.ChartConstants.SENSORINFO_HUM);
                mixtureVO32.setComRate(comRate32 / number);
                mixtureVO32.setWaveValue(waveValue32 / number);

                MixtureVO mixtureVO33 = new MixtureVO();
                mixtureVO33.setSensorId(Constants.ChartConstants.SENSORINFO_TMT);
                mixtureVO33.setComRate(comRate33 / number);
                mixtureVO33.setWaveValue(waveValue33 / number);

                mixtureVOList.add(mixtureVO32);
                mixtureVOList.add(mixtureVO33);

                SiteVO siteVO = new SiteVO();
                siteVO.setProvinceName(site);
                siteVO.setMixtureVOs(mixtureVOList);
                siteVOList.add(siteVO);

            }
        } catch (Exception e) {
            logger.error("获取Mixture数据失败", e);
        }
        return Results.json().asRoot(siteVOList).done();
    }

    @Route("findComRate")
    public String findComRate() {
        List<ComplianceRateVO> comRateVOs = null;
        try {
            Date date = DateTime.now().minusDays(1).toDate();
            int siteLogicId = Sessions.createByAction().currentLogicGroup().getId();
            comRateVOs = evaluateService.findComRate(siteLogicId, date);
        } catch (Exception e) {
            logger.error("获取最大波动值失败", e);
        }
        return Results.json().asRoot(comRateVOs).done();
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
        return getReturnName(name);
    }


    private String getReturnName(String name) {
        if (name.startsWith("黑龙江")) {
            return "黑龙江";
        } else if (name.startsWith("内蒙古")) {
            return "内蒙古";
        } else {
            return name.substring(0, 2);
        }
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

    public static String get_pagePath() {
        return _pagePath;
    }
}
