package com.microwise.halley.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.OpticsDVPlacePO;
import com.microwise.halley.dao.OpticsDVPlaceForHalleyDao;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 哈雷子项目光学摄像机查询Dao实现.
 *
 * @author wang.geng
 * @date 13-9-30 下午3:55
 */
@Beans.Dao
@Halley
public class OpticsDVPlaceForHalleyDaoImpl extends HalleyBaseDao implements OpticsDVPlaceForHalleyDao {
    @Override
    public List<OpticsDVPlacePO> findAllOpticsDV(String siteId) {
        return getSqlSession().selectList("halley.mybatis.OpticsDVPlaceForHalleyDao.findDVBySiteId", siteId);
    }

    @Override
    public List<OpticsDVPlacePO> findOpticsDVByZoneId(String zoneId, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("zoneId", zoneId);
        paramMap.put("siteId", siteId);
        return getSqlSession().selectList("halley.mybatis.OpticsDVPlaceForHalleyDao.findDVByZoneId", paramMap);
    }

}
