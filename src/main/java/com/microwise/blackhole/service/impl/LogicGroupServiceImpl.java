package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.*;
import com.microwise.blackhole.dao.LogicGroupDao;
import com.microwise.blackhole.dao.PrivilegeDao;
import com.microwise.blackhole.dao.RoleDao;
import com.microwise.blackhole.dao.UserDao;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.PrivilegeStateUtil;
import com.microwise.common.service.email.EmailService;
import com.microwise.common.service.email.EmailTemplateFactory;
import com.microwise.common.service.email.EmailTemplateFactory.EmailTemplate;
import com.microwise.common.sys.Constants.Emails;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * LogicGroupService接口的实现
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-12-13 zhangpeng svn:809
 */
@Service
@Transactional
@Blackhole
public class LogicGroupServiceImpl implements LogicGroupService {

    /**
     * 站点组Dao
     */
    @Autowired
    private LogicGroupDao logicGroupDao;

    /**
     * 角色 dao
     */
    @Autowired
    private RoleDao roleDao;

    /**
     * 用户 dao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 权限dao
     */
    @Autowired
    private PrivilegeDao privilegeDao;

    @Override
    public List<LogicGroup> findAll() {
        return logicGroupDao.findAll();
    }

    @Override
    public void saveLogicGroup(String logicGroupName, String adminEmail,
                               Integer parentLogicGroupId, String token, String url)
            throws UnsupportedEncodingException, MessagingException {
        LogicGroup parentLogicGroup = null;
        if (parentLogicGroupId != null) {
            parentLogicGroup = logicGroupDao.findById(parentLogicGroupId);
        }
        // 创建 组装logicGroup 实体 对象
        LogicGroup logicGroup = new LogicGroup(logicGroupName,
                parentLogicGroup, LogicGroup.LOGICGROUP_NOT_ACTIVESTATE,
                LogicGroup.LOGICGROUP_TYPE_ISNATIVE);
        // TODO 抽取常量
        // 使用默认主题
        logicGroup.setUseTitle(1);
        logicGroup.setUseBg(1);
        logicGroupDao.save(logicGroup);
        // 初始化 当前 logicGroup 下的管理员用户和 管理员角色 并发送 激活邮件给管理员 邮箱
        initAdminUserAndRole(logicGroup, adminEmail, token, url);
        // 将logicGroup 更改为待激活状态
        logicGroupDao.changeLogicGroupActiveState(logicGroup.getId(),
                LogicGroup.LOGICGROUP_DAI_ACTIVESTATE);
    }

    @Override
    public void saveLogicGroup(String logicGroupName, String adminEmail,
                               Integer parentLogicGroupId, String siteId, String token, String url)
            throws UnsupportedEncodingException, MessagingException {
        // 创建 组装logicGroup 实体 对象
        Site site = new Site();
        site.setSiteId(siteId);
        LogicGroup parentLogicGroup = null;
        if (parentLogicGroupId != null) {
            parentLogicGroup = logicGroupDao.findById(parentLogicGroupId);
        }
        LogicGroup logicGroup = new LogicGroup(logicGroupName,
                parentLogicGroup, LogicGroup.LOGICGROUP_NOT_ACTIVESTATE,
                LogicGroup.LOGICGROUP_TYPE_ISNATIVE);
        // TODO 抽取常量
        // 使用默认主题
        logicGroup.setUseTitle(1);
        logicGroup.setUseBg(1);
        logicGroup.setSite(site);
        logicGroupDao.save(logicGroup);
        // 初始化 管理员用户 和管理员角色
        initAdminUserAndRole(logicGroup, adminEmail, token, url);
        // 将logicGroup 更改为待激活状态
        logicGroupDao.changeLogicGroupActiveState(logicGroup.getId(),
                LogicGroup.LOGICGROUP_DAI_ACTIVESTATE);
    }

    @Override
    public int saveLogicGroup(String logicGroupName, String adminEmail,
                              String siteId, String token, boolean baseSite)
            throws UnsupportedEncodingException {
        LogicGroup logicGroup = new LogicGroup(logicGroupName, null, LogicGroup.LOGICGROUP_NOT_ACTIVESTATE,
                LogicGroup.LOGICGROUP_TYPE_ISNATIVE);

        // 使用默认主题
        logicGroup.setUseTitle(1);
        logicGroup.setUseBg(1);
        if (baseSite) {
            Site site = new Site();
            site.setSiteId(siteId);
            logicGroup.setSite(site);
        }
        logicGroupDao.save(logicGroup);

        Role role = roleDao.findAdminRoleByLogicGroupId(logicGroup.getId());
        if (role == null) {
            role = addRole(logicGroup);
        }
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        // 添加管理员用户
        User user = new User(logicGroup, adminEmail.toLowerCase(), UUID
                .randomUUID().toString(), new Date(), token, roles);
        user.setSex(User.SEX_MAN);

        // 保存用户
        userDao.saveUser(user);


        // 将logicGroup 更改为待激活状态
        logicGroupDao.changeLogicGroupActiveState(logicGroup.getId(),
                LogicGroup.LOGICGROUP_DAI_ACTIVESTATE);
        return logicGroup.getId();
    }


    @Override
    public void initAdminToLogicGroup(String email, int logicGroupId,
                                      String token, String url) throws UnsupportedEncodingException,
            MessagingException {
        LogicGroup logicGroup = new LogicGroup();
        logicGroup.setId(logicGroupId);
        initAdminUserAndRole(logicGroup, email, token, url);
        // 将logicGroup 更改为待激活状态
        logicGroupDao.changeLogicGroupActiveState(logicGroup.getId(),
                LogicGroup.LOGICGROUP_DAI_ACTIVESTATE);
    }

    /**
     * 用来初始化 当前logicGroup 下的管理员用户和管理员 角色的方法
     *
     * @param logicGroup 要初始化的分组
     * @param adminEmail 用户邮箱
     * @param token      用户激活邮件token
     * @param url        用户激活邮件url
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @author 许保吉
     * @date 2012-11-20
     */
    private void initAdminUserAndRole(LogicGroup logicGroup, String adminEmail,
                                      String token, String url) throws UnsupportedEncodingException,
            MessagingException {
        Role role = roleDao.findAdminRoleByLogicGroupId(logicGroup.getId());
        if (role == null) {
            role = addRole(logicGroup);
        }
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        // 添加管理员用户
        User user = new User(logicGroup, adminEmail.toLowerCase(), UUID
                .randomUUID().toString(), new Date(), token, roles);
        user.setSex(User.SEX_MAN);

        // 保存用户
        userDao.saveUser(user);

        // 发邮件
        EmailTemplateFactory emailTemplateFactory = EmailTemplateFactory
                .getInstance();
        EmailTemplate emailTemplate = emailTemplateFactory
                .getEmailTemplate(Emails.EMAIL_USER_ACTIVATES_PATH);
        String email = emailTemplate.mergeEmailTemplate(url);
        EmailService.sendEmail(adminEmail, "用户激活", email);
    }

    /**
     * 初始化 站点的默认角色 返回 该站点的管理员角色
     *
     * @param logicGroup logicGroup 实体对象
     * @return role 当前站点的管理员角色
     * @author 许保吉
     * @date 2012-12-14
     */
    private Role addRole(LogicGroup logicGroup) {
        // 获得管理员权限
        Set<Integer> pSet = PrivilegeStateUtil
                .getDBFuzzyState(PrivilegeStateUtil.PRIVILEGE_STATE_IS_MANAGER);
        List<Privilege> privileges = privilegeDao
                .findPrivilegesByStateSet(pSet);
        Set<Privilege> adminPrivileges = new HashSet<Privilege>();
        for (Privilege privilege : privileges) {
            adminPrivileges.add(privilege);
        }
        // 为当前 logicGroup 分组 添加管理员角色
        Role role = new Role(logicGroup, "站点管理员", true, adminPrivileges);
        roleDao.save(role);
        // 为当前 logicGroup 分组 添加浏览角色
        Set<Integer> set = PrivilegeStateUtil
                .getDBFuzzyState(PrivilegeStateUtil.PRIVILEGE_STATE_USER);
        List<Privilege> browsePrivileges = privilegeDao
                .findPrivilegesByStateSet(set);
        Set<Privilege> browseSet = new HashSet<Privilege>();
        for (Privilege privilege : browsePrivileges) {
            browseSet.add(privilege);
        }
        Role browseRole = new Role(logicGroup, "浏览角色", false, browseSet);
        roleDao.save(browseRole);
        return role;
    }

    @Override
    public List<Site> findSiteListByAreaCode(int areaCode) {
        return logicGroupDao.findSiteListByAreaCode(areaCode);
    }

    @Override
    public void deleteLogicGroup(int LogicGroupId) {
        List<Role> roles = roleDao.findRoleListByLogicGroupId(LogicGroupId,
                null);
        for (Role role : roles) {
            roleDao.deleteRoleById(role.getId());
        }
        List<User> users = userDao.findUserList(LogicGroupId, null, 0, 0);
        for (User user : users) {
            userDao.deleteUserById(user.getId());
        }
        logicGroupDao.deleteLogicGroup(LogicGroupId);
    }

    @Override
    public void updateLogicGroup(LogicGroup logicGroup) {
        logicGroupDao.updateLogicGroup(logicGroup);
    }

    @Override
    public List<LogicGroup> findSubLogicGroupList(Integer logicGroupId) {
        return logicGroupDao.findSubLogicGroupList(logicGroupId);
    }

    @Override
    public List<LogicGroup> findSubLogicGroupList(Integer logicGroupId,
                                                  String logicGroupName, int start, int max) {
        List<LogicGroup> logicGroups = logicGroupDao.findSubLogicGroupList(
                logicGroupId, logicGroupName, start, max);
        for (LogicGroup logicGroup : logicGroups) {
            if (logicGroup.getLogicGroupType() == 1
                    && logicGroup.getSite() == null) {
                if (logicGroupDao.findSubLogicGroupListCount(
                        logicGroup.getId(), null) > 0) {
                    logicGroup.setHaveChildren(true);
                }
            }
        }
        return logicGroups;
    }

    @Override
    public int findSubLogicGroupListCount(Integer logicGroupId,
                                          String logicGroupName) {
        return logicGroupDao.findSubLogicGroupListCount(logicGroupId,
                logicGroupName);
    }

    @Override
    public List<LogicGroup> findTopLogicGroupList() {
        return logicGroupDao.findSubLogicGroupList(null);
    }

    @Override
    public void changeSiteOfLogicGroup(int logicGroupId, String siteId) {
        logicGroupDao.changeSiteOfLogicGroup(logicGroupId, siteId);
    }

    @Override
    public void changeParentLogicGroup(int logicGroupId,
                                       Integer parentLogicGroupId) {
        List<Integer> userIds = userDao.findUserIdsByLogicGroupId(logicGroupId);
        System.out.println(userIds + ".....................................");
        logicGroupDao.clearUserLogicGroups(logicGroupId, userIds);
        logicGroupDao.changeParentLogicGroup(logicGroupId, parentLogicGroupId,
                userIds);
    }

    @Override
    public LogicGroup findLogicGroupById(int id) {
        return logicGroupDao.findById(id);
    }

    @Override
    public List<LogicGroup> findUserLogicGroups(int userId) {
        return logicGroupDao.findUserLogicGroups(userId);
    }

    @Override
    public LogicGroup findLogicGroupByUserId(int id) {
        return logicGroupDao.findLogicGroupByUserId(id);
    }

    @Override
    public List<LogicGroup> findAdmimLogicGroups(Integer logicGroupId) {
        return logicGroupDao.findAdmimLogicGroups(logicGroupId);
    }

    @Override
    public LogicGroup findLogicGroupCarrySite(Integer logicGroupId) {
        return logicGroupDao.findLogicGroupCarrySite(logicGroupId);
    }

    @Override
    public List<LogicGroup> findLogicGroupForMap(Integer logicGroupId,
                                                 int userId) {
        // 获取当前逻辑站点id
        Set<Integer> set = new HashSet<Integer>();
        set.add(logicGroupId);
        LogicGroup logicGroup = logicGroupDao.findLogicGroupByUserId(userId);
        if (logicGroup != null) {
            set.add(logicGroup.getId());
        }
        // 查询用户站点组对应逻辑站点id列表
        set.addAll(logicGroupDao.findUserLogicGroupIds(userId));
        return logicGroupDao.findLogicGroupForMap(set);
    }

    @Override
    public List<LogicGroup> findSubLogicGroupsCarrySite(Integer logicGroupId) {
        return logicGroupDao.findSubLogicGroupsCarrySite(logicGroupId);
    }

    @Override
    public List<LogicGroup> findUserLogicGroupsCarrySite(int userId) {
        return logicGroupDao.findUserLogicGroupsCarrySite(userId);
    }

    @Override
    public Integer findLogicGroupIdBySiteId(String siteId) {
        return logicGroupDao.findLogicGroupIdBySiteId(siteId);
    }

    @Override
    public void updateLogicGroupTitleImage(String titleImage, int logicGroupId) {
        logicGroupDao.updateLogicGroupTitleImage(titleImage, logicGroupId);
    }

    @Override
    public void updateLogicGroupBgImage(String bgImage, int logicGroupId) {
        logicGroupDao.updateLogicGroupBgImage(bgImage, logicGroupId);
    }

    @Override
    public void updateLogicGroupUseTitle(int useTitle, int logicGroupId) {
        logicGroupDao.updateLogicGroupUseTitle(useTitle, logicGroupId);
    }

    @Override
    public void updateLogicGroupUseBg(int useBg, int logicGroupId) {
        logicGroupDao.updateLogicGroupUseBg(useBg, logicGroupId);
    }

    @Override
    public String findTemplate(int logicGroupId) {
        return logicGroupDao.findTemplate(logicGroupId);
    }

    @Override
    public void updateTemplate(int logicGroupId, String template) {
        if (findTemplate(logicGroupId) == null) {
            logicGroupDao.insertTemplate(logicGroupId, template);
        } else {
            logicGroupDao.updateTemplate(logicGroupId, template);
        }
    }

}
