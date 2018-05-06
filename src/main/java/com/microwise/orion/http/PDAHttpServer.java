package com.microwise.orion.http;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.*;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.vo.OutEventVo;
import com.microwise.orion.vo.OutOrInRelicVo;
import com.microwise.orion.vo.ZoneVo;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.proxy.ZoneProxy;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.*;

/**
 * 为 文物管理手持机设备提供 http 服务
 *
 * @author xubaoji
 * @date 2013-5-20
 * @check @gaohui #4076 2013-06-06    修改后需要修改手持机设备同步修改暂不修改
 */
@Orion
@Beans.Action
public class PDAHttpServer {

	private static final Logger log = LoggerFactory
			.getLogger(PDAHttpServer.class);

	/** 区域代理 服务 */
	@Autowired
	private ZoneProxy zoneProxy;

	/** 文物 service */
	@Autowired
	private RelicService relicService;

	/** 盘点 service */
	@Autowired
	private InventoryService inventoryService;

	/**
	 * 盘点标签文物service
	 */
	@Autowired
	private InventoryTagService inventoryTagService;

	/** 出库文物 service */
	@Autowired
	private EventRelicService eventRelicService;

	/** 出库 单 service */
	@Autowired
	private OutEventService outEventService;

	/*** 工作流service */
	@Autowired
	private WorkFlowService workFlowService;

    // in
    /** 站点编号 */
    private String siteId;

    // in or out
    /** 手持机上传来的json 数据 或者要发送给手持设备的 数据 */
    private Object data;

	// out

	/** 系统时间 */
	private String sysTime;

	/** 是否成功 */
	private Boolean success;

	/** 错误消息 */
	private String message;

	/** 授时 获得系统时间 */
	public String getSystemTime() {

		try {
			sysTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, new Date());
			success = true;
		} catch (Exception e) {
			success = false;
			message = "系统服务出错！";
			log.error("手持机与系统交互是出错", e);
		}

		return Action.SUCCESS;
	}

	/** 获得区域信息 */
	public String getZoneInfo() {

		try {
			List<Zone> zoneList = zoneProxy.findZoneList(siteId);
			data = switchZoneToZoneVo(zoneList);
			success = true;
		} catch (Exception e) {
			success = false;
			message = "系统获得 区域信息出错！";
            // TODO 加异常参数, 其他也一样 @gaohui 2013-06-06
			log.error("手持机获得区域信息出错！",e);
		}

		return Action.SUCCESS;
	}

	/**
     * 获得有标签的文物信息
     * TODO 建议改名为 "getRelicWithTag"
     **/
	public String getRelicInfo() {
		try {
			data = relicService.findHasTagRelics(siteId);

			success = true;
		} catch (Exception e) {
			success = false;
			message = "系统获得文物信息出错";
			log.error("手持机获得文物信息出错！",e);
		}

		return Action.SUCCESS;
	}

	/** 获得 出库单 和对应的出库单文物信息 */
	public String getOutEvent() {
		try {
            // 库房确认阶段的出库单
            Map<String,String> outEventIds = workFlowService.findOutEventsAtStockOutConfirm(siteId);
            List<OutEventVo> outEvents = new ArrayList<OutEventVo>();
			if (outEventIds!=null && outEventIds.size()>0) {
				outEvents.addAll(outEventService.findOutEventVo(outEventIds));
            }

            // 查询出库阶段的出库单 @gaohui 2013-03-27
            List<OutEventVo> outedOutEventVos = outEventService.findOutEventVoByState(siteId, OutEvent.STATE_OUT);
            outEvents.addAll(outedOutEventVos);

            data = outEvents;
			success = true;
		} catch (Exception e) {
			success = false;
			message = "系统获得出库单信息出错";
			log.error("手持机获得出库单信息出错！", e);
		}

		return Action.SUCCESS;
	}

	/** 上传有标签的文物信息 */
	public String uploadRelicTag() {

		try {
			// 不知为何传来的数据 使用object 接收后 会是数组
            // TODO 建议使用另一个 String 字段 专门来接受 json 数据 @gaohui 2013-06-06
			String[] dataStrings = (String[]) data;

            Map<String, Object> dataMap = new HashMap<String, Object>();
            Gson gson = new Gson();
			dataMap = gson.fromJson(String.valueOf(dataStrings[0]),
					dataMap.getClass());
			siteId = (String) dataMap.get("siteId");
			List<String> tagCodeList = (List<String>) dataMap.get("tagCode");
			relicService.updateRelicTag(siteId, tagCodeList);
			success = true;
		} catch (Exception e) {
			success = false;
			message = "处理有标签的文物出错！";
			log.error("处理文物标签状态为有标签状态出错",e);
		}
		return Action.SUCCESS;
	}

	/**
     * "库房确认"阶段 上传文物出库信息
     *
     **/
	public String uploadStockOutInfo() {

		try {
			// 文物出库实体
			String[] dataStrings = (String[]) data;
			Gson gson = new Gson();
			@SuppressWarnings("serial")
			Type type = new TypeToken<List<OutOrInRelicVo>>() {}.getType();

            List<OutOrInRelicVo> stockOutData = gson.fromJson(dataStrings[0], type);

            if(stockOutData != null && !stockOutData.isEmpty()){
                // 判断上传上来的数据是否是最新的(通过当前 taskId 比较)
                OutOrInRelicVo outOrInRelicVo = stockOutData.get(0);
                String taskId = outOrInRelicVo.getTaskId();
                String outEventId = outOrInRelicVo.getEventId();
                String curTaskId = workFlowService.findTaskIdAtStockOutConfirm(outEventId);
                if(!taskId.equals(curTaskId)){
                    message = "事件过期，请重新同步!";
                    success = false;
                    return Action.SUCCESS;
                }
            }

			// 修改出库文物 扫描状态 以及 出库时间
			eventRelicService.updateOutRelicForHttp(stockOutData);

			success = true;
		} catch (Exception e) {
			success = false;
			message = "处理出库数据出错！";
			log.error("处理文物出库数据出错",e);
		}

		return Action.SUCCESS;
	}

    /**
     * "出库"阶段 上传确认出库的文物信息. 出库审批已经通过，文物为待出库。确认文物待出库为出库。
     *
     * @return
     */
    public String uploadDoneStockOut(){
        try {
            // 文物出库实体
            String[] dataStrings = (String[]) data;
            Gson gson = new Gson();
            @SuppressWarnings("serial")
            Type type = new TypeToken<List<OutOrInRelicVo>>() {
            }.getType();

            List<OutOrInRelicVo> stockOutData = gson.fromJson(dataStrings[0], type);
            System.out.println(stockOutData);
            if(stockOutData == null || stockOutData.isEmpty()){
               success = false;
               message = "请求参数不正确";
            } else {
                List<Integer> relicIds = Lists.transform(stockOutData, new Function<OutOrInRelicVo, Integer>() {
                    @Override
                    public Integer apply(OutOrInRelicVo outOrInRelicVo) {
                        return outOrInRelicVo.getRelicId();
                    }
                });
                relicService.updateRelicState(relicIds, Relic.STATE_OUT);
                success = true;
            }
        } catch (Exception e){
            success = false;
            message = "上传出库文物出错";
            log.error("", e);
        }
        return Action.SUCCESS;
    }

	/** 上传文物入库信息 */
	public String uploadStockInInfo() {

		try {
			String[] dataStrings = (String[]) data;
			Gson gson = new Gson();
			@SuppressWarnings("serial")
			Type type = new TypeToken<List<OutOrInRelicVo>>() {}.getType();
            // 文物入库实体
            List<OutOrInRelicVo> stockInDate = gson.fromJson(dataStrings[0], type);

			// 修改 入库文物 扫描状态和 入库时间
			eventRelicService.updateInRelicForHttp(stockInDate);

            // 修改 出库事件 表中 是否回库字段 为已回库

			success = true;
		} catch (Exception e) {
			success = false;
			message = "处理出库数据出错！";
			log.error("处理文物出库数据出错",e);
		}

		return Action.SUCCESS;
	}

	/** 上传文物盘点扫描 标签信息 */
	@SuppressWarnings("unchecked")
	public String updateInventoryTagInfo() {

		try {
			String[] dataStrings = (String[]) data;
            Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap = new Gson().fromJson(String.valueOf(dataStrings[0]), dataMap.getClass());
			siteId = (String) dataMap.get("siteId");

            List<Integer> relicIds = new ArrayList<Integer>();
			List<Double> relicIdList = (List<Double>) dataMap.get("relicIds");
			for (Double d : relicIdList) {
				relicIds.add(d.intValue());
			}

			// 判断是否有正在进行的盘点
			Boolean start = inventoryService.isStartInventory(siteId);
			if (!start) {
				success = false;
				message = "系统无正在盘点的记录";
				return Action.SUCCESS;
			}
			inventoryTagService.addInventoryTags(siteId, relicIds);
			success = true;
		} catch (Exception e) {
			success = false;
			message = "处理文物盘点扫描到的标签数据出错！";
			log.error("处理文物盘点扫描到的标签数据出错",e);
		}
		return Action.SUCCESS;
	}

	/**
	 * 将 zone 实体转换为 http服务 需要的zoneVo 实体
	 *
	 * @param zoneList
	 *            区域 列表
	 *
	 * @author 许保吉
	 * @date 2013-5-23
	 *
	 * @return
	 */
	private List<ZoneVo> switchZoneToZoneVo(List<Zone> zoneList) {
		List<ZoneVo> zoneVoList = new ArrayList<ZoneVo>();
		for (Zone zone : zoneList) {
			String parentId = "";
			if (zone.getParent() != null) {
				parentId = zone.getParent().getId();
			}
			ZoneVo zoneVo = new ZoneVo(zone.getId(), zone.getName(), siteId, parentId);
			zoneVoList.add(zoneVo);
		}
		return zoneVoList;
	}

	public String getSysTime() {
		return sysTime;
	}

	public void setSysTime(String sysTime) {
		this.sysTime = sysTime;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
