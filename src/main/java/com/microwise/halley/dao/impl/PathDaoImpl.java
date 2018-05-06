package com.microwise.halley.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.po.RouteHistoryPO;
import com.microwise.halley.bean.vo.PathPointsVO;
import com.microwise.halley.dao.PathDao;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外展预设线路Dao接口实现
 *
 * @author xu.yuexi
 * @date 13-10-17 上午11:02
 */
@Beans.Dao
@Halley
public class PathDaoImpl extends HalleyBaseDao implements PathDao {


    @Override
    public List<PathPO> findPathByExhibitionId(int exhibitionId) {
        return getSqlSession().selectList("halley.mybatis.PathDao.findPathByExhibitionId", exhibitionId);
    }

    @Override
    public List<RouteHistoryPO> findRouteHistoryByCarId(int carId, Date startDate, Date endDate) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("carId", carId);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        return getSqlSession().selectList("halley.mybatis.PathDao.findRouteHistorysByCarId", params);
    }

    @Override
    public void deleteByExhibitionId(int exhibitionId) {
        getSqlSession().delete("halley.mybatis.PathDao.deleteByExhibitionId", exhibitionId);
    }

    @Override
    public void deletePathPointsByExhibitionId(int exhibitionId) {
        getSqlSession().delete("halley.mybatis.PathDao.deletePathPointsByExhibitionId", exhibitionId);
    }

    @Override
    public void savePath(PathPO path) {
        getSqlSession().insert("halley.mybatis.PathDao.savePath", path);
    }

    @Override
    public void savePathPoints(List<PathPointsVO> pathPointsList) {
        getSqlSession().insert("halley.mybatis.PathDao.savePathPoints", pathPointsList);
    }

    @Override
    public RouteHistoryPO findRouteHistoryPO(int carId) {
        return getSqlSession().selectOne("halley.mybatis.PathDao.findRouteHistoryByCarId", carId);
    }
}
