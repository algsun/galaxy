package com.microwise.orion.action.relic;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.HealthEvaluation;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.StatusQuo;
import com.microwise.orion.service.HealthEvaluationService;
import com.microwise.orion.service.StatusQuoService;
import com.microwise.orion.sys.Orion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


/**
 * 文物健康评测Action
 *
 * @author bai.weixing
 * @since 2017/7/4.
 */
@Beans.Action
@Orion
@Route("orion/healthEvaluation")
public class HealthEvaluationAction {

    public static final Logger log = LoggerFactory.getLogger(HealthEvaluationAction.class);

    //文物健康评测service
    @Autowired
    private HealthEvaluationService healthEvaluationService;

    //文物现状记录信息service
    @Autowired
    private StatusQuoService statusQuoService;

    //文物实体
    private Relic relic;

    //文物健康评测实体
    private HealthEvaluation healthEvaluation;

    //文物现状记录信息实体
    private StatusQuo statusQuo;


    @Route("index")
    public String index() {
        ActionMessage.createByAction().consume();
        relic = healthEvaluationService.findHealthEvaluations(relic.getId());
        return Results.ftl("orion/pages/healthEvaluation/index.ftl");
    }

    @Route("detail")
    public String detail() {
        healthEvaluation = healthEvaluationService.find(healthEvaluation.getId());
        return Results.ftl("orion/pages/healthEvaluation/detail.ftl");
    }

    @Route("delete")
    public String delete() {

        try {
            healthEvaluation = healthEvaluationService.find(healthEvaluation.getId());
            healthEvaluationService.delete(healthEvaluation);
            ActionMessage.createByAction().success("删除文物保护评测成功");
            log.info("删除文物保护评测成功");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("删除文物保护评测失败");
            log.error("删除文物保护评测失败", e);
        }
        return Results.redirect("index?relic.id=" + relic.getId());
    }

    @Route("create")
    public String create() {
        statusQuo = statusQuoService.findStatusQuos(relic.getId());
        return Results.ftl("orion/pages/healthEvaluation/save.ftl");
    }

    @Route("save")
    public String save() {
        try {
            statusQuo.setQuoDate(healthEvaluation.getEvaluationDate());
            statusQuo.setRelic(healthEvaluation.getRelic());
            statusQuoService.saveOrUpdateStatusQuo(statusQuo);
            healthEvaluation.setStatusQuoId(statusQuo.getId());
            healthEvaluationService.save(healthEvaluation);
            ActionMessage.createByAction().success("添加文物保护评测成功");
            log.info("添加文物保护评测成功");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("添加文物保护评测失败");
            log.error("添加文物保护评测失败",e);
        }

        return Results.redirect("index?relic.id=" + healthEvaluation.getRelic().getId());
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public HealthEvaluation getHealthEvaluation() {
        return healthEvaluation;
    }

    public void setHealthEvaluation(HealthEvaluation healthEvaluation) {
        this.healthEvaluation = healthEvaluation;
    }

    public StatusQuo getStatusQuo() {
        return statusQuo;
    }

    public void setStatusQuo(StatusQuo statusQuo) {
        this.statusQuo = statusQuo;
    }
}
