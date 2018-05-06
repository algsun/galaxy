package com.microwise.orion.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.*;
import com.microwise.orion.dao.*;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.vo.RelicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文物 service 实现类
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-05 zhangpeng svn:4076
 * TODO 未审xiedeng代码
 */
@Service
@Orion
@Transactional
public class RelicServiceImpl implements RelicService {

    /**
     * 文物信息dao
     */
    @Autowired
    private RelicDao relicDao;

    /**
     * 文物属性信息 dao
     */
    @Autowired
    private RelicPropertyDao relicPropertyDao;

    /**
     * 文物事故记录dao
     */
    @Autowired
    private AccidentDao accidentDao;

    /**
     * 文物鉴定信息dao
     */
    @Autowired
    private AppraisalDao appraisalDao;

    /**
     * 文物修复信息dao
     */
    @Autowired
    private RestoreDao restoreDao;

    /**
     * 文物现状信息dao
     */
    @Autowired
    private StatusQuoDao statusQuoaDao;

    /**
     * 文物时代dao
     */
    @Autowired
    private EraDao eraDao;

    /**
     * 文物质地 dao
     */
    @Autowired
    private TextureDao textureDao;

    /**
     * 文物级别dao
     */
    @Autowired
    private LevelDao levelDao;


    @Override
    public List<String> findLocationsIdByRelicId(Integer relicId) {
        return relicDao.findLocationsIdByRelicId(relicId);
    }

    @Override
    public List<Relic> findRelics(String totalCode, String name, String zoneId,
                                  String catalogCode, String typeCode, Integer eraId,
                                  Integer levelId, Integer textureId, String siteId, Integer state, Integer index,
                                  Integer size, Boolean iscanceled) {
        return relicDao.findRelics(totalCode, name, zoneId, catalogCode,
                typeCode, eraId, levelId, textureId, siteId, state, index, size, iscanceled);
    }

    @Override
    public List<Relic> findRelics(String totalCode, String name, String zoneId, String catalogCode,
                                  String typeCode, Integer eraId, Integer levelId, Integer textureId, String siteId, Integer state, Boolean iscanceled) {
        return relicDao.findRelics(totalCode, name, zoneId, catalogCode,
                typeCode, eraId, levelId, textureId, siteId, state, iscanceled);
    }

    @Override
    public List<Relic> findRelics(String totalCode, String name, String[] zoneIds, Integer[] labelIds, String catalogCode,
                                  String typeCode, Integer[] eraIds, Integer[] levelIds, Integer[] textureIds,
                                  String siteId, Integer[] states, Integer index, Integer size, Boolean isCanceled) {
        return relicDao.findRelics(totalCode, name, zoneIds, labelIds, catalogCode,
                typeCode, eraIds, levelIds, textureIds, siteId, states, index, size, isCanceled);
    }

    @Override
    public int findRelicsCount(String siteId) {
        return relicDao.findRelicsCount(siteId);
    }

    @Override
    public List<Relic> findRelics(String siteId, int index, int sizePerPage) {
        return relicDao.findRelics(siteId, index, sizePerPage);
    }

    @Override
    public int findRelicsCount(String siteId, String relicNameTotalCode, List<Integer> ids) {
        return relicDao.findRelicsCount(siteId, relicNameTotalCode, ids);
    }

    @Override
    public List<Relic> findRelics(String siteId, String relicNameTotalCode, int pageIndex, int pageSize, List<Integer> ids) {
        return relicDao.findRelics(siteId, relicNameTotalCode, pageIndex, pageSize, ids);
    }


    @Override
    public Integer findRelicCount(String totalCode, String name, String zoneId,
                                  String catalogCode, String typeCode, Integer eraId,
                                  Integer levelId, Integer textureId, String siteId, Integer state, Boolean iscanceled) {
        return relicDao.findRelicCount(totalCode, name, zoneId, catalogCode,
                typeCode, eraId, levelId, textureId, siteId, state, iscanceled);
    }

    @Override
    public Integer findRelicCount(String totalCode, String name, String[] zoneIds, Integer[] labelIds, String catalogCode,
                                  String typeCode, Integer[] eraIds, Integer[] levelIds, Integer[] textureIds,
                                  String siteId, Integer[] states, Boolean isCanceled) {
        return relicDao.findRelicCount(totalCode, name, zoneIds, labelIds, catalogCode, typeCode, eraIds, levelIds,
                textureIds, siteId, states, isCanceled);
    }

    @Override
    public Integer findRelicAllCount(String totalCode, String name,
                                     String zoneId, String catalogCode, String typeCode, Integer eraId,
                                     Integer levelId, Integer textureId, String siteId, Integer state, Boolean iscanceled) {
        return relicDao.findRelicAllCount(totalCode, name, zoneId, catalogCode,
                typeCode, eraId, levelId, textureId, siteId, state, iscanceled);
    }

    @Override
    public Integer findRelicAllCount(String totalCode, String name,
                                     String[] zoneIds, Integer[] labelIds, String catalogCode, String typeCode, Integer[] eraIds,
                                     Integer[] levelIds, Integer[] textureIds, String siteId, Integer[] states, Boolean isCanceled) {
        return relicDao.findRelicAllCount(totalCode, name, zoneIds, labelIds, catalogCode,
                typeCode, eraIds, levelIds, textureIds, siteId, states, isCanceled);
    }

    @Override
    public Relic findRelicByRelicId(int relicId, String siteId) {
        Relic relic = relicDao.findRelic(relicId, siteId);
        // 查询文物所有属性信息
        if (relic != null) {
            relic.setRelicPropertielist(relicPropertyDao.findRelicPropertyList(
                    relic.getId(), Relic.RELIC_PROPERTY_ARCHIVES,
                    Relic.RELIC_PROPERTY_ARCHIVES_AND_CARD,
                    Relic.RELIC_PROPERTY_CARD, Relic.RELIC_PROPERTY_REPAIR));
        }
        return relic;
    }

    @Override
    public Relic findRelicArchives(String totalCode, String siteId) {
        Relic relic = relicDao.findRelic(totalCode, siteId);
        // 查询文物档案属性列表
        if (relic != null) {
            relic.setRelicPropertielist(relicPropertyDao.findRelicPropertyList(
                    relic.getId(), Relic.RELIC_PROPERTY_ARCHIVES,
                    Relic.RELIC_PROPERTY_ARCHIVES_AND_CARD));
        }
        return relic;
    }

    public Relic findRelicArchives(int relicId, String siteId) {
        Relic relic = relicDao.findRelic(relicId, siteId);
        // 查询文物档案属性列表
        if (relic != null) {
            relic.setRelicPropertielist(relicPropertyDao.findRelicPropertyList(
                    relic.getId(), Relic.RELIC_PROPERTY_ARCHIVES,
                    Relic.RELIC_PROPERTY_ARCHIVES_AND_CARD));
        }
        return relic;
    }

    @Override
    public RelicCard findRelicCard(String totalCode, String siteId) {
        Relic relic = relicDao.findRelicCard(totalCode, siteId);
        RelicCard relicCard = null;
        if (relic != null) {
            relic.setRelicPropertielist(relicPropertyDao.findRelicPropertyList(
                    relic.getId(), Relic.RELIC_PROPERTY_ARCHIVES_AND_CARD,
                    Relic.RELIC_PROPERTY_CARD));
            // 将relic 基本信息和属性信息 封装成 relicCard 文物藏品卡信息实体
            relicCard = new RelicCard(relic);
            // 获得文物鉴定、修复、事故、现状信息中的最近一条封装进relicCard 对象中
            //TODO 参数无法唯一标识文物信息
            relicCard.setAccident(accidentDao.findLatestAccident(relic.getId()));
            relicCard.setAppraisal(appraisalDao.findLatestAppraisal(relic.getId()));
            relicCard.setRestore(restoreDao.findLatestRestore(relic.getId()));
            relicCard.setStatusQuo(statusQuoaDao
                    .findLatestStatusQuoDao(relic.getId()));
        }
        return relicCard;
    }

    public RelicCard findRelicCard(int relicId, String siteId) {
        Relic relic = relicDao.findRelicCard(relicId, siteId);
        RelicCard relicCard = null;
        if (relic != null) {
            relic.setRelicPropertielist(relicPropertyDao.findRelicPropertyList(
                    relic.getId(), Relic.RELIC_PROPERTY_ARCHIVES_AND_CARD,
                    Relic.RELIC_PROPERTY_CARD));
            // 将relic 基本信息和属性信息 封装成 relicCard 文物藏品卡信息实体
            relicCard = new RelicCard(relic);
            // 获得文物鉴定、修复、事故、现状信息中的最近一条封装进relicCard 对象中
            //TODO 参数无法唯一标识文物信息
            relicCard.setAccident(accidentDao.findLatestAccident(relic.getId()));
            relicCard.setAppraisal(appraisalDao.findLatestAppraisal(relic.getId()));
            relicCard.setRestore(restoreDao.findLatestRestore(relic.getId()));
            relicCard.setStatusQuo(statusQuoaDao
                    .findLatestStatusQuoDao(relic.getId()));
        }
        return relicCard;
    }

    @Override
    public List<String> findPreAndNextRelicTotalCode(String totalCode,
                                                     String siteId) {
        Integer id = relicDao.findRelicId(totalCode, siteId);
        List<String> preAndNext = new ArrayList<String>();
        if (id == null) {
            preAndNext.add(null);
            preAndNext.add(null);
        } else {
            preAndNext.add(relicDao.findPreRelicTotalCode(id, siteId));
            preAndNext.add(relicDao.findNextRelicTotalCode(id, siteId));
        }
        return preAndNext;
    }

    @Override
    public List<String> findPreAndNextByConditions(String totalCode, List<Relic> relics,
                                                   String siteId) {

        Integer id = relicDao.findRelicId(totalCode, siteId);

        List<String> preAndNext = new ArrayList<String>();
        if (id == null) {
            preAndNext.add(null);
            preAndNext.add(null);
        } else {
            for (int i = 0; i < relics.size(); i++) {
                if (relics.get(i).getTotalCode().equals(totalCode)) {
                    if (i == 0) {
                        preAndNext.add(null);
                    } else {
                        preAndNext.add(relics.get(i - 1).getTotalCode());
                    }
                    if (i == relics.size() - 1) {
                        preAndNext.add(null);
                    } else {
                        preAndNext.add(relics.get(i + 1).getTotalCode());
                    }
                    break;
                }
            }
        }
        return preAndNext;
    }


    @Override
    public List<RelicVo> findHasTagRelics(String siteId) {
        return relicDao.findHasTagRelics(siteId);
    }

    @Override
    public void addRelic(Relic relic) {
        // 添加文物需要添加的字典信息
        addRelicDictionary(relic);
        // 添加文物
        relicDao.save(relic);
    }

    @Override
    public void updateRelic(Relic relic) {
        // 添加文物需要添加的字典信息
        addRelicDictionary(relic);
        // 修改文物信息
        relicDao.update(relic);
    }

    @Override
    public void updateRelicTag(String siteId, List<String> totalCodeList) {
        relicDao.updateRelicTag(siteId, totalCodeList);
    }

    @Override
    public void addRelicRecord(Object o) {
        if (o instanceof Accident) {
            accidentDao.save((Accident) o);
        }
        if (o instanceof Appraisal) {
            appraisalDao.save((Appraisal) o);
        }
        if (o instanceof Restore) {
            restoreDao.save((Restore) o);
        }
        if (o instanceof StatusQuo) {
            statusQuoaDao.save((StatusQuo) o);
        }
    }

    @Override
    public boolean isTotalCodeCanUse(String totalCode, String siteId) {
        // 通过 totalCode 和站点 查询文物id编号 如果为null 则 可用
        return relicDao.findRelicId(totalCode, siteId) == null;
    }

    /**
     * 添加文物字典信息
     *
     * @param relic
     * @return
     * @author 许保吉
     * @date 2013-5-17
     */
    private void addRelicDictionary(Relic relic) {
        // 获得文物 年代、级别、质地实体
        Era era = relic.getEra();
        Texture texture = relic.getTexture();
        Level level = relic.getLevel();
        // 判断是否需要添加 文物 年代、级别、质地 字典信息
        if (era != null && era.getId() == null) {
            eraDao.save(era);
        }
        if (texture != null && texture.getId() == null) {
            textureDao.save(texture);
        }
        if (level != null && level.getId() == null) {
            levelDao.save(level);
        }
    }

    @Override
    public void updateRelicState(List<Integer> relicIds, int relicState) {
        relicDao.updateRelicState(relicIds, relicState);
    }

    @Override
    public Relic findById(int id) {
        return relicDao.findById(id);
    }

    @Override
    public void delete(Relic relic) {
        relicDao.delete(relic);
    }

    @Override
    public void updateRelicCanceledState(String siteId, int relicId, boolean iscanceled) {
        relicDao.updateRelicCanceledState(siteId, relicId, iscanceled);
    }

    @Override
    public String prepareRelicJson(Relic relic) {
        // 重建索引
        Map<String, Object> relicMap = new HashMap<String, Object>();
        relicMap.put("ID编号", relic.getId().toString());
        relicMap.put("总登记号", relic.getTotalCode());
        relicMap.put("编目号", relic.getCatalogCode());
        relicMap.put("分类号", relic.getTypeCode());
        relicMap.put("名称", relic.getName());
        relicMap.put("时代", relic.getEra().getName());
        relicMap.put("级别", relic.getLevel().getName());
        relicMap.put("质地", relic.getTexture().getName());
        relicMap.put("件数", relic.getCount());
        relicMap.put("区域", (relic.getZone() == null ? "" : relic.getZone().getName()));
        relicMap.put("站点", relic.getSiteId());

        String relicState = "";
        switch (relic.getState()) {
            case 0:
                relicState = "在库";
                break;
            case 1:
                relicState = "待出库";
                break;
            case 2:
                relicState = "已出库";
                break;
            case 3:
                relicState = "出库申请中";
                break;
        }
        relicMap.put("文物状态", relicState);
        relicMap.put("是否有电子标签", relic.getHasTag() ? "有标签" : "无标签");
        relicMap.put("是否注销", (relic.getIscanceled() ? "已注销" : "未注销"));

        Map<String, RelicProperty> propertyMap = relic.getRelicPropertyMap();
        if (propertyMap.containsKey("weight")) {      // 重量
            relicMap.put("重量", propertyMap.get("weight").getPropertyValue());
        }
        if (propertyMap.containsKey("sizes")) { // 尺寸
            relicMap.put("尺寸", propertyMap.get("sizes").getPropertyValue());
        }
        if (propertyMap.containsKey("source")) {    // 来源
            relicMap.put("来源", propertyMap.get("source").getPropertyValue());
        }
        if (propertyMap.containsKey("photoCode")) {  // 照片号
            relicMap.put("照片号", propertyMap.get("photoCode").getPropertyValue());
        }
        if (propertyMap.containsKey("rubbingCode")) {   // 拓片号
            relicMap.put("拓片号", propertyMap.get("rubbingCode"));
        }
        if (propertyMap.containsKey("brief")) { // 简述
            relicMap.put("简述", propertyMap.get("brief").getPropertyValue());
        }
        if (propertyMap.containsKey("literature")) {    // 著录和文献
            relicMap.put("著录和文献", propertyMap.get("literature").getPropertyValue());
        }
        if (propertyMap.containsKey("origin")) {    // 出土地或产地
            relicMap.put("出土地或产地", propertyMap.get("origin").getPropertyValue());
        }
        if (propertyMap.containsKey("recordCode")) {   // 档案编号
            relicMap.put("档案编号", propertyMap.get("recordCode").getPropertyValue());
        }
        if (propertyMap.containsKey("odleName")) {  // 原名
            relicMap.put("原名", propertyMap.get("odleName").getPropertyValue());
        }
        if (propertyMap.containsKey("author")) {    // 作者
            relicMap.put("作者", propertyMap.get("author").getPropertyValue());
        }
        if (propertyMap.containsKey("color")) { // 色泽
            relicMap.put("色泽", propertyMap.get("color").getPropertyValue());
        }
        if (propertyMap.containsKey("productionDate")) {   // 制作时间
            relicMap.put("制作时间", propertyMap.get("productionDate").getPropertyValue());
        }
        if (propertyMap.containsKey("use")) {  // 用途
            relicMap.put("用途", propertyMap.get("use").getPropertyValue());
        }
        if (propertyMap.containsKey("authorInfo")) {    // 作者小传
            relicMap.put("作者小传", propertyMap.get("authorInfo").getPropertyValue());
        }
        if (propertyMap.containsKey("appendant")) { // 附属物
            relicMap.put("附属物", propertyMap.get("appendant").getPropertyValue());
        }
        if (propertyMap.containsKey("accessionDate")) {    // 入藏时间
            relicMap.put("入藏时间", propertyMap.get("accessionDate").getPropertyValue());
        }
        if (propertyMap.containsKey("accessionCode")) {    // 入馆凭证号
            relicMap.put("入馆凭证号", propertyMap.get("accessionCode").getPropertyValue());
        }
        if (propertyMap.containsKey("describe")) {   // 形状内容描述
            relicMap.put("形状内容描述", propertyMap.get("describe").getPropertyValue());
        }
        if (propertyMap.containsKey("collectInfo")) {   // 征集经过
            relicMap.put("征集经过", propertyMap.get("collectInfo").getPropertyValue());
        }
        if (propertyMap.containsKey("impress")) {   // 鉴藏印记
            relicMap.put("鉴藏印记", propertyMap.get("impress").getPropertyValue());
        }
        if (propertyMap.containsKey("remark")) {   // 备注
            relicMap.put("备注", propertyMap.get("remark").getPropertyValue());
        }
        if (propertyMap.containsKey("appendix")) {  // 附录
            relicMap.put("附录", propertyMap.get("appendix").getPropertyValue());
        }
        if (propertyMap.containsKey("maker")) { // 制卡人
            relicMap.put("制卡人", propertyMap.get("maker").getPropertyValue());
        }
        if (propertyMap.containsKey("makeDate")) {  // 制卡日期
            relicMap.put("制卡日期", propertyMap.get("makeDate").getPropertyValue());
        }
        if (propertyMap.containsKey("annex")) { // 附件
            relicMap.put("附件", propertyMap.get("annex").getPropertyValue());
        }
        if (propertyMap.containsKey("cardCode")) {  // 藏品卡编号
            relicMap.put("藏品卡编号", propertyMap.get("cardCode").getPropertyValue());
        }
        if (propertyMap.containsKey("originDate")) {    // 出土时间
            relicMap.put("出土时间", propertyMap.get("originDate").getPropertyValue());
        }

        relicMap.put("铭文题跋", relic.getInscriptions());
        relicMap.put("流传经历", relic.getRoves());
        relicMap.put("鉴定意见", relic.getAppraisals());
        relicMap.put("修复装裱复制记录", relic.getRestores());
        relicMap.put("现状记录", relic.getStatusQuos());
        relicMap.put("绘图(或拓片)", relic.getRubbings());
        relicMap.put("照片", relic.getPhotos());
        relicMap.put("挂接文档", relic.getAttachments());
        relicMap.put("事故记录", relic.getAccidents());
        relicMap.put("标签", relic.getRelicLabels());

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(relicMap);
    }

    @Override
    public boolean validateByTotalCode(String totalCode) {
        List<Relic> relicList = relicDao.validateByTotalCode(totalCode);
        if (relicList.size() < 1) {
            return true;
        }
        return false;
    }
}
