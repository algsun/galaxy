package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.InventoryZone;
import com.microwise.orion.dao.InventoryZoneDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import com.microwise.orion.vo.InventoryZoneVo;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.List;

/**
 * 盘点 按区域 统计信息 dao
 * 
 * @author xubaoji
 * @date 2013-6-3
 *
 * @check 2013-06-05 zhangpeng svn:4010
 */
@Orion
@Dao
public class InventoryZoneDaoImpl extends OrionBaseDao<InventoryZone> implements
		InventoryZoneDao {

	public InventoryZoneDaoImpl() {
		super(InventoryZone.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryZone> findCountList(String siteId) {
		String sql = "SELECT count(r.id) as bigCountNo , zoneId ,SUM(r.count) as bigSumNo FROM o_historical_relic r  WHERE siteId = :siteId and r.is_canceled=0 GROUP BY  zoneId ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("siteId", siteId);
		sqlQuery.setResultTransformer(Transformers
				.aliasToBean(InventoryZone.class));
		return sqlQuery.list();
	}

    @Override
    public List<InventoryZoneVo> findRealtimeCountList(String siteId) {
        String sql =
                "SELECT                           " +
                "  count(r.id) as bigCountNo,     " +
                "  r.zoneId,                      " +
                "  SUM(r.count) as bigSumNo,      " +
                "  tz.`zoneName`                  " +
                "FROM                             " +
                "  o_historical_relic r           " +
                "  left join t_zone tz            " +
                "  on r.`zoneId` = tz.`zoneId`    " +
                "WHERE r.siteId = :siteId         " +
                "  and r.is_canceled = 0          " +
                "GROUP BY r.zoneId                ";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("siteId", siteId);
        sqlQuery.setResultTransformer(Transformers
                .aliasToBean(InventoryZoneVo.class));
        return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<InventoryZone> findErrorList(Integer inventoryId, String siteId) {
		String sql = " SELECT count(r.id) as bigCountNo , zoneId ,SUM(r.count) as bigSumNo FROM o_historical_relic r  LEFT JOIN o_inventory_error ie ON r.id = ie.relicId  WHERE inventoryId = :inventoryId  AND r.siteId = :siteId and r.is_canceled=0 GROUP BY r.zoneId ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("siteId", siteId);
		sqlQuery.setParameter("inventoryId", inventoryId);
		sqlQuery.setResultTransformer(Transformers
				.aliasToBean(InventoryZone.class));
		return sqlQuery.list();
	}

    @Override
    public List<InventoryZoneVo> findRealtimeErrorList(String siteId) {
        String sql =
                "SELECT COUNT(tab.id) AS bigCountNo,tab.zoneId,SUM(tab.count) AS bigSumNo,tab.zoneName FROM              " +
                "(                                                                                                                                    " +
                "	SELECT r.id,r.zoneId,r.count,tz.zoneName                                " +
                "		FROM o_historical_relic r LEFT JOIN t_zone tz ON r.`zoneId` = tz.`zoneId` INNER JOIN o_inventory_tag  it ON r.id = it.relicId " +
                "		WHERE  r.state = 2 AND r.hasTag = TRUE AND r.siteId = :siteId and r.is_canceled=0                                                                " +
                "	UNION ALL                                                                                                                         " +
                "	SELECT r.id,r.zoneId,r.count,tz.zoneName                                " +
                "		FROM o_historical_relic r LEFT JOIN t_zone tz ON r.`zoneId` = tz.`zoneId` LEFT JOIN o_inventory_tag  it ON r.id = it.relicId  " +
                "		WHERE r.hasTag = TRUE AND r.state IN (0, 1, 3)  AND r.siteId = :siteId and r.is_canceled=0 AND  it.relicId IS NULL                                    " +
                ") tab                                                                                                                                " +
                "GROUP BY tab.zoneId                                                                                                                  ";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("siteId", siteId);
        sqlQuery.setResultTransformer(Transformers
                .aliasToBean(InventoryZoneVo.class));
        return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<InventoryZone> findScanList(Integer inventoryId, String siteId) {
		String sql = " SELECT COUNT(r.id) AS bigCountNo , zoneId ,SUM(r.count) AS bigSumNo FROM o_historical_relic r  LEFT JOIN o_inventory_tag it ON r.id = it.relicId  WHERE inventoryId = :inventoryId  AND r.siteId = :siteId and r.is_canceled=0 GROUP BY r.zoneId ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("siteId", siteId);
		sqlQuery.setParameter("inventoryId", inventoryId);
		sqlQuery.setResultTransformer(Transformers
				.aliasToBean(InventoryZone.class));
		return sqlQuery.list();
	}

    @Override
    public List<InventoryZoneVo> findRealtimeScanList(Integer inventoryId, String siteId) {
        String sql =
                        "SELECT                              " +
                        "  count(r.id) as bigCountNo,        " +
                        "  r.zoneId,                         " +
                        "  SUM(r.count) as bigSumNo,         " +
                        "  tz.`zoneName`                     " +
                        "FROM                                " +
                        "  o_historical_relic r              " +
                        "  left join t_zone tz               " +
                        "  on r.`zoneId` = tz.`zoneId`       " +
                        "  LEFT JOIN o_inventory_tag it      " +
                        "    ON r.id = it.relicId            " +
                        "WHERE it.inventoryId = :inventoryId " +
                        "  AND r.siteId = :siteId            " +
                        "  and r.is_canceled = 0          " +
                        "GROUP BY r.zoneId                   ";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("siteId", siteId);
        sqlQuery.setParameter("inventoryId", inventoryId);
        sqlQuery.setResultTransformer(Transformers
                .aliasToBean(InventoryZoneVo.class));
        return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<InventoryZone> findStockList(boolean isOut, String siteId) {
		StringBuilder sql = new StringBuilder(
				" SELECT COUNT(r.id) AS bigCountNo , zoneId ,SUM(r.count) AS bigSumNo FROM o_historical_relic r WHERE r.siteId = :siteId and r.is_canceled=0");
		if (isOut) {
			sql.append("  and r.state = 2");
		} else {
			sql.append("  and r.state  in (0,1)");
		}
		sql.append(" GROUP BY r.zoneId  ");
		SQLQuery sqlQuery = getSession().createSQLQuery(sql.toString());
		sqlQuery.setParameter("siteId", siteId);
		sqlQuery.setResultTransformer(Transformers
				.aliasToBean(InventoryZone.class));
		return sqlQuery.list();
	}

    @Override
    public List<InventoryZoneVo> findRealtimeStockList(boolean isOut, String siteId) {
        StringBuilder sql = new StringBuilder(
                " SELECT COUNT(r.id) AS bigCountNo ,r.zoneId ,SUM(r.count) AS bigSumNo,tz.`zoneName` FROM o_historical_relic r LEFT JOIN t_zone tz ON r.`zoneId` = tz.`zoneId` WHERE r.siteId = :siteId and r.is_canceled=0");
        if (isOut) {
            sql.append("  and r.state = 2");
        } else {
            sql.append("  and r.state  in (0,1)");
        }
        sql.append(" GROUP BY r.zoneId  ");
        SQLQuery sqlQuery = getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("siteId", siteId);
        sqlQuery.setResultTransformer(Transformers
                .aliasToBean(InventoryZoneVo.class));
        return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<InventoryZone> findTagList(String siteId) {
		String sql = "SELECT count(r.id) as bigCountNo , zoneId ,SUM(r.count) as bigSumNo FROM o_historical_relic r  WHERE siteId = :siteId and r.hasTag =  1 and r.is_canceled=0 GROUP BY  zoneId ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("siteId", siteId);
		sqlQuery.setResultTransformer(Transformers
				.aliasToBean(InventoryZone.class));
		return sqlQuery.list();
	}

    @Override
    public List<InventoryZoneVo> findRealtimeTagList(String siteId) {
        String sql = "SELECT count(r.id) as bigCountNo , r.zoneId ,SUM(r.count) as bigSumNo,tz.`zoneName` FROM o_historical_relic r LEFT JOIN t_zone tz ON r.`zoneId` = tz.`zoneId`  WHERE r.siteId = :siteId and r.hasTag =  1 and r.is_canceled=0 GROUP BY  r.zoneId ";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("siteId", siteId);
        sqlQuery.setResultTransformer(Transformers
                .aliasToBean(InventoryZoneVo.class));
        return sqlQuery.list();
    }

    public void addInventoryZone(Integer inventoryId, Integer statisticsType,
			List<InventoryZone> inventoryZones) {
		String sql = " insert into  o_inventory_zone  set statisticsType = :statisticsType, inventoryId = :inventoryId , zoneId = :zoneId ,countNo = :countNo , sumNo = :sumNo ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("inventoryId", inventoryId);
		sqlQuery.setParameter("statisticsType", statisticsType);
		if (inventoryZones != null && inventoryZones.size() > 0) {
			for (InventoryZone inventoryZone : inventoryZones) {
				sqlQuery.setParameter("zoneId", inventoryZone.getZoneId());
				sqlQuery.setParameter("countNo", inventoryZone.getCountNo());
				sqlQuery.setParameter("sumNo", inventoryZone.getSumNo());
				sqlQuery.executeUpdate();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryZoneVo> findAll(Integer inventoryId) {
		String  sql = " SELECT   oz.* ,tz.zoneName FROM  o_inventory_zone  oz  LEFT JOIN t_zone  tz ON oz.zoneId = tz.zoneId  WHERE oz.inventoryId = :inventoryId ";
		SQLQuery sqlQuery =  getSession().createSQLQuery(sql);
		sqlQuery.setParameter("inventoryId", inventoryId);
		sqlQuery.setResultTransformer(Transformers
				.aliasToBean(InventoryZoneVo.class));
		return sqlQuery.list();
	}
	
}
