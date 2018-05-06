package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.vo.TopoViewVO;
import com.microwise.blueplanet.dao.TopoDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiedeng
 * @date 13-9-27
 * @check liuzhu 2013-10-11 #5894
 */

@Beans.Dao
@Blueplanet
public class TopoDaoImpl extends BlueplanetBaseDao implements TopoDao {

    @Override
    public List<TopoViewVO> getDevices(String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        return getSqlSession().selectList("blueplanet.mybatis.TopoDao.getDevices", paramMap);
    }

    @Override
    public List<TopoViewVO> getTopos(int parentId, String siteId, int size, int nodeVersion) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("parentId", parentId);
        paramMap.put("siteId", siteId);
        paramMap.put("size", size);
        paramMap.put("nodeVersion", nodeVersion);
        return getSqlSession().selectList("blueplanet.mybatis.TopoDao.getTopos", paramMap);
    }

    @Override
    public TopoViewVO getNodeInfoByNodeId(String nodeId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nodeId", nodeId);
        return getSqlSession().selectOne("blueplanet.mybatis.TopoDao.getNodeInfoByNodeId", paramMap);
    }

    @Override
    public List<TopoViewVO> getTimeoutDevice(String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        return getSqlSession().selectList("blueplanet.mybatis.TopoDao.getTimeoutDevice", paramMap);
    }

    @Override
    public Integer getChildrenCount(int parentId, String siteId, int size, int nodeVersion) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("parentId", parentId);
        paramMap.put("siteId", siteId);
        paramMap.put("size", size);
        paramMap.put("nodeVersion", nodeVersion);
        return getSqlSession().<Integer>selectOne("blueplanet.mybatis.TopoDao.getChildrenCount", paramMap);
    }

    @Override
    public List<TopoViewVO> getCountDevices(String siteId, int anomaly) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("anomaly", anomaly);
        return getSqlSession().selectList("blueplanet.mybatis.TopoDao.getCountDevices", paramMap);
    }

    @Override
    public List<TopoViewVO> getRssi(String siteId, int anomaly) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("anomaly",anomaly);
        return getSqlSession().selectList("blueplanet.mybatis.TopoDao.getRssi", paramMap);
    }

    @Override
    public Integer getNodeCount(String nodeId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nodeId", nodeId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().<Integer>selectOne("blueplanet.mybatis.TopoDao.getNodeCount", paramMap);
    }

    @Override
    public Date getMinTime(String nodeId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nodeId", nodeId);
        return getSqlSession().<Date>selectOne("blueplanet.mybatis.TopoDao.getMinTime", paramMap);
    }

    @Override
    public List<TopoViewVO> getHistoryRoute(String nodeId, Date startDate, Date endDate, int startIndex, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nodeId", nodeId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("startIndex", startIndex);
        paramMap.put("pageSize", pageSize);
        return getSqlSession().selectList("blueplanet.mybatis.TopoDao.getHistoryRoute", paramMap);
    }

    @Override
    public Integer getHistoryRouteCount(String nodeId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nodeId", nodeId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().<Integer>selectOne("blueplanet.mybatis.TopoDao.getHistoryRouteCount", paramMap);
    }
}
