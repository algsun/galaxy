package com.microwise.blueplanet.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microwise.biela.bean.vo.ComplianceRateVO;
import com.microwise.biela.bean.vo.LocationSensorVO;
import com.microwise.biela.bean.vo.MixtureVO;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 站点vo对象
 *
 * @author zhangpeng
 * @date 2013-1-17
 */
@ApiModel(description = "站点实体类")
@JsonIgnoreProperties({"zones", "devices"})
public class SiteVO {

    /**
     * 自增id
     */
    @ApiModelProperty("自增ID")
    private int id;

    /**
     * 站点id
     */
    @ApiModelProperty(value = "站点ID")
    private String siteId;

    /**
     * 站点名称
     */
    @ApiModelProperty(hidden = true)
    private String siteName;

    /**
     * 站点下的区域集合
     */
    @ApiModelProperty(hidden = true)
    private List<ZoneVO> zones;

    /**
     * 站点下的设备集合
     */
    @ApiModelProperty(hidden = true)
    private List<DeviceVO> devices;

    /**
     * 父站点组id
     */
    private int parentLogicGroupId;

    /**
     * 站点组名称
     */
    private String logicGroupName;

    /**
     * 站点达标率
     */
    private List<ComplianceRateVO> complianceRateVOs;

    /**
     * 站点监测指标最大值，最小值
     */
    private List<LocationSensorVO> locationSensorVOs;

    /**
     * 达标率，波动值vo
     */
    private List<MixtureVO> mixtureVOs;

    /**
     * 经度
     */
    private Double lngBaiDu;

    /**
     * 纬度
     */
    private Double latBaiDu;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 地区编码
     */
    private int areaCode;


    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public List<ZoneVO> getZones() {
        return zones;
    }

    public void setZones(List<ZoneVO> zones) {
        this.zones = zones;
    }

    public List<DeviceVO> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceVO> devices) {
        this.devices = devices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentLogicGroupId() {
        return parentLogicGroupId;
    }

    public void setParentLogicGroupId(int parentLogicGroupId) {
        this.parentLogicGroupId = parentLogicGroupId;
    }

    public String getLogicGroupName() {
        return logicGroupName;
    }

    public void setLogicGroupName(String logicGroupName) {
        this.logicGroupName = logicGroupName;
    }

    public List<ComplianceRateVO> getComplianceRateVOs() {
        return complianceRateVOs;
    }

    public void setComplianceRateVOs(List<ComplianceRateVO> complianceRateVOs) {
        this.complianceRateVOs = complianceRateVOs;
    }

    public List<LocationSensorVO> getLocationSensorVOs() {
        return locationSensorVOs;
    }

    public void setLocationSensorVOs(List<LocationSensorVO> locationSensorVOs) {
        this.locationSensorVOs = locationSensorVOs;
    }

    public List<MixtureVO> getMixtureVOs() {
        return mixtureVOs;
    }

    public void setMixtureVOs(List<MixtureVO> mixtureVOs) {
        this.mixtureVOs = mixtureVOs;
    }

    public Double getLngBaiDu() {
        return lngBaiDu;
    }

    public void setLngBaiDu(Double lngBaiDu) {
        this.lngBaiDu = lngBaiDu;
    }

    public Double getLatBaiDu() {
        return latBaiDu;
    }

    public void setLatBaiDu(Double latBaiDu) {
        this.latBaiDu = latBaiDu;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }
}
