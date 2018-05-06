package com.microwise.uma.dao.impl;

import com.google.common.base.Strings;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.bean.Zone;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.bean.RuleBean;
import com.microwise.uma.dao.RuleDao;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 行为规则 dao 实现
 *
 * @author xubaoji
 * @date 2013-4-15
 */
@Dao
@Uma
public class RuleDaoImpl extends UmaBaseDao implements RuleDao {

    @Override
    public List<RuleBean> findRule(String ruleName, Integer ruleType,
                                   Integer pageSize, Integer pageNumber, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (!Strings.isNullOrEmpty(ruleName)) {
            paramMap.put("ruleName", "%" + ruleName + "%");
        }
        paramMap.put("ruleType", ruleType);
        paramMap.put("start", (pageNumber - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("siteId", siteId);
        return getSqlSession().selectList("uma.mybatis.RuleDao.findRule", paramMap);
    }

    @Override
    public Integer findRuleCount(String ruleName, Integer ruleType, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (!Strings.isNullOrEmpty(ruleName)) {
            paramMap.put("ruleName", "%" + ruleName + "%");
        }
        paramMap.put("ruleType", ruleType);
        paramMap.put("siteId", siteId);
        return getSqlSession().selectOne("uma.mybatis.RuleDao.findRuleCount", paramMap);
    }

    @Override
    public List<DeviceBean> findRuleDevice(Integer ruleId) {
        return getSqlSession().selectList("uma.mybatis.RuleDao.findRuleDevice", ruleId);
    }

    @Override
    public RuleBean findRuleById(Integer id) {
        return getSqlSession().selectOne("uma.mybatis.RuleDao.findRuleById", id);
    }

    @Override
    public void addRule(RuleBean rule) {
        getSqlSession().insert("uma.mybatis.RuleDao.addRule", rule);

    }

    @Override
    public void addRuleSequence(Integer ruleId, Integer deviceId, Integer sequence) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ruleId", ruleId);
        paramMap.put("deviceId", deviceId);
        paramMap.put("sequence", sequence);
        getSqlSession().insert("uma.mybatis.RuleDao.addRuleSequence", paramMap);
    }

    @Override
    public void deleteRuleSequence(Integer ruleId) {
        getSqlSession().delete("uma.mybatis.RuleDao.deleteRuleSequence", ruleId);

    }

    @Override
    public void updateRule(RuleBean rule) {
        getSqlSession().update("uma.mybatis.RuleDao.updateRule", rule);
    }

    @Override
    public void setRuleEnable(Integer id, boolean enable) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("enable", enable ? 1 : 0);
        getSqlSession().update("uma.mybatis.RuleDao.setRuleEnable", paramMap);
    }

    @Override
    public RuleBean findRuleByName(String ruleName, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ruleName", ruleName);
        paramMap.put("siteId", siteId);
        return getSqlSession().selectOne("uma.mybatis.RuleDao.findRuleByName", paramMap);
    }

    @Override
    public RuleBean findParentRuleInfo(int ruleId) {
        RuleBean parentRule = getSqlSession().selectOne("uma.mybatis.RuleDao.findParentRuleById", ruleId);
        if (parentRule == null || parentRule.getRuleName() == null) {
            return null;
        }

        //单程规则查询子规则
        if (parentRule.getType() == RuleBean.TYPE_SINGLE) {
            Map<String, Integer> params = new TreeMap<String, Integer>();
            params.put("parentId", parentRule.getId());
            params.put("type", RuleBean.TYPE_SINGLE); // 子规则
            List<RuleBean> singles = getSqlSession().selectList("uma.mybatis.RuleDao.findSubRulesByParentId", params);
            parentRule.setSingleRuleList(singles);
        }
        //往返规则需要查询 往 和 返
        else {
            Map<String, Integer> params = new TreeMap<String, Integer>();
            params.put("parentId", parentRule.getId());
            params.put("type", RuleBean.TYPE_GO); // 往规则
            List<RuleBean> goRules = getSqlSession().selectList("uma.mybatis.RuleDao.findSubRulesByParentId", params);
            parentRule.setGoRuleList(goRules);

            params.put("type", RuleBean.TYPE_BACK); // 返规则
            List<RuleBean> backRules = getSqlSession().selectList("uma.mybatis.RuleDao.findSubRulesByParentId", params);
            parentRule.setBackRuleList(backRules);
        }

        return parentRule;
    }

    @Override
    public void updateRuleEnable(Integer ruleId, Integer enable) {
        Map<String, Integer> params = new TreeMap<String, Integer>();
        params.put("ruleId", ruleId); // 规则Id
        params.put("enable", enable);
        getSqlSession().update("uma.mybatis.RuleDao.updateRuleEnable", params);
    }

    @Override
    public void updateRuleInfo(RuleBean rule) {
        getSqlSession().update("uma.mybatis.RuleDao.updateRuleInfo", rule);
    }

    @Override
    public List<Zone> findZoneList(String siteId) {
        return getSqlSession().selectList("uma.mybatis.RuleDao.findZoneList", siteId);
    }

    @Override
    public void updateRuleIsCount(String zoneId, int isCount) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("zoneId", zoneId);
        map.put("isCount", isCount);
        getSqlSession().update("uma.mybatis.RuleDao.updateRuleIsCount", map);
    }
}
