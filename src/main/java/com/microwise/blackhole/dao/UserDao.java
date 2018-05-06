package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.User;

import java.util.List;

/**
 * 用户Dao
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-12-13 xubaoji svn:805
 */
public interface UserDao {

    /**
     * 添加用户
     *
     * @param user 用户对象
     * @author zhangpeng
     * @date 2012-11-19
     */
    public void saveUser(User user);

    /**
     * 根据用户id删除用户
     *
     * @param userId 用户id
     * @author zhangpeng
     * @date 2012-11-20
     */
    public void deleteUserById(int userId);

    /**
     * 根据站点组id查询用户id列表
     *
     * @param logicGroupId 站点组id
     * @return List<Integer> 用户id列表
     * @author zhangpeng
     * @date 2012-12-04
     */
    public List<Integer> findUserIdsByLogicGroupId(int logicGroupId);

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
     * 修改用户部门
     *
     * @param userId       客户id
     * @param departmentId 部门id
     * @author liuzhu
     * @date 2013-08-19
     */

    public void updateUserById(int userId, int departmentId);

    /**
     * 修改用户信息(有角色)
     *
     * @param user 用户对象
     * @author zhangpeng
     * @date 2012-11-19
     */
    public void updateUser(User user);

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
    public List<User> findUserList(int LogicGroupId, String userName,
                                   int start, int max);

    /**
     * 根据用户名和logicGroup编号查询用户列表
     *
     * @param logicGroupId LogicGroup编号
     * @param id           用户id
     * @return List<User> 用户列表
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
     * 根据email查询用户，用于用户登录
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
     * 根据email保存用户token
     *
     * @param token 邮件找回密码标记
     * @param email 用户邮箱
     * @author zhangpeng
     * @date 2012-11-20
     */
    public void updateUserToken(String token, String email);

    /**
     * 根据用户id更新用户头像图片文件名
     *
     * @param userId 用户id
     * @param photo  用户头像图片文件名
     * @date 2012-11-20
     */
    public void updateUserPhotoById(int userId, String photo);

    /**
     * 根据id激活用户
     *
     * @param userId   用户id
     * @param userName 用户名称
     * @param password 用户密码
     * @param sex      性别
     * @param mobile   用户手机
     * @param active   激活状态
     * @param token    邮箱标识
     * @author zhangpeng
     * @date 2012-12-3
     */
    public void activeUser(int userId, String userName, String password,
                           int sex, String mobile, boolean active, String token);

    /**
     * 返回匿名用户, 如果不存在返回 null
     *
     * @return 匿名用户
     */
    User findAnonymity();

    /**
     * 根据站点编号和权限编号查询用户信息集合
     *
     * @param logicGroupId 站点编号
     * @param privilegeId  权限编号
     * @return 用户信息集合
     * @author xiedeng
     * @date 2013-5-28
     */
    public List<User> findUserByLogicGroupIdAndPrivilegeId(int logicGroupId,
                                                           String privilegeId);

    /**
     * 查询用户列表
     *
     * @param logicGroupId logicGroup id 编号
     * @param isActive     是否 可用
     * @param isDisable    是否激活
     * @return List<User> 用户列表
     * @author 许保吉
     * @date 2013-6-17
     */
    public List<User> findUserList(int logicGroupId, boolean isActive,
                                   boolean isDisable, String userName, int index, int max);

    /**
     * 查询用户数量
     *
     * @param logicGroupId logicGroup id 编号
     * @param isActive     是否 可用
     * @param isDisable    是否激活
     * @return List<User> 用户列表
     * @author 许保吉
     * @date 2013-6-17
     */
    public Integer findUserCount(int logicGroupId, boolean isActive,
                                 boolean isDisable, String userName);

    public List<User> findUserList();
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
     * 修改用户对应部门
     *
     * @param userId       用户id
     * @param departmentId 部门编号
     * @author xu.baoji
     * @date 2013-8-17
     */
    public void updateUserDepartment(int userId, Integer departmentId);

    /**
     * 查询部门下所有 激活 可用的用户列表
     *
     * @param departmentId 部门id
     * @return List<User> 用户列表
     * @author 许保吉
     * @date 2013.08.17
     */
    public List<User> findUserListsByDepartment(int logicGroupId, Integer departmentId);
}
