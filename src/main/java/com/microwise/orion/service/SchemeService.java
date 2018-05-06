package com.microwise.orion.service;

import com.microwise.orion.bean.Scheme;

import java.util.List;

/**
 * @author 王耕
 * @date 15-9-9
 */
public interface SchemeService {
    /**
     * 保存或修改方案
     *
     * @param scheme 方案实体
     */
    public void saveOrUpdateScheme(Scheme scheme);

    /**
     * 根据ID删除方案
     *
     * @param id 方案id
     */
    public void deleteScheme(int id);

    /**
     * 查询所有方案
     *
     * @param siteId 站点编号
     * @return 方案集合
     */
    public List<Scheme> findAll(String siteId);

    /**
     * 根据ID查询方案
     *
     * @param id 方案
     * @return 方案实体        f
     */
    public Scheme findSchemeById(int id);
}
