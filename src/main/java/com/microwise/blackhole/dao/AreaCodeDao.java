package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.AreaCodeCN;

import java.util.List;

/**
 * 地区行政 dao
 *
 * @author xubaoji
 * @date 2012-11-29
 * @check 2012-12-05 zhangpeng svn:642
 */
public interface AreaCodeDao {

    /**
     * 根据上级行政编码查询下级地区编码对象列表（在此 将市辖区、县  行政区过滤）
     *
     * @param areaCode 地区行政编码
     * @return List<AreaCodeCN> 地区列表
     * @author 许保吉
     * @date 2012-11-29
     */
    public List<AreaCodeCN> findAreaListByAreaCode(int areaCode);

    /**
     * 获得所有实际行政区（在此 将市辖区、县  行政区过滤）
     *
     * @return List<AreaCodeCN> 地区列表
     * @author 许保吉
     * @date 2012-11-29
     */
    public List<AreaCodeCN> findAllArea();


}
