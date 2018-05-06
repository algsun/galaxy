package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RelicLabel;
import com.microwise.orion.dao.RelicLabelDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * 文物标签 Dao 实现
 *
 * @author li.jianfei
 * @date 2014-04-25
 */
@Beans.Dao
@Orion
public class RelicLabelDaoImpl extends OrionBaseDao<RelicLabel> implements RelicLabelDao {

    public RelicLabelDaoImpl() {
        super(RelicLabel.class);
    }

    @Override
    public List<RelicLabel> findRelicLabelList(String siteId) {
        String sql = "SELECT o.* FROM `o_relic_label` o LEFT JOIN `o_relic_label_ref` r ON o.`id` = r.`labelId` LEFT JOIN `o_historical_relic` oh ON oh.id = r.`relicId`\n" +
                " WHERE oh.`siteId` ='" + siteId + "'  GROUP BY o.`labelName` ORDER BY COUNT(o.`labelName`) DESC";
        SQLQuery query = getSession().createSQLQuery(sql);
        return query.addEntity("o", RelicLabel.class).list();
    }

    @Override
    public List<RelicLabel> findRelicLabelListByRelicId(int relicId) {
        String hql = "SELECT t from RelicLabel t join t.relics r where  r.id = :relicId";
        Query query = getSession().createQuery(hql);
        query.setParameter("relicId", relicId);
        return query.list();
    }

    @Override
    public boolean deleteRelicLabel(int relicId, int labelId) {
        try {
            String sql = "delete from o_relic_label_ref where relicId= " + relicId + " and labelId = " + labelId;
            SQLQuery query = getSession().createSQLQuery(sql);
            query.executeUpdate();
        } catch (HibernateException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteRelicLabel(int labelId) {
        try {
            String sql = "delete from o_relic_label where id = " + labelId;
            SQLQuery query = getSession().createSQLQuery(sql);
            query.executeUpdate();
        } catch (HibernateException e) {
            return false;
        }
        return true;
    }

    @Override
    public void addRelicLabel(RelicLabel relicLabel) {
        getSession().save(relicLabel);
    }

    @Override
    public RelicLabel findRelicLabelByLabelName(String labelName) {
        String hql = "SELECT t from RelicLabel t where  t.name = :labelName";
        Query query = getSession().createQuery(hql);
        query.setParameter("labelName", labelName);
        return (RelicLabel) query.uniqueResult();
    }

}
