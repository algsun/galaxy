package com.microwise.uma.service;

import com.microwise.proxima.bean.Zone;
import com.microwise.uma.bean.RuleBean;

import java.util.List;

/**
 * 行为规则 service 接口
 *
 * @author xubaoji
 * @date 2013-4-15
 */
public interface RuleService {

    /**
     * 分页查询 行为规则
     *
     * @param ruleName   行为规则名称
     * @param ruleType   行为规则类型
     * @param pageSize   分页单位
     * @param pageNumber 当前页码
     * @param siteId     站点
     * @return List<RuleBean> 行为规则列表
     * @author 许保吉
     * @date 2013-4-16
     */
    public List<RuleBean> findRule(String ruleName, Integer ruleType,
                                   Integer pageSize, Integer pageNumber, String siteId);

    /**
     * 查询行为规则总数量
     *
     * @param ruleName 行为规则名称
     * @param ruleType 行为规则类型
     * @param siteId   站点
     * @return integer 数量
     * @author 许保吉
     * @date 2013-4-16
     */
    public Integer findRuleCount(String ruleName, Integer ruleType, String siteId);

    /**
     * 判断行为规则名是否可用
     *
     * @param ruleName 行为规则名称
     * @param id       行为规则id
     * @param siteId   隶属站点
     * @return true 存在 ，false 不存在
     * @author 许保吉
     * @date 2013-4-17
     */
    public boolean isUsable(String ruleName, Integer id, String siteId);

    /**
     * 添加行为规则
     *
     * @param rule 一组行为规则集合(单程/往返)
     */
    public void addRules(RuleBean rule);

    /**
     * 更新父规则信息级子规则关联激发器顺序
     *
     * @param rule 父规则
     */
    public void updateRules(RuleBean rule);

    /**
     * 根据规则ID查询当前规则及其子规则列表
     *
     * @param ruleId 规则ID
     * @return 规则集合
     */
    public RuleBean findRulesById(int ruleId);

    /**
     * 停用启用行为规则
     * (有子规则时同时停用或启用所有子规则)
     *
     * @param id     行为规则ID
     * @param enable true-启用；false-停用
     */
    public void setRuleEnable(Integer id, boolean enable);

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
    public void updateRulesIsCount(String[] zoneIds);
}
