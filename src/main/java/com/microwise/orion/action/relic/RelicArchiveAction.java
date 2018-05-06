package com.microwise.orion.action.relic;

import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.RelicLabel;
import com.microwise.orion.service.AttachmentService;
import com.microwise.orion.service.RelicLabelService;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文物档案
 * <p/>
 * </pre>
 *
 * @author Wang yunlong
 * @time 13-5-9 下午1:33
 * @check @gaohui #4040 2013-06-04
 */
@Beans.Action
@Orion
public class RelicArchiveAction extends OrionLoggerAction {

    /**
     * 藏品信息挂接文档上传子目录
     */
    private static final String FILE_UPLOAD_PATH = "file";

    @Autowired
    private RelicService relicService;

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 文物标签
     */
    @Autowired
    private RelicLabelService relicLabelService;

    /**
     * 所有文物标签
     */
    private List<RelicLabel> allLabels;

    /**
     * 文物自定义标签集合
     */
    private List<RelicLabel> relicLabels;

    //input
    /**
     * 文物总登记号
     */
    private String totalCode;

    /**
     * 文物
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
     * 标记了是向前翻还是向后翻了
     * 0：不是翻页操作
     * 1：向前翻
     * 2：向后翻
     * 3:跳转操作
     */
    private int pre;


    //output
    /**
     * 文物档案
     */
    private Relic relicArchives;

    //input & output
    /**
     * 上一个文物
     */
    private String preCode;

    /**
     * 下一个文物
     */
    private String nextCode;

    // 获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();

    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    /**
     * 当前页面
     */
    private int index;

    /**
     * 初始化 图片路径
     */ {
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "orion" + File.separator + "images";
    }

    public static List<Relic> relics = new ArrayList<Relic>();


    /**
     * 查询档案
     *
     * @return
     */
    public String execute() {

        relicArchives = relicService.findRelicArchives(totalCode, siteId);

        if (relicArchives == null) {
            return Action.NONE;
        }
        relicLabels = relicLabelService.findRelicLabelListByRelicId(relicArchives.getId());
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

//        List<String> siblingCodes = relicService.findPreAndNextRelicTotalCode(totalCode, siteId);
        List<String> siblingCodes = relicService.findPreAndNextByConditions(totalCode, relics, siteId);
        if (siblingCodes == null || siblingCodes.isEmpty()) {
            preCode = null;
            nextCode = null;
        } else {
            preCode = siblingCodes.get(0);
            nextCode = siblingCodes.get(1);
        }
        log("藏品管理", "查询档案");
        return Action.SUCCESS;
    }

    /**
     * 查询档案（按照文物id）
     *
     * @return
     */
    public String relicArchiveRelicId() {

        relicArchives = relicService.findRelicArchives(relicId, siteId);

        totalCode = relicArchives.getTotalCode();
        if (relicArchives == null) {
            return Action.NONE;
        }
        relicLabels = relicLabelService.findRelicLabelListByRelicId(relicArchives.getId());
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

//        List<String> siblingCodes = relicService.findPreAndNextRelicTotalCode(totalCode, siteId);
        List<String> siblingCodes = relicService.findPreAndNextByConditions(totalCode, relics, siteId);
        if (siblingCodes == null || siblingCodes.isEmpty()) {
            preCode = null;
            nextCode = null;
        } else {
            preCode = siblingCodes.get(0);
            nextCode = siblingCodes.get(1);
        }
        log("藏品管理", "查询档案");
        return Action.SUCCESS;
    }

    private void setIndex(List<Relic> relics, String totalCode) {
        for (int i = 0; i < relics.size(); i++) {
            if(Strings.isNullOrEmpty(relics.get(i).getTotalCode())) continue;
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
     * 档案挂接文档下载
     *
     * @return
     */
    public String downloadFileAction() {
        HttpServletRequest request = ServletActionContext.getRequest();
        int fileId = Integer.parseInt(request.getParameter("downFileId"));
        String fileName = "";
        if (attachmentService.findById(fileId) != null) {
            fileName = attachmentService.findById(fileId).getPath();
        }
        String subdirectory = FILE_UPLOAD_PATH + File.separator + siteId + File.separator + relicId;
        UpLoadFileUtil.fileDownLoad(subdirectory, fileName, "/orion");
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

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }

    public Relic getRelicArchives() {
        return relicArchives;
    }

    public void setRelicArchives(Relic relicArchives) {
        this.relicArchives = relicArchives;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public int getPre() {
        return pre;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

}
