package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.RelicProperty;

import java.util.List;

/**
 * 文物属性信息 dao
 *
 * @author xubaoji
 * @date 2013-5-16
 * @check 2013-06-04 zhangpeng svn:4060
 */
public interface RelicPropertyDao extends BaseDao<RelicProperty> {
    /**
     * 通过文物总登记号 查询文物 档案/藏品卡 属性信息
     *
     * @param relicId 文物id 编号
     * @param belongs 属性隶属标识数组
     * @return List<RelicProperty> 文物属性信息列表
     * @author 许保吉
     * @date 2013-5-16
     */
    public List<RelicProperty> findRelicPropertyList(Integer relicId,
                                                     Integer... belongs);


    /**
     * 添加或修改文物属性信息
     *
     * @param relicPropertyList 文物属性信息
     * @return void
     * @author 许保吉
     * @date 2013-5-20
     */
    public void saveOrUpdateRelicProperty(List<RelicProperty> relicPropertyList);

    /**
     * 通过id 编号删除一个文物属性值
     *
     * @param relicPropertyId 文物属性信息 id 编号
     * @return void
     * @author 许保吉
     * @date 2013-5-23
     */
    public void deleteRelicProperty(Integer relicPropertyId);

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
