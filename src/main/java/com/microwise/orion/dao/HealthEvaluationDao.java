package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.HealthEvaluation;
import com.microwise.orion.bean.Relic;

import java.util.List;

/**
 * 文物健康评测dao
 *
 * @author bai.weixing
 * @since 2017/7/4.
 */
public interface HealthEvaluationDao extends BaseDao<HealthEvaluation> {
    /**
     * 通过文物id查询文物健康评测
     *
     * @param relicId
     * @return
     */
    public Relic findHealthEvaluations(int relicId);

    /**
     * 根据id查询健康评测
     *
     * @param id
     * @return
     */
    public HealthEvaluation find(int id);


}
