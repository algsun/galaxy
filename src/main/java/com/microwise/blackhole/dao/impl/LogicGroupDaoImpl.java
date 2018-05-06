package com.microwise.blackhole.dao.impl;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Site;
import com.microwise.blackhole.dao.LogicGroupDao;
import com.microwise.blackhole.sys.AreaCodeUtil;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.*;

/**
 * LogicGroupDao的实现
 *
 * @author xubaoji
 * @date 2012-11-20
 * @check 2012-12-13 zhangpeng svn:809
 */
@Dao
@Blackhole
public class LogicGroupDaoImpl extends BlackholeBaseDao<LogicGroup> implements
        LogicGroupDao {

    public LogicGroupDaoImpl() {
        super(LogicGroup.class);
    }

    @Override
    public List<LogicGroup> findAll() {
        String hql = "From LogicGroup l ";
        Query query = getSession().createQuery(hql);

        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Site> findSiteListByAreaCode(int areaCode) {
        List<Integer> allSubordinates = AreaCodeUtil
                .getAllSubordinates(areaCode);
        allSubordinates.add(areaCode);
        String hql = " from Site s where s.areacodeCN.areaCode in :areaCodeList and s.siteId not in (select distinct lg.site.siteId from LogicGroup lg where lg.site.siteId is not null )";
        Query q = getSession().createQuery(hql);
        q.setParameterList("areaCodeList", allSubordinates);
        return q.list();
    }

    @Override
    public void deleteLogicGroup(int logicGroupId) {
        delete(findById(logicGroupId));
    }

    @Override
    public void updateLogicGroup(LogicGroup logicGroup) {
        update(logicGroup);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LogicGroup> findSubLogicGroupList(Integer logicGroupId) {
        String hql = "";
        if (logicGroupId != null) {
            hql = " from LogicGroup  l where  l.parent.id=:logicGroupId";
        } else {
            hql = " from LogicGroup  l where  l.parent.id is null";
        }
        Query q = getSession().createQuery(hql);
        if (logicGroupId != null) {
            q.setParameter("logicGroupId", logicGroupId);
        }
        return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LogicGroup> findSubLogicGroupList(Integer logicGroupId,
                                                  String logicGroupName, int start, int max) {
        String hql = "";
        if (logicGroupId != null) {
            hql = " from LogicGroup lg where lg.parent.id= :logicGroupId ";
        } else {
            hql = " from LogicGroup lg where lg.parent.id=null ";
        }
        if (!Strings.isNullOrEmpty(logicGroupName)) {
            hql += " and lg.logicGroupName like :logicGroupName";
        }
        hql += " order by lg.id desc ";
        Query q = getSession().createQuery(hql);
        if (logicGroupId != null) {
            q.setParameter("logicGroupId", logicGroupId);
        }
        if (StringUtils.isNotEmpty(logicGroupName)) {
            q.setParameter("logicGroupName", "%" + logicGroupName + "%");
        }
        q.setFirstResult((start - 1) * max);
        q.setMaxResults(max);
        return q.list();
    }

    @Override
    public int findSubLogicGroupListCount(Integer logicGroupId,
                                          String logicGroupName) {
        String hql = "";
        if (logicGroupId != null) {
            hql = "select count(lg.id) from LogicGroup lg where lg.parent.id= :logicGroupId ";
        } else {
            hql = "select count(lg.id) from LogicGroup lg where lg.parent.id=null ";
        }
        if (StringUtils.isNotEmpty(logicGroupName)) {
            hql = hql + " and lg.logicGroupName like :logicGroupName";
        }
        Query q = getSession().createQuery(hql);
        if (logicGroupId != null) {
            q.setParameter("logicGroupId", logicGroupId);
        }
        if (StringUtils.isNotEmpty(logicGroupName)) {
            q.setParameter("logicGroupName", "%" + logicGroupName + "%");
        }
        return Integer.parseInt(q.uniqueResult().toString());
    }

    @Override
    public void changeSiteOfLogicGroup(int logicGroupId, String siteId) {
        String hql = "update LogicGroup l set l.site.siteId=? ,l.logicGroupName=(select s.siteName from Site s where s.siteId= ? ) where  l.id=? ";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, siteId);
        q.setParameter(1, siteId);
        q.setParameter(2, logicGroupId);
        q.executeUpdate();
    }

    @Override
    public void changeParentLogicGroup(int logicGroupId,
                                       Integer parentLogicGroupId, List<Integer> userIds) {
        // 更改当前logicGroup的 父站点
        String sql = "";
        if (parentLogicGroupId != null) {
            sql = "update t_logicgroup t set t.parentLogicGroupId=? where t.id= ?";
        } else {
            sql = "update t_logicgroup t set t.parentLogicGroupId=null where t.id= ?";
        }
        SQLQuery sq = getSession().createSQLQuery(sql);
        if (parentLogicGroupId != null) {
            sq.setParameter(0, parentLogicGroupId);
            sq.setParameter(1, logicGroupId);
        } else {
            sq.setParameter(0, logicGroupId);
            sq.executeUpdate();
        }
        sq.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LogicGroup> findUserLogicGroups(int userId) {
        String hql = " Select  u.logicGroups  from User u where  u.id=?";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, userId);
        return q.list();
    }

    @Override
    public LogicGroup findLogicGroupByUserId(int id) {
        String hql = " Select u.logicGroup From User u Where u.id = ? ";
        Query query = getSession().createQuery(hql).setParameter(0, id);
        return (LogicGroup) query.uniqueResult();
    }

    @Override
    public void changeLogicGroupActiveState(int logicGroupId,
                                            Integer activeState) {
        String hql = " update LogicGroup lg set lg.activeState= ? where lg.id= ?";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, activeState);
        q.setParameter(1, logicGroupId);
        q.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LogicGroup> findAdmimLogicGroups(Integer logicGroupId) {
        String hql = "";
        if (logicGroupId != null) {
            hql = " from LogicGroup  l where  l.parent.id=? and l.site is null";
        } else {
            hql = " from LogicGroup  l where  l.parent.id is null and l.site=null ";
        }
        Query q = getSession().createQuery(hql);
        if (logicGroupId != null) {
            q.setParameter(0, logicGroupId);
        }
        return q.list();
    }

    public void clearUserLogicGroups(Integer logicGroupId, List<Integer> userIds) {
        String sql = "delete  from t_user_logicgroup where logicGroupId  = :logicGroupId   ";
        if (userIds.size() > 0) {
            sql += " or userId  in (:userIds) ";
        }
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("logicGroupId", logicGroupId);
        if (userIds.size() > 0) {
            sqlQuery.setParameterList("userIds", userIds);
        }
        sqlQuery.executeUpdate();
    }

    @Override
    public LogicGroup findLogicGroupCarrySite(Integer logicGroupId) {
        String hql = "from LogicGroup lg  left outer join fetch lg.site where lg.id=? ";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, logicGroupId);
        return (LogicGroup) q.uniqueResult();
    }

    @Override
    public List<LogicGroup> findLogicGroupForMap(Set<Integer> set) {
        Iterator<Integer> it = set.iterator();
        Set<Integer> quertSet = new HashSet<Integer>();
        while (it.hasNext()) {
            SQLQuery sql = getSession().createSQLQuery(
                    " SELECT fun_getBaseLogicGroupChildren(:logicGroupId) ");
            sql.setParameter("logicGroupId", it.next());
            String arr[] = String.valueOf(sql.uniqueResult()).split(",");
            List<String> arrList = Arrays.asList(arr);
            List<Integer> ilist = new ArrayList<Integer>();
            for (String s : arrList) {
                ilist.add(Integer.valueOf(s));
            }
            quertSet.addAll(ilist);
        }
        String hql = " select l from LogicGroup as l inner join fetch l.site where l.site is not null and l.id in :set ";
        Query q = getSession().createQuery(hql);
        q.setParameterList("set", quertSet);
        return q.list();
    }

    @Override
    public List<Integer> findUserLogicGroupIds(int userId) {
        String sql = " Select ul.logicGroupId from t_user_logicgroup as ul where ul.userId = :userId ";
        SQLQuery q = getSession().createSQLQuery(sql);
        q.setParameter("userId", userId);
        return q.list();
    }

    @Override
    public List<LogicGroup> findSubLogicGroupsCarrySite(Integer logicGroupId) {
        String hql = "";
        if (logicGroupId != null) {
            hql = " from LogicGroup l left join fetch l.site where  l.parent.id=:logicGroupId";
        } else {
            hql = " from LogicGroup l left join fetch l.site where  l.parent.id is null";
        }
        Query q = getSession().createQuery(hql);
        if (logicGroupId != null) {
            q.setParameter("logicGroupId", logicGroupId);
        }
        return q.list();
    }

    @Override
    public List<LogicGroup> findUserLogicGroupsCarrySite(int userId) {
        String hql = " Select lg from User u inner join u.logicGroups as lg left join fetch lg.site where  u.id=:userId ";
        Query q = getSession().createQuery(hql);
        q.setParameter("userId", userId);
        return q.list();
    }

    @Override
    public Integer findLogicGroupIdBySiteId(String siteId) {
        String sql = "SELECT id FROM t_logicgroup l WHERE l.`siteId` = :siteId ";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("siteId", siteId);
        return (Integer) sqlQuery.uniqueResult();
    }

    @Override
    public void updateLogicGroupTitleImage(String titleImage, int logicGroupId) {
        String hql = " update LogicGroup lg set lg.titleImage= ? where lg.id= ?";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, titleImage);
        q.setParameter(1, logicGroupId);
        q.executeUpdate();
    }

    @Override
    public void updateLogicGroupBgImage(String bgImage, int logicGroupId) {
        String hql = "  update LogicGroup lg set lg.bgImage= ? where lg.id= ?";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, bgImage);
        q.setParameter(1, logicGroupId);
        q.executeUpdate();
    }

    @Override
    public void updateLogicGroupUseTitle(int useTitle, int logicGroupId) {
        String hql = "  update LogicGroup lg set lg.useTitle= ? where lg.id= ?";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, useTitle);
        q.setParameter(1, logicGroupId);
        q.executeUpdate();
    }

    @Override
    public void updateLogicGroupUseBg(int useBg, int logicGroupId) {
        String hql = "  update LogicGroup lg set lg.useBg= ? where lg.id= ?";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, useBg);
        q.setParameter(1, logicGroupId);
        q.executeUpdate();
    }


    @Override
    public String findTemplate(int logicGroupId) {
        String sql = "SELECT th.template FROM t_theme th WHERE  th.logicgroupId = :logicGroupId";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("logicGroupId", logicGroupId);
        return (String) sqlQuery.uniqueResult();
    }

    @Override
    public void insertTemplate(int logicGroupId, String template) {
        String sql = "INSERT  INTO  t_theme  (logicgroupId,template) VALUES(:logicGroupId,:template)";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("logicGroupId", logicGroupId);
        sqlQuery.setParameter("template", template);
        sqlQuery.executeUpdate();
    }

    @Override
    public void updateTemplate(int logicGroupId, String template) {
        String sql = "UPDATE t_theme SET template = :template WHERE logicgroupId  = :logicGroupId";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("logicGroupId", logicGroupId);
        sqlQuery.setParameter("template", template);
        sqlQuery.executeUpdate();
    }

}
