package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 用户Service
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-12-13 xubaoji svn:805
 */
public interface UserService {

    /**
     * 添加用户
     *
     * @param user                  用户对象
     * @param logicGroupId          logicGroup编号
     * @param departmentName        部门名称
     * @param roleIdList            角色id列表
     * @param url                   找回密码连接（包含token）
     * @param isSendValidationEmail 新增参数，选项是否发送验证邮件或者通知邮件，2013-05-13 added by wanggeng
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     * @author zhangpeng
     * @date 2012-11-19
     */
    public void saveUser(User user, int logicGroupId, String departmentName,
                         List<Integer> roleIdList, String url, boolean isSendValidationEmail)
            throws UnsupportedEncodingException, MessagingException;

    /**
     * 根据用户id删除用户
     *
     * @param userId 用户id
     * @author zhangpeng
     * @date 2012-11-20
     */
    public void deleteUserById(int userId);

    /**
     * 根据站点组id删除用户
     *
     * @param logicGroupId 站点组id
     * @author zhangpeng
     * @date 2012-12-03
     */
    public void deleteUserByLogicGroupId(int logicGroupId);

    /**
     * 根据用户id禁用用户
     *
     * @param userId 用户id
     * @author zhangpeng
     * @date 2012-11-19
     */
    public void changeUserDisableState(int userId);

    /**
     * 修改用户信息(无角色)
     *
     * @param userId   用户编号 id
     * @param userName 用户名
     * @param sex      用户性别
     * @param mobile   用户手机
     * @author zhangpeng
     * @date 2012-11-19
     */
    public void updateUser(int userId, String userName, int sex, String mobile);

    /**
     * 修改用户信息(有角色)
     *
     * @param user       用户对象
     * @param roleIdList 角色id列表
     * @author zhangpeng
     * @date 2012-11-19
     */
    public void updateUser(User user, List<Integer> roleIdList);

    /**
     * 根据用户名和logicGroup编号查询用户列表
     *
     * @param LogicGroupId LogicGroup编号
     * @param userName     用户姓名 、(默认模糊查询)
     * @param start        起始位置,分页使用
     * @param max          最多数目，分页使用
     * @return List<User> 用户列表
     * @author zhangpeng
     * @date 2012-11-20
     */
    public List<User> findUserList(int LogicGroupId, String userName, int start, int max);


    /**
     * 根据用户名和logicGroup编号查询用户列表
     *
     * @param logicGroupId LogicGroup编号
     * @param id           用户id
     * @return 用户
     * @author liuzhu
     * @date 2013-08-19
     */
    public User findUser(int logicGroupId, int id);


    /**
     * 根据用户名和logicGroup编号查询用户数量
     *
     * @param LogicGroupId LogicGroup编号
     * @param userName     用户姓名 、(默认模糊查询)
     * @return int 用户数量
     * @author zhangpeng
     * @date 2012-11-20
     */
    public int findUserListCount(int LogicGroupId, String userName);

    /**
     * 给用户分配角色（一个或多个）
     *
     * @param userId     用户编号
     * @param roleIdList 角色ID列表
     * @author zhangpeng
     * @date 2012-11-20
     */
    public void assignRoleToUser(int userId, List<Integer> roleIdList);

    /**
     * 给用户指定分组（一个或多个，可复合）
     *
     * @param userId         用户编号
     * @param logicGroupList logicGroupid 列表
     * @author zhangpeng
     * @date 2012-11-20
     */
    public void assignLogicGroupToUser(int userId, List<Integer> logicGroupList);

    /**
     * 根据用户编号查询用户
     *
     * @param userId 用户id
     * @return User 用户对象
     * @author zhangpeng
     * @date 2012-11-15
     */
    public User findUserById(int userId);

    /**
     * 根据邮箱token查询用户
     *
     * @param token 邮箱token
     * @return User 用户对象
     * @author zhangpeng
     * @date 2012-11-26
     */
    public User findUserByToken(String token);

    /**
     * 根据用户编号查询用户
     *
     * @param userId   用户id
     * @param password 用户密码
     * @author zhangpeng
     * @date 2012-11-19
     */
    public void updateUserPassword(int userId, String password);

    /**
     * 重置用户个性化设置
     *
     * @param userId 用户id
     * @author zhangpeng
     * @date 2012-11-19
     */
    public void resetDefalultSetting(int userId);

    /**
     * 根据逻辑站点id查询站点管理员
     *
     * @param logicGroupId 逻辑站点id
     * @return User 用户对象
     * @author zhangpeng
     * @date 2012-12-12
     */
    public User findManagerByLogicGroupId(int logicGroupId);

    /**
     * 根据email查询用户
     *
     * @param email 用户邮箱
     * @return User 用户对象
     * @author zhangpeng
     * @date 2012-11-15
     */
    public User findUserByEmail(String email);

    /**
     * 根据email查询激活的用户
     *
     * @param email 用户邮箱
     * @return User 用户对象
     * @author zhangpeng
     * @date 2012-11-15
     */
    public User findActiveUserByEmail(String email);

    /**
     * 发送密码找回邮件，用于首页忘记密码
     *
     * @param email 用户邮箱
     * @param token 用户邮箱标识
     * @param url   找回密码连接（包含token）
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     * @author zhangpeng
     * @date 2012-11-20
     */
    public void sendResetPasswordEmail(String email, String token, String url)
            throws UnsupportedEncodingException, MessagingException;

    /**
     * 根据用户id更新用户头像图片文件名
     *
     * @param userId 用户id
     * @param photo  用户 头像图片文件名
     * @author zhangpeng
     * @date 2012-11-20
     */
    public void updateUserPhotoById(int userId, String photo);

    /**
     * 修改用户部门
     *
     * @param userId         用户id
     * @param logicGroupId   逻辑站点编号
     * @param departmentName 部门名称
     * @author xu.baoji
     * @date 2013-8-17
     */
    public void updateUserDepartment(int userId, int logicGroupId, String departmentName);

    /**
     * 激活用户
     *
     * @param userId   用户id
     * @param userName 用户 名称
     * @param password 用户 密码
     * @param sex      用户 性别
     * @param mobile   用户 手机号
     * @author zhangpeng
     * @date 2012-12-03
     */
    public void activeUser(int userId, String userName, String password, int sex, String mobile);

    /**
     * 匿名登录是否启用
     *
     * @return 是否匿名登录
     */
    public boolean isAnonymityLoginEnable();

    /**
     * 启用或者停用匿名登录
     */
    public void enableAnonymityLogin(boolean isEnable);

    /**
     * 根据站点编号和权限编号查询用户信息集合
     *
     * @param logicGroupId 站点编号
     * @param privilegeId  权限编号
     * @return 用户编号集合
     * @author xiedeng
     * @date 2013-5-28
     */
    public List<String> findUserByLogicGroupIdAndPrivilegeId(int logicGroupId, String privilegeId);

    /**
     * 分页查询站点下所有 激活 可用的用户列表
     *
     * @param logicGroupId logicGroup id 编号
     * @return List<User> 用户列表
     * @author 许保吉
     * @date 2013-6-17
     */
    public List<User> findUserLists(int logicGroupId, String userName, int index, int max);

    /**
     * 查询 站点下 已激活的可用 用户的数量
     *
     * @param logicGroupId 站点编号
     * @param userName     用户名（模糊）
     * @return integer 数量
     * @author 许保吉
     * @date 2013-6-18
     */
    public Integer findUserCount(int logicGroupId, String userName);

    /**
     * 查询站点下所有 激活 可用的用户列表
     *
     * @param logicGroupId logicGroup id 编号
     * @return List<User> 用户列表
     * @author 许保吉
     * @date 2013-6-17
     */
    public List<User> findUserLists(int logicGroupId);

    /**
     * 查询部门下所有 激活 可用的用户列表
     *
     * @param departmentId 部门id ，如果部门id 为null 查询站点下所有 用户
     * @return List<User> 用户列表
     * @author 许保吉
     * @date 2013.08.17
     */
    public List<User> findUserListsByDepartment(int logicGroupId, Integer departmentId);

    public List<User> findUserList();
}
