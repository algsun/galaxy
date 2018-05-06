package com.microwise.uma.service.impl;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.proxima.bean.Zone;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.bean.RuleBean;
import com.microwise.uma.dao.RuleDao;
import com.microwise.uma.service.RuleService;
import com.microwise.uma.sys.Uma;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 行为规则 service 实现
 *
 * @author xubaoji
 * @date 2013-4-15
 * @check @li.jianfei 2013-06-04 #3754
 */
@Service
@Uma
public class RuleServiceImpl implements RuleService {

    /**
     * 行为规则dao
     */
    @Autowired
    private RuleDao ruleDao;

    @Override
    public List<RuleBean> findRule(String ruleName, Integer ruleType, Integer pageSize, Integer pageNumber, String siteId) {
        // 查询行为规则 信息 （不携带行为规则激发器信息）
        List<RuleBean> ruleList = ruleDao.findRule(ruleName, ruleType,
                pageSize, pageNumber, siteId);
        List<RuleBean> res = new ArrayList<RuleBean>();

        // 查找子规则及激发器信息
        for (RuleBean rule : ruleList) {
            res.add(findRulesById(rule.getId()));
        }
        return res;
    }

    @Override
    public Integer findRuleCount(String ruleName, Integer ruleType, String siteId) {
        return ruleDao.findRuleCount(ruleName, ruleType, siteId);
    }

    /**
     * 为行为规则 添加激发器序列
     *
     * @param ruleId     行为规则编号
     * @param deviceList 激发器设备列表
     * @return void
     * @author 许保吉
     * @date 2013-4-16
     */
    private void addRuleDevice(Integer ruleId, List<DeviceBean> deviceList) {
        if (deviceList != null) {
            for (int i = 0; i < deviceList.size(); i++) {
                DeviceBean device = deviceList.get(i);
                ruleDao.addRuleSequence(ruleId, device.getId(), i);
            }
        }
    }

    @Override
    public boolean isUsable(String ruleName, Integer id, String siteId) {
        RuleBean rule = ruleDao.findRuleByName(ruleName, siteId);
        if (id == null) {// 添加
            return rule == null;
        } else {
            return rule == null || id.equals(rule.getId());
        }
    }

    @Override
    public void addRules(RuleBean rule) {
        ruleDao.addRule(rule);
        // 添加往规则
        for (RuleBean goRule : rule.getGoRuleList()) {
            goRule.setParentId(rule.getId());
            ruleDao.addRule(goRule);
            addRuleDevice(goRule.getId(), goRule.getDeviceList());
        }

        // 添加返规则
        for (RuleBean backRule : rule.getBackRuleList()) {
            backRule.setParentId(rule.getId());
            ruleDao.addRule(backRule);
            addRuleDevice(backRule.getId(), backRule.getDeviceList());
        }

        // 添加单程规则
        for (RuleBean singleRule : rule.getSingleRuleList()) {
            singleRule.setParentId(rule.getId());
            ruleDao.addRule(singleRule);
            addRuleDevice(singleRule.getId(), singleRule.getDeviceList());
        }
    }

    @Override
    public RuleBean findRulesById(int ruleId) {

        RuleBean rule = ruleDao.findParentRuleInfo(ruleId);

        if (rule == null) {
            return null;
        }
        // 单程
        if (rule.getType().intValue() == RuleBean.TYPE_SINGLE) {
            if (rule.getSingleRuleList() != null
                    && rule.getSingleRuleList().size() > 0) {
                for (RuleBean single : rule.getSingleRuleList()) {
                    single.setDeviceList(ruleDao.findRuleDevice(single.getId()));
                }
            }
        } else { // 往返
            // 往
            if (rule.getGoRuleList() != null && rule.getGoRuleList().size() > 0) {
                for (RuleBean go : rule.getGoRuleList()) {
                    go.setDeviceList(ruleDao.findRuleDevice(go.getId()));
                }
            }
            // 返
            if (rule.getBackRuleList() != null
                    && rule.getBackRuleList().size() > 0) {
                for (RuleBean back : rule.getBackRuleList()) {
                    back.setDeviceList(ruleDao.findRuleDevice(back.getId()));
                }
            }
        }
        return rule;
    }

    @Override
    public void setRuleEnable(Integer id, boolean enable) {
        ruleDao.updateRuleEnable(id, enable ? 1 : 0);
    }

    @Override
    public void updateRules(RuleBean rule) {
        if (rule != null && rule.getId() != null) {
            // 修改父规则信息
            ruleDao.updateRuleInfo(rule);
            // 删除子规则对应的激发器顺序，修改子规则信息，并重新添加激发器顺序
            List<RuleBean> subRules = new ArrayList<RuleBean>();
            subRules.addAll(rule.getSingleRuleList() == null ? new ArrayList<RuleBean>() : rule.getSingleRuleList());
            subRules.addAll(rule.getGoRuleList() == null ? new ArrayList<RuleBean>() : rule.getGoRuleList());
            subRules.addAll(rule.getBackRuleList() == null ? new ArrayList<RuleBean>() : rule.getBackRuleList());
            for (RuleBean subRule : subRules) {
                // 判断子规则是否有Id:没有Id的规则需要add，有Id的直接Update
                if (subRule.getId() == null || subRule.getId().intValue() == 0) {
                    ruleDao.addRule(subRule);
                } else {
                    // 修改子规则信息
                    ruleDao.updateRuleInfo(subRule);
                    // 删除子规则下激发器顺序
                    ruleDao.deleteRuleSequence(subRule.getId());
                }
                // 增加激发器顺序
                addRuleDevice(subRule.getId(), subRule.getDeviceList());
            }
        }
    }

    @Override
    public List<Zone> findZoneList(String siteId) {
        return ruleDao.findZoneList(siteId);
    }

    @Override
    public void updateRulesIsCount(String[] zoneIds) {
        String siteId = Sessions.createByAction().currentSiteId();
        List<Zone> zones = findZoneList(siteId);
        if (zoneIds != null) {
            List<String> zoneIdsList = Arrays.asList(zoneIds);
            for (Zone zone : zones) {
                if (zoneIdsList.contains(zone.getId())) {
                    ruleDao.updateRuleIsCount(zone.getId(), 1);
                } else {
                    ruleDao.updateRuleIsCount(zone.getId(), 0);
                }
            }
        } else {
            for (Zone zone : zones) {
                ruleDao.updateRuleIsCount(zone.getId(), 0);
            }
        }
    }
}
