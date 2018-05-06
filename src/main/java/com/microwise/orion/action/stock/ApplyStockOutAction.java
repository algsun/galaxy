package com.microwise.orion.action.stock;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
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
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出库申请
 *
 * @author gaohui
 * @date 13-5-28 16:21
 * @check xiedeng 2013-6-6 14:39  svn:3998
 */
@Beans.Action
@Orion
public class ApplyStockOutAction extends OrionLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(ApplyStockOutAction.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private OutEventService outEventService;
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

    //input
    private OutEvent outEvent;

    private int[] relicIds;

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

    /**
     * 页面
     *
     * @return
     */
    public String view() {
        String siteId = Sessions.createByAction().currentSiteId();

        listEra = eraService.findAllEra();
        listTexture = textureService.findAllTexture();
        listLevel = levelService.findAllLevel();
        listZone = zoneService.findHasRelicZone(siteId);

        outEvent = new OutEvent();
        return Action.SUCCESS;
    }


    /**
     * 执行
     *
     * @return
     */
    public String execute() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            outEvent.setSiteId(siteId);
            outEvent.setUser(Sessions.createByAction().currentUser());
            outEvent.setState(OutEvent.STATE_APPLYING);

            // 通过 Set 转换一下，过滤重复的文物 id
            List<Integer> relicIds2 = Lists.newArrayList(Sets.newHashSet(Ints.asList(relicIds)));
            outEventService.saveOutEventInfo(outEvent, relicIds2);
            try {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("applicant", String.valueOf(Sessions.createByAction().currentUser().getId()));
                variables.put("outEventId", outEvent.getId());
                variables.put("siteId", siteId);
                variables.put("internal", outEvent.isOutBound());
                variables.put("logicGroupId", Sessions.createByAction().currentLogicGroup().getId());
                // 发起出库流程
                ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(Constants.StockOut.PROCESS_DEFINITION_KEY, variables);
            } catch (Exception e) {
                outEventService.deleteOutEvent(outEvent.getId());
                ActionMessage.createByAction().fail("文物出库申请失败").consume();
                logFailed("添加文物出库申请", "添加文物出库申请失败");
                log.error("", e);
                return Action.INPUT;
            }

            log("出入库管理", "出库申请单号：" + outEvent.getId());
            ActionMessage.createByAction().success("文物出库申请成功");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("文物出库申请失败").consume();
            logFailed("添加文物出库申请", "添加文物出库申请失败");
            log.error("", e);
            return Action.INPUT;
        }

        return Action.SUCCESS;
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
