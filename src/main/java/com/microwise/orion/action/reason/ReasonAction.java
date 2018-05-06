package com.microwise.orion.action.reason;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairReason;
import com.microwise.orion.service.RepairReasonService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 原因管理
 *
 * @author 王耕
 * @date 15-9-15
 */
@Beans.Action
@Orion
@Route("/orion/reason")
public class ReasonAction extends OrionLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(ReasonAction.class);

    @Autowired
    private RepairReasonService repairReasonService;

    //input
    /**
     * 原因新增对象
     */
    private RepairReason repairReason;

    /**
     * 根据ID删除原因
     */
    private int id;

    //output
    /**
     * 原因实体集合
     */
    private List<RepairReason> repairReasons;

    /**
     * 根据reasonText验证原因是否已经存在
     */
    private String reason;

    @Route("index")
    public String execute() {
        ActionMessage.createByAction().consume();
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            repairReasons = repairReasonService.findAll(siteId);
            log("原因管理", "资产管理");
        } catch (Exception e) {
            log.error("原因管理", e);
        }
        return Results.ftl("orion/pages/reason/reason-list.ftl");
    }

    @Route("toAddReason")
    public String toAddReason() {
        return Results.ftl("orion/pages/reason/add-reason.ftl");
    }

    @Route("toUpdateReason")
    public String toUpdateReason() {
        try {
            repairReason = repairReasonService.findReasonById(id);
            log("修改原因", "资产管理");
        } catch (Exception e) {
            log.error("修改原因", e);
        }
        return Results.ftl("orion/pages/reason/update-reason.ftl");
    }

    @Route("saveOrUpdateReason")
    public String saveOrUpdateReason() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            repairReason.setSiteId(siteId);
            repairReasonService.saveOrUpdate(repairReason);
            ActionMessage.createByAction().success("新增或修改原因成功");
            log("新增或修改原因", "资产管理");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("新增或修改原因失败");
            log.error("新增或修改原因", e);
        }
        return Results.redirect("index");
    }

    @Route("deleteReason")
    public String deleteReason() {
        try {
            repairReasonService.deleteRepairReason(id);
            ActionMessage.createByAction().success("删除原因成功");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("删除原因失败");
            log.error("删除原因", e);
        }
        return Results.redirect("index");
    }

    @Route("validationText")
    public String validationText() {
        boolean flag = false;
        try {
            repairReason = repairReasonService.findReasonByReason(reason,id);
            if (repairReason == null) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Results.json().asRoot(flag).done();
    }

    public List<RepairReason> getRepairReasons() {
        return repairReasons;
    }

    public void setRepairReasons(List<RepairReason> repairReasons) {
        this.repairReasons = repairReasons;
    }

    public RepairReason getRepairReason() {
        return repairReason;
    }

    public void setRepairReason(RepairReason repairReason) {
        this.repairReason = repairReason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
