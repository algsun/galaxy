package com.microwise.halley.service;

import com.microwise.halley.bean.vo.TimeIntervalVO;

import java.util.List;

/**
 * 历史数据 Service
 *
 * @author li.jianfei
 * @date 2013-10-31
 */
public interface HistoryDataService {

    /**
     * 查询外展时段划分
     *
     * @param exhibitionId 外展ID
     * @return 时段集合
     */
    public List<TimeIntervalVO> getTimeInterval(int exhibitionId);
}
