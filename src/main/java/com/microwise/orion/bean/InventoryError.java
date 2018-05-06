package com.microwise.orion.bean;

/**
 * 盘点异常文物
 * 
 * @author xubaoji
 * @date 2013-5-25
 *
 * @check 2013-06-04 zhangpeng svn:3789
 */
public class InventoryError {
	
	/**扫描状态标识常量 ：被扫描到*/
	public static final Integer IS_SCAN_STATE = 1 ;
	
	/**扫描状态标识常量 ：未扫描到*/
	public static final Integer NO_SCAN_STATE = 2 ;
	
	/**扫描状态标识常量 ：待扫描*/
	public static final Integer SCAN_STATE = 0 ;

	/** id编号 */
	private Integer id;

	/** 文物实体 */
	private Relic relic;

	/** 系统库房状态 */
	private Integer sysState;

	/** 扫描状态 */
	private Integer scanState;

	/** 盘点记录 */
	private Inventory inventory;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Relic getRelic() {
		return relic;
	}

	public void setRelic(Relic relic) {
		this.relic = relic;
	}

	public Integer getSysState() {
		return sysState;
	}

	public void setSysState(Integer sysState) {
		this.sysState = sysState;
	}

	public Integer getScanState() {
		return scanState;
	}

	public void setScanState(Integer scanState) {
		this.scanState = scanState;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
