package com.microwise.blueplanet.dao.impl;

import com.google.common.base.Strings;
import com.microwise.blueplanet.bean.po.*;
import com.microwise.blueplanet.bean.vo.NodeSensorVO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.dao.DataCenterDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzhu
 * @date 13-12-3
 */
@Dao
@Blueplanet
public class DataCenterDaoImpl extends BlueplanetBaseDao implements DataCenterDao {

    @Override
    public void saveLayout(DCLayoutPO dcLayoutPO) {
        getSqlSession().insert("blueplanet.mybatis.DataCenterDao.saveLayout", dcLayoutPO);
    }

    @Override
    public void updateLayoutDescription(DCLayoutPO dcLayoutPO) {
        getSqlSession().update("blueplanet.mybatis.DataCenterDao.updateLayoutDescription", dcLayoutPO);
    }

    @Override
    public void saveItem(DCItemPO item) {
        getSqlSession().insert("blueplanet.mybatis.DataCenterDao.saveItem", item);
    }

    @Override
    public List<DCLayoutPO> findAllLayouts(int logicGroupId) {
        return getSqlSession().selectList("blueplanet.mybatis.DataCenterDao.findAllLayouts", logicGroupId);
    }

    @Override
    public List<DCItemPO> findItemByLayoutId(String layoutID) {
        return getSqlSession().selectList("blueplanet.mybatis.DataCenterDao.findItemByLayoutId", layoutID);
    }

    @Override
    public DCItemPO findItemById(String itemId) {
        return getSqlSession().selectOne("blueplanet.mybatis.DataCenterDao.findItemById", itemId);
    }

    @Override
    public DCConditionPO findConditionByItemId(String itemId) {
        return getSqlSession().selectOne("blueplanet.mybatis.DataCenterDao.findConditionByItemId", itemId);
    }

    @Override
    public List<DCConditionPO> findConditionByLayoutId(String layoutId) {
        return getSqlSession().selectList("blueplanet.mybatis.DataCenterDao.findConditionByLayoutId", layoutId);
    }

    @Override
    public void saveConditions(DCConditionPO dcConditionPO) {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("related_item_id", dcConditionPO.getRelated_item_id());
        paramMap.put("chart_type", dcConditionPO.getChart_type());
        paramMap.put("locationId", dcConditionPO.getLocationId());
        paramMap.put("related_layout_id", dcConditionPO.getRelated_layout_id());
        if (dcConditionPO.getStartTime() != null) {
            paramMap.put("startTime", dcConditionPO.getStartTime());
        }
        if (dcConditionPO.getEndTime() != null) {
            paramMap.put("endTime", dcConditionPO.getEndTime());
        }
        if (dcConditionPO.getDateNum() != 0) {
            paramMap.put("dateNum", dcConditionPO.getDateNum());
        }
        if (!Strings.isNullOrEmpty(dcConditionPO.getParam1())) {
            paramMap.put("param1", dcConditionPO.getParam1());
        }
        paramMap.put("url", dcConditionPO.getUrl());
        paramMap.put("serializationParams", dcConditionPO.getSerializationParam());
        paramMap.put("sensorPhysicalid", dcConditionPO.getSensorPhysicalid());

        getSqlSession().insert("blueplanet.mybatis.DataCenterDao.saveConditions", paramMap);
    }

    @Override
    public void deleteLayoutById(String layoutId) {
        getSqlSession().delete("blueplanet.mybatis.DataCenterDao.deleteLayoutById", layoutId);
    }

    @Override
    public void deleteLayoutItemById(String itemId) {
        getSqlSession().delete("blueplanet.mybatis.DataCenterDao.deleteLayoutItemById", itemId);
    }

    @Override
    public void deleteLayoutItemByLayoutId(String layoutId) {
        getSqlSession().delete("blueplanet.mybatis.DataCenterDao.deleteLayoutItemByLayoutId", layoutId);
    }

    @Override
    public void deleteConditionsById(String layoutId) {
        getSqlSession().delete("blueplanet.mybatis.DataCenterDao.deleteConditionsById", layoutId);
    }

    @Override
    public void deleteConditionByItemId(String itemId) {
        getSqlSession().delete("blueplanet.mybatis.DataCenterDao.deleteConditionByItemId", itemId);
    }

    @Override
    public DCConfigPO findDataCenterConfig(String layoutId) {
        return getSqlSession().selectOne("blueplanet.mybatis.DataCenterDao.findDataCenterConfig", layoutId);
    }

    @Override
    public void saveDataCenterConfig(DCConfigPO dcConfigPO) {
        getSqlSession().insert("blueplanet.mybatis.DataCenterDao.saveDataCenterConfig", dcConfigPO);
    }

    @Override
    public void deleteDataCenterConfig(String layoutId) {
        getSqlSession().delete("blueplanet.mybatis.DataCenterDao.deleteDataCenterConfig", layoutId);
    }

    @Override
    public List<DCSlidePO> findItemIdByLayoutId(String layoutId) {
        return getSqlSession().selectList("blueplanet.mybatis.DataCenterDao.findItemIdByLayoutId", layoutId);
    }

    @Override
    public List<DCSlidePO> findIdByItemId(String itemId) {
        return getSqlSession().selectList("blueplanet.mybatis.DataCenterDao.findIdByItemId", itemId);
    }

    @Override
    public DCSlidePO findSlideShowById(Integer id) {
        return getSqlSession().selectOne("blueplanet.mybatis.DataCenterDao.findSlideShowById", id);
    }

    @Override
    public List<NodeSensorVO> findNodeSensorVO(String locationId) {
        return getSqlSession().selectList("blueplanet.mybatis.DataCenterDao.findNodeSensor", locationId);
    }

    @Override
    public void saveSlide(DCSlidePO dcSlidePO) {
        getSqlSession().insert("blueplanet.mybatis.DataCenterDao.saveSlide", dcSlidePO);
    }

    @Override
    public void updateSlide(DCSlidePO dcSlidePO){
        getSqlSession().update("blueplanet.mybatis.DataCenterDao.updateSlidePO",dcSlidePO);
    }

    @Override
    public List<DCSlidePO> findDataCenterSlides(String relatedLayoutId, String relatedItemId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("relatedLayoutId", relatedLayoutId);
        paramMap.put("relatedItemId", relatedItemId);
        return getSqlSession().selectList("blueplanet.mybatis.DataCenterDao.findDataCenterSlides", paramMap);
    }

    @Override
    public void deleteSlideById(int slideId) {
        getSqlSession().delete("blueplanet.mybatis.DataCenterDao.deleteSlideById", slideId);
    }

    @Override
    public void deleteItemAllSlidesByIds(String layoutId, String itemId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("layoutId", layoutId);
        paramMap.put("itemId", itemId);
        getSqlSession().delete("blueplanet.mybatis.DataCenterDao.deleteItemAllSlidesByIds", paramMap);
    }

    @Override
    public List<SiteVO> findSiteVOByLogicGroupId(int logicGroupId) {
        return getSqlSession().selectList("blueplanet.mybatis.DataCenterDao.findSiteVOByLogicGroupId",logicGroupId);
    }

    @Override
    public SiteVO findSiteVOById(int logicGroupId) {
        return getSqlSession().selectOne("blueplanet.mybatis.DataCenterDao.findSiteVOById",logicGroupId);
    }
}
