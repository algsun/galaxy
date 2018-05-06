package com.microwise.phoenix.bean.vo;

import java.util.List;

/**
 * 系统管理：用户登录 统计实体
 *
 * @author xu.baoji
 * @date 2013-7-22
 */
public class UserLoginStat {

    /**
     * 周统计 按周排序 ,如果没有数据对应的位置为null
     */
    private List<Float> weekStat;

    /**
     * 日统计 按时间排序 ，如果没有数据对应的位置为null
     */
    private List<Float> dayStat;

    /**
     * 是否有数据 true:有数据  false ：无数据
     */
    private boolean hasData = false;

    public List<Float> getWeekStat() {
        return weekStat;
    }

    public void setWeekStat(List<Float> weekStat) {
        this.weekStat = weekStat;
    }

    public List<Float> getDayStat() {
        return dayStat;
    }

    public void setDayStat(List<Float> dayStat) {
        this.dayStat = dayStat;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
}
