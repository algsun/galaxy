package com.microwise.saturn.dao;

import com.microwise.saturn.bean.Affair;

import java.util.List;

/**
 * Created by lijianfei on 15-3-19.
 */
public interface AffairDao {
    /**
     * 添加事件
     *
     * @param affair
     */
    public void save(Affair affair);

    /**
     * 更新事件
     *
     * @param affair
     */
    public void update(Affair affair);


    /**
     * 查询所有成果
     *
     * @return
     */
    public List<Affair> findAll(String siteId);

    /**
     * 查询事件
     *
     * @param affairId
     * @return
     */
    public Affair findById(int affairId);

    /**
     * 删除事件
     *
     * @param affairId
     */
    public void delete(int affairId);


    /**
     * 根据年份、季度查询
     *
     * @param type
     * @param year
     * @param quarterNum
     * @return
     */
    public List<Affair> findAllByTypeAndYear(Integer type, Integer year, Integer quarterNum);

    /**
     * 查询数据最多的type
     *
     * @param siteId
     * @param year
     * @param quarterNum
     * @return
     */
    public Integer findMaxSizeType(String siteId, Integer year, Integer quarterNum);

    /**
     * 查找所有事件类型
     *
     * @param siteId
     * @param year
     * @param quarterNum
     * @return
     */
    public List<Affair> findAffairType(String siteId, Integer year, Integer quarterNum);

    /**
     * 查询所有type数量
     *
     * @param siteId
     * @param type
     * @param year
     * @param quarterNum
     * @return
     */
    public Integer findAffairTypeCount(String siteId, Integer type, Integer year, Integer quarterNum);

}
