package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Institution;

import java.util.List;

/**
 * 单位dao
 *
 * @author liuzhu
 * @date 2015-9-8
 */
public interface InstitutionDao extends BaseDao<Institution>{

    /**
     * 查询
     *
     * @param siteId
     */
    public List<Institution> findBySiteId(String siteId);

    /**
     * 查询制定类型的单位
     * @param siteId 站点ID
     * @param institutionType 单位类型 0:设计单位 1:收藏单位 2:修复单位
     * @return
     */
    List<Institution> findInstitutions(String siteId, int institutionType);
}
