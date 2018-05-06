package com.microwise.proxima.service;

import com.microwise.proxima.bean.OpticsDVPlaceBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 光学摄像机 service
 * 
 * @author gaohui
 * @date 2012-7-9
 * 
 * @check zhang.licong 2012-07-14
 */
@Transactional
public interface OpticsDVPlaceService {

	/**
	 * <pre>
	 * 查询站点下所有的光学摄像机，要携带区域信息，不分页
	 * 
	 * @param siteId 站点id
	 * 
	 * @author zhangpeng
	 * @date 2012-7-11
	 * 
	 * @return List<OpticsDVPlaceBean> 查询出的光学摄像机点位列表
	 * </pre>
	 */
	public List<OpticsDVPlaceBean> findAll(String siteId);

	/**
	 * <pre>
	 * 查询站点下所有的光学摄像机，要携带区域信息，分页
	 * 
	 * @param siteId 站点id
	 * @param pageNmb 当前页码
	 * @param pageSize 每页最大条数
	 * 
	 * @author zhangpeng
	 * @date 2012-7-11
	 * 
	 * @return List<OpticsDVPlaceBean> 查询出的光学摄像机点位列表
	 * </pre>
	 */
	public List<OpticsDVPlaceBean> findAll(String siteId, int pageNmb,
			int pageSize);

	/**
	 * <pre>
	 * 查询站点下所有的光学摄像机数量
	 * 
	 * @param siteId 站点id
	 * 
	 * @author zhangpeng
	 * @date 2013-3-22
	 * 
	 * @return int 站点下的所有光学摄像机
	 * </pre>
	 */
	public int findAllCount(String siteId);

	/**
	 * <pre>
	 * 保存摄像机点位
	 * 
	 * @param dvPlace 摄像机点位对象
	 * 
	 * @author zhangpeng
	 * @date 2012-3-22
	 * 
	 * @return void
	 * </pre>
	 */
	public void save(OpticsDVPlaceBean dvPlace);

	/**
	 * <pre>
	 * 保存拍照计划和IO模块参数
	 * 
	 * @param dvPlace 摄像机点位对象
	 * @param everydayPeriod 每天周期变量
	 * @param everydayPoint 每天时间点变量
	 * @param sevendayPeriod 7天周期变量
	 * @param sevendayPoint 7天时间点变量
	 * @param radioType 任务计划类型
	 * 
	 * @author zhangpeng
	 * @date 2012-3-22
	 * 
	 * @return void
	 * </pre>
	 */
	public void save(OpticsDVPlaceBean dvPlace, String everydayPeriod,
			String everydayPoint, String sevendayPeriod, String sevendayPoint,
			String radioType) throws Exception;

	/**
	 * <pre>
	 * 更新光学摄像机点位信息
	 * 
	 * @param opticsDVPlace 光学摄像机点位信息对象
	 * 
	 * @author zhangpeng
	 * @date 2012-3-22
	 * 
	 * @return void
	 * </pre>
	 */
	public void update(OpticsDVPlaceBean opticsDVPlace);

	/**
	 * <pre>
	 * 修改拍照计划和IO模块参数
	 * 
	 * @param dvPlace 摄像机点位对象
	 * @param everydayPeriod 每天周期变量
	 * @param everydayPoint 每天时间点变量
	 * @param sevendayPeriod 7天周期变量
	 * @param sevendayPoint 7天时间点变量
	 * @param radioType 任务计划类型
	 * 
	 * @author zhangpeng
	 * @date 2012-3-22
	 * 
	 * @return void
	 * </pre>
	 */
	public void update(OpticsDVPlaceBean dvPlace, String everydayPeriod,
			String everydayPoint, String sevendayPeriod, String sevendayPoint,
			String radioType) throws Exception;

	/**
	 * <pre>
	 * 根据id获取光学摄像机点位信息，需要携带区域及ftp信息
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
	 * 查询区域下所有的光学摄像机，有分页
	 * 
	 * @param zoneId 区域id
	 * @param pageNmb 当前页码
	 * @param pageSize 每页最大条数
	 * 
	 * @author zhangpeng
	 * @date 2012-7-11
	 * 
	 * @return List<OpticsDVPlaceBean> 查询出的光学摄像机点位列表
	 * </pre>
	 */
	public List<OpticsDVPlaceBean> findByZoneId(String zoneId, int pageNmb,
			int pageSize);

	/**
	 * <pre>
	 * 查询区域下所有的光学摄像机数量
	 * 
	 * @param zoneId 区域id
	 * 
	 * @author zhangpeng
	 * @date 2013-3-22
	 * 
	 * @return int 区域下的光学摄像机对象
	 * </pre>
	 */
	public int findByZoneIdCount(String zoneId);

	/**
	 * <pre>
	 * TODO 未验证
	 * </pre>
	 * 
	 * 根据监测点ID查询启用的光学摄像机
	 * 
	 * @param monitorPointId
	 *            监测点ID
	 * @return 监测点下的所有光学摄像机集合
	 */
	public List<OpticsDVPlaceBean> findByMonitorPointId(int monitorPointId);

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
	
	/**
	 * <pre>
	 * 查询io端口是否占用
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
	public boolean isIoPortUsing(String dvPlaceId, int ioPort);

}
