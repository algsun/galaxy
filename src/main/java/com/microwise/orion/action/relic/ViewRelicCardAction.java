package com.microwise.orion.action.relic;

import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.RelicCard;
import com.microwise.orion.bean.RelicLabel;
import com.microwise.orion.service.RelicLabelService;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 藏品卡
 *
 * @author Wang rensong
 * @time 13-5-11
 */
@Beans.Action
@Orion
public class ViewRelicCardAction extends OrionLoggerAction {

    /**
     * 档案
     */
    @Autowired
    private RelicService relicService;

    //Input & Output
    /**
     * 总登记号
     */
    private String totalCode;

    /**
     * 文物id
     */
    private int relicId;

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
     * 上一个文物
     */
    private String preCode;

    /**
     * 下一个文物
     */
    private String nextCode;

    //Output
    /**
     * 文物藏品卡信息
     */
    private RelicCard relicCard;

    // 获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();

    /**
     * 所有文物标签
     */
    private List<RelicLabel> allLabels;

    /**
     * 文物自定义标签集合
     */
    private List<RelicLabel> relicLabels;

    /**
     * 文物标签
     */
    @Autowired
    private RelicLabelService relicLabelService;

    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    {
        //初始化 图片路径
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "orion" + File.separator + "images";
    }

    /**
     * 当期页码
     */
    private int index;

    /**
     * 当type值为 fullTextSearch 时，说明是从全文检索连接过来的
     */
    private String type;

    /**
     * 标记了是向前翻还是向后翻了
     * 0：不是翻页操作
     * 1：向前翻
     * 2：向后翻
     * 3:跳转操作
     */
    private int pre;

    public static List<Relic> relics = new ArrayList<Relic>();


    /**
     * 查询藏品卡
     *
     * @return
     */
    public String execute() {
        try {
            //获得藏品卡信息
            relicCard = relicService.findRelicCard(totalCode, siteId);

            if (relicCard == null) {
                return Action.NONE;
            }
            relicLabels = relicLabelService.findRelicLabelListByRelicId(relicCard.getId());
            //初始化自定义标签信息
            allLabels = relicLabelService.findRelicLabelList(siteId);

            Integer[] labelIds = getIntArray(labelId);
            String[] zoneIds = Strings.isNullOrEmpty(zoneId) ? null : zoneId.split(",");
            Integer[] eraIds = getIntArray(eraId);
            Integer[] levelIds = getIntArray(levelId);
            Integer[] textureIds = getIntArray(textureId);
            Integer[] states = getIntArray(state);

            if (pre == 0) {
                relics.clear();
            }

            if (relics.size() == 0) {
                relics = relicService.findRelics(totalCodeAsCondition, name, zoneIds, labelIds, catalogCode,
                        typeCode, eraIds, levelIds, textureIds, siteId, states, null,
                        null, false);
            }

            setIndex(relics, totalCode);

            List<String> siblingCodes = relicService.findPreAndNextByConditions(totalCode, relics, siteId);

            if ("fullTextSearch".equals(type)) {
                setPreAndNextCode();
            } else {
                preCode = siblingCodes.get(0);
                nextCode = siblingCodes.get(1);
            }

            log("藏品信息", "查询藏品卡");
        } catch (Exception ex) {
            logFailed("藏品卡", "查询藏品卡失败");
            ex.printStackTrace();
        }
        return Action.SUCCESS;
    }


    /**
     * 查询藏品卡
     *
     * @return
     */
    public String executeRelicId() {
        try {
            //获得藏品卡信息
            relicCard = relicService.findRelicCard(relicId, siteId);

            if (relicCard == null) {
                return Action.NONE;
            }

            totalCode = relicCard.getTotalCode();
            relicLabels = relicLabelService.findRelicLabelListByRelicId(relicCard.getId());
            //初始化自定义标签信息
            allLabels = relicLabelService.findRelicLabelList(siteId);

            Integer[] labelIds = getIntArray(labelId);
            String[] zoneIds = Strings.isNullOrEmpty(zoneId) ? null : zoneId.split(",");
            Integer[] eraIds = getIntArray(eraId);
            Integer[] levelIds = getIntArray(levelId);
            Integer[] textureIds = getIntArray(textureId);
            Integer[] states = getIntArray(state);

            if (pre == 0) {
                relics.clear();
            }

            if (relics.size() == 0) {
                relics = relicService.findRelics(totalCodeAsCondition, name, zoneIds, labelIds, catalogCode,
                        typeCode, eraIds, levelIds, textureIds, siteId, states, null,
                        null, false);
            }

            setIndex(relics, totalCode);

            List<String> siblingCodes = relicService.findPreAndNextByConditions(totalCode, relics, siteId);

            if ("fullTextSearch".equals(type)) {
                setPreAndNextCode();
            } else {
                preCode = siblingCodes.get(0);
                nextCode = siblingCodes.get(1);
            }

            log("藏品信息", "查询藏品卡");
        } catch (Exception ex) {
            logFailed("藏品卡", "查询藏品卡失败");
            ex.printStackTrace();
        }
        return Action.SUCCESS;
    }

    private void setPreAndNextCode() {
        List<String> totalCodes = (List<String>) ServletActionContext.getRequest().getSession().getAttribute("totalCodes");
        int index = totalCodes.indexOf(totalCode);
        if (index <= 0) {
            preCode = "";
        } else {
            preCode = totalCodes.get(index - 1);
        }
        if (index >= totalCodes.size() - 1) {
            nextCode = "";
        } else {
            nextCode = totalCodes.get(index + 1);
        }
    }

    private void setIndex(List<Relic> relics, String totalCode) {
        for (int i = 0; i < relics.size(); i++) {
            if (relics.get(i).getTotalCode().equals(totalCode)) {
                int j = (i + 1) % Constants.SIZE_PER_PAGE;
                if (j == 0 && pre == 1) {
                    index--;
                } else if (j == 1 && pre == 2) {
                    index++;
                } else if (pre == 3) {
                    if ((i + 1) <= 10) {
                        index = 1;
                    } else {
                        if (j == 0) {
                            index = (i + 1) / Constants.SIZE_PER_PAGE;
                        } else {
                            index = (i + 1) / Constants.SIZE_PER_PAGE + 1;
                        }
                    }
                }
            }
        }
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
            if (!Strings.isNullOrEmpty(str1)) {
                returnArray[index] = Integer.parseInt(str1);
                index++;
            }
        }
        return returnArray;
    }


    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
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

    public String getPreCode() {
        return preCode;
    }

    public void setPreCode(String preCode) {
        this.preCode = preCode;
    }

    public String getNextCode() {
        return nextCode;
    }

    public void setNextCode(String nextCode) {
        this.nextCode = nextCode;
    }

    public RelicCard getRelicCard() {
        return relicCard;
    }

    public void setRelicCard(RelicCard relicCard) {
        this.relicCard = relicCard;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<RelicLabel> getAllLabels() {
        return allLabels;
    }

    public void setAllLabels(List<RelicLabel> allLabels) {
        this.allLabels = allLabels;
    }

    public List<RelicLabel> getRelicLabels() {
        return relicLabels;
    }

    public void setRelicLabels(List<RelicLabel> relicLabels) {
        this.relicLabels = relicLabels;
    }

    public RelicLabelService getRelicLabelService() {
        return relicLabelService;
    }

    public void setRelicLabelService(RelicLabelService relicLabelService) {
        this.relicLabelService = relicLabelService;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPre() {
        return pre;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

    public static List<Relic> getRelics() {
        return relics;
    }

    public static void setRelics(List<Relic> relics) {
        ViewRelicCardAction.relics = relics;
    }
}

