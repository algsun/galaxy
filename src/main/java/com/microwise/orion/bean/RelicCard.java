package com.microwise.orion.bean;


/**
 * 文物藏品卡信息 实体
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3505
 */
public class RelicCard extends Relic {

    private static final long serialVersionUID = -3051782630337258602L;

    /**
     * 文物修复信息
     */
    private Restore restore;

    /**
     * 文物现状
     */
    private StatusQuo statusQuo;

    /**
     * 文物鉴定信息
     */
    private Appraisal appraisal;

    /**
     * 文物事故信息
     */
    private Accident accident;

    public RelicCard(Relic relic) {
        // TODO 需要 考虑其他方式重新封装 参数传递
        id = relic.getId();
        attachments = relic.getAttachments();
        catalogCode = relic.getCatalogCode();
        count = relic.getCount();
        era = relic.getEra();
        hasTag = relic.getHasTag();
        inscriptions = relic.getInscriptions();
        level = relic.getLevel();
        name = relic.getName();
        relicPropertielist = relic.getRelicPropertielist();
        siteId = relic.getSiteId();
        state = relic.getState();
        texture = relic.getTexture();
        totalCode = relic.getTotalCode();
        typeCode = relic.getTypeCode();
        zone = relic.getZone();
        photos = relic.getPhotos();
    }

    public Restore getRestore() {
        return restore;
    }

    public void setRestore(Restore restore) {
        this.restore = restore;
    }

    public StatusQuo getStatusQuo() {
        return statusQuo;
    }

    public void setStatusQuo(StatusQuo statusQuo) {
        this.statusQuo = statusQuo;
    }

    public Appraisal getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(Appraisal appraisal) {
        this.appraisal = appraisal;
    }

    public Accident getAccident() {
        return accident;
    }

    public void setAccident(Accident accident) {
        this.accident = accident;
    }

}
