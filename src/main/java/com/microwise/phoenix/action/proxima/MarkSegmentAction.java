package com.microwise.phoenix.action.proxima;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.MarkSegment;
import com.microwise.phoenix.service.MarkSegmentService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 标记段幅度统计
 *
 * @author duan.qixin
 * @date 2013-08-08
 * @check @xu.baoji 2013-8-12 #4970
 */
@Beans.Action
@Phoenix
public class MarkSegmentAction extends PhoenixLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(MarkSegmentAction.class);

    public static final String _pagePath = "../proxima/mark-seg-render.ftl";

    //input
    /**
     * 要查询的数据年份
     */
    private int year;
    /**
     * 要查询的标记段的id
     */
    private String markId;

    //output
    /**
     * 结论
     */
    private String backup;
    /**
     * 月份数据
     */
    private String monthData = "";
    /**
     * 标记段数据
     */
    private String markData = "";
    /**
     * 标记段信息
     */
    private List<MarkSegment> marks;

    @Autowired
    private MarkSegmentService service;

    //获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/phoenix/proxima/markSegRender")
    public String view() {
        if (year == 0) {
            //默认系统当前年份
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        //查询标记段信息
        marks = service.findAllMark(siteId);
        //默认markId
        if (markId == null && marks != null && marks.size() > 0) {
            markId = marks.get(0).getMarkId();
        }

        //查询一个标记段的年统计数据
        findMark();

        log("数据分析", "标记段幅度");
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /**
     * 组装json数据
     *
     * @author 刘柱
     * @date 2013-09-10
     */
    private void findMark() {
        if (markId != null) {
            Map<Integer, Float> datas = service.findMarkYearStat(markId, year);
            if (datas != null && datas.size() > 0) {
                int m_temp = 0;
                float m_data = 0;
                //装配JSON数据
                markData = "{name: '" + year + "年',data: [";
                for (Integer key : datas.keySet()) {
                    float value = datas.get(key);
                    monthData += "'" + key + "月',";
                    markData += value + ",";
                    //判断大小
                    if (Math.abs(value) >= m_data) {
                        m_temp = key;
                        m_data = value;
                    }
                }
                //结论
                backup = "这一年中，绝对值变化最大的月份是" + m_temp + "月，数值为" + m_data + "毫米。";

                if (monthData.length() > 0) {
                    monthData = "[" + monthData.substring(0, monthData.length() - 1) + "]";
                    markData = markData.substring(0, markData.length() - 1) + "]}";
                }
            }
        }
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    //getter setter
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public List<MarkSegment> getMarks() {
        return marks;
    }

    public void setMarks(List<MarkSegment> marks) {
        this.marks = marks;
    }

    public String getMonthData() {
        return monthData;
    }

    public void setMonthData(String monthData) {
        this.monthData = monthData;
    }

    public String getMarkData() {
        return markData;
    }

    public void setMarkData(String markData) {
        this.markData = markData;
    }
}
