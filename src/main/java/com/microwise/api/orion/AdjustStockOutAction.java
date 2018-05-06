package com.microwise.api.orion;

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
import com.microwise.proxima.bean.Zone;
import com.opensymphony.xwork2.Action;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调整申请
 */
@Beans.Action
@Controller
public class AdjustStockOutAction {

    public static final Logger log = LoggerFactory.getLogger(AdjustStockOutAction.class);
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

    @RequestMapping(value = "/orion/adjustStock/{siteId}/{taskId}/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "重新申请", position = 11, httpMethod = "GET",
            notes = "重新申请"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
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
    @RequestMapping(value = "/orion/doAdjustStock/{taskId}/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取出入库列表", position = 12, httpMethod = "GET",
            notes = "获取出入库列表"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public String execute() {
        try {
            // 通过 Set 转换一下，过滤重复的文物 id
            List<Integer> relicIds2 = Lists.newArrayList(Sets.newHashSet(Ints.asList(relicIds)));
            outEventService.updateOutEvent(outEvent, relicIds2);

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("internal", outEvent.isOutBound());
            variables.put("resendRequest", true);
            taskService.complete(taskId, variables);

            //log.info("出入库管理", "重新申请，出库申请单号：" + outEvent.getId());
        } catch (Exception e) {
            //log.error("出库申请单调整失败", e);
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
