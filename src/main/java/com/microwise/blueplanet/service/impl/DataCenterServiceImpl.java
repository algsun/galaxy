package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.po.*;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.dao.DataCenterDao;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzhu
 * @date 13-12-3
 * @check @liu.zhu wang.geng 2013-12-18 #7218
 * @check @xie.deng li.jianfei 2014-3-5 #8018
 */
@Service
@Transactional
@Blueplanet
public class DataCenterServiceImpl implements DataCenterService {

    /**
     * 数据中心dao
     */
    @Autowired
    private DataCenterDao dataCenterDao;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ZoneService zoneService;

    @Override
    public void saveLayout(DCLayoutPO dcLayoutPO) {
        List<DCLayoutPO> layouts = dataCenterDao.findAllLayouts(dcLayoutPO.getLogicGroupId());
        for (DCLayoutPO layout : layouts) {
            if (layout.getLayoutId().equals(dcLayoutPO.getLayoutId())) {
                dataCenterDao.updateLayoutDescription(dcLayoutPO);
                return;
            }
        }
        dataCenterDao.saveLayout(dcLayoutPO);
    }

    @Override
    public void updateLayoutName(String uuid, String layoutDescription, int logicGroupId) {
        DCLayoutPO layout = new DCLayoutPO(uuid, layoutDescription, logicGroupId);
        dataCenterDao.updateLayoutDescription(layout);
    }

    @Override
    public void saveLayoutItems(List<DCItemPO> items) {
        dataCenterDao.deleteLayoutItemByLayoutId(items.get(0).getRelated_layoutId());
        for (DCItemPO item : items) {
            DCItemPO dbItem = dataCenterDao.findItemById(item.getItem_id());
            if (dbItem == null) {
                dataCenterDao.saveItem(item);
            }
        }
    }

    @Override
    public void saveLayoutItem(DCItemPO item) {
        DCItemPO dbItem = dataCenterDao.findItemById(item.getItem_id());
        if (dbItem == null) {
            dataCenterDao.saveItem(item);
        }
    }

    @Override
    public List<DCLayoutPO> findAllLayouts(int logicGroupId) {
        return dataCenterDao.findAllLayouts(logicGroupId);
    }

    @Override
    public List<DCItemPO> findItemByLayoutId(String layoutId) {
        return dataCenterDao.findItemByLayoutId(layoutId);
    }

    @Override
    public List<DCConditionPO> findConditions(String layoutId) {
        return dataCenterDao.findConditionByLayoutId(layoutId);
    }

    @Override
    public void saveConditions(DCConditionPO dcConditionPO) {
        dataCenterDao.deleteConditionByItemId(dcConditionPO.getRelated_item_id());
        dataCenterDao.saveConditions(dcConditionPO);
    }

    @Override
    public void deleteLayoutById(String layoutId) {
        List<DCItemPO> items = dataCenterDao.findItemByLayoutId(layoutId);
        for (DCItemPO item : items) {
            String itemId = item.getItem_id();
            dataCenterDao.deleteLayoutItemById(itemId);
            dataCenterDao.deleteItemAllSlidesByIds(layoutId, itemId);
        }
        dataCenterDao.deleteDataCenterConfig(layoutId);
        dataCenterDao.deleteConditionsById(layoutId);
        dataCenterDao.deleteLayoutById(layoutId);
    }

    @Override
    public void deleteItemById(String itemId) {
        dataCenterDao.deleteLayoutItemById(itemId);
    }

    @Override
    public void deleteConditionByItemId(String itemId) {
        dataCenterDao.deleteConditionByItemId(itemId);
    }

    @Override
    public DCConfigPO findDCConfig(String layoutId) {
        return dataCenterDao.findDataCenterConfig(layoutId);
    }

    @Override
    public void saveDCConfig(DCConfigPO dcConfigPO) {
        DCConfigPO dcConfig = dataCenterDao.findDataCenterConfig(dcConfigPO.getRelated_layoutId());
        if (dcConfig != null) {
            dataCenterDao.deleteDataCenterConfig(dcConfigPO.getRelated_layoutId());
        }
        dataCenterDao.saveDataCenterConfig(dcConfigPO);
    }

    @Override
    public void deleteDCCondig(String layoutId) {
        dataCenterDao.deleteDataCenterConfig(layoutId);
    }

    @Override
    public Map<String, Object> findSlideShowId(String layoutId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DCSlidePO> itemIdList = dataCenterDao.findItemIdByLayoutId(layoutId);
        for (DCSlidePO dcSlidePO : itemIdList) {
            List<Integer> idList = new ArrayList<Integer>();
            for (DCSlidePO s : dataCenterDao.findIdByItemId(dcSlidePO.getRelatedItemId())) {
                idList.add(s.getId());
            }
            map.put(dcSlidePO.getRelatedItemId(), idList);
        }
        return map;
    }

    @Override
    public DCSlidePO findSlideShowById(Integer id) {
        return dataCenterDao.findSlideShowById(id);
    }

    @Override
    public List<NodeSensorVO> findNodeSensorVO(String nodeId) {
        return dataCenterDao.findNodeSensorVO(nodeId);
    }

    @Override
    public List<ZoneLocationVO> findZoneLocationBySiteId(String siteId) {
        List<ZoneLocationVO> zoneLocationList = new ArrayList<ZoneLocationVO>();
        List<ZoneVO> zoneList = zoneService.findZonesBySiteId(siteId);
        for (ZoneVO zone : zoneList) {
            ZoneLocationVO zoneLocation = new ZoneLocationVO();
            List<LocationVO> locationList = locationService.findLocationsByZoneId(zone.getZoneId(),false);
            if (locationList.size() > 0) {
                zoneLocation.setZoneName(zone.getZoneName());
                zoneLocation.setLocationList(locationList);
                zoneLocationList.add(zoneLocation);
            }
        }
        return zoneLocationList;
    }

    @Override
    public void saveSlide(DCSlidePO dcSlidePO) {
        dataCenterDao.saveSlide(dcSlidePO);
    }

    @Override
    public void updateSlide(DCSlidePO dcSlidePO) {
        dataCenterDao.updateSlide(dcSlidePO);
    }

    @Override
    public List<DCSlidePO> findSlideList(String layoutId, String itemId) {
        return dataCenterDao.findDataCenterSlides(layoutId, itemId);
    }

    @Override
    public void deleteSlideById(int slideId) {
        dataCenterDao.deleteSlideById(slideId);
    }

    @Override
    public void deleteItemAllSlidesByIds(String layoutId, String itemId) {
        dataCenterDao.deleteItemAllSlidesByIds(layoutId, itemId);
    }

    @Override
    public List<SiteVO> findSiteVOByLogicGroupId(int logicGroupId) {
        List<SiteVO> siteVOList = new ArrayList<SiteVO>();
        List<SiteVO> siteVOs = dataCenterDao.findSiteVOByLogicGroupId(logicGroupId);
        if (siteVOs.size() == 0) {
            SiteVO siteVO = dataCenterDao.findSiteVOById(logicGroupId);
            siteVOList.add(siteVO);
            return siteVOList;
        } else {
            List<SiteVO> tempSiteVOList = findSiteVO(logicGroupId, siteVOList);
            List<SiteVO> basicSiteVOList = new ArrayList<SiteVO>();
            for (SiteVO siteVO : tempSiteVOList) {
                if (siteVO.getSiteId() != null) {
                    basicSiteVOList.add(siteVO);
                }
            }
            return basicSiteVOList;
        }
    }

    private List<SiteVO> findSiteVO(int logicGroupId, List<SiteVO> siteVOs) {
        List<SiteVO> siteVOList = dataCenterDao.findSiteVOByLogicGroupId(logicGroupId);
        siteVOs.addAll(siteVOList);
        for (SiteVO siteVO : siteVOList) {
            if (siteVO.getParentLogicGroupId() != 0) {
                findSiteVO(siteVO.getId(), siteVOs);
            }
        }
        return siteVOs;
    }
}
