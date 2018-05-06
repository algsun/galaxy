package com.microwise.orion.action.stock;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.EventZone;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.EventZoneService;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.sys.Orion;
import com.opensymphony.xwork2.Action;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出库单核对
 *
 * @author gaohui
 * @date 13-5-30 19:36
 *
 * @check xiedeng 2013-6-6 15:24 svn:3909
 */
@Beans.Action
@Orion
public class StockOutCheckAction {

    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;

    /**
     * 出库单文物分单确认service
     */
    @Autowired
    private EventZoneService eventZoneService;
    
    @Autowired
    private TaskService taskService;

    //input
    // 出库申请单号
    private String outEventId;
    // taskId
    private String taskId;
    //文物出库申请状态
    private int state;
    //区域Id
    private String zoneId;
    //系统当前登录用户
    private int userId = Sessions.createByAction().currentUser().getId();

    //output
    private OutEvent outEvent;
    //出库申请
    private List<EventZone> zones;
    // EventZone.id => couldCheck,
    // 库房区域对应此区域内的文件是否可以审核通过, 如果区域内所在的文物有一件是非“在库”状态，则不能审核通过 @gaohui
    private Map<Integer, Boolean> zoneCouldChecks = new HashMap<Integer, Boolean>();
    // TODO deprecated
    private boolean couldCheck = true;

    public String view() {
        outEvent = outEventService.findById(outEventId);
        
        zones = eventZoneService.findEventZones(outEventId);
        for(EventZone eventZone: zones){
            boolean couldCheck = true;
            for(Relic relic: eventZone.getRelics()){
                // 如果区域内所在的文物有一件是非“在库”状态，则不能审核通过 @gaohui
                if(relic.getState() != Relic.STATE_IN){
                    couldCheck = false;
                    break;
                }
            }
            zoneCouldChecks.put(eventZone.getId(), couldCheck);
        }

        return Action.SUCCESS;
    }
    
    /**
     * 修改文物出库审批状态
     * @return
     */
    public String changeStatus() {
    	//更新流程状态
    	eventZoneService.updateEventZoneUserAndState(outEventId, userId, state, zoneId);
    	
    	
    	Map<String, Object> variables = new HashMap<String, Object>();
    	if(state == EventZone.EVENT_ZONE_STATE_PASS) { //通过
    		zones = eventZoneService.findEventZones(outEventId);
    		if(zones != null && zones.size() > 0) {
                for(EventZone eventZone: zones){
                    //有管理员未审批或者不同意时
                    if(eventZone.getState() != EventZone.EVENT_ZONE_STATE_PASS) {
                        return Action.SUCCESS;
                    }
                }
    		}
            // 将文物状态改为“出库申请中”
            outEventService.updateRelicState(outEventId, Relic.STATE_APPLYING_OUT);
    		//判断，所有人都通过的时候才能通过
    		variables.put("listCheckApproved", true);
    	}else if(state == EventZone.EVENT_ZONE_STATE_NOT_PASS){//不通过
    		variables.put("listCheckApproved", false);
    	}
    	
    	//出库单核对完成，流程转向下一环节
    	taskService.complete(taskId, variables);
    	
    	return Action.INPUT;
    }

    public String getOutEventId() {
        return outEventId;
    }

    public void setOutEventId(String outEventId) {
        this.outEventId = outEventId;
    }

    public OutEvent getOutEvent() {
        return outEvent;
    }

    public void setOutEvent(OutEvent outEvent) {
        this.outEvent = outEvent;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean isCouldCheck() {
        return couldCheck;
    }

    public void setCouldCheck(boolean couldCheck) {
        this.couldCheck = couldCheck;
    }

	public List<EventZone> getZones() {
		return zones;
	}

	public void setZones(List<EventZone> zones) {
		this.zones = zones;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

    public Map<Integer, Boolean> getZoneCouldChecks() {
        return zoneCouldChecks;
    }

    public void setZoneCouldChecks(Map<Integer, Boolean> zoneCouldChecks) {
        this.zoneCouldChecks = zoneCouldChecks;
    }
}
