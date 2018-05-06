package com.microwise.orion.service;

import com.microwise.orion.bean.RelicProperty;

import java.util.List;

/**
 * 文物 属性信息service
 *
 * @author xubaoji
 * @date 2013-6-4
 * @check 2013-06-04 zhangpeng svn:4046
 */
public interface RelicPropertyService {

    /**
     * 添加或修改文物属性信息
     *
     * @param relicPropertyList 文物属性对象列表
     * @return void
     * @author 许保吉
     * @date 2013-5-20
     */
    public void saveOrUpdateRelicProperty(List<RelicProperty> relicPropertyList);


    /**
     * 通过id 删除 文物的一个属性信息内容
     *
     * @param relicPropertyId 文物 属性信息 id 编号
     * @return void
     * @author 许保吉
     * @date 2013-5-23
     */
    public void deleteRelicProperty(Integer relicPropertyId);

    /**
     * 通过 id 编号查询一个文物属性信息
     *
     * @param id 文物属性id 编号
     * @return
     * @author 许保吉
     * @date 2013-6-5
     */
    public RelicProperty findById(Integer id);

    /**
     * 根据文物ID，与属性ID查询文物属性
     *
     * @param relicId    文物ID
     * @param propertyId 属性ID
     * @return 文物属性
     * @author 王耕
     * @date 2015-9-21
     */
    public RelicProperty findByRelicIdAndPropertyId(int relicId, int propertyId);
}
