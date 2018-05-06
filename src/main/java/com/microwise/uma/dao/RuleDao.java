package com.microwise.uma.dao;

import com.microwise.proxima.bean.Zone;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.bean.RuleBean;

import java.util.List;

/**
 * 行为规则dao 接口
 *
 * @author xubaoji
 * @date 2013-4-15
 */
public interface RuleDao {

    /**
     * 分页查询 行为规则
     *
     * @param ruleName   行为规则名称
     * @param ruleType   行为规则类型
     * @param pageSize   分页单位
     * @param pageNumber 当前页码
     * @param siteId     站点编号
     * @return List<RuleBean> 行为规则列表
     * @author 许保吉
     * @date 2013-4-16
     */
    public List<RuleBean> findRule(String ruleName, Integer ruleType,
                                   Integer pageSize, Integer pageNumber, String siteId);

    /**
     * 根据行为规则id 编号查询组成该行为规则的设备
     *
     * @param ruleId 行为规则编号
     * @return List<DeviceBean> 组成该行为规则的设备列表
     * @author 许保吉
     * @date 2013-4-16
     */
    public List<DeviceBean> findRuleDevice(Integer ruleId);

    /**
     * 通过id 编号查询行为规则
     *
     * @param id 行为规则编号
     * @return ruleBean 行为规则实体对象
     * @author 许保吉
     * @date 2013-4-16
     */
    public RuleBean findRuleById(Integer id);

    /**
     * 查询行为规则总数量
     *
     * @param ruleName 行为规则名称
     * @param ruleType 行为规则类型
     * @param siteId   站点编号
     * @return integer 数量
     * @author 许保吉
     * @date 2013-4-16
     */
    public Integer findRuleCount(String ruleName, Integer ruleType, String siteId);

    /**
     * 添加行为规则
     *
     * @param rule 行为规则实体对象
     * @return void
     * @author 许保吉
     * @date 2013-4-16
     */
    public void addRule(RuleBean rule);

    /**
     * 添加行为规则激发器
     *
     * @param ruleId   行为规则编号
     * @param deviceId 设备编号
     * @param sequence 序列
     * @return void
     * @author 许保吉
     * @date 2013-4-16
     */
    public void addRuleSequence(Integer ruleId, Integer deviceId,
                                Integer sequence);

    /**
     * 删除行为规则 的激发器序列
     *
     * @param ruleId 行为规则编号
     * @return
     * @author 许保吉
     * @date 2013-4-16
     */
    public void deleteRuleSequence(Integer ruleId);

    /**
     * 修改行为规则
     *
     * @param rule 行为规则实体对象
     * @return void
     * @author 许保吉
     * @date 2013-4-16
     */
    public void updateRule(RuleBean rule);

    /**
     * 停用启用行为规则
     *
     * @param id     行为规则ID
     * @param enable true-启用；false-停用
     * @author li.jianfei
     * @date 2013-04-28
     */
    public void setRuleEnable(Integer id, boolean enable);

    /**
     * 通过行为规则名称查询
     *
     * @param ruleName 行为规则名称
     * @param siteId   站点编号
     * @return rule 行为规则实体
     * @author 许保吉
     * @date 2013-4-17
     */
    public RuleBean findRuleByName(String ruleName, String siteId);
    
    
    /**
     * 通过规则Id查询规则信息级包含的子规则信息
     * 
     * @param ruleId 规则Id
     * @return RuleBean
     */
    public RuleBean findParentRuleInfo(int ruleId);

    
    /**
     * 修改规则状态级包含子规则的状态
     * 
     * @param ruleId 规则Id
     * @param enable enable状态
     */
    public void updateRuleEnable(Integer ruleId , Integer enable);
    
    
    /**
     * 更新父规则
     * 
     * @param rule 规则bean
     */
    public void updateRuleInfo(RuleBean rule);

    /**
     * 查找站点下所有启用的规则相关区域
     *
     * @param siteId
     * @return
     */
    public List<Zone> findZoneList(String siteId);

    /**
     * 更改区域是否显示
     */
    public void updateRuleIsCount(String zoneId,int isCount);
}
