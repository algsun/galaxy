package com.microwise.blackhole.dao.impl;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.dao.UserDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * 用户Dao实现
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-12-13 xubaoji svn:805
 */
@Dao
@Blackhole
public class UserDaoImpl extends BlackholeBaseDao<User> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public void saveUser(User user) {
        save(user);
    }

    @Override
    public void deleteUserById(int userId) {
        User user = findById(userId);
        delete(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findUserIdsByLogicGroupId(int logicGroupId) {
        String hql = " select u.id from User u where u.logicGroup.id = :logicGroupId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("logicGroupId", logicGroupId);
        return query.list();
    }

    @Override
    public void changeUserDisableState(int userId) {
        User user = findById(userId);
        user.setDisable(!user.isDisable());
        update(user);
    }

    @Override
    public void updateUser(int userId, String userName, int sex, String mobile) {
        String hql = " Update User u set u.userName = ?,u.sex = ?,u.mobile = ? Where u.id = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, userName);
        query.setParameter(1, sex);
        query.setParameter(2, mobile);
        query.setParameter(3, userId);
        query.executeUpdate();
    }

    @Override
    public void updateUserById(int userId, int departmentId) {
        // 处理 用户部门 ，如果该部门不存在 添加部门

        String hql = " Update User u set u.department.id = ? Where u.id = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, departmentId);
        query.setParameter(1, userId);
        query.executeUpdate();
    }

    @Override
    public void updateUser(User user) {
        update(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findUserList(int LogicGroupId, String userName, int start, int max) {
        String hql = " select u from User u where u.logicGroup.id = :logicGroupId ";
        if (!Strings.isNullOrEmpty(userName)) {
            hql += " and u.userName like :userName ";
        }
        hql += " order by u.createTime desc ";
        Query query = getSession().createQuery(hql);
        query.setParameter("logicGroupId", LogicGroupId);
        if (!Strings.isNullOrEmpty(userName)) {
            query.setParameter("userName", "%" + userName + "%");
        }
        query.setFirstResult((start - 1) * max);
        query.setMaxResults(max);
        return query.list();
    }

    @Override
    public User findUser(int logicGroupId, int id) {
        String hql = " select u from User u where u.logicGroup.id = :logicGroupId and u.id = :id";
        hql += " order by u.createTime desc ";
        Query query = getSession().createQuery(hql);
        query.setParameter("logicGroupId", logicGroupId);
        query.setParameter("id", id);
        return (User) query.uniqueResult();
    }

    @Override
    public int findUserListCount(int logicGroupId, String userName) {
        String hql = " select count(u.id) from User u where u.logicGroup.id = :logicGroup ";
        if (!Strings.isNullOrEmpty(userName)) {
            hql += " and u.userName like :userName";
        }
        Query query = getSession().createQuery(hql);
        query.setParameter("logicGroup", logicGroupId);
        if (!Strings.isNullOrEmpty(userName)) {
            query.setParameter("userName", "%" + userName + "%");
        }
        return Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public User findManagerByLogicGroupId(int logicGroupId) {
        String sql = " SELECT u.* FROM t_users u LEFT JOIN t_user_role ur ON u.id = ur.userId LEFT JOIN t_roles r ON ur.roleId = r.id WHERE r.isManager = 1 AND u.logicGroupId = :logicGroupId ";
        SQLQuery sQuery = getSession().createSQLQuery(sql).addEntity(User.class);
        sQuery.setParameter("logicGroupId", logicGroupId);
        return (User) sQuery.uniqueResult();
    }

    @Override
    public User findUserById(int userId) {
        return findById(userId);
    }

    @Override
    public User findUserByToken(String token) {
        String hql = " select u from User u where u.token = :token ";
        Query query = getSession().createQuery(hql).setParameter("token", token);
        return (User) query.uniqueResult();
    }

    @Override
    public void updateUserPassword(int userId, String password) {
        String hql = " Update User u set u.password = ?,u.token = null Where u.id = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, password);
        query.setParameter(1, userId);
        query.executeUpdate();
    }

    @Override
    public void resetDefalultSetting(int userId) {
        String hql = " Delete from Individuation i Where i.user.id = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, userId);
        query.executeUpdate();
    }

    @Override
    public User findUserByEmail(String email) {
        String hql = " Select u From User u left outer join fetch  u.department Where u.email = ? ";
        Query query = getSession().createQuery(hql).setParameter(0, email);
        return (User) query.uniqueResult();
    }

    @Override
    public User findActiveUserByEmail(String email) {
        String hql = " Select u From User u Where u.email = ? and u.active = true ";
        Query query = getSession().createQuery(hql).setParameter(0, email);
        return (User) query.uniqueResult();
    }

    @Override
    public void updateUserToken(String token, String email) {
        String hql = " Update User u set u.token = ? Where u.email = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, token);
        query.setParameter(1, email);
        query.executeUpdate();
    }

    @Override
    public void updateUserPhotoById(int id, String photo) {
        String hql = " Update User u set u.photo = ? Where u.id = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, photo);
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public void activeUser(int userId, String userName, String password, int sex, String mobile,
                           boolean active, String token) {
        String hql = " Update User u set u.userName = :userName,u.password = :password,u.sex = :sex,u.mobile = :mobile,u.active = :active,u.token = :token,u.disable = false Where u.id = :userId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("userName", userName);
        query.setParameter("password", password);
        query.setParameter("sex", sex);
        query.setParameter("mobile", mobile);
        query.setParameter("active", active);
        query.setParameter("token", token);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Override
    public User findAnonymity() {
        Query query = getSession().createQuery(
                "select u from User u inner join u.roles as role where role.state = 1");
        query.setMaxResults(1);
        return (User) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findUserByLogicGroupIdAndPrivilegeId(int logicGroupId, String privilegeId) {
        Query query = getSession()
                .createQuery(
                        "select u from User u inner join u.roles as role inner join role.privileges as privilege where privilege.privilegeId = ? and u.logicGroup.id = ?");
        query.setParameter(0, privilegeId);
        query.setParameter(1, logicGroupId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findUserList(int logicGroupId, boolean isActive, boolean isDisable,
                                   String userName, int index, int max) {
        String hql = " from User  u  where u.logicGroup.id = :logicGroupId and u.active = :isActive and u.disable = :isDisable  ";
        if (userName != null && !userName.equals("")) {
            hql = hql + " and u.userName like :userName ";
        }
        Query query = getSession().createQuery(hql);
        query.setParameter("logicGroupId", logicGroupId);
        query.setParameter("isActive", isActive);
        query.setParameter("isDisable", isDisable);
        if (userName != null && !userName.equals("")) {
            query.setParameter("userName", "%" + userName + "%");
        }
        query.setFirstResult((index - 1) * max);
        query.setMaxResults(max);
        return query.list();
    }

    @Override
    public Integer findUserCount(int logicGroupId, boolean isActive, boolean isDisable,
                                 String userName) {
        String hql = "select count(u.id) from User  u  where u.logicGroup.id = :logicGroupId and u.active = :isActive and u.disable = :isDisable  ";
        if (userName != null && !userName.equals("")) {
            hql = hql + " and u.userName like :userName ";
        }
        Query query = getSession().createQuery(hql);
        query.setParameter("logicGroupId", logicGroupId);
        query.setParameter("isActive", isActive);
        query.setParameter("isDisable", isDisable);
        if (userName != null && !userName.equals("")) {
            query.setParameter("userName", "%" + userName + "%");
        }
        return Integer.parseInt(query.uniqueResult().toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findUserList() {
        String hql = " from User  u  where u.active = true ";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findUserLists(int logicGroupId) {
        String hql = " from User  u  where (u.logicGroup.id = :logicGroupId or u.logicGroup.id is null) and u.disable = false  and u.active = true ";
        Query query = getSession().createQuery(hql);
        query.setParameter("logicGroupId", logicGroupId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findUserListsByDepartment(int logicGroupId, Integer departmentId) {
        String hql = " from User  u left outer join fetch  u.department  where u.logicGroup.id = :logicGroupId and u.disable = false  and u.active = true ";
        if (departmentId != null) {
            hql = hql + " and u.department.id = :departmentId";
        }
        Query query = getSession().createQuery(hql);
        query.setParameter("logicGroupId", logicGroupId);
        if (departmentId != null) {
            query.setParameter("departmentId", departmentId);
        }
        return query.list();
    }

    @Override
    public void updateUserDepartment(int userId, Integer departmentId) {
        String sql = " update t_users set departmentId = :departmentId  where id = :userId";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("userId", userId);
        sqlQuery.setParameter("departmentId", departmentId);
        sqlQuery.executeUpdate();
    }
}
