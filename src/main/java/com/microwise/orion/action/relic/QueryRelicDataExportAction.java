package com.microwise.orion.action.relic;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ClassUtil;
import com.microwise.orion.bean.*;
import com.microwise.orion.service.PropertyService;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.microwise.proxima.bean.Zone;
import com.opensymphony.xwork2.Action;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 藏品数据导出
 *
 * @author liuzhu
 * @date 13-07-29
 * @check @gaohui #5068 2013-08-15
 */
@Beans.Action
@Orion
public class QueryRelicDataExportAction extends OrionLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(QueryRelicDataExportAction.class);

    public static int ROW_SIZE_OF_SHEET = 0;

    static {
        String str = ConfigFactory.getInstance().getConfig("config.properties").get("orion.relic.export.page.rowSize");  //每个sheet中的总显示数
        ROW_SIZE_OF_SHEET = Integer.parseInt(str);
    }

    //藏品service
    @Autowired
    private RelicService relicService;

    //藏品属性service
    @Autowired
    private PropertyService propertyService;

    // input 以下是查询条件
    private String totalCode;
    private String name;
    private String zoneId;
    private String catalogCode;
    private String typeCode;
    private String exportLabelId;
    private String eraId;
    private String levelId;
    private String textureId;
    private String state;
    private String logOff;
    private Integer index;
    private Integer count;
    /**
     * 文物注销状态
     */
    private Boolean isCanceled;
    /**
     * 选择的属性
     */
    private String checkPro;
    /**
     * 条件查询
     */
    private String param;

    // 获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();

    //藏品数据字典list
    private List<Property> proList;

    //藏品信息list
    private List<Relic> relics;

    @Route("/orion/queryRelic/waiting-for-export")
    public String viewExport() {
        Integer[] labelIds = getIntArray(exportLabelId);
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

        param = "?totalCode=" + totalCode + "&name=" + name + "&zoneId=" + zoneId + "&catalogCode=" + catalogCode + "&typeCode=" + typeCode;
        param += "&eraId=" + eraId +"&count="+count+"&exportLabelId="+exportLabelId+ "&levelId=" + levelId + "&textureId=" + textureId + "&siteId=" + siteId + "&state=" + state + "&logOff=" + logOff;
        return Results.ftl("/orion/pages/relic/query-relic-data-export");
    }

    /**
     * 导出excel
     *
     * @author liuzhu
     * @date 13-08-02
     */
    public String export() {

        log.debug("开始封装excle");
//        relics = relicService.findRelics(totalCode, name, zoneId, catalogCode,
//                typeCode, eraId, levelId, textureId, siteId, state, isCanceled);

        Integer[] labelIds = getIntArray(exportLabelId);
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

        //藏品信息
        relics = relicService.findRelics(totalCode, name, zoneIds, labelIds, catalogCode,
                typeCode, eraIds, levelIds, textureIds, siteId, states, 1,
                count, isCanceled);

        createExcel(relics);
        log.debug("结束封装excle");
        log("资产管理", "导出excle");
        return Action.NONE;
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

    /**
     * 实现导出excel
     *
     * @param relicList
     * @return void
     * @author liuzhu
     * @date 13-08-14
     */
    private void createExcel(List<Relic> relicList) {

        // 预先初始化所有要导出的 title
        Map<String, String> map = new HashMap<String, String>();
        String put = map.put("name", "文物名称");
        map.put("totalCode", "总登记号");
        map.put("catalogCode", "编目号");
        map.put("typeCode", "分类号");
        map.put("zone", "库房位次");
        map.put("era", "时代");
        map.put("state", "状态");
        map.put("texture", "质地");
        map.put("count", "件数");
        map.put("level", "级别");
        map.put("currentSituation", "现状");
        proList = propertyService.findAllProperty();
        for (Property p : proList) {
            if (!p.getEnName().equals("annex")) {
                map.put(p.getEnName(), p.getCnName());
            }
        }
        int intRowCount = relicList.size(); //总记录数
        int allSheetCount = (intRowCount + (ROW_SIZE_OF_SHEET - 1)) / ROW_SIZE_OF_SHEET;            //计算总工作表数
        // 创建新的Excel 工作簿
        Workbook wb = new HSSFWorkbook();
        checkPro = checkPro.substring(0, checkPro.length() - 1);
        String[] title1 = checkPro.split(",");


        CellStyle titleCellStyle = getCellStyle(wb, 15);
        CellStyle dataCellStyle = getCellStyle(wb, 12);
        for (int sheetIndex = 1; sheetIndex <= allSheetCount; sheetIndex++) {
            // 创建 sheet
            Sheet sheet = wb.createSheet("第" + sheetIndex + "页");
            // 创建 title
            packageTitle(title1, map, wb, sheet, titleCellStyle);

            // 填充数据
            int start = (sheetIndex - 1) * ROW_SIZE_OF_SHEET;
            int end = (sheetIndex) * ROW_SIZE_OF_SHEET;
            end = Math.min(end, relicList.size());
            //  设置标题列，自动调宽
            for(int i = 0;i<title1.length;i++){
                sheet.autoSizeColumn(i,true);
            }

            int rowIndex = 1; // row 从 0 开始
            for (Relic relic : relicList.subList(start, end)) {
                Row row = sheet.createRow((short) rowIndex);
                row.setHeightInPoints((float) 20);
                packageData(title1, relic, row, sheet, dataCellStyle);
                sheet.autoSizeColumn(rowIndex, true);
                rowIndex++;
            }
        }

        HttpServletResponse res = ServletActionContext.getResponse();
        String fileName = "藏品信息.xls";
        // 文件名编码，要不然乱码
        fileName = new String(fileName.getBytes(), Charsets.ISO_8859_1);
        res.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
        res.setContentType("application/octet-stream;charset=" + "ISO8859-1");
        try {
            wb.write(res.getOutputStream());
        } catch (IOException e) {
            logFailed("导出文物 excel", "导出文物 excel失败");
            e.printStackTrace();
        } finally {
            try {
                res.getOutputStream().close();
            } catch (IOException e) {
                logFailed("导出文物 excel", "导出文物 excel失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回默认样式
     *
     * @param wb
     * @return
     */
    private CellStyle getCellStyle(Workbook wb, int fontSize) {
        //设置excle的字体
        Font font = wb.createFont();
        font.setFontName("华文楷体");
        font.setFontHeightInPoints((short) fontSize);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框

        // 设置文字居中
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setLocked(false);
        return cellStyle;
    }

    /**
     * 封装数据
     *
     * @param title     所选择的属性
     * @param relic     藏品对戏那个
     * @param row1      excle中的行
     * @param sheet1    excle中的sheet
     * @param cellStyle excle中单元格的样式
     * @author liuzhu
     * @date 13-08-14
     */
    public void packageData(String[] title, Relic relic, Row row1, Sheet sheet1, CellStyle cellStyle) {
        for (int k = 0; k < title.length; k++) { //title为藏品对象的属性
            Cell cell = row1.createCell(k);//k为当前循环的下标
            cell.setCellStyle(cellStyle);
            String checkPro = title[k].toString();
            Object objectValue = ClassUtil.getFieldValue(relic, checkPro);
            Object objectType = ClassUtil.getFieldType(relic, checkPro);
            try {
                if (objectValue == null) {
                    for (int m = 0; m < relic.getRelicPropertielist().size(); m++) {  //relic.getRelicPropertielist()为藏品的属性表
                        RelicProperty rp = relic.getRelicPropertielist().get(m);
                        if (rp != null) {
                            if (rp.getProperty().getEnName().equals(checkPro)) {  //rp.getProperty().getEnName()为属性类中的英文名称
                                cell.setCellValue(rp.getPropertyValue());
                            } else if (checkPro.equals("currentSituation")) {
                                Iterator iterator = relic.getStatusQuos().iterator();
                                while (iterator.hasNext()){
                                    StatusQuo statusQuo = (StatusQuo)iterator.next();
                                    cell.setCellValue(statusQuo.getQuoInfo());
                                    break;
                                }
                            }
                        }
                    }
                } else if (objectType.equals(Level.class) ||   //藏品基本属性
                        objectType.equals(Texture.class) ||
                        objectType.equals(Zone.class) ||
                        objectType.equals(Era.class)) {
                    Object obj = objectValue;
                    cell.setCellValue(ClassUtil.getFieldValue(obj, "name").toString());
                } else {
                    if (checkPro.equals("state")) {
                        String state = objectValue.toString();
                        if (state.equals("0")) {
                            cell.setCellValue("在库");
                        } else if (state.equals("1")) {
                            cell.setCellValue("待出库");
                        } else {
                            cell.setCellValue("出库");
                        }
                    } else if (checkPro.equals("level")) {
                        String state = objectValue.toString();
                        if (state.equals("1")) {
                            cell.setCellValue("一级");
                        } else if (state.equals("2")) {
                            cell.setCellValue("二级");
                        } else if (state.equals("3")) {
                            cell.setCellValue("三级");
                        } else if (state.equals("4")) {
                            cell.setCellValue("一般");
                        } else if (state.equals("5")) {
                            cell.setCellValue("未定级");
                        }

                    } else {
                        cell.setCellValue(objectValue.toString());
                    }
                }
            } catch (Exception e) {
                logFailed("导出文物 excel", "导出文物 excel失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 封装标题
     *
     * @param title     所选择的属性
     * @param map       藏品的所有属性
     * @param wb        workbook工作薄
     * @param cellStyle excle中单元格的样式
     * @author liuzhu
     * @date 13-08-14
     */
    public void packageTitle(String[] title, Map<String, String> map, Workbook wb, Sheet sheet, CellStyle cellStyle) {
        Row row = sheet.createRow((short) 0);
        row.setHeightInPoints((float) 25);
        int index = 0;
        for (String s : title) {
            Cell cell = row.createCell(index);
            cell.setCellValue(map.get(s));
            cell.setCellStyle(cellStyle);
            index++;
        }
    }

    public RelicService getRelicService() {
        return relicService;
    }

    public void setRelicService(RelicService relicService) {
        this.relicService = relicService;
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

    public String getExportLabelId() {
        return exportLabelId;
    }

    public void setExportLabelId(String exportLabelId) {
        this.exportLabelId = exportLabelId;
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

    public List<Relic> getRelics() {
        return relics;
    }

    public void setRelics(List<Relic> relics) {
        this.relics = relics;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCheckPro() {
        return checkPro;
    }

    public void setCheckPro(String checkPro) {
        this.checkPro = checkPro;
    }

    public Boolean getCanceled() {
        return isCanceled;
    }

    public void setCanceled(Boolean canceled) {
        isCanceled = canceled;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getLogOff() {
        return logOff;
    }

    public void setLogOff(String logOff) {
        this.logOff = logOff;
    }
}
