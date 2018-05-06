package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.*;
import com.microwise.blueplanet.bean.vo.NodeSensorVO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.bean.vo.ZoneLocationVO;

import java.util.List;
import java.util.Map;

/**
 * 数据中心service
 *
 * @author liuzhu
 * @date 13-12-3
 * @check @liu.zhu wang.geng 2013-12-18 #7192
 */
public interface DataCenterService {

    /**
     * 保存布局
     *
     * @param dcLayoutPO 布局实体类
     * @author wang.geng
     * @date 2013-12-11
     */
    public void saveLayout(DCLayoutPO dcLayoutPO);

    /**
     * 更新布局名称
     *
     * @param uuid              布局id
     * @param layoutDescription 布局名称
     * @param logicGroupId  站点组id
     * @author wang.geng
     * @date 2013-12-27
     * @author liuzhu
     * @date 2014-2-28
     */
    public void updateLayoutName(String uuid, String layoutDescription, int logicGroupId);

    /**
     * 保存布局控件
     *
     * @param items 布局控件集合
     * @author wang.geng
     * @date 2013-12-11
     */
    public void saveLayoutItems(List<DCItemPO> items);

    /**
     * 保存布局控件
     *
     * @param item
     */
    public void saveLayoutItem(DCItemPO item);

    /**
     * 查询所有的布局
     *
     * @return 布局列表
     * @author wang.geng
     * @date 2013-12-11
     */
    public List<DCLayoutPO> findAllLayouts(int logicGroupId);

    /**
     * 根据布局ID 查找布局的所有控件
     *
     * @param layoutId 布局ID
     * @return 控件集合
     * @author wang.geng
     * @date 2013-12-11
     */
    public List<DCItemPO> findItemByLayoutId(String layoutId);

    /**
     * 根据布局ID，查询所有条件实例
     *
     * @param layoutId 布局ID
     * @return 条件实例集合
     */
    public List<DCConditionPO> findConditions(String layoutId);

    /**
     * 保存控件的图表查询信息
     *
     * @param dcConditionPO 条件实体类
     * @author wang.geng
     * @date 2013-12-11
     */
    public void saveConditions(DCConditionPO dcConditionPO);

    /**
     * 根据布局ID，删除布局
     *
     * @param layoutId 布局ID
     * @author wang.geng
     * @date 2013-12-11
     */
    public void deleteLayoutById(String layoutId);

    /**
     * 根据布局控件ID删除布局控件
     *
     * @param itemId 布局控件ID
     * @author wang.geng
     * @date 2013-12-11
     */
    public void deleteItemById(String itemId);

    /**
     * 根据布局控件ID删除布局控件条件
     *
     * @param itemId 布局控件ID
     * @author wang.geng
     * @date 2013-12-11
     */
    public void deleteConditionByItemId(String itemId);

    /**
     * 根据布局ID查询布局配置
     *
     * @param layoutId 布局ID
     * @return 布局配置实体
     */
    public DCConfigPO findDCConfig(String layoutId);

    /**
     * 保存布局配置
     *
     * @param dcConfigPO 布局实体
     */
    public void saveDCConfig(DCConfigPO dcConfigPO);

    /**
     * 根据布局ID删除布局配置
     *
     * @param layoutId 布局ID
     */
    public void deleteDCCondig(String layoutId);

    /**
     * 根据layoutId获取所有的幻灯片
     *
     * @param layoutId 布局id
     * @return 幻灯片Map集合，key为幻灯片id，value为片子id
     * @author liuzhu
     * @date 2014-2-13
     */
    public Map<String, Object> findSlideShowId(String layoutId);

    /**
     * 根据id获取幻灯片vo
     *
     * @param id 幻灯片id
     * @return 幻灯片VO
     * @author liuzhu
     * @date 2014-2-13
     */
    public DCSlidePO findSlideShowById(Integer id);

    /**
     * 根据位置点查询位置点实时数据
     *
     * @param locationId 位置点ID
     * @return NodeSensorVO
     * @author liuzhu
     * @date 2014-2-13
     */
    public List<NodeSensorVO> findNodeSensorVO(String locationId);

    /**
     * 根据站点id，获取区域位置点实体集合
     *
     * @param siteId 站点编号
     * @return 区域位置点集合
     * @author wang.geng
     * @date 2014-7-29
     */
    public List<ZoneLocationVO> findZoneLocationBySiteId(String siteId);

    /**
     * 保存幻灯片
     *
     * @param dcSlidePO
     */
    public void saveSlide(DCSlidePO dcSlidePO);

    /**
     * 更新幻灯片
     *
     * @param dcSlidePO 被更新的幻灯片实例
     */
    public void updateSlide(DCSlidePO dcSlidePO);

    /**
     * 查询一个布局的布局控件的所有幻灯片
     *
     * @param layoutId 布局ID
     * @param itemId   控件ID
     * @return 幻灯片集合
     */
    public List<DCSlidePO> findSlideList(String layoutId, String itemId);

    /**
     * 根据幻灯片ID删除幻灯片
     *
     * @param slideId 幻灯片ID
     */
    public void deleteSlideById(int slideId);

    /**
     * 根据布局ID，与控件ID删除控件的所有幻灯片
     *
     * @param layoutId 布局ID
     * @param itemId   控件ID
     */
    public void deleteItemAllSlidesByIds(String layoutId, String itemId);

    /**
     * 根据站点id查找基层站点(包含子孙)
     *
     * @param logicGroupId 站点组id
     * @return siteVO
     * @author liuzhu
     * @date 2014-2-27
     */
    public List<SiteVO> findSiteVOByLogicGroupId(int logicGroupId);
}
