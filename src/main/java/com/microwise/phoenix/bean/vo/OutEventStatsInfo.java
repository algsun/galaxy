package com.microwise.phoenix.bean.vo;

import com.microwise.phoenix.bean.po.OutEventInfo;

import java.util.List;

/***
 * 出库 统计详情
 *
 * @author xu.baoji
 * @date 2013-7-9
 */
public class OutEventStatsInfo {

    /**
     * 出库 类型
     */
    private Integer eventType;

    /**
     * 该类型出库次数
     */
    private Integer typeCount;

    /**
     * 该类型 出库文物总套数
     */
    private Integer typeSum;

    /**
     * 出库事件单
     */
    private List<OutEventInfo> outEventInfos;

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(Integer typeCount) {
        this.typeCount = typeCount;
    }

    public Integer getTypeSum() {
        return typeSum;
    }

    public void setTypeSum(Integer typeSum) {
        this.typeSum = typeSum;
    }

    public List<OutEventInfo> getOutEventInfos() {
        return outEventInfos;
    }

    public void setOutEventInfos(List<OutEventInfo> outEventInfos) {
        this.outEventInfos = outEventInfos;
    }

}
