package com.microwise.orion.action.stock;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Era;
import com.microwise.orion.bean.Level;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.bean.Texture;
import com.microwise.orion.service.*;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.microwise.proxima.bean.Zone;
import com.opensymphony.xwork2.Action;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调整申请
 *
 * @author gaohui
 * @date 13-5-31 10:40
 * @check xiedeng 2013-6-6 15:39 svn:3985
 */
@Beans.Action
@Orion
public class AdjustStockOutAction extends OrionLoggerAction {

    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;

    @Autowired
    private TaskService taskService;

    /**
     * 时代
     */
    @Autowired
    private EraService eraService;
    /**
     * 质地
     */
    @Autowired
    private TextureService textureService;
    /**
     * 级别
     */
    @Autowired
    private LevelService levelService;
    /**
     * 区域
     */
    @Autowired
    private ZoneService zoneService;

    //input & output
    private String outEventId;
    private String taskId;

    //output
    /**
     * 查询时代列表
     */
    private List<Era> listEra;
    /**
     * 查询质地列表
     */
    private List<Texture> listTexture;
    /**
     * 查询级别列表
     */
    private List<Level> listLevel;
    /**
     * 查询有文物的区域
     */
    private List<Zone> listZone;

    private int[] relicIds;

    //output
    private OutEvent outEvent;

    /**
     * 展示
     *
     * @return
     */
    public String view() {
        String siteId = Sessions.createByAction().currentSiteId();

        listEra = eraService.findAllEra();
        listTexture = textureService.findAllTexture();
        listLevel = levelService.findAllLevel();
        listZone = zoneService.findHasRelicZone(siteId);

        outEvent = outEventService.findById(outEventId);

        return Action.SUCCESS;
    }

    /**
     * 执行
     *
     * @return
     */
    public String execute() {
        try {
            // 通过 Set 转换一下，过滤重复的文物 id
            List<Integer> relicIds2 = Lists.newArrayList(Sets.newHashSet(Ints.asList(relicIds)));
            outEventService.updateOutEvent(outEvent, relicIds2);

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("internal", outEvent.isOutBound());
            variables.put("resendRequest", true);
            taskService.complete(taskId, variables);

            log("出入库管理", "重新申请，出库申请单号：" + outEvent.getId());
        } catch (Exception e) {
            logFailed("出库申请单调整失败", "出库申请单调整失败");
            outEvent = outEventService.findById(outEventId);
            return Action.INPUT;
        }
        return Action.SUCCESS;
    }

    public String getOutEventId() {
        return outEventId;
    }

    public void setOutEventId(String outEventId) {
        this.outEventId = outEventId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public OutEvent getOutEvent() {
        return outEvent;
    }

    public void setOutEvent(OutEvent outEvent) {
        this.outEvent = outEvent;
    }

    public int[] getRelicIds() {
        return relicIds;
    }

    public void setRelicIds(int[] relicIds) {
        this.relicIds = relicIds;
    }

    public List<Era> getListEra() {
        return listEra;
    }

    public void setListEra(List<Era> listEra) {
        this.listEra = listEra;
    }

    public List<Texture> getListTexture() {
        return listTexture;
    }

    public void setListTexture(List<Texture> listTexture) {
        this.listTexture = listTexture;
    }

    public List<Level> getListLevel() {
        return listLevel;
    }

    public void setListLevel(List<Level> listLevel) {
        this.listLevel = listLevel;
    }

    public List<Zone> getListZone() {
        return listZone;
    }

    public void setListZone(List<Zone> listZone) {
        this.listZone = listZone;
    }
}
