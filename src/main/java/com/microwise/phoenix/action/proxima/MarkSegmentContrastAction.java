package com.microwise.phoenix.action.proxima;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.MarkSegmentContrast;
import com.microwise.phoenix.service.MarkSegmentService;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.microwise.phoenix.util.MarkSegmentComparator;
import com.microwise.proxima.sys.Proxima;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 标记段对比
 *
 * @author liuzhu
 * @date 2013-08-06
 * @check @gaohui #4956 2013-8-15
 */

@Beans.Action
@Proxima
public class MarkSegmentContrastAction extends PhoenixLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(MarkSegmentContrastAction.class);

    public static final String _pagePath = "../proxima/markSegmentContrast.ftl";

    /**
     * 标记段service
     */
    @Autowired
    private MarkSegmentService markSegmentService;

    // input
    /**
     * 时间类型(1-年  2-月)
     */
    private int dateType = Constants.FIND_TYPE_MONTH;

    /**
     * 日期
     */
    private Date date;

    // output
    /**
     * 结论
     */
    private String conclusion = "";

    /**
     * 标记段list
     */
    private List<MarkSegmentContrast> markSegmentList;

    /**
     * 标记段变化最大
     */
    private String maxChange;

    /**
     * 标记段变化最小
     */
    private String minChange;

    @Route("/phoenix/proxima/markSegmentContrast")
    public String view() {
        String siteId = Sessions.createByAction().currentSiteId();
        if (date == null) {
            date = new Date();
        }
        markSegmentList = markSegmentService.contrast(siteId, date, dateType);
        Collections.sort(markSegmentList, new MarkSegmentComparator(false));
        Conclusion(); //结论
        log("数据分析", "标记段对比");
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /**
     * 拼结论
     *
     * @author 刘柱
     * @date 2013-09-10
     */
    private void Conclusion() {
        String timeTemp = "";
        if (dateType == Constants.FIND_TYPE_YEAR) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
            timeTemp = sdf.format(date);
        } else if (dateType == Constants.FIND_TYPE_MONTH) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            timeTemp = sdf.format(date);
        }
        if (markSegmentList.size() > 0) {
            maxChange = "<strong>" + markSegmentList.get(0).getMarkName() + "(" + markSegmentList.get(0).getPlaceName() + ")</strong>变化的长度为：<strong>" + markSegmentList.get(0).getLengthRealDelta() + "mm</strong>";
            minChange = "<strong>" + markSegmentList.get(markSegmentList.size() - 1).getMarkName() + "(" + markSegmentList.get(0).getPlaceName() + ")</strong>变化的长度为：<strong>"
                    + markSegmentList.get(markSegmentList.size() - 1).getLengthRealDelta() + "mm</strong>";
            conclusion = timeTemp + "<br/>" + "标记段变化最大的是：" + maxChange +
                    "<br/>标记段变化最小的是：" + minChange + "";
        } else if (markSegmentList.size() == 1) {
            maxChange = markSegmentList.get(0).getMarkName() + "(" + markSegmentList.get(0).getPlaceName() + "),变化的长度为：" + markSegmentList.get(0).getLengthRealDelta() + "mm";
            conclusion = timeTemp + "<br/>" + "标记段变化最大的是：<strong>" + maxChange;
        }
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MarkSegmentService getMarkSegmentService() {
        return markSegmentService;
    }

    public void setMarkSegmentService(MarkSegmentService markSegmentService) {
        this.markSegmentService = markSegmentService;
    }

    public List<MarkSegmentContrast> getMarkSegmentList() {
        return markSegmentList;
    }

    public void setMarkSegmentList(List<MarkSegmentContrast> markSegmentList) {
        this.markSegmentList = markSegmentList;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getMaxChange() {
        return maxChange;
    }

    public void setMaxChange(String maxChange) {
        this.maxChange = maxChange;
    }

    public String getMinChange() {
        return minChange;
    }

    public void setMinChange(String minChange) {
        this.minChange = minChange;
    }
}
