package com.microwise.halley.service;

import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.po.RouteHistoryPO;
import com.microwise.halley.bean.vo.PathPointsVO;

import java.util.Date;
import java.util.List;

/**
 * 外展预设线路接口
 *
 * @author xu.yuexi
 * @date 13-10-17
 * @check @wang.geng li.jianfei  2013-10-25 #6107
 */
public interface PathService {

    /**
     * 获取外展预设线路经纬度信息：
     *
     * @param exhibitionId 外展ID
     * @return 预设线路经纬度 List<PathPO>
     * @author xu.yuexi
     * @date 13-10-17
     */
    public List<PathPO> findPathByExhibitionId(int exhibitionId);

    /**
     * 保存路线集
     *
     * @param pathList 路线集  l
     */
    public void savePathList(List<PathPO> pathList);

    /**
     * 保存GPS点集
     *
     * @param pathPointsList GPS点集
     */
    public void savePathPointList(List<PathPointsVO> pathPointsList);

    /**
     * 删除该外展id下所有的Path
     *
     * @param exhibitionId 外展ID
     */
    public void deleteByExhibitionId(int exhibitionId);


    /**
     * 删除该外展id下所有的PathPoints
     *
     * @param exhibitionId
     */
    public void deletePathPointsByExhibitionId(int exhibitionId);

    /**
     * 获取经纬度历史数据：
     *
     * @param carId     车辆ID
     * @param startDate 外展开始时间
     * @param endDate   外展结束时间
     * @return 预设线路经纬度
     */
    public List<RouteHistoryPO> findRouteHistoryByCarId(int carId, Date startDate, Date endDate);
}
