/**
 *
 */
package com.microwise.proxima.dao;

import com.microwise.proxima.bean.InfraredDVPlaceBean;
import com.microwise.proxima.dao.base.BaseDao;

import java.util.List;

/**
 * <pre>
 * 红外热像仪点位信息数据库访问层，继承BaseDao
 * </pre>
 *
 * @author zhangpeng
 * @date 2012-7-9
 * @check zhang.licong 2012-07-14
 */
public interface InfraredDVPlaceDao extends BaseDao<InfraredDVPlaceBean> {

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 获取红外热像仪列表
     *
     * @return 获取的红外热像仪的List
     * @author zhangpeng
     * @date 2012-7-11
     */
    public List<InfraredDVPlaceBean> findAll(String siteId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 获取红外热像仪列表
     *
     * @return 获取的红外热像仪的List
     * @author liuzhu
     * @date 2014-7-31
     */
    public List<InfraredDVPlaceBean> findAllByZoneId(String zoneId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 获取开启的红外热像仪
     *
     * @param enable 开启状态
     * @return 开启的红外热像仪List
     * @author zhangpeng
     * @date 2012-7-11
     */
    public List<InfraredDVPlaceBean> findAllEnable(boolean enable);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 返回所有的红外摄像机点位（带有监测点）. 默认按创建时间降序排列
     *
     * @return
     * @author gaohui
     * @date 2012-09-06 AM
     */
    public List<InfraredDVPlaceBean> findAllWithMonitorPoint();

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 根据监测点ID查询启用的红外摄像机
     *
     * @param monitorPointId 监测点ID
     * @return 监测点下的所有红外摄像机集合
     * @author JinGang
     * @date 2012-09-11
     */
    public List<InfraredDVPlaceBean> findByMonitorPointId(int monitorPointId);

    /**
     * 根据id获取红外热像仪点位信息
     *
     * @param id 红外热像仪点位信息对象id
     * @return InfraredDVPlaceBean 要查询的红外热像仪点位信息对象
     * @author zhangpeng
     * @date 2012-7-11
     */
    public InfraredDVPlaceBean findById(String id);
}
