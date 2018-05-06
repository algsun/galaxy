package com.microwise.proxima.service;

import com.microwise.proxima.bean.InfraredDVPlaceBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * 红外热像仪点位信息服务层
 * </pre>
 *
 * @author zhangpeng
 * @date 2012-7-9
 * @check zhang.licong 2012-07-14
 * @check li.jianfei liu.zhu 2014-4-15 #8281
 */
@Transactional
public interface InfraredDVPlaceService {

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 添加红外热像仪点位信息
     *
     * @param infraredDVPlace 红外热像仪点位信息对象
     * @author zhangpeng
     * @date 2012-7-11
     */
    public void save(InfraredDVPlaceBean infraredDVPlace);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 修改红外热像仪点位信息
     *
     * @param infraredDVPlace 红外热像仪点位信息对象
     * @author zhangpeng
     * @date 2012-7-11
     */
    public void update(InfraredDVPlaceBean infraredDVPlace);

    /**
     * 根据id获取红外热像仪点位信息
     *
     * @param id 红外热像仪点位信息对象id
     * @return InfraredDVPlaceBean 要查询的红外热像仪点位信息对象
     * @author zhangpeng
     * @date 2012-7-11
     */
    public InfraredDVPlaceBean findById(String id);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 根据id获取红外热像仪点位信息
     *
     * @param id 红外热像仪点位信息对象id
     * @return InfraredDVPlaceBean 要查询的红外热像仪点位信息对象
     * @author zhangpeng
     * @date 2012-7-11
     */
    public InfraredDVPlaceBean findById(int id);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 获取红外热像仪点位信息列表
     *
     * @return List<InfraredDVPlaceBean> 封装着红外热像仪点位信息对象的List
     * @author zhangpeng
     * @date 2012-7-11
     */
    public List<InfraredDVPlaceBean> findAll(String siteId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 获取红外热像仪点位信息列表
     *
     * @return List<InfraredDVPlaceBean> 封装着红外热像仪点位信息对象的List
     * @author liuzhu
     * @date 2014-7-31
     */
    public List<InfraredDVPlaceBean> findAllByZoneId(String zoneId);


    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 根据监测点ID查询启用的红外摄像机
     *
     * @param monitorPointId 监测点ID
     * @return 监测点下的所有红外摄像机集合
     * @author JinGang
     * @date 2012-09-11
     */
    public List<InfraredDVPlaceBean> findByMonitorPointId(int monitorPointId);


}
