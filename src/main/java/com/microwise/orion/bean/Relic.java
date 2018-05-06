package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microwise.proxima.bean.Zone;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文物基本信息实体
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3909
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Relic implements Serializable {

    /**
     * 文物修复属性标识常量
     */
    public static final int RELIC_PROPERTY_REPAIR = 4;

    /**
     * 文物藏品卡属性标识常量
     */
    public static final Integer RELIC_PROPERTY_CARD = 0;

    /**
     * 文物档案属性标识常量
     */
    public static final Integer RELIC_PROPERTY_ARCHIVES = 1;

    /**
     * 文物档案和藏品卡共同属性标识常量
     */
    public static final Integer RELIC_PROPERTY_ARCHIVES_AND_CARD = 2;

    private static final long serialVersionUID = 1L;

    /**
     * 在库常量
     */
    public static final int STATE_IN = 0;

    /**
     * 出库申请中
     */
    public static final int STATE_APPLYING_OUT = 3;

    /**
     * 待出库常量
     */
    public static final int STATE_TO_BE_OUT = 1;

    /**
     * 出库常量
     */
    public static final int STATE_OUT = 2;

    /**
     * id 编号
     */
    protected Integer id;

    /**
     * m_location_relic 表id
     */
    protected int locationRelicId;

    /**
     * 位置点ID
     */
    protected String locationId;

    /**
     * 总登记号
     */
    protected String totalCode;

    /**
     * 编目号
     */
    protected String catalogCode;

    /**
     * 分类号
     */
    protected String typeCode;

    /**
     * 名称
     */
    protected String name;

    /**
     * 时代
     */
    protected Era era;

    /**
     * 级别
     */
    protected Level level;

    /**
     * 质地
     */
    protected Texture texture;

    /**
     * 件数
     */
    protected Integer count;

    /**
     * 区域
     */
    protected Zone zone;

    /**
     * 站点
     */
    protected String siteId;

    /**
     * 文物状态：0、在库 ;1、待出库；2、出库
     */
    protected int state = STATE_IN;

    /**
     * 是否有电子标签：
     */
    protected Boolean hasTag = false;

    /**
     * 是否注销 false 未 注销 true 注销
     */
    protected Boolean iscanceled = false;

    /**
     * 文物属性信息list
     */
    protected List<RelicProperty> relicPropertielist;

    /**
     * 文物属性信息Set
     */
    protected Set<Property> relicPropertiySet;

    /**
     * 文物属性信息map
     */
    protected Map<String, RelicProperty> relicPropertyMap;

    /**
     * 文物修复信息
     */
    protected Set<Restore> restores;

    /**
     * 文物现状
     */
    protected Set<StatusQuo> statusQuos;

    /**
     * 文物健康评测
     */
    protected Set<HealthEvaluation> healthEvaluations;

    /**
     * 文物鉴定信息
     */
    protected Set<Appraisal> appraisals;

    /**
     * 文物事故信息
     */
    protected Set<Accident> accidents;

    /**
     * 文物 挂接文档信息
     */
    protected Set<Attachment> attachments;

    /**
     * 文物铭文题跋
     */
    protected Set<Inscription> inscriptions;

    /**
     * 文物相片列表
     */
    protected Set<Photo> photos;

    /**
     * 文物拓片列表
     */
    protected Set<Rubbing> rubbings;


    /**
     * 文物标签
     */
    private Set<RelicLabel> relicLabels;

    private String stateName;

    /**
     * 流传经历列表
     */
    protected Set<Rove> roves;


    public Relic() {
        super();
    }

    public Set<HealthEvaluation> getHealthEvaluations() {
        return healthEvaluations;
    }

    public void setHealthEvaluations(Set<HealthEvaluation> healthEvaluations) {
        this.healthEvaluations = healthEvaluations;
    }

    public Set<RelicLabel> getRelicLabels() {
        return relicLabels;
    }

    public void setRelicLabels(Set<RelicLabel> relicLabels) {
        this.relicLabels = relicLabels;
    }

    public Set<Rove> getRoves() {
        return roves;
    }

    public void setRoves(Set<Rove> roves) {
        this.roves = roves;
    }

    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Boolean getHasTag() {
        return hasTag;
    }

    public void setHasTag(Boolean hasTag) {
        this.hasTag = hasTag;
    }

    public Era getEra() {
        return era;
    }

    public void setEra(Era era) {
        this.era = era;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Set<Restore> getRestores() {
        return restores;
    }

    public void setRestores(Set<Restore> restores) {
        this.restores = restores;
    }

    public Set<StatusQuo> getStatusQuos() {
        return statusQuos;
    }

    public void setStatusQuos(Set<StatusQuo> statusQuos) {
        this.statusQuos = statusQuos;
    }

    public Set<Appraisal> getAppraisals() {
        return appraisals;
    }

    public void setAppraisals(Set<Appraisal> appraisals) {
        this.appraisals = appraisals;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Rubbing> getRubbings() {
        return rubbings;
    }

    public void setRubbings(Set<Rubbing> rubbings) {
        this.rubbings = rubbings;
    }

    public Set<Accident> getAccidents() {
        return accidents;
    }

    public void setAccidents(Set<Accident> accidents) {
        this.accidents = accidents;
    }

    public List<RelicProperty> getRelicPropertielist() {
        return relicPropertielist;
    }

    public void setRelicPropertielist(List<RelicProperty> relicPropertielist) {
        this.relicPropertielist = relicPropertielist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Property> getRelicPropertiySet() {
        return relicPropertiySet;
    }

    public void setRelicPropertiySet(Set<Property> relicPropertiySet) {
        this.relicPropertiySet = relicPropertiySet;
    }

    /**
     * 在get 方法中将 relicPropertyList 转换成 map
     *
     * @author 许保吉
     * @date 2013-5-16
     */
    public Map<String, RelicProperty> getRelicPropertyMap() {
        relicPropertyMap = new HashMap<String, RelicProperty>();
        for (RelicProperty relicProperty : relicPropertielist) {
            relicPropertyMap.put(relicProperty.getProperty().getEnName(), relicProperty);
        }
        return relicPropertyMap;
    }

    public Boolean getIscanceled() {
        return iscanceled;
    }

    public void setIscanceled(Boolean iscanceled) {
        this.iscanceled = iscanceled;
    }

    public int getLocationRelicId() {
        return locationRelicId;
    }

    public void setLocationRelicId(int locationRelicId) {
        this.locationRelicId = locationRelicId;
    }

    public String getStateName() {
        //String stateName="";
        switch (state) {
            case 0:
                stateName = "在库";
                break;
            case 1:
                stateName = "待出库";
                break;
            case 2:
                stateName = "出库";
                break;
            case 3:
                stateName = "出库申请中";
                break;

        }
        return stateName;
    }

}
