/**
 * 
 */
package com.microwise.proxima.service;

import com.microwise.proxima.bean.DVPlaceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 摄像机点位 service
 * 
 * <pre>
 * 
 * @author zhangpeng
 * @date 2012-7-10
 * @check @li.jianfei 2013.09.02 #5293
 */
public interface DVPlaceService {

	public static final Logger log = LoggerFactory
			.getLogger(InfraredDVPlaceService.class);

	/**
	 * <pre>
	 * 根据id获取摄像机点位
	 * 
	 * @param id 点位的id
	 * 
	 * @author zhangpeng
	 * @date 2013-3-25
	 * 
	 * @return DVPlaceBean dv点位信息对象
	 * </pre>
	 */
	public DVPlaceBean findById(String id);

	/**
	 * <pre>
	 * 修改摄像机点位
	 * 
	 * @param dvPlace 摄像机点位信息对象
	 * 
	 * @author zhangpeng
	 * @date 2012-6-11
	 * 
	 * @return void
	 * </pre>
	 */
	public void update(DVPlaceBean dvPlace);

	/**
	 * <pre>
	 * 修改摄像机启用/停用状态（取反）
	 * 
	 * @param dvPlaceId 摄像机点位id
	 * 
	 * @author zhangpeng
	 * @date 2013-3-22
	 * 
	 * @return void
	 * </pre>
	 */
	public void changeEnable(String dvPlaceId);

	/**
	 * <pre>
	 * TODO 未验证
	 * 根据IO模块发送的信息修改摄像机的IP地址
	 * 
	 * @param dvPlaceId 点位ID
	 * @param dvIp 摄像机IP
	 * 
	 * @author zhang.licong
	 * @date 2012-8-15
	 * 
	 * @return void
	 * </pre>
	 */
	public void updateDvIP(int dvPlaceId, String dvIp);

	/**
	 * <pre>
	 * TODO 未验证
	 * </pre>
	 * 
	 * 函数功能说明 根据监测点id查询出绑定在监测点上的摄像机的集合 JinGang 2012-9-5 下午03:03:08
	 * 
	 * @参数： @param monitorPointId 监测点id
	 * @参数： @return 绑定在监测点上的摄像机的集合
     * @deprecated 监测点已经废弃 @gaohui 2013-06-14
	 */
	public List<DVPlaceBean> findByMonitorPointId(int monitorPointId);

	/**
	 * <pre>
	 * TODO 未验证
	 * </pre>
	 * 
	 * 查询出所有摄像机点位 GuoTian 2012-9-11 下午16:13:08
	 * 
	 * @参数： @param 无
	 * @参数： @return 摄像机点位信息
	 */
	public List<DVPlaceBean> findAllDVPlace();

	/**
	 * <pre>
	 * TODO 未验证
	 * </pre>
	 * 
	 * 根据摄像机点位名称查询摄像机点位
	 * 
	 * @param dvPlaceName
	 *            摄像机点位名称
	 * @return
	 */
	public DVPlaceBean findByName(String dvPlaceName);
	
	/**
	 * <pre>
	 * 获取摄像机启用/停用状态
	 * 
	 * @param dvPlaceId 摄像机点位id
	 * 
	 * @author zhangpeng
	 * @date 2013-3-27
	 * 
	 * @return boolean true启用，false禁用
	 * </pre>
	 */
	public boolean findDvEnable(String dvPlaceId);

	/**
	 * <pre>
	 * 添加业务使用：判断区域下是否拥有同名摄像机
	 *
     * TODO 建议使用 hasSameNameWhenAdd @gaohui 2013-03-29
	 * @param zoneId 区域id
	 * @param dvPlaceName 摄像机点位名称
	 * 
	 * @return boolean true有/false无
	 * </pre>
	 */
	public boolean hasSameNameByAdd(String zoneId, String dvPlaceName);

	/**
	 *<pre>
	 * 修改业务使用：判断区域下是否拥有同名摄像机
	 * 
	 * @param zoneId 区域id
	 * @param dvPlaceName 摄像机点位名称
	 * @param dvPlaceId 摄像机id 
	 * 
	 * @return boolean true有/false无
	 * </pre>
	 */
	public boolean hasSameNameByUpdate(String zoneId, String dvPlaceName,
			String dvPlaceId);

	/**
	 *<pre>
	 * 添加修改都能使用（应前台要求封装）
	 * 
	 * @param zoneId 区域id
	 * @param dvPlaceName 摄像机点位名称
	 * @param dvPlaceId 摄像机id
	 * 
	 * @return boolean true有/false无
	 * </pre>
	 */
	public boolean hasSameName(String zoneId, String dvPlaceName,
			String dvPlaceId);
	/***
	 * 修改 摄像机 实景图
	 * 
	 * @author xu.baoji
	 * @date 2013-8-22
	 *
	 * @param dvId 摄像机id
	 * @param realmap 实景图 名称
	 */
	public void updateRealmap(String dvId,String realmap);

    /**
     * 返回某个区域下的摄像机点位.
     *
     * 注意：如果无数据返回空集合
     *
     * @param zoneId
     * @return
     */
    public List<DVPlaceBean> findDvPlacesByZoneId(String zoneId);

    /**
     * <pre>
     * 查询站点下所有的摄像机，要携带区域信息，分页
     *
     * @param siteId 站点id
     * @param pageNmb 当前页码
     * @param pageSize 每页最大条数
     *
     * @author zhangpeng
     * @date 2012-7-11
     *
     * @return List<DVPlaceBean> 查询出的摄像机点位列表
     * </pre>
     */
    public List<DVPlaceBean> findAll(String siteId, int pageNmb,
                                           int pageSize);

    /**
     * <pre>
     * 查询站点下所有的摄像机，要携带区域信息，不分页
     *
     * @param siteId 站点id
     *
     * @author zhangpeng
     * @date 2012-7-11
     *
     * @return List<DVPlaceBean> 查询出的摄像机点位列表
     * </pre>
     */
    public List<DVPlaceBean> findAll(String siteId);

    /**
     * 返回某个区域下的摄像机点位.，分页
     *
     * 注意：如果无数据返回空集合
     *
     * @param zoneId
     * @return
     */
    public List<DVPlaceBean> findDvPlacesByZoneId(String zoneId, int pageNmb,
                                                  int pageSize);
}
