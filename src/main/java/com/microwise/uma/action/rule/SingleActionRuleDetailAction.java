package com.microwise.uma.action.rule;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.uma.bean.UserActionBean;
import com.microwise.uma.service.UserActionService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 行为规则显示 页面跳转 action
 *
 * @author wangrensong
 * @date 2013-4-27
 *
 * @check li.jianfei 2013-5-3 #3107
 */
@Beans.Action
@Uma
public class SingleActionRuleDetailAction extends UmaLoggerAction {

    /**
     * 用户行为规则service
     */
    @Autowired
    private UserActionService userActionService;

    //  Input & Output
    /**
     * 行为规则id
     */
    private Integer ruleId;

    /**
     * 按年月日类型查询  年：1 月：2  日：3
     */
    private int dateType;

    /**
     * 时间
     */
    private Date date;

    /**
     * 过滤条件
     */
    private String ruleFilter;

    // Output
    /**
     * 单程行为规则列表
     */
    private List<UserActionBean> userActionList;

    /**
     * 行为规则名称
     */
    private String ruleName;

    //Input
    /**
     * 当前页码
     */
    private int index;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * *true occurrenceTime 正序, false occurrenceTime 逆序
     */
    private boolean order;

    /**
     * 行为规则显示 页面跳转
     * @return
     */
    public String execute() {
        try {
            // 默认查询当天数据
            dateType = (dateType == 0) ? 3 : dateType;

            // 当前页数
            index = (index == 0) ? 1 : index;

            // 计算总页数
            int count = userActionService.findCountByRuleId(ruleId, dateType, date);
            pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);

            userActionList = userActionService.findSingleActionsByRuleId(ruleId, dateType, date, index,
                    Constants.SIZE_PER_PAGE, true);
            log("统计分析","查询行为规则详情");
        } catch (Exception e) {
            logFailed("查询单程行为规则详情出错！", "查询行为规则详情失败");
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<UserActionBean> getUserActionList() {
        return userActionList;
    }

    public void setUserActionList(List<UserActionBean> userActionList) {
        this.userActionList = userActionList;
    }

    public UserActionService getUserActionService() {
        return userActionService;
    }

    public void setUserActionService(UserActionService userActionService) {
        this.userActionService = userActionService;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleFilter() {
        return ruleFilter;
    }

    public void setRuleFilter(String ruleFilter) {
        this.ruleFilter = ruleFilter;
    }
}
