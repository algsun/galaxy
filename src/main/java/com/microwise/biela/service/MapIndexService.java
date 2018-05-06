package com.microwise.biela.service;

import com.microwise.biela.bean.po.*;
import com.microwise.biela.bean.vo.CustomizeVO;
import com.microwise.biela.bean.vo.LocationInfoVO;
import com.microwise.biela.bean.vo.NodeSensorInfoVO;

import java.util.List;
import java.util.Map;

/**
 * 站点地图service
 *
 * @author liuzhu
 * @date 14-1-2
 * @check @wang.geng 2014-1-17 #7693
 */
public interface MapIndexService {

    /**
     * 根据站点ID获取区域对象
     *
     * @param siteId 站点ID
     * @return AreaCodePO
     * @author wang.geng
     * @date 2014-3-20
     */
    public AreaCodePO findAreaCodePOBySiteId(String siteId);

    /**
     * 获取当前站点及子站点信息
     *
     * @param logicGroupId
     * @param userId       用户id
     * @return sitePOList 站点相关信息
     * @author liuzhu
     * @date 2013-1-3
     */
    public List<SitePO> findSitePOList(Integer logicGroupId, int userId);

    /**
     * 根据站点，获取站点下所有监测指标
     *
     * @param siteId
     * @return sensorList 监测指标集合
     * @author liuzhu
     * @date 2014-1-7
     */
    public List<SensorInfoPO> findSensorInfo(String siteId);

    /**
     * 获取站点检测指标平均值
     *
     * @return 平均值对象List
     * @author wang.geng
     * @date 2014-1-11
     */
    public List<LocationInfoVO> findGeneralLocationInfoBySite(String siteId);

    /**
     * 根据站点名称获取站点组VO
     *
     * @param siteId 站点id
     * @return 站点VO
     * @author liuzhu
     * @date 2013-1-9
     */
    public LogicGroupPO findLogicGroupBySiteId(String siteId);

    /**
     * 根据站点id，监测指标id获取区域设备PO
     *
     * @param siteId   站点id
     * @param sensorId 监测指标id
     * @return 设备区域PO集合
     * @author liuzhu
     * @date 2013-1-9
     */
    public List<ZoneLocationPO> findZoneLocationBySiteIdSensorId(String siteId, Integer sensorId);

    /**
     * 保存定制信息
     *
     * @param siteId          站点id
     * @param locationId      位置点id
     * @param sensorId        监测指标id
     * @param customizeRemark 监测指标定制
     * @return void
     * @author liuzhu
     * @date 2014-1-9
     */
    public void saveCustomize(String siteId, String locationId, Integer sensorId, String customizeRemark);

    /**
     * 根据siteId获取定制信息
     *
     * @param siteId 站点组id
     * @return CustomizeVO 集合
     * @author liuzhu
     * @date 2014-1-10
     */
    public List<CustomizeVO> findCustomizeVOList(String siteId);

    /**
     * 根据id删除一个定制信息
     *
     * @param id
     * @author liuzhu
     * @date 2014-1-10
     */
    public void deleteCustomizeById(Integer id);

    /**
     * 验证该监测指标是否已经定制
     *
     * @param siteId     站点id
     * @param locationId 位置点id
     * @param sensorId   监测指标id
     * @author liuzhu
     * @date 2014-1-11
     */
    public Integer verifyCustomize(String siteId, String locationId, Integer sensorId);

    /**
     * ajax查看定制监测指标的数量
     *
     * @param siteId 站点id
     * @return 数量
     * @author liuzhu
     * @date 2014-1-11
     */
    public Integer customizeCount(String siteId);

    /**
     * 查询指点监测指标的各站点的实时数据的平均值
     *
     * @param sensorIds 指定的监测指标
     * @return 结果对象集
     * @author 王耕
     * @date 2014-11-6
     */
    public Map<String, List<NodeSensorInfoVO>> findSiteRealtimeAvg(List<Integer> sensorIds, int logicGroupId);
}

