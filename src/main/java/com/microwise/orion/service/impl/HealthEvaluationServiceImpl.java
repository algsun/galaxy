package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.HealthEvaluation;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.StatusQuo;
import com.microwise.orion.dao.HealthEvaluationDao;
import com.microwise.orion.dao.RelicPropertyDao;
import com.microwise.orion.dao.StatusQuoDao;
import com.microwise.orion.service.HealthEvaluationService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文物健康评测ServiceImpl
 *
 * @author bai.weixing
 * @since 2017/7/4.
 */
@Beans.Service
@Orion
@Transactional
public class HealthEvaluationServiceImpl implements HealthEvaluationService {

    @Autowired
    private HealthEvaluationDao healthEvaluationDao;

    @Autowired
    private RelicPropertyDao relicPropertyDao;

    @Autowired
    private StatusQuoDao statusQuoDao;

    @Override
    public Relic findHealthEvaluations(int relicId) {
        Relic relic = healthEvaluationDao.findHealthEvaluations(relicId);
        // 查询文物档案属性列表
        if (relic != null) {
            relic.setRelicPropertielist(relicPropertyDao.findRelicPropertyList(
                    relic.getId(), Relic.RELIC_PROPERTY_ARCHIVES,
                    Relic.RELIC_PROPERTY_ARCHIVES_AND_CARD));
        }
        return relic;
    }

    @Override
    public HealthEvaluation find(int id) {
        HealthEvaluation healthEvaluation = healthEvaluationDao.find(id);
        Relic relic = healthEvaluation.getRelic();
        // 查询文物档案属性列表
        if (relic != null) {
            relic.setRelicPropertielist(relicPropertyDao.findRelicPropertyList(
                    relic.getId(), Relic.RELIC_PROPERTY_ARCHIVES,
                    Relic.RELIC_PROPERTY_ARCHIVES_AND_CARD));
        }
        return healthEvaluation;
    }

    @Override
    public void delete(HealthEvaluation healthEvaluation) {
        healthEvaluationDao.delete(healthEvaluation);
        StatusQuo statusQuo = new StatusQuo();
        statusQuo.setId(healthEvaluation.getStatusQuoId());
        statusQuoDao.delete(statusQuo);
    }

    @Override
    public void save(HealthEvaluation healthEvaluation) {
        healthEvaluationDao.save(healthEvaluation);
    }
}
