package com.microwise.blueplanet.bean.vo;

import java.util.List;

/**
 * 设备运行状态饼图实体
 *
 * @author 王耕
 * @date 2014-10-24
 * @check xu.yuexi wang.geng #9744 2014-10-30
 */
public class DeviceStatusPieData {

    /**设备运行状态饼图数据  list<object> 如果hasData 为true ：该列表有两个元素，第一个 为饼图 块名称，第二个为饼图块 占有 %*/
    private List<List<Object>> pieData;

    /**是否有数据*/
    private boolean hasData;

    public List<List<Object>> getPieData() {
        return pieData;
    }

    public void setPieData(List<List<Object>> pieData) {
        this.pieData = pieData;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
}
