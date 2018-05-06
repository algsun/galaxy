package com.microwise.orion.action.relic;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.*;
import com.microwise.orion.service.*;
import com.microwise.orion.sys.Orion;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.File;
import java.util.*;

/**
 * 档案编辑
 *
 * @author Wang yunlong
 * @time 13-5-15 上午9:11
 */
@Beans.Action
@Orion
public class UpdateRelicAction {

    public static final Logger log = LoggerFactory.getLogger(UpdateRelicAction.class);

    /**
     * 档案
     */
    @Autowired
    private RelicService relicService;

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
     * 文物属性字典查询服务
     */
    @Autowired
    private PropertyService propertyService;

    /**
     * 文物标签
     */
    @Autowired
    private RelicLabelService relicLabelService;

    @Autowired
    private StatusQuoService statusQuoService;

    @Autowired
    private AppraisalService appraisalService;

    // input 以下是查询条件
    private String totalCodeAsCondition;
    private String name;
    private String catalogCode;
    private String typeCode;
    // 设置条件默认值为空字符串
    private String zoneId;
    private String eraId = "";
    private String levelId = "";
    private String textureId = "";
    private String state = "";
    private String labelId = "";


    /**
     * 站点
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    private int index;

    //output
    /**
     * 档案
     */
    private Relic relic;

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
     * 文物自定义标签集合
     */
    private List<RelicLabel> relicLabels;

    /**
     * 查询属性列表
     */
    private List<RelicProperty> propertyList = new ArrayList<RelicProperty>();

    /**
     * 属性字典表
     */
    private List<Property> propertys = new ArrayList<Property>();

    /**
     * 属性List
     */
    private List<Map<String, Object>> propertyDictionaryList = new ArrayList<Map<String, Object>>();


    /**
     * 是否成功
     */
    private boolean isSuccess;

    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    /**
     * 文物编号
     */
    private int relicId;

    /**
     * 文物标签编号
     */
    private int selectedLabelId;

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 所有文物标签
     */
    private List<RelicLabel> allLabels;

    /**
     * 返回的参数
     */
    private Map<String, Object> dataMap;

    //初始化 图片路径
    {
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "orion" + File.separator + "images";
    }

    /**
     * 铭文提拔描述
     */
    private String inscriptionDescription;

    private int relicIndex;

    /**
     * 现状id
     */
    private Integer statusQuoId;

    /**
     * 鉴定id
     */
    private Integer appraisalId;


    /**
     * 修改文物标签
     *
     * @return
     */
    public String addRelicLabel() {
        boolean isSuccess = true;
        try {
            relicLabelService.addRelicLabel(relicId, labelName.trim());
        } catch (DataIntegrityViolationException e) {
            log.error("添加文物标签信息失败", e);
            isSuccess = false;
        }
        dataMap = new HashMap<String, Object>();
        dataMap.put("Success", isSuccess);
        dataMap.put("allLabels", getCurrentSiteAllLabels());
        return Action.SUCCESS;
    }

    /**
     * 调转到文物列表页面
     *
     * @return
     */
    public String toRelicList() {
        RelicLabel relicLabel = relicLabelService.findRelicLabelByLabelName(labelName);
        selectedLabelId = relicLabel.getId();
        return Action.SUCCESS;
    }

    /**
     * 删除文物标签
     *
     * @return
     */
    public String deleteRelicLabel() {
        isSuccess = relicLabelService.deleteRelicLabel(relicId, labelName.trim());
        dataMap = new HashMap<String, Object>();
        dataMap.put("Success", isSuccess);
        dataMap.put("allLabels", getCurrentSiteAllLabels());
        return Action.SUCCESS;
    }

    /**
     * 进入更新页面
     *
     * @return
     */
    public String view() {
        relicIndex = index;
        ActionMessage.createByAction().consume();
        listEra = eraService.findAllEra();
        listLevel = levelService.findAllLevel();
        listTexture = textureService.findAllTexture();
        relic = relicService.findRelicByRelicId(relicId, siteId);
        disposeInscription(relic);
        propertys = propertyService.findAllProperty();
        propertyList = relic.getRelicPropertielist();
        for (RelicProperty relicPor : propertyList) {
            if (relicPor.getPropertyValue() == null) {
                relicPor.setPropertyValue("");
            }
        }
        //初始化自定义标签信息
        relicLabels = relicLabelService.findRelicLabelListByRelicId(relic.getId());
        allLabels = relicLabelService.findRelicLabelList(siteId);
        Map<String, RelicProperty> propertyMap = relic.getRelicPropertyMap();
        for (Property p : propertys) {
            Map<String, Object> map = new HashMap<String, Object>();
            String pValue = "";
            Integer relicPId = 0;
            if (propertyMap.get(p.getEnName()) != null) {
                pValue = propertyMap.get(p.getEnName()).getPropertyValue();
                relicPId = propertyMap.get(p.getEnName()).getId();
            }
            map.put("relicProId", relicPId);
            map.put("enName", p.getEnName());
            map.put("cnName", p.getCnName());
            map.put("propertyId", p.getId());
            map.put("propertyValue", pValue);
            map.put("propertyType", p.getPropertyType());
            map.put("orderNum", p.getOrderNum());
            propertyDictionaryList.add(map);
        }
        return Action.SUCCESS;
    }

    public String execute() {
        return Action.SUCCESS;
    }

    public String deleteStatusQuo() {
        try {
            StatusQuo statusQuo = new StatusQuo();
            statusQuo.setId(statusQuoId);
            statusQuoService.deleteStatusQuo(statusQuo);
            dataMap = new HashMap<String, Object>();
            dataMap.put("success", true);
        } catch (Exception e) {
            dataMap.put("success", false);
        }
        return Action.SUCCESS;
    }

    public String deleteAppraisal() {
        try {
            Appraisal appraisal = new Appraisal();
            appraisal.setId(appraisalId);
            appraisalService.deleteAppraisal(appraisal);
            dataMap = new HashMap<String, Object>();
            dataMap.put("success", true);
        } catch (Exception e) {
            dataMap.put("success", false);
        }
        return Action.SUCCESS;
    }

    private void disposeInscription(Relic relic) {
        Set<Inscription> list = relic.getInscriptions();
        if (list.size() > 0) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Inscription inscription = (Inscription) iterator.next();
                if ("inscriptionDescription".equals(inscription.getPath())) {
                    inscriptionDescription = inscription.getInfo();
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 获取当前站点所有的标签
     *
     * @return 当前站点所有的标签
     */
    private List<String> getCurrentSiteAllLabels() {
        allLabels = relicLabelService.findRelicLabelList(siteId);
        List<String> labels = new ArrayList<String>();
        for (RelicLabel relicLabel : allLabels) {
            labels.add(relicLabel.getName());
        }
        return labels;
    }


    public String getTotalCodeAsCondition() {
        return totalCodeAsCondition;
    }

    public void setTotalCodeAsCondition(String totalCodeAsCondition) {
        this.totalCodeAsCondition = totalCodeAsCondition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getEraId() {
        return eraId;
    }

    public void setEraId(String eraId) {
        this.eraId = eraId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getTextureId() {
        return textureId;
    }

    public void setTextureId(String textureId) {
        this.textureId = textureId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
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

    public List<RelicLabel> getRelicLabels() {
        return relicLabels;
    }

    public void setRelicLabels(List<RelicLabel> relicLabels) {
        this.relicLabels = relicLabels;
    }

    public List<RelicProperty> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<RelicProperty> propertyList) {
        this.propertyList = propertyList;
    }

    public List<Property> getPropertys() {
        return propertys;
    }

    public void setPropertys(List<Property> propertys) {
        this.propertys = propertys;
    }

    public List<Map<String, Object>> getPropertyDictionaryList() {
        return propertyDictionaryList;
    }

    public void setPropertyDictionaryList(List<Map<String, Object>> propertyDictionaryList) {
        this.propertyDictionaryList = propertyDictionaryList;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public int getSelectedLabelId() {
        return selectedLabelId;
    }

    public void setSelectedLabelId(int selectedLabelId) {
        this.selectedLabelId = selectedLabelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public List<RelicLabel> getAllLabels() {
        return allLabels;
    }

    public void setAllLabels(List<RelicLabel> allLabels) {
        this.allLabels = allLabels;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public String getInscriptionDescription() {
        return inscriptionDescription;
    }

    public void setInscriptionDescription(String inscriptionDescription) {
        this.inscriptionDescription = inscriptionDescription;
    }

    public int getRelicIndex() {
        return relicIndex;
    }

    public void setRelicIndex(int relicIndex) {
        this.relicIndex = relicIndex;
    }

    public Integer getStatusQuoId() {
        return statusQuoId;
    }

    public void setStatusQuoId(Integer statusQuoId) {
        this.statusQuoId = statusQuoId;
    }

    public Integer getAppraisalId() {
        return appraisalId;
    }

    public void setAppraisalId(Integer appraisalId) {
        this.appraisalId = appraisalId;
    }
}
