package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.AlarmRecordVO;
import com.microwise.blueplanet.bean.vo.MeasureVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;

import java.util.Date;
import java.util.List;

/**
 * 监测预警service
 *
 * @author liuzhu
 * @date 14-3-11
 */
public interface AlarmService {

    /**
     * 添加措施
     *
     * @param measureVO 措施vo
     * @return void
     * @author liuzhu
     * @date 2014-3-11
     */
    public void addMeasure(MeasureVO measureVO);

    /**
     * 根据站点查措施
     *
     * @param siteId 站点id
     * @return 措施集合
     * @author liuzhu
     * @date 2014-3-13
     */
    public List<MeasureVO> findMeasureList(String siteId);

    /**
     * 根据id查措施
     *
     * @param id 措施id
     * @return 措施vo
     * @author liuzhu
     * @date 2011-3-13
     */
    public MeasureVO findMeasureVOById(String id);

    /**
     * 修改措施
     *
     * @param measureVO
     * @author liuzhu
     * @date 2014-3-13
     */
    public void updateMeasure(MeasureVO measureVO);

    /**
     * 删除措施
     *
     * @param id 措施id
     * @author liuzhu
     * @date 2014-3-13
     */
    public void deleteMeasure(String id);

    /**
     * 根据站点id,父区域id查找区域绑定的措施
     *
     * @param siteId       站点id
     * @param parentZoneId 区域id
     * @return
     */
    public List<ZoneVO> findZoneMeasure(String siteId, String parentZoneId);


    /**
     * 分页条件查询所有的报警记录
     *
     * @param sensorId  监测指标ID
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @param index     当前页
     * @param size      分页单位
     * @param siteId    站点编号
     * @return List<AlarmRecordVO> 分页结果集合
     * @author wang.geng
     * @date 2014-3-17
     */
    public List<AlarmRecordVO> findRecordVOByPages(Integer sensorId,
                                                   Date startTime, Date endTime, Integer index,
                                                   Integer size, String siteId);

    /**
     * 条件查询所有的报警记录数目
     *
     * @param sensorId  监测指标ID
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @param siteId    站点编号
     * @return Integer 对象数目
     * @author wang.geng
     * @date 2014-3-17
     */
    public Integer findRecordVOCount(Integer sensorId,
                                     Date startTime, Date endTime, String siteId);


//    /**
//     * 查询措施是否有绑定
//     *
//     * @param measureId 措施id
//     * @return true 绑定 false未绑定
//     * @author liuzhu
//     * @date 2014-3-20
//     */
//    public boolean hasZoneMeasureId(String measureId);

    /**
     * 查询报警记录
     *
     * @param zoneId 区域id
     * @return
     */
    public List<AlarmRecordVO> findAlarmRecordList(String zoneId);


    /**
     * 查询最近的报警信息
     *
     * @param locationId 位置点ID
     * @return
     */
    public AlarmRecordVO findRecentAlarmRecord(String locationId);
}
