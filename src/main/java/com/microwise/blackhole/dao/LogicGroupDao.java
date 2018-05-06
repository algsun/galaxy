package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Site;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * logicGroup的Dao
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-12-13 zhangpeng svn:809
 */
public interface LogicGroupDao {
    /**
     * 查询所有站点组
     *
     * @return 站点组集合
     */
    public List<LogicGroup> findAll();

    /**
     * 添加 logicGroup 实体
     *
     * @param logicGroup 逻辑站点组对象
     * @return void
     * @author 许保吉
     * @date 2012-11-20
     */
    public Serializable save(LogicGroup logicGroup);

    /**
     * 通过id 获得唯一 logicGroup
     *
     * @param id logicGroup id 号
     * @return logicGroup
     * @author 许保吉
     * @date 2012-11-20
     */
    public LogicGroup findById(Serializable id);

    /**
     * 根据地区行政编码查询该地区下所有站点列表，地区不限制级别
     *
     * @param areaCode 地区行政编码
     * @return List<Site> 物理站点列表
     * @author 许保吉
     * @date 2012-11-20
     */
    public List<Site> findSiteListByAreaCode(int areaCode);

    /**
     * 根据logicGroupId删除站点
     *
     * @param logicGroupId logicGroup的Id
     * @author 许保吉
     * @date 2012-11-21
     */
    public void deleteLogicGroup(int logicGroupId);

    /**
     * 修改logicGroup信息
     *
     * @param logicGroup LogicGroup对象
     * @author 许保吉
     * @date 2012-11-21
     */
    public void updateLogicGroup(LogicGroup logicGroup);

    /**
     * 根据logicGroupId分页查询直接子站点列表（无分页）
     *
     * @param logicGroupId logicGroup编号 编号为null 表示 查询 所有顶级站点
     * @return List<LogicGroup> 逻辑站点列表
     * @author 许保吉
     * @date 2012-11-21
     */
    public List<LogicGroup> findSubLogicGroupList(Integer logicGroupId);

    /**
     * 根据logicGroupId分页查询直接子站点列表（有分页）
     *
     * @param logicGroupId   logicGroup编号 为null 表示 查所有顶级站点
     * @param logicGroupName logicGroup名称
     * @param start          起始位置,分页使用
     * @param max            最多数目，分页使用
     * @return List<LogicGroup> 逻辑站点列表
     * @author 许保吉
     * @date 2012-11-21
     */
    public List<LogicGroup> findSubLogicGroupList(Integer logicGroupId,
                                                  String logicGroupName, int start, int max);

    /**
     * 根据logicGroupId和logicGroupName查询直接子站点数量
     *
     * @param logicGroupId   logicGroup编号
     * @param logicGroupName logicGroup名称
     * @return int 直接子站点数量
     * @author 许保吉
     * @date 2012-11-21
     */
    public int findSubLogicGroupListCount(Integer logicGroupId,
                                          String logicGroupName);

    /**
     * 调整Site对应关系
     *
     * @param logicGroupId logicGroup编号
     * @param siteId       物理站点id
     * @author 许保吉
     * @date 2012-11-21
     */
    public void changeSiteOfLogicGroup(int logicGroupId, String siteId);

    /**
     * 调整父LogicGroup
     *
     * @param logicGroupId       logicGroup编号
     * @param parentLogicGroupId 父级logicGroup编号
     * @author 许保吉
     * @date 2012-11-21
     */
    public void changeParentLogicGroup(int logicGroupId,
                                       Integer parentLogicGroupId, List<Integer> userIds);

    /**
     * 通过用户编号获得 用户对应站点组
     *
     * @param userId 用户编号
     * @return list<LogicGroup> 用户对应的站点组
     * @author 许保吉
     * @date 2012-11-26
     */
    public List<LogicGroup> findUserLogicGroups(int userId);

    /**
     * 根据用户id查询用户归属站点
     *
     * @param userId 用户id
     * @return LogicGroup 逻辑站点组对象
     * @author 许保吉
     * @date 2012-11-26
     */
    public LogicGroup findLogicGroupByUserId(int userId);

    /**
     * 该 变一个logicGroup 的 ActiveState 激活 状态
     *
     * @param logicGroupId 要改变的logicGroup 编号
     * @param activeState  要改变的状态
     * @author 许保吉
     * @date 2012-11-28
     */
    public void changeLogicGroupActiveState(int logicGroupId,
                                            Integer activeState);

    /**
     * 根据logicGroup 编号查询直接行政站点
     *
     * @param logicGroupId logicGroup 编号 null：查所有顶级行政站点， not Null 查当前站点下的直接行政站点
     * @return List<LogicGroup> 行政站点列表
     * @author 许保吉
     * @date 2012-12-4
     */
    public List<LogicGroup> findAdmimLogicGroups(Integer logicGroupId);

    /**
     * 清除logicGroup下用户可查看的logicGroups （不包括自身） 以及别的用户对当前logicGroup的查看权限
     *
     * @param logicGroupId 站点组ID
     * @param userIds      用户ID 集合
     * @author 许保吉
     * @date 2012-12-4
     */
    public void clearUserLogicGroups(Integer logicGroupId,
                                     List<Integer> userIds);

    /**
     * 通过logicGroup id 获得 logicGroup 对象  带有 site 对象
     *
     * @param logicGroupId 站点组ID
     * @return logicGroup 返回 logicGroup 对象 带有 site 对象
     * @author 许保吉
     * @date 2012-12-4
     */
    public LogicGroup findLogicGroupCarrySite(Integer logicGroupId);

    /**
     * 通过用户id获取用户归属站点及用户可查询的站点组包括子孙中所有基层站点，携带site信息
     *
     * @param logicGroupIdList 站点组ID集合
     * @return List<logicGroup> logicGroup列表带有 site 对象
     * @author zhangpeng
     * @date 2013-4-2
     */
    public List<LogicGroup> findLogicGroupForMap(Set<Integer> logicGroupIdList);

    /**
     * 通过用户编号获得 用户拥有查询权限的logicGroup的id列表
     *
     * @param userId 用户编号
     * @return list<LogicGroup> 用户拥有查询权限的站点组
     * @author zhangpeng
     * @date 2013-4-3
     */
    public List<Integer> findUserLogicGroupIds(int userId);

    /**
     * 根据logicGroupId查询直接子站点列表（无分页）,有site的携带site
     *
     * @param logicGroupId logicGroup编号
     * @return List<LogicGroup> 逻辑站点列表
     * @author zhangpeng
     * @date 2013-4-3
     */
    public List<LogicGroup> findSubLogicGroupsCarrySite(Integer logicGroupId);

    /**
     * 通过用户编号获得 用户拥有查询权限的站点组，有site的带上site
     *
     * @param userId 用户编号
     * @return list<LogicGroup> 用户拥有查询权限的站点组
     * @author zhangpeng
     * @date 2013-4-3
     */
    public List<LogicGroup> findUserLogicGroupsCarrySite(int userId);

    /**
     * 通过siteId 查询 logicGroupid
     *
     * @param siteId 站点编号
     * @return int
     * @author xu.baoji
     * @date 2013-7-26
     */
    public Integer findLogicGroupIdBySiteId(String siteId);

    /**
     * 修改 LogicGroup的 TitleImage
     *
     * @param titleImage
     * @param logicGroupId
     * @author xu.yuexi
     * @date 2013-11-25
     */
    public void updateLogicGroupTitleImage(String titleImage, int logicGroupId);

    /**
     * 修改 LogicGroup的 bgImage
     *
     * @param bgImage
     * @param logicGroupId
     * @author xu.yuexi
     * @date 2013-11-25
     */
    public void updateLogicGroupBgImage(String bgImage, int logicGroupId);

    /**
     * 修改 LogicGroup的 useTitle
     *
     * @param logicGroupId
     * @author xu.yuexi
     * @date 2013-11-26
     */
    public void updateLogicGroupUseTitle(int useTitle, int logicGroupId);

    /**
     * 修改 LogicGroup的 UseBg
     *
     * @param logicGroupId
     * @author xu.yuexi
     * @date 2013-11-26
     */
    public void updateLogicGroupUseBg(int useBg, int logicGroupId);

    /**
     * 查询 站点 当前模版
     *
     * @param logicGroupId 站点id
     */
    public String findTemplate(int logicGroupId);

    /**
     * 修改 当前站点模版
     *
     * @param logicGroupId 站点id
     * @param template     模版
     */
    public void updateTemplate(int logicGroupId, String template);

    /**
     * 修改 当前站点模版
     *
     * @param logicGroupId 站点id
     * @param template     模版
     */
    public void insertTemplate(int logicGroupId, String template);
}
