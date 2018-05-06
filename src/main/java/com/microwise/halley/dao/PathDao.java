package com.microwise.halley.dao;

import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.po.RouteHistoryPO;
import com.microwise.halley.bean.vo.PathPointsVO;

import java.util.Date;
import java.util.List;

/**
 * 外展预设线路接口
 *
 * @author xu.yuexi
 * @Date 13-10-17
 */
public interface PathDao {
    /**
     * 获取外展预设线路经纬度信息：
     *
     * @param exhibitionId 外展ID
     * @return 预设线路经纬度
     */
    public List<PathPO> findPathByExhibitionId(int exhibitionId);

    /**
     * 获取经纬度历史数据：
     *
     * @param carId     车辆ID
     * @param startDate 外展开始时间
     * @param endDate   外展结束时间
     * @return 预设线路经纬度
     */
    public List<RouteHistoryPO> findRouteHistoryByCarId(int carId, Date startDate, Date endDate);


    /**
     * 删除该外展id下所有的Path
     *
     * @param exhibitionId
     */
    public void deleteByExhibitionId(int exhibitionId);

    /**
     * 删除该外展id下所有的PathPoints
     *
     * @param exhibitionId
     */
    public void deletePathPointsByExhibitionId(int exhibitionId);

    /**
     * 保存路线
     *
     * @param path
     */
    public void savePath(PathPO path);

    /**
     * 保存GPS点
     *
     * @param pathPointsList
     */
    public void savePathPoints(List<PathPointsVO> pathPointsList);

    /**
     * 获取经纬度最新一包数据
     *
     * @param carId 车id
     * @return
     * @author liuzhu
     * @date 2015-7-10
     */
    public RouteHistoryPO findRouteHistoryPO(int carId);
}
