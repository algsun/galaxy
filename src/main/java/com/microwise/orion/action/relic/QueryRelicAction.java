package com.microwise.orion.action.relic;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.orion.bean.*;
import com.microwise.orion.service.*;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.microwise.proxima.bean.Zone;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 藏品信息查询
 *
 * @author Wang yunlong
 * @author liu.zhu
 * @time 13-5-8 下午3:45
 * @date 2013-08-08
 * @check @gaohui #3862 2013-06-04
 * @check @gaohui #5066 2013-08-15
 */
@Beans.Action
@Orion
public class QueryRelicAction extends OrionLoggerAction {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
     * 区域
     */
    @Autowired
    private ZoneService zoneService;

    /**
     * 藏品属性service
     */
    @Autowired
    private PropertyService propertyService;

    /**
     * 文物标签service
     */
    @Autowired
    private RelicLabelService relicLabelService;


    // input 以下是查询条件
    private String totalCode;
    private String name;
    private String zoneId;
    private String catalogCode;
    private String typeCode;
    // 设置条件默认值为空字符串
    private String eraId = "";
    private String levelId = "";
    private String textureId = "";
    private String state = "";
    private String labelId = "";
    private String logOff = "";

    //output
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
     * 查询有文物的区域
     */
    private List<Zone> listZone;

    /**
     * 文物标签列表
     */
    private List<RelicLabel> relicLabels;

    /**
     * 分页
     */
    private Integer index = 1;
    private int pageCount;

    /**
     * 文物总件数
     */
    private int allCount;
    /**
     * 文物总数
     */
    private int count;

    /**
     * 文物总记录数
     */
    private int totalCount;

    /**
     * 文物注销状态
     */
    private Boolean isCanceled;

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 获得siteId 当前站点编号
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    /**
     * 页面是否展开全部查询条件
     */
    private boolean inputView;

    /**
     * 文物列表
     */
    private List<Relic> relics;

    /**
     * 藏品数据字典list ,藏品信息导出功能
     */
    private List<Property> proList;

    /**
     * 默认选中的文物标签编号
     */
    private int selectedLabelId = -1;

    /**
     * 查询藏品信息
     *
     * @return
     */
    public String execute() {
        ActionMessage.createByAction().consume();

        try {
            //时代，级别，质地，区域的显示初始化
            listEra = eraService.findAllEra();
            listLevel = levelService.findAllLevel();
            listTexture = textureService.findAllTexture();
            listZone = zoneService.findHasRelicZone(siteId);
            relicLabels = relicLabelService.findRelicLabelList(siteId);

            Integer[] labelIds = null;

            if (selectedLabelId != -1) {
                labelIds = new Integer[]{
                        selectedLabelId
                };
                labelId = labelId + "," + selectedLabelId;
            } else {
                labelIds = getIntArray(labelId);
            }
            String[] zoneIds = Strings.isNullOrEmpty(zoneId) ? null : zoneId.split(",");
            Integer[] eraIds = getIntArray(eraId);
            Integer[] levelIds = getIntArray(levelId);
            Integer[] textureIds = getIntArray(textureId);
            Integer[] states = getIntArray(state);

            // 1-未注销
            if (logOff.contains("0") && logOff.contains("1")) {
                isCanceled = null;
            } else if (logOff.contains("1")) {
                isCanceled = true;
            } else if (logOff.contains("0")) {
                isCanceled = false;
            }

            //条件查询藏品数目
            count = relicService.findRelicCount(totalCode, name, zoneIds, labelIds,
                    catalogCode, typeCode, eraIds, levelIds, textureIds,
                    siteId, states, isCanceled);

            //总分页数
            pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);

            //藏品信息
            relics = relicService.findRelics(totalCode, name, zoneIds, labelIds, catalogCode,
                    typeCode, eraIds, levelIds, textureIds, siteId, states, index,
                    Constants.SIZE_PER_PAGE, isCanceled);

            //查询文物总件数
            allCount = relicService.findRelicAllCount(totalCode, name, zoneIds, labelIds,
                    catalogCode, typeCode, eraIds, levelIds, textureIds, siteId, states, isCanceled);

            //查询所有文物数目
            totalCount = relicService.findRelicCount(null, null, null, null, null, -1, -1, -1, siteId, -1, null);

            //藏品信息导出数据字典查询
            proList = propertyService.findAllProperty();

            log("藏品信息", "查询藏品");
        } catch (Exception e) {
            logFailed("藏品信息查询", "藏品信息查询失败");
            logger.error("查询藏品列表出错", e);
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }


    /**
     * 将拼接的参数转换成Integer数组
     *
     * @param str 参数
     * @return 返回的数组
     */
    private Integer[] getIntArray(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return null;
        }
        String[] strs = str.split(",");
        Integer[] returnArray = new Integer[strs.length];
        int index = 0;
        for (String str1 : strs) {
            if (!Strings.isNullOrEmpty(str1.trim())) {
                returnArray[index] = Integer.parseInt(str1);
                index++;
            }
        }
        return returnArray;
    }

    public List<Relic> getRelics() {
        return relics;
    }

    public void setRelics(List<Relic> relics) {
        this.relics = relics;
    }

    public Boolean getCanceled() {
        return isCanceled;
    }

    public void setCanceled(Boolean canceled) {
        isCanceled = canceled;
    }

    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
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

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isInputView() {
        return inputView;
    }

    public void setInputView(boolean inputView) {
        this.inputView = inputView;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public List<Zone> getListZone() {
        return listZone;
    }

    public void setListZone(List<Zone> listZone) {
        this.listZone = listZone;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<Property> getProList() {
        return proList;
    }

    public void setProList(List<Property> proList) {
        this.proList = proList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
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

    public String getLogOff() {
        return logOff;
    }

    public void setLogOff(String logOff) {
        this.logOff = logOff;
    }

    public List<RelicLabel> getRelicLabels() {
        return relicLabels;
    }

    public void setRelicLabels(List<RelicLabel> relicLabels) {
        this.relicLabels = relicLabels;
    }

    public int getSelectedLabelId() {
        return selectedLabelId;
    }

    public void setSelectedLabelId(int selectedLabelId) {
        this.selectedLabelId = selectedLabelId;
    }
}
