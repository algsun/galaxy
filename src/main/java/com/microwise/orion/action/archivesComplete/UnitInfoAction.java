package com.microwise.orion.action.archivesComplete;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.google.common.collect.Maps;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairRecord;
import com.microwise.orion.service.RepairRecordService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 单位信息记录表
 *
 * @author 王耕
 * @date 15-9-23
 */
@Beans.Action
@Orion
@Route("/orion/archives")
public class UnitInfoAction extends OrionLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(UnitInfoAction.class);

    @Autowired
    private RepairRecordService repairRecordService;

    //input
    /**
     * 修复记录ID
     */
    private int repairRecordId;

    //output
    /**
     * 用来切换表格的标记名称
     */
    private String actionName;
    /**
     * 修复记录
     */
    private RepairRecord repairRecord;

    @Route(value = "design-and-repair-unit.json")
    public String unitInfo() {
        try{
            repairRecord = repairRecordService.findRepairRecordById(repairRecordId);
            if (repairRecord == null) {
                return Results.json().asRoot(null).done();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Hibernate4Module());
            Map<String, Object> data = Maps.newHashMap();
            data.put("actionName", actionName);
            data.put("repairRecord", repairRecord);
            String jsonString = objectMapper.writeValueAsString(data);

            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/json;charset=UTF8");
            response.getWriter().print(jsonString);
            log("单位信息记录表","文物修复");
        }catch (Exception e){
            log.error("文物修复，单位信息记录表",e);
        }
        /*return Results.json().excludeProperties("repairRecord.mainUser.roles," +
                "repairRecord.checkUser," +
                "repairRecord.mainUser.logicGroups," +
                "repairRecord.mainUser.logicGroup," +
                "repairRecord.drawRegisters,repairRecord.relic").done();*/
        return null;
    }

    public int getRepairRecordId() {
        return repairRecordId;
    }

    public void setRepairRecordId(int repairRecordId) {
        this.repairRecordId = repairRecordId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
    }
}
