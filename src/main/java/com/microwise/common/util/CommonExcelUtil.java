package com.microwise.common.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel使用工具类.
 *
 * @author wang.geng
 * @date 14-4-29 上午10:50
 */
public class CommonExcelUtil {

    /**
     * 单元格样式对象创建，并设置基础样式，可被重新设置
     * 1.上下左右有边框，边框宽度为1px
     * 2.文字居中
     * 3.字体为"华文楷体"
     *
     * @param wb       工作簿
     * @param fontSize 字体大小
     * @return 样式对象
     */
    public static CellStyle createCellStyle(Workbook wb, int fontSize) {
        CellStyle cellStyle = wb.createCellStyle();
        //设置excle的字体
        Font font = wb.createFont();
        font.setFontName("华文楷体");
        font.setFontHeightInPoints((short) fontSize);
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

}
