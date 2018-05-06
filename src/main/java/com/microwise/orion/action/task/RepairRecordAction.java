package com.microwise.orion.action.task;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.orion.bean.*;
import com.microwise.orion.service.InstitutionService;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.service.RepairReasonService;
import com.microwise.orion.service.RepairRecordService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 文物修复登记
 *
 * @author lijianfei
 * @date 2015-09-16
 */

@Beans.Action
@Orion
@Route("orion")
public class RepairRecordAction {

    @Autowired
    private RelicService relicService;

    @Autowired
    private RepairReasonService repairReasonService;

    @Autowired
    private RepairRecordService repairRecordService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private UserService userService;

    private Relic relic;

    private RepairRecord repairRecord;

    private List<RepairReason> repairReasons;

    private List<RepairRecord> repairRecords;

    private Institution institution;

    private String siteId = Sessions.createByAction().currentSiteId();

    private int userId;

    /**
     * 当前页
     */
    private Integer index = 0;


    /**
     * 分页总数
     */
    private Integer pageCount;


    @Route(value = "repairRecords", method = MethodType.GET)
    public String records() {
        repairRecords = repairRecordService.findBySiteId(siteId);
        pageCount = PagingUtil.pagesCount(repairRecords.size(), Constants.SIZE_PER_PAGE);
        repairRecords = repairRecordService.findRepairRecords(index, Constants.SIZE_PER_PAGE, siteId);

        for (RepairRecord record : repairRecords) {
            if (record.getSecondaryUserId() != null) {
                String[] userIds = record.getSecondaryUserId().split(",");
                List<String> userNames = new ArrayList<String>();
                for (String id : userIds) {
                    int userId = Integer.parseInt(id.trim());
                    User users = userService.findUserById(userId);
                    userNames.add(users.getUserName());
                }
                record.setSecondaryUserName(userNames);
            }
        }
        //总页数
        ActionMessage.createByAction().consume();
        userId = Sessions.createByAction().currentUser().getId();
        return Results.ftl("orion/pages/task/task.ftl");
    }

    @Route(value = "repairRecords/new")
    public String newRecord() {
        relic = relicService.findRelicByRelicId(relic.getId(), siteId);
        repairReasons = repairReasonService.findAll(siteId);
        // 查询收藏单位
        if (relic.getRelicPropertyMap().containsKey("institution")) {
            RelicProperty property = relic.getRelicPropertyMap().get("institution");
            int institutionId = Integer.parseInt(property.getPropertyValue());
            institution = institutionService.findById(institutionId);
        }
        return Results.ftl("orion/pages/task/add-repair-record.ftl");
    }

    @Route(value = "repairRecords/edit")
    public String editRecord() {
        repairRecord = repairRecordService.findById(repairRecord.getId());
        relic = relicService.findRelicByRelicId(repairRecord.getRelic().getId(), siteId);
        repairReasons = repairReasonService.findAll(siteId);
        // 查询收藏单位
        if (relic.getRelicPropertyMap().containsKey("institution")) {
            RelicProperty property = relic.getRelicPropertyMap().get("institution");
            int institutionId = Integer.parseInt(property.getPropertyValue());
            institution = institutionService.findById(institutionId);
        }
        return Results.ftl("orion/pages/task/edit-repair-record.ftl");
    }

    @Route(value = "repairRecords", method = MethodType.POST)
    public String saveOrUpdate() {
        try {
            repairRecord.setSiteId(Sessions.createByAction().currentSiteId());
            if (repairRecord.getIdentifier() == 0) {
                repairRecord.setIdentifier(repairRecordService.getIdentifier());
            }
            repairRecord.setState(RepairRecord.unAllocation);
            repairRecordService.saveOrUpdate(repairRecord);
            ActionMessage.createByAction().success("添加记录成功!");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("添加记录失败!");
            e.printStackTrace();
        }
        return Results.redirect("repairRecords");
    }

    @Route(value = "repairRecords/delete")
    public String delete() {
        try {
            repairRecordService.delete(repairRecord);
            ActionMessage.createByAction().success("删除记录成功!");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("删除记录失败!");
            e.printStackTrace();
        }
        return Results.redirect("../repairRecords");
    }

    @Route(value = "repairRecords/updateReturnDate")
    public String updateReturnDate() {
        try {
            RepairRecord record = repairRecordService.findById(repairRecord.getId());
            record.setReturnDate(repairRecord.getReturnDate());
            repairRecordService.update(record);
            ActionMessage.createByAction().success("修改返回时间成功!");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("修改返回时间失败!");
            e.printStackTrace();
        }
        return Results.redirect("../repairRecords");
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
    }

    public List<RepairReason> getRepairReasons() {
        return repairReasons;
    }

    public void setRepairReasons(List<RepairReason> repairReasons) {
        this.repairReasons = repairReasons;
    }

    public List<RepairRecord> getRepairRecords() {
        return repairRecords;
    }

    public void setRepairRecords(List<RepairRecord> repairRecords) {
        this.repairRecords = repairRecords;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
