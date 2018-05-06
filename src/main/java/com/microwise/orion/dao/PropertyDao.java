package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Property;

import java.util.List;

/**
 * 文物属性字典信息dao
 *
 * @author xubaoji
 * @date 2013-5-22
 * @check 2013-06-04 zhangpeng svn:3610
 */
public interface PropertyDao extends BaseDao<Property> {

    /**
     * 查询文物属性字典信息
     *
     * @return List<Property> 文物字典信息列表
     * @author 许保吉
     * @date 2013-5-22
     */
    public List<Property> findAllProperty();

    /**
     * 根据英文名称查询属性信息
     *
     * @param enName 英文名称
     * @return 属性信息
     * @author 王耕
     * @date 2015-10-15
     */
    public Property findByEnName(String enName);

}
