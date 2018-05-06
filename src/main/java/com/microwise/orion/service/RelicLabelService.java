package com.microwise.orion.service;

import com.microwise.orion.bean.RelicLabel;

import java.util.List;

/**
 * 文物标签 Service
 *
 * @author li.jianfei
 * @date 2014-04-25
 */
public interface RelicLabelService {

    /**
     * 查找文物标签
     *
     * @return
     */
    public List<RelicLabel> findRelicLabelList(String siteId);

    /**
     * 根据文物ID查询文物标签集合
     *
     * @param relicId 文物ID
     * @return 标签集合
     */
    public List<RelicLabel> findRelicLabelListByRelicId(int relicId);


    /**
     * 删除所有文物标签
     *
     * @param relicId   文物ID
     * @param labelName 文物标签
     */
    public boolean deleteRelicLabel(int relicId, String labelName);

    /**
     * 添加文物标签
     *
     * @param relicId   文物编号
     * @param labelName 标签名称
     */
    public void addRelicLabel(int relicId, String labelName);

    /**
     * 根据标签名称查询文物标签
     * @param labelName
     * @return
     */
    public RelicLabel findRelicLabelByLabelName(String labelName);
}
