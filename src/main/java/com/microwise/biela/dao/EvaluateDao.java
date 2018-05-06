package com.microwise.biela.dao;

import com.microwise.biela.bean.vo.ComplianceRateVO;
import com.microwise.biela.bean.vo.LocationSensorVO;
import com.microwise.biela.bean.vo.MixtureVO;

import java.util.Date;
import java.util.List;

/**
 * Evaluate dao接口
 *
 * @author liuzhu
 * @date 2014-11-21
 */
public interface EvaluateDao {
    /**
     * 查询达标率
     *
     * @param siteId 站点id
     * @param date   时间
     * @return 达标率vo
     */
    public List<ComplianceRateVO> findComplianceRates(String siteId, Date date);

    /**
     * 查询平均数据
     *
     * @param siteId   站点id
     * @param date     时间
     * @param sensorId 监测指标id
     * @return NodeSensor集合
     */
    public List<LocationSensorVO> findAvgPeak(String siteId, Date date, int sensorId);

    /**
     * 查询混合数据（全国及区域中心首页）
     *
     * @param siteId 站点id
     * @param date   时间
     * @return Mixture集合
     */
    public List<MixtureVO> findMixture(String siteId, Date date);

    /**
     * 查询温湿度波动率
     *
     * @param siteLogicId 站点组id
     * @param date        时间
     * @return comRate集合
     */
    public List<ComplianceRateVO> findComRate(int siteLogicId, Date date);
}
