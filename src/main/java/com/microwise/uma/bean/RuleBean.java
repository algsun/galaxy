package com.microwise.uma.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 行为规则实体对象
 *
 * @author xubaoji
 * @date 2013-4-15
 */
@JsonIgnoreProperties({"siteId", "enable", "deviceList", "goRuleList", "backRuleList", "singleRuleList",
        "zoneName", "zoneId", "parentId"})
public class RuleBean {
    // 单程
    public static final int TYPE_SINGLE = 1;
    // 往返
    public static final int TYPE_CENSUS = 2;
    // 往
    public static final int TYPE_GO = 3;
    // 返
    public static final int TYPE_BACK = 4;


    // 规则编号
    private Integer id;

    // 父规则编号
    private Integer parentId;

    // 区域ID
    private String zoneId;

    // 区域名称
    private String zoneName;

    // 规则名称
    private String ruleName;

    // 规则类型 1 单程： 2 往返   3:往  4：返
    private Integer type;

    // 隶属站点
    private String siteId;

    // 是否启用(true 启用, false 停用)
    private boolean enable;

    // 该规则 激发器 列表（按联动顺序排序）
    private List<DeviceBean> deviceList;

    /**
     * 往规则列表
     */
    private List<RuleBean> goRuleList;

    /**
     * 返规则列表
     */
    private List<RuleBean> backRuleList;

    /**
     * 单程子规则列表
     */
    private List<RuleBean> singleRuleList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<DeviceBean> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceBean> deviceList) {
        this.deviceList = deviceList;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<RuleBean> getGoRuleList() {
        return goRuleList;
    }

    public void setGoRuleList(List<RuleBean> goRuleList) {
        this.goRuleList = goRuleList;
    }

    public List<RuleBean> getBackRuleList() {
        return backRuleList;
    }

    public void setBackRuleList(List<RuleBean> backRuleList) {
        this.backRuleList = backRuleList;
    }

    public List<RuleBean> getSingleRuleList() {
        return singleRuleList;
    }

    public void setSingleRuleList(List<RuleBean> singleRuleList) {
        this.singleRuleList = singleRuleList;
    }

}
