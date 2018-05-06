package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Property;
import com.microwise.orion.dao.PropertyDao;
import com.microwise.orion.service.PropertyService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文物属性 ServiceImpl
 *
 * @author li.jianfei
 * @date 2015-09-09
 */
@Transactional
@Orion
@Beans.Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyDao propertyDao;

    public List<Property> findAllProperty() {
        return propertyDao.findAllProperty();
    }

    @Override
    public Property findByEnName(String enName) {
        return propertyDao.findByEnName(enName);
    }

}
