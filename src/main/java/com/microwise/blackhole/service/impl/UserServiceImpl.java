package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.Department;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.dao.DepartmentDao;
import com.microwise.blackhole.dao.LogicGroupDao;
import com.microwise.blackhole.dao.RoleDao;
import com.microwise.blackhole.dao.UserDao;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.service.email.EmailService;
import com.microwise.common.service.email.EmailTemplateFactory;
import com.microwise.common.service.email.EmailTemplateFactory.EmailTemplate;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.Constants.Emails;
import com.microwise.common.sys.annotation.Beans.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 用户Service实现
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-12-13 xubaoji svn:805
 */
@Service("userService")
@Transactional
@Blackhole
public class UserServiceImpl implements UserService {

    /**
     * 用户Dao
     */
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * 站点组Dao
     */
    @Autowired
    private LogicGroupDao logicGroupDao;

    /**
     * 部门dao
     */
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public void saveUser(User user, int logicGroupId, String departmentName,
                         List<Integer> roleIdList, String url, boolean isSendValidationEmail)
            throws UnsupportedEncodingException, MessagingException {
        // 处理 用户部门 ，如果该部门不存在 添加部门
        if (departmentName != null) {
            Department department = departmentDao.findByName(departmentName, logicGroupId);
            if (department == null) {
                department = handleDepartment(departmentName, logicGroupId);
            }

            // 给用户添加 部门
            user.setDepartment(department);
        }
        LogicGroup logicGroup = new LogicGroup();
        // 给用户赋归属站点
        user.setCreateTime(new Date());
        logicGroup.setId(logicGroupId);
        user.setLogicGroup(logicGroup);
        Set<Role> roles = new HashSet<Role>();
        for (Integer roleId : roleIdList) {
            Role role = new Role();
            role.setId(roleId);
            roles.add(role);
        }
        // 给用户添加角色
        user.setRoles(roles);
        user.setPassword(UUID.randomUUID().toString());
        // 保存用户
        userDao.saveUser(user);
        // 发邮件 激活邮件或者通知邮件
        String emailTitle = "用户激活";
        EmailTemplate emailTemplate = null;
        EmailTemplateFactory emailTemplateFactory = EmailTemplateFactory.getInstance();
        if (isSendValidationEmail) {
            emailTemplate = emailTemplateFactory.getEmailTemplate(Emails.EMAIL_USER_ACTIVATES_PATH);
        } else {
            user.setActive(true);
            user.setPassword(BCrypt.hashpw("11111111", BCrypt.gensalt(Constants.BCRYPT_SALT)));// 设置默认密码为8个1
            emailTemplate = emailTemplateFactory.getEmailTemplate(Emails.EMAIL_NOTIFICATION_PATH);
            emailTitle = "通知邮件";
        }
        if (emailTemplate != null) {
            String email = emailTemplate.mergeEmailTemplate(url);
            EmailService.sendEmail(user.getEmail(), emailTitle, email);
        }
    }

    @Override
    public void deleteUserById(int userId) {
        userDao.deleteUserById(userId);
    }

    @Override
    public void deleteUserByLogicGroupId(int logicGroupId) {
        List<Integer> userIds = userDao.findUserIdsByLogicGroupId(logicGroupId);
        for (Integer userId : userIds) {
            userDao.deleteUserById(userId);
        }
    }

    @Override
    public void changeUserDisableState(int userId) {
        userDao.changeUserDisableState(userId);
    }

    @Override
    public void updateUser(int userId, String userName, int sex, String mobile) {
        userDao.updateUser(userId, userName, sex, mobile);
    }

    @Override
    public void updateUser(User user, List<Integer> roleIdList) {
        Set<Role> roles = new HashSet<Role>();
        for (Integer roleId : roleIdList) {
            Role role = new Role();
            role.setId(roleId);
            roles.add(role);
        }
        user.setRoles(roles);
        userDao.updateUser(user);
    }

    @Override
    public List<User> findUserList(int LogicGroupId, String userName, int start, int max) {
        return userDao.findUserList(LogicGroupId, userName, start, max);

    }

    @Override
    public User findUser(int logicGroupId, int id) {
        return userDao.findUser(logicGroupId, id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int findUserListCount(int LogicGroupId, String userName) {
        return userDao.findUserListCount(LogicGroupId, userName);
    }

    @Override
    public void assignRoleToUser(int userId, List<Integer> roleIdList) {
        User user = userDao.findUserById(userId);
        Set<Role> roles = new HashSet<Role>();
        for (Integer roleId : roleIdList) {
            Role role = new Role();
            role.setId(roleId);
            roles.add(role);
        }
        user.setRoles(roles);
        userDao.updateUser(user);
    }

    @Override
    public void assignLogicGroupToUser(int userId, List<Integer> logicGroupList) {
        User user = userDao.findUserById(userId);
        Set<LogicGroup> logicGroups = new HashSet<LogicGroup>();
        for (Integer roleId : logicGroupList) {
            LogicGroup logicGroup = new LogicGroup();
            logicGroup.setId(roleId);
            logicGroups.add(logicGroup);
        }
        user.setLogicGroups(logicGroups);
        userDao.updateUser(user);
    }

    @Override
    public User findUserById(int userId) {
        return userDao.findUserById(userId);
    }

    @Override
    public User findUserByToken(String token) {
        return userDao.findUserByToken(token);
    }

    @Override
    public void updateUserPassword(int userId, String password) {
        String pass = BCrypt.hashpw(password, BCrypt.gensalt(Constants.BCRYPT_SALT));
        userDao.updateUserPassword(userId, pass);
    }

    @Override
    public void resetDefalultSetting(int userId) {
        userDao.resetDefalultSetting(userId);
    }

    @Override
    public User findManagerByLogicGroupId(int logicGroupId) {
        return userDao.findManagerByLogicGroupId(logicGroupId);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email.toLowerCase());
    }

    @Override
    public User findActiveUserByEmail(String email) {
        return userDao.findActiveUserByEmail(email.toLowerCase());
    }

    @Override
    public void sendResetPasswordEmail(String email, String token, String url)
            throws UnsupportedEncodingException, MessagingException {
        EmailTemplateFactory emailTemplateFactory = EmailTemplateFactory.getInstance();
        EmailTemplate emailTemplate = emailTemplateFactory
                .getEmailTemplate(Emails.EMAIL_FAND_PASSWORD_PATH);
        userDao.updateUserToken(token, email.toLowerCase());
        String emailText = emailTemplate.mergeEmailTemplate(url);
        EmailService.sendEmail(email, "密码找回", emailText);
    }

    @Override
    public void updateUserPhotoById(int id, String photo) {
        userDao.updateUserPhotoById(id, photo);
    }

    @Override
    public void activeUser(int userId, String userName, String password, int sex, String mobile) {
        password = BCrypt.hashpw(password, BCrypt.gensalt(Constants.BCRYPT_SALT));
        userDao.activeUser(userId, userName, password, sex, mobile, true, null);
        LogicGroup logicGroup = logicGroupDao.findLogicGroupByUserId(userId);
        logicGroupDao.changeLogicGroupActiveState(logicGroup.getId(),
                LogicGroup.LOGICGROUP_IS_ACTIVESTATE);
    }

    @Override
    public boolean isAnonymityLoginEnable() {
        User anonymity = userDao.findAnonymity();
        // 如果匿名用户不存在, 返回 false
        if (anonymity == null) {
            return false;
        }

        // 如果没有禁用
        return anonymity.isActive();

    }

    @Override
    public void enableAnonymityLogin(boolean isEnable) {
        User anonymity = userDao.findAnonymity();
        if (anonymity == null) {
            // 匿名用户不存在的情况下
            createAnonymity();
        }
        enableAnonymityLoginIfUserExists(isEnable);
    }

    /**
     * 创建匿名用户
     *
     * @return
     */
    private User createAnonymity() {
        Role role = new Role();
        role.setRoleName("访客角色");
        role.setManager(false);
        role.setState(Role.STATE_ANONYMITY);
        roleDao.save(role);

        User anonymity = new User();
        anonymity.setEmail("guest");
        anonymity.setPassword(BCrypt.hashpw("12345678", BCrypt.gensalt(Constants.BCRYPT_SALT)));
        anonymity.setUserName("访客");
        anonymity.setSex(2);
        anonymity.setActive(false);
        anonymity.setDisable(false);
        anonymity.setCreateTime(new Date());
        anonymity.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userDao.saveUser(anonymity);
        return anonymity;
    }

    /**
     * 匿名用户存在的情况下, 启用或者停用匿名登录
     *
     * @param isEnable
     */
    private void enableAnonymityLoginIfUserExists(boolean isEnable) {
        User anonymity = userDao.findAnonymity();

        if (isEnable) {
            anonymity.setActive(true);
        } else {
            anonymity.setActive(false);
        }

        userDao.updateUser(anonymity);
    }

    @Override
    public List<String> findUserByLogicGroupIdAndPrivilegeId(int logicGroupId, String privilegeId) {
        List<User> userList = userDao.findUserByLogicGroupIdAndPrivilegeId(logicGroupId,
                privilegeId);
        List<String> userIdList = new ArrayList<String>();
        for (User user : userList) {
            userIdList.add(String.valueOf(user.getId()));
        }
        return userIdList;
    }

    @Override
    public List<User> findUserLists(int logicGroupId, String userName, int index, int max) {
        return userDao.findUserList(logicGroupId, true, false, userName, index, max);
    }

    @Override
    public Integer findUserCount(int logicGroupId, String userName) {
        return userDao.findUserCount(logicGroupId, true, false, userName);
    }

    @Override
    public List<User> findUserLists(int logicGroupId) {
        return userDao.findUserLists(logicGroupId);
    }

    @Override
    public List<User> findUserListsByDepartment(int logicGroupId, Integer departmentId) {
        return userDao.findUserListsByDepartment(logicGroupId, departmentId);
    }

    @Override
    public void updateUserDepartment(int userId, int logicGroupId, String departmentName) {
        // 处理 部门
        Integer departmentId = null;
        if (departmentName != null) {
            Department department = departmentDao.findByName(departmentName, logicGroupId);
            if (department == null) {
                department = handleDepartment(departmentName, logicGroupId);
            }
            departmentId = department.getId();
        }
        // 修改用户对应部门
        userDao.updateUserDepartment(userId, departmentId);
    }

    /**
     * 处理 用户 部门
     *
     * @param departmentId   部门id 编号
     * @param departmentName 部门名称
     * @param logicGroupId   逻辑站点编号
     * @author xu.baoji
     * @date 2013-8-17
     */
    private Department handleDepartment(String departmentName, int logicGroupId) {
        Department department = new Department();
        department.setName(departmentName);
        LogicGroup logicGroup = new LogicGroup();
        logicGroup.setId(logicGroupId);
        department.setLogicGroup(logicGroup);
        departmentDao.save(department);
        return department;
    }

    public List<User> findUserList() {
        return userDao.findUserList();
    }

}
