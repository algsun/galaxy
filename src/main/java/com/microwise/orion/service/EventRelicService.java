package com.microwise.orion.service;

import com.microwise.orion.bean.EventRelic;
import com.microwise.orion.vo.OutOrInRelicVo;

import java.util.Date;
import java.util.List;

/**
 * 出库文物service
 *
 * @author xubaoji
 * @date 2013-5-29
 *
 * @check 2013-06-05 zhangpeng svn:3965
 * TODO 暂未审核xiedeng代码
 */
public interface EventRelicService {

    /**
     * 批量 修改 出库文物 时间 和扫描状态
     *
     * @param outRelics 扫描到的出库文物列表
     * @return void
     * @author 许保吉
     * @date 2013-5-29
     */
    public void updateOutRelicForHttp(List<OutOrInRelicVo> outRelics);

    /**
     * 批量 修改 入库文物 时间 和扫描状态
     *
     * @param outRelics 扫描到的出库文物列表
     * @return  void
     * @author 许保吉
     * @date 2013-5-29
     */
    public void updateInRelicForHttp(List<OutOrInRelicVo> outRelics);

    /**
     * 根据编号修改eventRelic状态
     *
     * @param id    编号
     * @param state 状态
     */
    public void updateStateById(int id, int state);

    /**
     * 根据编号修改eventRelic outDate
     *
     * @param id 编号
     */
    public void updateOutDateById(int id, Date date);

    /**
     * 根据编号查询EventRelic
     *
     * @param id 事件编号
     * @return
     */
    public EventRelic findById(int id);


}
