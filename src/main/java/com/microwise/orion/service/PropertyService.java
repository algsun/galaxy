package com.microwise.orion.service;

import com.microwise.orion.bean.Property;

import java.util.List;

/**
 * 文物属性 Service
 *
 * @author li.jianfei
 * @date 2015-09-09
 */
public interface PropertyService {


    /**
     * 查询文物所有属性字典信息
     *
     * @return List<Property> 文物属性字典表信息
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
