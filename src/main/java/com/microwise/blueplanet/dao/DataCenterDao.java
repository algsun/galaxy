package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.*;
import com.microwise.blueplanet.bean.vo.NodeSensorVO;
import com.microwise.blueplanet.bean.vo.SiteVO;

import java.util.List;

/**
 * 数据中心Dao
 *
 * @author liuzhu
 * @date 13-12-3
 * @check @liu.zhu wang.geng 2013-12-18 #7192
 */
public interface DataCenterDao {

    /**
     * 保存布局
     *
     * @param dcLayoutPO 布局实体类
     */
    public void saveLayout(DCLayoutPO dcLayoutPO);

    /**
     * 更新布局描述
     *
     * @param dcLayoutPO 布局实体
     */
    public void updateLayoutDescription(DCLayoutPO dcLayoutPO);

    /**
     * 保存布局控件
     *
     * @param item 布局控件实体类
     */
    public void saveItem(DCItemPO item);

    /**
     * 查询所有的布局
     *
     * @return 布局列表
     */
    public List<DCLayoutPO> findAllLayouts(int logicGroupId);

    /**
     * 根据布局ID查询所有的布局控件
     *
     * @param layoutId 布局ID
     * @return 布局控件列表
     */
    public List<DCItemPO> findItemByLayoutId(String layoutId);

    /**
     * 根据控件ID查询控件
     *
     * @param itemId 控件ID
     * @return 控件实体
     */
    public DCItemPO findItemById(String itemId);

    /**
     * 根据控件ID查询图表条件
     *
     * @param itemId 控件ID
     * @return 图表条件实体
     */
    public DCConditionPO findConditionByItemId(String itemId);

    /**
     * 根据布局ID，查询所有条件实例
     *
     * @param layoutId
     */
    public List<DCConditionPO> findConditionByLayoutId(String layoutId);

    /**
     * 保存控件图表查询条件
     *
     * @param dcConditionPO 条件实体类
     */
    public void saveConditions(DCConditionPO dcConditionPO);

    /**
     * 查询一个布局的布局控件的所有幻灯片
     *
     * @param relatedLayoutId 布局ID
     * @param relatedItemId   布局控件ID
     * @return 幻灯片集合
     */
    public List<DCSlidePO> findDataCenterSlides(String relatedLayoutId, String relatedItemId);

    /**
     * 根据layoutId删除布局
     *
     * @param layoutId 布局ID
     */
    public void deleteLayoutById(String layoutId);

    /**
     * 根据控件ID删除布局控件图表
     *
     * @param itemId 控件ID
     */
    public void deleteLayoutItemById(String itemId);

    /**
     * 根据布局ID删除控件
     *
     * @param layoutId
     */
    public void deleteLayoutItemByLayoutId(String layoutId);

    /**
     * 根据布局ID删除图表条件
     *
     * @param layoutId 布局ID
     */
    public void deleteConditionsById(String layoutId);

    /**
     * 根据控件ID删除图表条件
     *
     * @param itemId 控件ID
     */
    public void deleteConditionByItemId(String itemId);

    /**
     * 根据布局ID查询配置
     *
     * @param layoutId 布局配置
     * @return 配置实体
     */
    public DCConfigPO findDataCenterConfig(String layoutId);

    /**
     * 保存布局配置
     *
     * @param dcConfigPO 布局实体
     */
    public void saveDataCenterConfig(DCConfigPO dcConfigPO);

    /**
     * 根据布局ID删除布局配置
     *
     * @param layoutId 布局ID
     */
    public void deleteDataCenterConfig(String layoutId);

    /**
     * 根据layoutId查找DCSlidePO
     *
     * @param layoutId 布局id
     * @return DCSlidePO集合
     * @author liuzhu
     * @date 2014-2-13
     */
    public List<DCSlidePO> findItemIdByLayoutId(String layoutId);

    /**
     * 根据itemId查找DCSlidePOId
     *
     * @param itemId 控件id
     * @return DCSlidePO集合
     * @author liuzhu
     * @date 2014-2-13
     */
    public List<DCSlidePO> findIdByItemId(String itemId);

    /**
     * 根据id获取SlideShow
     *
     * @param id 自增id
     * @return DCSlidePO
     * @author liuzhu
     * @date 2014-2-13
     */
    public DCSlidePO findSlideShowById(Integer id);

    /**
     * 根据位置点查找位置点监测指标
     *
     * @param locationId 位置点ID
     * @return 监测指标集合
     * @author liuzhu
     * @date 2014-2-13
     */
    public List<NodeSensorVO> findNodeSensorVO(String locationId);

    /**
     * 保存幻灯片
     *
     * @param dcSlidePO 幻灯片实体
     */
    public void saveSlide(DCSlidePO dcSlidePO);

    /**
     * 更新幻灯片
     *
     * @param dcSlidePO 幻灯片实体
     */
    public void updateSlide(DCSlidePO dcSlidePO);

    /**
     * 根据幻灯片ID删除幻灯片
     *
     * @param slideId 幻灯片ID
     */
    public void deleteSlideById(int slideId);

    /**
     * 根据布局ID，与控件ID删除布局控件下所有的幻灯片
     *
     * @param layoutId 布局ID
     * @param itemId   控件ID
     */
    public void deleteItemAllSlidesByIds(String layoutId, String itemId);

    /**
     * 根据站点id查找站点
     *
     * @param logicGroupId 站点组id
     * @return siteVO
     * @author liuzhu
     * @date 2014-2-27
     */
    public List<SiteVO> findSiteVOByLogicGroupId(int logicGroupId);

    /**
     * 根据站点id查询站点vo
     *
     * @param logicGroupId 占地id
     * @return SiteVO
     * @author liuzhu
     * @date 2014-2-28
     */
    public SiteVO findSiteVOById(int logicGroupId);
}
