package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.EventRelic;
import com.microwise.orion.vo.OutOrInRelicVo;

import java.util.List;

/**
 * 出库文物dao
 * 
 * @author xubaoji
 * @date 2013-5-29
 * 
 * @check 2013-06-05 zhangpeng svn:3965
 */
public interface EventRelicDao extends BaseDao<EventRelic> {

	/**
	 * 批量 修改 出库文物 时间 和扫描状态
	 * 
	 * @param outRelics
	 *            扫描到的出库文物列表
	 * @return void
	 * @author 许保吉
	 * @date 2013-5-29
	 */
	public void updateOutRelicForHttp(List<OutOrInRelicVo> outRelics);

	/**
	 * 批量 修改 入库文物 时间 和扫描状态
	 * 
	 * @param outRelics
	 *            扫描到的出库文物列表
	 * @return void
	 * @author 许保吉
	 * @date 2013-5-29
	 */
	public void updateInRelicForHttp(List<OutOrInRelicVo> outRelics);

	/**
	 * 查询 当前盘点 出库文物
	 * 
	 * @param siteId
	 *            站点编号
	 * @return
	 * @author 许保吉
	 * @date 2013-5-28
	 */
	public List<EventRelic> findInventoryOut(String siteId);

	/**
	 * 根据 outEventId 删除 eventRelics
	 * 
	 * @param outEventId
	 */
	public void deleteEventRelicsByOutEventId(String outEventId);

	/**
	 * 根据编号修改eventRelic状态
	 * 
	 * @param id
	 *            编号
	 * @param state
	 *            状态
	 */
	public void updateStateById(int id, int state);

	/**
	 * 根据编号修改eventRelic outDate
	 * 
	 * @param id
	 *            编号
	 */
	public void updateOutDateById(int id);

	/***
	 * 查询没有 管理员区域的出库文物
	 * 
	 * @param zoneId
	 *            管理员区域的子孙区域列表
	 * 
	 * @author 许保吉
	 * @date 2013-6-19
	 * 
	 * @return List<Integer> 文物id 列表
	 */
	public List<Integer> findNoUserZoneEventRelic(List<String> zoneId,
			String eventId);

	/**
	 * 查询没有区域的出库文物
	 * 
	 * @param eventId
	 *            出库事件id
	 * 
	 * @author 许保吉
	 * @date 2013-6-24
	 * 
	 * @return List<Integer> 文物id 列表
	 */
	public List<Integer> findNoZoneEventRelic(String eventId);
}
