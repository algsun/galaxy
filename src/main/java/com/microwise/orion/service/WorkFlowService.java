package com.microwise.orion.service;

import java.util.Map;

/**
 * @author gaohui
 * @date 13-5-31 14:41
 */
public interface WorkFlowService {

	/**
	 * 根据站点编号查询出库事件的编号 key 为 outEventId value 为 对应 taskId
	 * 
	 * @param siteId
	 *            站点编号
	 * @return 事件的编号集合
	 */
	Map<String, String> findOutEventsAtStockOutConfirm(String siteId);

	/**
	 * 根据outEventId获取TaskId
	 * 
	 * @param outEventId
	 * @return
	 */
	String findTaskIdAtStockOutConfirm(String outEventId);
}
