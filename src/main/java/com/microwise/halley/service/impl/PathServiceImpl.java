package com.microwise.halley.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.po.RouteHistoryPO;
import com.microwise.halley.bean.vo.PathPointsVO;
import com.microwise.halley.dao.PathDao;
import com.microwise.halley.service.PathService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 外展预设线路接口
 *
 * @author xu.yuexi
 * @Date: 13-10-17
 */
@Beans.Service
@Transactional
@Halley
public class PathServiceImpl implements PathService {
    /**
     * 外展预设线路信息查询Dao接口
     */
    @Autowired
    private PathDao pathDao;

    @Override
    public List<PathPO> findPathByExhibitionId(int exhibitionId) {
        return pathDao.findPathByExhibitionId(exhibitionId);
    }

    @Override
    public void savePathList(List<PathPO> pathList) {
        for (PathPO pathPO : pathList) {
            pathDao.savePath(pathPO);
        }
    }

    @Override
    public void savePathPointList(List<PathPointsVO> pathPointsList) {
        this.deletePathPointsByExhibitionId(pathPointsList.get(0).getExhibitionId());
        pathDao.savePathPoints(pathPointsList);
    }

    @Override
    public void deleteByExhibitionId(int exhibitionId) {
        pathDao.deleteByExhibitionId(exhibitionId);
    }

    @Override
    public void deletePathPointsByExhibitionId(int exhibitionId) {
        pathDao.deletePathPointsByExhibitionId(exhibitionId);
    }

    @Override
    public List<RouteHistoryPO> findRouteHistoryByCarId(int carId, Date startDate, Date endDate) {
        return pathDao.findRouteHistoryByCarId(carId, startDate, endDate);
    }
}
