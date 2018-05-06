package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Site;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * logicGroup的service(logicGroup没合适名称，注释无营养啊)
 *
 * @author xubaoji
 * @date 2012-11-20
 * @check 2012-12-13 zhangpeng svn:809
 */
public interface LogicGroupService {

    /**
     * 查询所有站点组
     *
     * @return 站点组集合
     */
    public List<LogicGroup> findAll();

    /**
     * 创建行政logicGroup，无对应site
     *
     * @param logicGroupName     logicGroup名称
     * @param adminEmail         用户邮箱
     * @param parentLogicGroupId 父级logicGroup的id
     * @param token              站点管理员邮箱token
     * @param url                站点管理员邮箱激活链接
     * @return void
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @author 许保吉
     * @date 2012-11-20
     */
    public void saveLogicGroup(String logicGroupName, String adminEmail,
                               Integer parentLogicGroupId, String token, String url)
            throws UnsupportedEncodingException, MessagingException;

    /**
     * 创建基层logicGroup，有对应site
     *
     * @param logicGroupName     logicGroup名称
     * @param adminEmail         用户邮箱
     * @param parentLogicGroupId 父级logicGroup的id
     * @param siteId             对应物理站点id
     * @param token              站点管理员邮箱token
     * @param url                站点管理员邮箱激活链接（包含token）
     * @return void
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @author 许保吉
     * @date 2012-11-20
     */
    public void saveLogicGroup(String logicGroupName, String adminEmail,
                               Integer parentLogicGroupId, String siteId, String token, String url)
            throws UnsupportedEncodingException, MessagingException;

    /**
     * 根据布尔类型baseSite创建基层或者行政站点，提供给V1-API使用的接口
     *
     * @param logicGroupName logicGroup名称
     * @param adminEmail     用户邮箱
     * @param siteId         对应物理站点id
     * @param token          站点管理员邮箱token
     * @param url            站点管理员邮箱激活链接（包含token）
     * @param baseSite       是否基层站点
     * @throws UnsupportedEncodingException
     * @author 王耕
     * @date 2014-12-23
     */
    public int saveLogicGroup(String logicGroupName, String adminEmail,
                              String siteId, String token, boolean baseSite)
            throws UnsupportedEncodingException;

    /**
     * 初始化管理员（同步上来的LogicGroup）
     *
     * @param email        管理员邮箱
     * @param logicGroupId 同步LogicGroup的id
     * @param token        站点管理员邮箱token
     * @param url          站点管理员邮箱激活链接（包含token）
     * @return void
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @author 许保吉
     * @date 2012-11-27
     */
    public void initAdminToLogicGroup(String email, int logicGroupId,
                                      String token, String url) throws UnsupportedEncodingException,
            MessagingException;

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
     * @return void
     * @author 许保吉
     * @date 2012-11-21
     */
    public void deleteLogicGroup(int logicGroupId);

    /**
     * 修改logicGroup信息
     *
     * @param logicGroup LogicGroup对象
     * @return void
     * @author 许保吉
     * @date 2012-11-21
     */
    public void updateLogicGroup(LogicGroup logicGroup);

    /**
     * 根据logicGroupId查询直接子站点列表（无分页）
     *
     * @param logicGroupId logicGroup编号
     * @return List<LogicGroup> 逻辑站点列表
     * @author 许保吉
     * @date 2012-11-21
     */
    public List<LogicGroup> findSubLogicGroupList(Integer logicGroupId);

    /**
     * 根据logicGroupId查询直接子站点列表（有分页）
     *
     * @param logicGroupId   logicGroup编号 为null 表示查询所有顶级站点
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
     * 获取所有顶级站点组
     *
     * @return List<LogicGroup> 所以的顶级站点
     * @author 许保吉
     * @date 2012-11-22
     */
    public List<LogicGroup> findTopLogicGroupList();

    /**
     * 调整Site对应关系
     *
     * @param logicGroupId logicGroup编号
     * @param siteId       物理站点id
     * @return void
     * @author 许保吉
     * @date 2012-11-21
     */
    public void changeSiteOfLogicGroup(int logicGroupId, String siteId);

    /**
     * 调整父LogicGroup
     *
     * @param logicGroupId       logicGroup编号
     * @param parentLogicGroupId 父级logicGroup编号
     * @return void
     * @author 许保吉
     * @date 2012-11-21
     */
    public void changeParentLogicGroup(int logicGroupId,
                                       Integer parentLogicGroupId);

    /**
     * 通过 logicGroup 的编号 获得 一个 logicGroup实体对象
     *
     * @param id logicGroup 编号
     * @return logicGroup 实体对象
     * @author 许保吉
     * @date 2012-11-22
     */
    public LogicGroup findLogicGroupById(int id);

    /**
     * 通过用户编号获得 用户拥有查询权限的站点组
     *
     * @param userId 用户编号
     * @return list<LogicGroup> 用户拥有查询权限的站点组
     * @author 许保吉
     * @date 2012-11-26
     */
    public List<LogicGroup> findUserLogicGroups(int userId);

    /**
     * 根据用户id查询用户归属站点
     *
     * @param id 用户id
     * @return LogicGroup 用户归属站点
     * @author zhangpeng
     * @date 2012-11-20
     */
    public LogicGroup findLogicGroupByUserId(int id);

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
     * 通过logicGroup id 获得 logicGroup 对象 带有 site 对象
     *
     * @param logicGroupId
     * @return logicGroup 返回 logicGroup 对象 带有 site 对象
     * @author 许保吉
     * @date 2012-12-4
     */
    public LogicGroup findLogicGroupCarrySite(Integer logicGroupId);

    /**
     * <pre>
     * 通过用户id获取用户归属站点及用户可查询的站点组包括子孙中所有基层站点，携带site信息
     *
     * @param logicGroupId 当前切换到的逻辑站点id
     * @param userId       用户id
     * @return List<logicGroup> logicGroup列表带有 site 对象
     * </pre>
     * @author zhangpeng
     * @date 2013-4-2
     */
    public List<LogicGroup> findLogicGroupForMap(Integer logicGroupId,
                                                 int userId);

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
}
