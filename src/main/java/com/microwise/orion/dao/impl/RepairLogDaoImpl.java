package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairLog;
import com.microwise.orion.dao.RepairLogDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * @author 王耕
 * @date 15-10-9
 */
@Beans.Dao
@Orion
public class RepairLogDaoImpl extends OrionBaseDao<RepairLog> implements RepairLogDao {

    public RepairLogDaoImpl(){
        super(RepairLog.class);
    }

    @Override
    public List<RepairLog> findRepairLogs(int repairRecordId) {
        String hql = "from RepairLog rl where rl.repairRecord.id = :repairRecordId";
        Query query = getSession().createQuery(hql);
        query.setParameter("repairRecordId",repairRecordId);
        return query.list();
    }

    @Override
    public void saveRepairLog(RepairLog repairLog) {
        getSession().saveOrUpdate(repairLog);
    }
}
