package com.microwise.uma.action.rule;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.bean.RuleBean;
import com.microwise.uma.service.RuleService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaDaemonApiClients;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加/ 修改行为规则 action
 *
 * @author xubaoji
 * @date 2013-4-15
 *
 * @check @wang yunlong 2013-5-6 #3210
 * @check @hou.xiaocheng 2013-06-04 #3754
 */
@Beans.Action
@Uma
public class AddOrUpdateRuleAction extends UmaLoggerAction {

    /**
     * 行为规则service
     */
    @Autowired
    private RuleService ruleService;

    // Input
    /**
     * 行为规则id编号
     */
    private Integer id;

    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 往返规则名称
     */
    private String ruleName;

    /**
     * 往规则名称
     */
    private String goRuleName;

    /**
     * 返规则名称
     */
    private String backRuleName;

    /**
     * 行为规则字符串
     * 数据格式：规则类型，激发器1，激发器2...
     */
    private String exciterIds[];

    /**
     * 行为规则类型
     */
    private int type;


    /**
     * 行为规则激发器
     */
    private String[] deviceId;


    /**
     * 添加行为规则
     */
    public String addRule() {

        try {
            // 添加规则
            ruleService.addRules(assemblyBean());

            // 通知后台程序
            boolean success = UmaDaemonApiClients.getClient().ruleChanged();
            if (success) {
                ActionMessage.createByAction().success("添加行为规则成功！");
            } else {
                ActionMessage.createByAction().fail("添加行为规则成功，通知后台任务失败！");
            }

            log("行为规则管理", "添加行为规则");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务器异常，添加失败！");
            logFailed("行为规则", "添加行为规则");
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    /**
     * 编辑规则
     */
    public String updateRule() {

        try {
            // 编辑规则
            ruleService.updateRules(assemblyBean());

            // 通知后台程序
            boolean success = UmaDaemonApiClients.getClient().ruleChanged();
            if (success) {
                ActionMessage.createByAction().success("修改行为规则成功！");
            } else {
                ActionMessage.createByAction().fail("修改行为规则成功，通知后台任务失败！");
            }
            log("行为规则管理", "修改行为规则");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务器异常，修改失败！");
            logFailed("行为规则", "修改行为规则");
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    /**
     * 处理页面传回的数据
     *
     * @return RuleBean
     */
    private RuleBean assemblyBean() {
        List<DeviceBean> deviceList;
        // 往规则
        List<RuleBean> goRuleList = new ArrayList<RuleBean>();
        // 返规则
        List<RuleBean> backRuleList = new ArrayList<RuleBean>();
        // 单程规则
        List<RuleBean> singleRuleList = new ArrayList<RuleBean>();

        // 获得siteId 当前站点编号
        String siteId = Sessions.createByAction().currentLogicGroup()
                .getSite().getSiteId();

        // 处理父规则
        RuleBean rule = new RuleBean();
        rule.setId(id);
        rule.setType(type);
        rule.setRuleName(ruleName);
        rule.setSiteId(siteId);
        rule.setZoneId(zoneId);

        // 处理子规则
        for (String exciterId : exciterIds) {
            String data[] = (exciterId).split(",");
            RuleBean subRule = new RuleBean();
            if (!data[0].equals("")) {
                subRule.setId(Integer.parseInt(data[0]));
            }
            if (rule.getId() != null) {
                subRule.setParentId(rule.getId());
            }
            subRule.setZoneId(rule.getZoneId());
            subRule.setType(Integer.parseInt(data[1]));    // 规则类型
            subRule.setEnable(data[2].equals("1"));
            if (subRule.isEnable()) {
                rule.setEnable(subRule.isEnable());
            }
            subRule.setSiteId(siteId); // 站点Id

            deviceList = new ArrayList<DeviceBean>();
            for (int j = 3; j < data.length; j++) {
                DeviceBean device = new DeviceBean();
                device.setId(Integer.parseInt(data[j]));
                deviceList.add(device);
            }
            subRule.setDeviceList(deviceList); // 激发器序列

            if (data[1].equals("3")) { // 往
                subRule.setRuleName(goRuleName);   // 站点名称
                goRuleList.add(subRule);
            } else if (data[1].equals("4")) { // 返
                subRule.setRuleName(backRuleName);
                backRuleList.add(subRule);
            } else if (data[1].equals("1")) { // 单程
                subRule.setRuleName(rule.getRuleName());
                singleRuleList.add(subRule);
            }
        }
        rule.setGoRuleList(goRuleList);
        rule.setBackRuleList(backRuleList);
        rule.setSingleRuleList(singleRuleList);
        return rule;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String[] getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String[] deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getGoRuleName() {
        return goRuleName;
    }

    public void setGoRuleName(String goRuleName) {
        this.goRuleName = goRuleName;
    }

    public String getBackRuleName() {
        return backRuleName;
    }

    public void setBackRuleName(String backRuleName) {
        this.backRuleName = backRuleName;
    }

    public String[] getExciterIds() {
        return exciterIds;
    }

    public void setExciterIds(String[] exciterIds) {
        this.exciterIds = exciterIds;
    }
}
