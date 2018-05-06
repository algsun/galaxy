package com.microwise.biela.bean.po;

import com.microwise.biela.bean.vo.LocationInfoVO;

import java.util.List;

/**
 * @author liuzhu
 * @date 14-1-2
 */
public class SitePO {

    /**
     * 站点组id
     */
    private int id;

    /**
     * 站点组名称
     */
    private String logicGroupName;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 父级站点组
     */
    private int parentLogicGroupId;

    /**
     * 经度
     */
    private double lngBaiDu;

    /**
     * 纬度
     */
    private double latBaiDu;

    /**
     * 温度稳定性
     */
    private Float temperatureStability;

    /**
     * 温度等级
     */
    private String temperatureRank;

    /**
     * 所属区域
     */
    private AreaCodePO areaCodePO;

    /**
     * 监测指标list
     */
    private List<LocationInfoVO> nodeSensorInfoVOList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogicGroupName() {
        return logicGroupName;
    }

    public void setLogicGroupName(String logicGroupName) {
        this.logicGroupName = logicGroupName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getParentLogicGroupId() {
        return parentLogicGroupId;
    }

    public void setParentLogicGroupId(int parentLogicGroupId) {
        this.parentLogicGroupId = parentLogicGroupId;
    }

    public double getLngBaiDu() {
        return lngBaiDu;
    }

    public void setLngBaiDu(double lngBaiDu) {
        this.lngBaiDu = lngBaiDu;
    }

    public double getLatBaiDu() {
        return latBaiDu;
    }

    public void setLatBaiDu(double latBaiDu) {
        this.latBaiDu = latBaiDu;
    }

    public AreaCodePO getAreaCodePO() {
        return areaCodePO;
    }

    public void setAreaCodePO(AreaCodePO areaCodePO) {
        this.areaCodePO = areaCodePO;
    }

    public Float getTemperatureStability() {
        return temperatureStability;
    }

    public void setTemperatureStability(Float temperatureStability) {
        this.temperatureStability = temperatureStability;
    }

    public String getTemperatureRank() {
        return temperatureRank;
    }

    public void setTemperatureRank(String temperatureRank) {
        this.temperatureRank = temperatureRank;
    }

    public List<LocationInfoVO> getNodeSensorInfoVOList() {
        return nodeSensorInfoVOList;
    }

    public void setNodeSensorInfoVOList(List<LocationInfoVO> nodeSensorInfoVOList) {
        this.nodeSensorInfoVOList = nodeSensorInfoVOList;
    }
}
