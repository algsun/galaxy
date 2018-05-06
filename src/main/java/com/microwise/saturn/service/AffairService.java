package com.microwise.saturn.service;

import com.microwise.saturn.bean.Affair;

import java.util.List;

/**
 * Created by lijianfei on 15-3-16.
 */
public interface AffairService {

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
     * @param year
     * @param quarterNum
     * @return
     */
    public List<Affair> findAllByTypeAndYear(String siteId,Integer type, Integer year, Integer quarterNum);

    /**
     * 专业成果饼图
     *
     * @param siteId
     * @param year
     * @param quarterNum
     * @return
     */
    public List<Affair> findAffairChart(String siteId, Integer year ,Integer quarterNum);
}
