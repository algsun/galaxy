package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Charsets;
import com.microwise.blueplanet.bean.po.AvgdataPO;
import com.microwise.blueplanet.bean.vo.LocationAvgdataVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.CommonExcelUtil;
import com.microwise.common.util.ResourceBundleUtil;
import com.microwise.uma.util.DateTypeGenerator;
import com.opensymphony.xwork2.Action;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 均峰值Action
 *
 * @author liuzhu
 * @date 2014-7-9
 */
@Beans.Action("locationAverageAdnPeakAction")
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class AverageAndPeakAction extends BlueplanetLoggerAction {

    /**
     * 日志对象
     */
    public static final Logger log = LoggerFactory.getLogger(AverageAndPeakAction.class);

    public static int ROW_SIZE_OF_SHEET = 0;

    /**
     * 日期格式
     */
    private final static SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat SHORT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    //每个sheet中的总显示数
    static {
        String str = ConfigFactory.getInstance().getConfig("config.properties").get("orion.relic.export.page.rowSize");
        ROW_SIZE_OF_SHEET = Integer.parseInt(str);
    }

    /**
     * 内容页面
     */
    private static final String _pagePath = "charts/average-peak.ftl";

    @Autowired
    private LocationService locationService;

    //input
    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 位置点
     */
    private LocationVO location;

    /**
     * 起始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    {
        startDate = DateTypeGenerator.monthStart();
        endDate = new Date();
    }

    //output
    /**
     * hightChart图表显示数据组装
     */
    private List<LocationAvgdataVO> locationAvgdatas;

    /**
     * 数据条数
     */
    private int count;


    @Route(value = "/blueplanet/location/{locationId}/average-peak-data", interceptors = "locationStack")
    public String defaultView() {
        location = locationService.findLocationById(locationId);
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    @Route(value = "/blueplanet/location/{locationId}/getAvgPeakDataByTime.json")
    public String getAvgPeakDataByTime() {
        try {
            location = locationService.findLocationById(locationId);
            List<AvgdataPO> avgdataPOList = locationService.findAverageAndPeakValue(locationId, startDate, endDate);
            locationAvgdatas = locationService.assembleAverageAndPeakValue(locationId, avgdataPOList);
            for (LocationAvgdataVO locationAvgdata : locationAvgdatas) {
                List<AvgdataPO> sortAvgPOs = locationAvgdata.getAvgdataList();
                List<Date> returnDate = getStartDateAndEndDate(sortAvgPOs);
                locationAvgdata.setDateType(getDateTypeByStartEndDate(returnDate.get(0), returnDate.get(1)));
            }
            log("位置点", "均峰值");
        } catch (Exception e) {
            log.error("均峰值查询", e);
        }
        return Results.json().asRoot(locationAvgdatas).done();
    }

    @Route(value = "/blueplanet/location/{locationId}/waitingForExport", params = {"startDate", "endDate"})
    public String waitingForExport() {
        try {
            count = locationService.findAverageAndPeakValue(locationId, startDate, endDate).size();
            log("导出数据条目数", "均峰值");
        } catch (Exception e) {
            log.error("均峰值查询导出数据条目数失败", e);
        }
        return Results.ftl("/blueplanet/pages/location/charts/avgPeak-data-export");
    }

    /**
     * 均峰值数据导出
     *
     * @return
     * @throws java.io.IOException
     */
    public String export() {
        try {
            List<AvgdataPO> avgdataList = locationService.findAverageAndPeakValue(locationId, startDate, endDate);
            List<LocationAvgdataVO> locationStatisticsList = locationService.assembleAverageAndPeakValue(locationId, avgdataList);
            String locationName = locationService.findLocationById(locationId).getLocationName();
            createExcel(locationStatisticsList, locationName);
        } catch (Exception e) {
            log.error("均峰值Excel数据导出失败", e);
        }
        return Action.NONE;
    }


    private void createExcel(List<LocationAvgdataVO> locationStatisticsList, String locationName) {

        //初始化表头
        Map<String, String> excelHeads = initializeExcelHeader();


        // 创建新的Excel 工作簿
        Workbook wb = new HSSFWorkbook();

        //表头单元格样式与数据单元格样式
        CellStyle titleCellStyle = CommonExcelUtil.createCellStyle(wb, 15);
        CellStyle dataCellStyle = CommonExcelUtil.createCellStyle(wb, 12);

        for (LocationAvgdataVO locationAvgdataVO : locationStatisticsList) {

            //总记录数计数器
            int dataSum = locationAvgdataVO.getAvgdataList().size();

            //指标工作表总数，一张工作表记录数最大为65535
            int allSheetCount = (dataSum + (ROW_SIZE_OF_SHEET - 1)) / ROW_SIZE_OF_SHEET;
            for (int sheetIndex = 1; sheetIndex <= allSheetCount; sheetIndex++) {
                // 创建 sheet
                String sheetName;
                if (locationAvgdataVO.getSensorPhysicalid() == 43) {
                    sheetName = "露点（霜点）";
                } else {
                    sheetName = locationAvgdataVO.getCnName();
                }
                //指标分页sheet命名
                if (sheetIndex > 1) {
                    sheetName = sheetName + sheetIndex;
                }

                Sheet sheet = wb.createSheet(sheetName);
                // 创建 title
                packageTitle(excelHeads, sheet, titleCellStyle);

                //  设置标题列，自动调宽
                for (int i = 0; i < excelHeads.size(); i++) {
                    sheet.autoSizeColumn(i, true);
                }

                int rowIndex = 1; // row 从 0 开始
                List<AvgdataPO> avgdataList = locationAvgdataVO.getAvgdataList();
                String sensorCnName = locationAvgdataVO.getCnName();
                for (AvgdataPO avgdataPO : avgdataList) {
                    Row row = sheet.createRow((short) rowIndex);
                    row.setHeightInPoints((float) 20);
                    createRowCells(0, row, dataCellStyle, avgdataPO.getNodeid());
                    createRowCells(1, row, dataCellStyle, sensorCnName);
                    createRowCells(2, row, dataCellStyle, getDefaultAttrValue(avgdataPO.getMaxValue()));
                    createRowCells(3, row, dataCellStyle, getDefaultAttrValue(avgdataPO.getMaxTime()));
                    createRowCells(4, row, dataCellStyle, getDefaultAttrValue(avgdataPO.getMinValue()));
                    createRowCells(5, row, dataCellStyle, getDefaultAttrValue(avgdataPO.getMinTime()));
                    createRowCells(6, row, dataCellStyle, getDefaultAttrValue(avgdataPO.getAvgValue()));
                    createRowCells(7, row, dataCellStyle, getDefaultAttrValue(avgdataPO.getWaveValue()));
                    createRowCells(8, row, dataCellStyle, avgdataPO.getMsDate() == null ? "" : SHORT_FORMAT.format(avgdataPO.getMsDate()));
                    sheet.autoSizeColumn(rowIndex, true);
                    rowIndex++;
                }
            }
        }

        //生成的Excel下载
        HttpServletResponse res = ServletActionContext.getResponse();
        String startDateStr = DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate);
        String endDateStr = DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate);
        //todo 国际化
        String fileName = '(' + locationName + ')' + startDateStr + '至' + endDateStr + "均峰值数据.xls";
        // 文件名编码，要不然乱码
        fileName = new String(fileName.getBytes(), Charsets.ISO_8859_1);
        res.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
        res.setContentType("application/octet-stream;charset=" + "ISO8859-1");
        try {
            wb.write(res.getOutputStream());
        } catch (IOException e) {
            log.error("导出均峰值 excel失败", e);
        } finally {
            try {
                res.getOutputStream().close();
            } catch (IOException e) {
                log.error("导出均峰值 excel失败", e);
            }
        }
    }

    private Map<String, String> initializeExcelHeader() {
        //初始化表头
        Map<String, String> map = new TreeMap<String, String>();
        map.put("anodeId", ResourceBundleUtil.getBundle().getString("blueplanet.action.location.nodeID"));
        map.put("bsensorPhysicalId", ResourceBundleUtil.getBundle().getString("common.monitoringIndicators"));
        map.put("cmaxValue", ResourceBundleUtil.getBundle().getString("blueplanet.statistics.maxValue"));
        map.put("dmaxDate", ResourceBundleUtil.getBundle().getString("blueplanet.action.location.maxDate"));
        map.put("eminValue", ResourceBundleUtil.getBundle().getString("blueplanet.statistics.minValue"));
        map.put("fminDate", ResourceBundleUtil.getBundle().getString("blueplanet.action.location.minDate"));
        map.put("gavgValue", ResourceBundleUtil.getBundle().getString("blueplanet.action.location.averageValue"));
        map.put("hwaveValue", ResourceBundleUtil.getBundle().getString("blueplanet.statistics.waveValue"));
        map.put("idate", ResourceBundleUtil.getBundle().getString("blueplanet.action.location.date"));
        return map;
    }

    private void createRowCells(int index, Row row1, CellStyle dataCellStyle, String value) {
        Cell cell = row1.createCell(index);
        cell.setCellStyle(dataCellStyle);
        cell.setCellValue(value);
    }

    private String getDefaultAttrValue(Object strObj) {
        if (strObj == null) {
            return "";
        } else if (strObj instanceof String) {
            return strObj.toString();
        } else if (strObj instanceof Double) {
            return strObj.toString();
        } else if (strObj instanceof Date) {
            return FULL_FORMAT.format(strObj);
        } else {
            return "";
        }
    }

    /**
     * 根据起始时间与结束时间判断图表显示方式
     * 一天以内按小时显示
     * 一月以内按日显示
     * 大于一个月都按年显示
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @return Integer 时间类型
     */
    private int getDateTypeByStartEndDate(Date startDate, Date endDate) {
        int dateType = 0;
        long dayMillis = 24 * 3600 * 1000;
        long millis = endDate.getTime() - startDate.getTime();
        long days = (millis / dayMillis) + 1;

        //起始时间与结束时间在一天以内，按小时显示数据
        if (0 < millis && millis < dayMillis && days == 1) {
            dateType = Constants.FIND_TYPE_DAY;
        }
        //起始时间与结束时间在一个月内，按天显示数据
        else if (0 < days && days <= 30) {
            dateType = Constants.FIND_TYPE_MONTH;
        }
        //起始时间与结束时间在一年与多年之间，都按月来显示
        else if (30 < days) {
            dateType = Constants.FIND_TYPE_YEAR;
        } else {
            dateType = 0;
        }
        return dateType;
    }

    /**
     * 封装标题
     *
     * @param map       所有属性
     * @param cellStyle excle中单元格的样式
     * @author wang.geng
     * @date 13-08-14
     */
    public void packageTitle(Map<String, String> map, Sheet sheet, CellStyle cellStyle) {
        Row row = sheet.createRow((short) 0);
        row.setHeightInPoints((float) 25);
        int index = 0;
        Set<String> nameSet = map.keySet();
        for (String enName : nameSet) {
            Cell cell = row.createCell(index);
            cell.setCellValue(map.get(enName));
            cell.setCellStyle(cellStyle);
            index++;
        }
    }

    /**
     * 根据 sortAvgPOs集合中的对象的msDate字段进行排序，第一个元素为起始时间，第二个元素为结束时间
     *
     * @param sortAvgPOs 对象集合
     * @return 起始时间结束时间List
     */
    private List<Date> getStartDateAndEndDate(List<AvgdataPO> sortAvgPOs) {
        List<Date> returnDate = new ArrayList<Date>();
        if (sortAvgPOs.size() == 0) {
            Date startDate = DateTypeGenerator.monthStart();
            Date endDate = new Date();
            returnDate.add(0, startDate);
            returnDate.add(1, endDate);
            return returnDate;
        }
        long maxTempMillis = 0;
        long minTempMillis = 0;
        for (AvgdataPO avgdataPO : sortAvgPOs) {
            long millis = avgdataPO.getMsDate().getTime();
            if (maxTempMillis == 0 && minTempMillis == 0) {
                maxTempMillis = millis;
                minTempMillis = millis;
            } else if (millis >= maxTempMillis) {
                maxTempMillis = millis;
            } else if (millis <= minTempMillis) {
                minTempMillis = millis;
            }
        }
        returnDate.add(0, new Date(minTempMillis));
        returnDate.add(1, new Date(maxTempMillis));
        return returnDate;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<LocationAvgdataVO> getLocationAvgdatas() {
        return locationAvgdatas;
    }

    public void setLocationAvgdatas(List<LocationAvgdataVO> locationAvgdatas) {
        this.locationAvgdatas = locationAvgdatas;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }
}
