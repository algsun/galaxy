package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.RelicLabel;

import java.util.List;

/**
 * 文物标签 Dao
 *
 * @author li.jianfei
 * @date 2014-04-25
 */
public interface RelicLabelDao extends BaseDao<RelicLabel> {

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
     * 删除文物标签
     *
     * @param labelId 文物标签ID
     * @return
     */
    public boolean deleteRelicLabel(int labelId);

    /**
     * 删除文物和标签关系
     *
     * @param relicId 文物ID
     * @param labelId 文物标签ID
     */
    public boolean deleteRelicLabel(int relicId, int labelId);

    /**
     * 添加文物标签
     *
     * @param relicLabel 文物标签
     */
    public void addRelicLabel(RelicLabel relicLabel);

    /**
     * 根据文物标签名称查询文物标签编号
     *
     * @param labelName
     * @return
     */
    public RelicLabel findRelicLabelByLabelName(String labelName);
}
