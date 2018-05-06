package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.RelicProperty;
import com.microwise.orion.dao.RelicPropertyDao;
import com.microwise.orion.service.RelicPropertyService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文物属性 信息 service 实现
 *
 * @author xubaoji
 * @date 2013-6-4
 * @check 2013-06-04 zhangpeng svn:4046
 */
@Transactional
@Orion
@Service
public class RelicPropertyServiceImpl implements RelicPropertyService {

    /**
     * 文物 属性信息 dao
     */
    @Autowired
    private RelicPropertyDao relicPropertyDao;

    @Override
    public void saveOrUpdateRelicProperty(List<RelicProperty> relicPropertyList) {
        relicPropertyDao.saveOrUpdateRelicProperty(relicPropertyList);
    }

    @Override
    public void deleteRelicProperty(Integer relicPropertyId) {
        relicPropertyDao.deleteRelicProperty(relicPropertyId);
    }

    @Override
    public RelicProperty findById(Integer id) {
        return relicPropertyDao.findById(id);
    }

    @Override
    public RelicProperty findByRelicIdAndPropertyId(int relicId, int propertyId){
        return relicPropertyDao.findByRelicIdAndPropertyId(relicId,propertyId);
    }
}
