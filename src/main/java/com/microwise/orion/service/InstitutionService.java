package com.microwise.orion.service;

import com.microwise.orion.bean.Institution;

import java.util.List;

/**
 * 单位service
 *
 * @author liuzhu
 * @date 2015-9-8
 */
public interface InstitutionService {

    /**
     * 添加单位
     *
     * @param institution
     */
    public void save(Institution institution);

    /**
     * 更新
     *
     * @param institution
     */
    public void update(Institution institution);

    /**
     * 删除
     *
     * @param institution
     */
    public void delete(Institution institution);


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
    public List<Institution> findInstitutions(String siteId, int institutionType);

    /**
     * 查询一个对象
     *
     * @param id
     * @return
     */
    public Institution findById(int id);
}
