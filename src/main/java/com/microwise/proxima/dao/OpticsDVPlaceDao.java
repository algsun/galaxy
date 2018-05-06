package com.microwise.proxima.dao;

import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.dao.base.BaseDao;

import java.util.List;

/**
 * 光学摄像机 dao
 * 
 * @author gaohui
 * @date 2012-7-10
 * 
 * @check zhang.licong 2012-07-14
 */
public interface OpticsDVPlaceDao extends BaseDao<OpticsDVPlaceBean> {

	/**
	 * <pre>
	 * 查询所有的光学摄像机. 注意如果没有返回空集合
	 * 
	 * @param siteId 站点id
	 * 
	 * @return List<OpticsDVPlaceBean> 光学摄像机集合
	 * </pre>
	 */
	public List<OpticsDVPlaceBean> findAll(String siteId);

	/**
	 * <pre>
	 * 查询所有的光学摄像机. 注意如果没有返回空集合
	 * 
	 * @param siteId 站点id
	 * @param pageNmb 当前页码
	 * @param pageSize 每页最大条数
	 * 
	 * @return List<OpticsDVPlaceBean> 光学摄像机集合
	 * </pre>
	 */
	public List<OpticsDVPlaceBean> findAll(String siteId, int pageNmb,
			int pageSize);

	/**
	 * <pre>
	 * 查询站点下所有的光学摄像机数量
	 * 
	 * @param siteId 站点id
	 * </pre>
	 * 
	 * @author zhangpeng
	 * @date 2013-3-22
	 * 
	 * @return int 站点下所有的光学摄像机数量
	 */
	public int findAllCount(String siteId);

	/**
	 * <pre>
	 * 查询区域下所有的光学摄像机，无分页
	 * 
	 * @param zoneId 区域id
	 * 
	 * @author zhangpeng
	 * @date 2012-7-11
	 * 
	 * @return List<OpticsDVPlaceBean> 查询出的光学摄像机点位列表
	 * </pre>
	 */
	public List<OpticsDVPlaceBean> findByZoneId(String zoneId);

	/**
	 * <pre>
	 * 查询区域下所有的光学摄像机
	 * 
	 * @param zoneId 区域id
	 * @param pageNmb 当前页码
	 * @param pageSize 每页最大条数
	 * </pre>
	 * 
	 * @author zhangpeng
	 * @date 2012-7-11
	 * 
	 * @return List<OpticsDVPlaceBean> 查询出的光学摄像机点位列表
	 */
	public List<OpticsDVPlaceBean> findByZoneId(String zoneId, int pageNmb,
			int pageSize);

	/**
	 * <pre>
	 * 查询区域下所有的光学摄像机数量
	 * 
	 * @param zoneId 区域id
	 * </pre>
	 * 
	 * @author zhangpeng
	 * @date 2013-3-22
	 * 
	 * @return int 区域下所有的光学摄像机数量
	 */
	public int findByZoneIdCount(String zoneId);

	/**
	 * <pre>
	 * 根据id获取光学摄像机点位信息
	 * 
	 * @param id 光学摄像机点位信息对象id
	 * 
	 * @author zhangpeng
	 * @date 2012-7-11
	 * 
	 * @return InfraredDVPlaceBean 要查询的光学摄像机点位信息对象
	 * </pre>
	 */
	public OpticsDVPlaceBean findById(String id);

	/**
	 * <p>
	 * TODO 未验证
	 * </p>
	 * 查询所有启用的光学摄像机. 注意如果没有返回空集合
	 * 
	 * @return
	 */
	public List<OpticsDVPlaceBean> findAllEnable();

	/**
	 * <p>
	 * TODO 未验证
	 * </p>
	 * 根据监测点ID查询启用的光学摄像机
	 * 
	 * @return
	 * @author JinGang
	 * @date 2012-9-13
	 */
	public List<OpticsDVPlaceBean> findByMonitorPointId(int monitorPointId);

	/**
	 * <p>
	 * TODO 未验证
	 * </p>
	 * 返回所有的光学摄像机点位（带有监测点）. 默认按创建时间降序排列
	 * 
	 * @return
	 * @author li.jianfei
	 * @date 2012-09-13
	 */
	public List<OpticsDVPlaceBean> findAllWithMonitorPoint();

	/**
	 * <pre>
	 * 查询io端口是否占用，添加时
	 * 
	 * @param ioPort io端口
	 * 
	 * @author zhangpeng
	 * @date 2013-3-22
	 * 
	 * @return int 区域下的光学摄像机对象
	 * </pre>
	 */
	public boolean isIoPortUsingByAdd(int ioPort);

	/**
	 * <pre>
	 * 查询io端口是否占用，更新时
	 * 
	 * @param dvPlaceId 摄像机点位id
	 * @param ioPort io端口
	 * 
	 * @author zhangpeng
	 * @date 2013-3-22
	 * 
	 * @return int 区域下的光学摄像机对象
	 * </pre>
	 */
	public boolean isIoPortUsingByUpdate(String dvPlaceId, int ioPort);

}
