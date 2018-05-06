package com.microwise.phoenix.action.orion;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.orion.bean.*;
import com.microwise.orion.service.OutEventService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 外展统计
 *
 * @author li.jianfei
 * @date 2016-09-22
 */
@Beans.Action
@Phoenix
public class ExhibitionStatAction extends PhoenixLoggerAction {

    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String _pagePath = "../orion/exhibition-stat.ftl";

    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;

    /**
     * 外展开始时间
     */
    private Date beginTime;

    /**
     * 外展结束时间
     */
    private Date endTime;

    /**
     * 出库事件记录(出库单) 实体集合
     */
    private List<OutEvent> outEventList;

    /**
     * 外展事件id
     */
    private String eventId;

    /**
     * 外展数据分析
     *
     * @return String
     */
    @Route("/phoenix/orion/exhibition-stat")
    public String exhibitionStat() {
        if (beginTime == null) {
            beginTime = DateTimeUtil.startOfYear(DateTime.now().toDate());
            endTime = new Date();
        }
        //查询指定时间范围内的外展事件
        outEventList = outEventService.findExhibitions(Sessions.createByAction().currentSiteId(), beginTime, endTime);
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /***
     * 外展数据分析导出
     * exportName 外展导出数据excel表名
     * headers 导出excel的表头
     * decriptions 导出excel表头上面的内容
     *
     */
    @Route("/phoenix/orion/exhibition-stat-export")
    public String exhibitionStatExport() {
        try {
            //查询出库事件集合
            outEventList = outEventService.findExhibitions(Sessions.createByAction().currentSiteId(),
                    beginTime, endTime);
            //创建一个工作薄
            Workbook workbook = new HSSFWorkbook();
            //创建excel表格
            createExcel(workbook, outEventList);
            //时间格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //写入excel的名称
            String exportName = sdf.format(beginTime) + "至" + sdf.format(endTime) + "外展分析数据" + ".xls";
            //取得输出流
            OutputStream os = new FileOutputStream(exportName);
            //写入excel文件并关闭流
            writeAndClose(workbook, os);
            //创建响应对象
            HttpServletResponse response = ServletActionContext.getResponse();
            //下载excel表格
            downloadExcel(exportName, response);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | FileNotFoundException e) {
            log.error("外展数据导出", e);
        }

        return Action.NONE;
    }

    /***
     *excel文档下载
     * @param path 要下载文件的路径
     * @param response 响应对象
     */
    private void downloadExcel(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            // 清空response
            response.reset();
            HttpServletRequest request = ServletActionContext.getRequest();
            String Agent = request.getHeader("User-Agent");
            Agent = Agent.toLowerCase();
            if (Agent.indexOf("msie") != -1) {
                filename = URLEncoder.encode(filename, "UTF-8");//IE浏览器
                response.addHeader("Content-Disposition", "attachment;filename="
                        + filename);
            } else {
                response.addHeader("Content-Disposition", "attachment;filename="
                        + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
            }
            // 设置response的Header
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException e) {
            log.error("外展数据导出-下载excel", e);
        }
    }

    /**
     * 创建excel表格
     *
     * @param workbook  excel工作薄
     * @param outEvents 出库事件集合
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private void createExcel(Workbook workbook, List<OutEvent> outEvents) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        int sheetNum = 0;
        //时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (OutEvent outEvent : outEvents) {
            String[] descriptions = {"申请人:   " + outEvent.getApplicant(), "提用目的:   " + outEvent.getUseFor(),
                    "类型:   " + (outEvent.getEventType() == 1 ? "外出借展" : "科研修复"),
                    "是否出馆:  " + (outEvent.isOutBound() ? "是" : "否"), "开始时间:    " + sdf.format(outEvent.getBeginDate()),
                    "结束时间:   " + sdf.format(outEvent.getEndDate())};
            Set<EventRelic> eventRelics = outEvent.getEventRelics();
            List<Relic> exportList = new ArrayList<>();
            for (EventRelic eventRelic : eventRelics) {
                exportList.add(eventRelic.getRelic());
            }
            Sheet sheet = workbook.createSheet();
            workbook.setSheetName(sheetNum++, StringFilter(outEvent.getUseFor()));
            //设置每列的宽度
            sheet.setColumnWidth(0, (short) 2500);
            sheet.setColumnWidth(1, (short) 5500);
            sheet.setColumnWidth(2, (short) 2000);
            sheet.setColumnWidth(3, (short) 2500);
            sheet.setColumnWidth(4, (short) 2500);
            sheet.setColumnWidth(5, (short) 3000);
            sheet.setColumnWidth(6, (short) 35000);
            // 生成一个样式
            CellStyle style = workbook.createCellStyle();
            // 设置这些样式
            //设置背景色
            style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            //设置上下左右边框
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
            // 生成一个字体
            Font font = workbook.createFont();
            font.setColor(HSSFColor.BLACK.index);
            font.setFontHeightInPoints((short) 12);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            // 把字体应用到当前的样式
            style.setFont(font);
            // 生成并设置另一个样式
            CellStyle style2 = workbook.createCellStyle();
            //设置背景颜色
            style2.setFillForegroundColor(HSSFColor.WHITE.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            //设置边框上下左右
            style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
            //文字垂直水平居中
            //style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            // 生成并设置另一个样式
            CellStyle style3 = workbook.createCellStyle();
            style3.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            // 生成另一个字体
            Font font2 = workbook.createFont();
            font2.setColor(HSSFColor.BLACK.index);
            font2.setFontHeightInPoints((short) 12);
            font2.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            // 把字体应用到当前的样式
            style2.setFont(font2);
            style3.setFont(font2);
            //用单元格合并方式生成decriptions
            if (descriptions != null) {
                int next = 0;
                for (int row = 0; row < 2; row++) {
                    Row r = sheet.createRow(row);
                    Cell c = null;
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 1));
                            c = r.createCell(0);
                        } else if (col == 1) {
                            sheet.addMergedRegion(new CellRangeAddress(row, row, 2, 5));
                            c = r.createCell(2);
                        } else if (col == 2) {
                            sheet.addMergedRegion(new CellRangeAddress(row, row, 6, 6));
                            c = r.createCell(6);
                        }
                        c.setCellStyle(style3);
                        HSSFRichTextString text1 = new HSSFRichTextString(descriptions[next++]);
                        c.setCellValue(text1);
                    }
                }
            }
            String[] headers = {"总登记号", "名称", "级别", "质地", "件数", "库存状态", "流转经历"};
            // 产生表格标题行
            Row row = sheet.createRow(3);
            row.setHeight((short) 500);
            for (short i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            // 遍历集合数据，产生数据行
            int index = 3;
            for (int j = 0; j < exportList.size(); j++) {
                Object t = exportList.get(j);
                //流转经历次数
                int roveCount = exportList.get(j).getRoves().size();
                int a = 0;
                do {
                    index++;
                    row = sheet.createRow(index);
                    //合并单元格
                    int startRow = index;
                    int lastRow;
                    for (int col = 0; col < 6; col++) {
                        if (roveCount == 0 || roveCount == 1) {
                            lastRow = startRow;
                        } else {
                            lastRow = startRow + roveCount - 1;
                        }
                        sheet.addMergedRegion(new CellRangeAddress(startRow, lastRow, col, col));
                    }
                    row.setHeight((short) 400);
                    // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                    Field[] fields = t.getClass().getDeclaredFields();
                    Cell cell = null;
                    int cellIndex = 0;
                    for (short i = 0; i < fields.length; i++) {
                        Field field = fields[i];
                        String fieldName = field.getName();
                        String textValue = "";
                        //如果是指定的字段,进行单元格赋值
                        if (fieldName.equals("totalCode") || fieldName.equals("name") || fieldName.equals("level") ||
                                fieldName.equals("texture") || fieldName.equals("count") ||
                                fieldName.equals("stateName") || fieldName.equals("roves")) {
                            cell = row.createCell(cellIndex);
                            cellIndex++;
                            cell.setCellStyle(style2);
                            String getMethodName = "get"
                                    + fieldName.substring(0, 1).toUpperCase()
                                    + fieldName.substring(1);
                            Class tCls = t.getClass();
                            //反射拿到属性的get方法
                            Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                            //调用get方法拿到属性值
                            Object value = getMethod.invoke(t, new Object[]{});
                            //轮转经历信息集合
                            List<String> roveInfo = new ArrayList<>();
                            // 判断值的类型后进行强制类型转换
                            if (value instanceof Date) {
                                Date date = (Date) value;
                                textValue = sdf.format(date);
                            } else if (value instanceof Texture) {
                                textValue = ((Texture) value).getName();
                            } else if (value instanceof Level) {
                                textValue = ((Level) value).getName();
                            } else if (value instanceof Set) {
                                if (roveCount == 0) {
                                    textValue = "无";
                                } else {
                                    for (Rove rove : (Set<Rove>) value) {
                                        roveInfo.add(rove.getRoveInfo());
                                    }
                                    textValue = roveInfo.get(a);
                                }
                            } else if (value != null) {
                                // 其它数据类型都当作字符串简单处理
                                textValue = value.toString();
                            }
                            cell.setCellValue(new HSSFRichTextString(textValue));
                        }
                        // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                        if (textValue != "") {
                            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                            Matcher matcher = p.matcher(textValue);
                            if (matcher.matches()) {
                                // 是数字当作double处理
                                cell.setCellValue(Integer.parseInt(textValue));
                            } else {
                                cell.setCellValue(textValue);
                            }
                        }
                    }
                    a++;
                } while (a < roveCount);
            }

        }
    }

    /**
     * 过滤掉excel sheet名不允许的特殊字符
     *
     * @param str 要过滤的字符串
     * @return String
     */

    private String StringFilter(String str) throws PatternSyntaxException {
        String regEx = "[*:?\\[\\]【】？：*]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 写入excel文件并关闭
     *
     * @param workbook excel文件
     * @param os       输出流
     */
    private void writeAndClose(Workbook workbook, OutputStream os) {
        if (workbook != null) {
            try {
                workbook.write(os);
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                log.error("外展数据导出-写入excel文件并关闭", e);
            }
        }

    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<OutEvent> getOutEventList() {
        return outEventList;
    }

    public void setOutEventList(List<OutEvent> outEventList) {
        this.outEventList = outEventList;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}

