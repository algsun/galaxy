package com.microwise.orion.service;

import com.microwise.orion.bean.DetectionAnalysis;

import java.util.List;

/**
 * 文物检测分析service
 *
 * @author 王耕
 * @date 15-9-29
 */
public interface DetectionAnalysisService {
    /**
     * 保存文物检测分析
     *
     * @param detectionAnalysis 检测分析实体
     */
    public void saveOrUpdate(DetectionAnalysis detectionAnalysis);

    /**
     * 查询修复记录的所有检测记录
     *
     * @param repairRecordId 修复记录ID
     * @return 检测记录集合
     */
    public List<DetectionAnalysis> findByRepairRecordId(int repairRecordId);

    /**
     * 根据id删除文物检测
     * @param analysisNum
     */
    public void deleteDetectionAnalysis(int analysisNum);
}
