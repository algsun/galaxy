package com.microwise.orion.action.inventory;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.orion.bean.Inventory;
import com.microwise.orion.bean.InventoryError;
import com.microwise.orion.bean.InventoryOut;
import com.microwise.orion.service.InventoryService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 出库盘点
 *
 * @author duan.qixin
 * @date 2013-06-03
 *
 * @check @gaohui #4063 2013-06-06
 */
@Beans.Action
@Orion
public class InventoryCheckAction extends OrionLoggerAction{
    public static final Logger log = LoggerFactory.getLogger(InventoryCheckAction.class);

    /**
     * 盘点相关 service
     */
	@Autowired
	private InventoryService inventoryService;
	

    // output
	/**
	 * struts OGNL参数
	 */
	//库存盘点列表
	private List<Inventory> inventorys;//盘点文物列表
	private List<InventoryOut> inventoryOut;//出库文物详细列表
	private List<InventoryError> inventoryErrors;//异常文物详细列表
	private Inventory inventory;//文物详单
	private int id;//文物ID
	private String remark;//文物备注信息
	private boolean isStart; //是否有正在盘点的信息

    // input & output
	//分页信息
	private Integer index = 1;
	private int pageCount;
	private int count;
	
	
	/**
	 * 私有化参数
	 */
	// 获得siteId 当前站点编号
	private String siteId = Sessions.createByAction().currentSiteId();
	
	/**
	 * 库存盘点处理方法
	 * 展示库存盘点列表，分页展示
	 * @return
	 */
	public String inventoryCheck() {
        ActionMessage.createByAction().consume();
        try{
            //分页信息
            count = inventoryService.findInventoryCount(siteId);
            pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);
            //库存盘点信息
            inventorys = inventoryService.findInventorys(siteId, index, Constants.SIZE_PER_PAGE);
            isStart = inventoryService.isStartInventory(siteId);
        } catch (Exception e){
            log.error("", e);
        }
		return "success";
	}
	
	/**
	 * 库存信息盘点报告
	 * @return
	 */
	public String inventoryCheckInfo() {
        try{
            inventory = inventoryService.findInventoryReport(id);
            inventoryOut = inventoryService.findInventoryOuts(id);
            inventoryErrors = inventoryService.findInventoryErrors(id);
            backTo = "report";
        }  catch (Exception e){
            log.error("", e);
        }

		return "success";
	}

    /**
     * 实时库存信息盘点报告
     * @return
     */
    public String realtimeInventoryCheckInfo(){
        try{
            inventory = inventoryService.findRealtimeInventoryReport(id, siteId);
            inventoryOut = inventoryService.findRealtimeInventoryOuts(id, siteId);
            inventoryErrors = inventoryService.findRealtimeInventoryErrors(id, siteId);
            backTo = "realtime";
        }catch (Exception e){
            log.error("", e);
        }
        return "success";
    }

    // 更新完成后跳转的页面:  实时报告 realtime, 报告 report
    private String backTo = "realtime";
	/**
	 * 保存备注信息
	 * @return
	 */
	public String updateRemark() {
		try {
			inventoryService.updateInventoryRemark(id, remark);
			log("库存盘点", "更新盘点报告备注信息");
		} catch (Exception e) {
            logFailed("盘点报告", "更新盘点报告备注信息失败");
            e.printStackTrace();
		}
		return backTo;
	}
	
	/**
	 * 停止盘点
	 * @return
	 */
	public String stopInventory() {
        try{
            inventoryService.closeInventory(id, siteId);
            ActionMessage.createByAction().success("结束文物盘点成功");
            log("库存盘点", "停止当前盘点");
        } catch (Exception e){
            logFailed("盘点报告", "停止当前盘点失败");
            log.error("", e);
        }
		return "success";
	}
	
	/**
	 * 创建盘点
	 * @return
	 */
	public String createInventory() {
		try {
			inventoryService.createInventory(siteId);
            ActionMessage.createByAction().success("创建文物盘点成功");
			log("库存盘点", "创建文物盘点");
		} catch (Exception e) {
            logFailed("盘点报告", "创建文物盘点失败");
            log.error("", e);
		}
		return "success";
	}
	
	
	public List<Inventory> getInventorys() {
		return inventorys;
	}
	public void setInventorys(List<Inventory> inventorys) {
		this.inventorys = inventorys;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public List<InventoryOut> getInventoryOut() {
		return inventoryOut;
	}
	public void setInventoryOut(List<InventoryOut> inventoryOut) {
		this.inventoryOut = inventoryOut;
	}
	public List<InventoryError> getInventoryErrors() {
		return inventoryErrors;
	}
	public void setInventoryErrors(List<InventoryError> inventoryErrors) {
		this.inventoryErrors = inventoryErrors;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

    public boolean getIsStart() {
        return isStart;
    }

    public void setIsStart(boolean start) {
        isStart = start;
    }

    public String getBackTo() {
        return backTo;
    }

    public void setBackTo(String backTo) {
        this.backTo = backTo;
    }
}
