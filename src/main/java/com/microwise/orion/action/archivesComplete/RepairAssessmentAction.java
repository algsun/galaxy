package com.microwise.orion.action.archivesComplete;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairAssessment;
import com.microwise.orion.service.RepairAssessmentService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自评估action
 *
 * @author liuzhu
 * @date 2015-11-11
 */
@Beans.Action
@Orion
@Route("/orion/archives")
public class RepairAssessmentAction {

    @Autowired
    private RepairAssessmentService repairAssessmentService;

    private RepairAssessment repairAssessment;

    @Route("assessment/save.json")
    public String save() {
        try {
            repairAssessmentService.delete(repairAssessment.getRepairRecord().getId());
            repairAssessmentService.save(repairAssessment);
            return Results.json().asRoot(true).done();
        } catch (Exception e) {
            e.printStackTrace();
            return Results.json().asRoot(false).done();
        }
    }

    public RepairAssessment getRepairAssessment() {
        return repairAssessment;
    }

    public void setRepairAssessment(RepairAssessment repairAssessment) {
        this.repairAssessment = repairAssessment;
    }
}
