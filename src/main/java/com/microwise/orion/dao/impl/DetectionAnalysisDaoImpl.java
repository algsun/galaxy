package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.DetectionAnalysis;
import com.microwise.orion.dao.DetectionAnalysisDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.List;

/**
 * 文物检测分析
 *
 * @author 王耕
 * @date 15-9-29
 */
@Beans.Dao
@Orion
public class DetectionAnalysisDaoImpl extends OrionBaseDao<DetectionAnalysis> implements
        DetectionAnalysisDao {
    public DetectionAnalysisDaoImpl() {
        super(DetectionAnalysis.class);
    }

    @Override
    public void saveOrUpdate(DetectionAnalysis detectionAnalysis) {
        getSession().saveOrUpdate(detectionAnalysis);
    }

    @Override
    public List<DetectionAnalysis> findByRepairRecordId(int repairRecordId) {
        String hql = "from DetectionAnalysis d where d.repairRecord.id = :repairRecordId";
        Query query = getSession().createQuery(hql);
        query.setParameter("repairRecordId",repairRecordId);
        return query.list();
    }

    @Override
    public void deleteDetectionAnalysis(int analysisNum) {
        DetectionAnalysis d =  new DetectionAnalysis();
        d.setAnalysisNum(analysisNum);
        super.delete(d);
    }
}
