package com.microwise.orion.service;

import com.microwise.orion.bean.HealthEvaluation;
import com.microwise.orion.bean.Relic;


/**
 * 文物健康评测service
 *
 * @author bai.weixing
 * @since 2017/7/4
 */
public interface HealthEvaluationService {

    /**
     * 根据文物id查询文物健康评测列表
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

    /**
     * 根据id删除文物健康评测
     *
     * @param healthEvaluation
     */
    public void  delete(HealthEvaluation healthEvaluation);

    /**
     * 保存文物健康评测
     *
     * @param healthEvaluation
     */
    public void save(HealthEvaluation healthEvaluation);
}
