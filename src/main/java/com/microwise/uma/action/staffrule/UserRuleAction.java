package com.microwise.uma.action.staffrule;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.uma.bean.AbstractUserActionBean;
import com.microwise.uma.bean.PersonBean;
import com.microwise.uma.service.AllotcardService;
import com.microwise.uma.service.UserActionService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 人员行为规则请求页面action
 *
 * @author wang.geng
 * @date 13-4-26
 *
 * @check li.jianfei 2013-5-3   #3133
 */

@Beans.Action
@Uma
public class UserRuleAction extends UmaLoggerAction{

    /**
     * 电子卡管理 服务层
     */
    @Autowired
    private AllotcardService allotcardService;

    /**
     * 人员规则 服务层
     */
    @Autowired
    private UserActionService userActionService;

     //INPUT
    /**
     * 姓名id
     */
    private int userId;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;


    //OUTPUT
    /**
     * 人员列表
     */
    private List<PersonBean> personList;

    /**
     * 人员规则统计列表
     */
    private List<? extends AbstractUserActionBean> staffruleList;

    /**
     * 分页总数
     */
    private int pageCount;


    //INPUT & OUTPUT
    /**
     * 按时间正序或倒序排序:true正序，false倒序
     */
    private boolean order = false;

    /**
     *往返规则是否收缩
     */
    private int showSub = 1;

    /**
     * 当前页数
     */
    private int currentPage = 1;

     //初始化开始时间与结束时间
     {
        startDate = DateTime.now().withTimeAtStartOfDay().toDate();
        endDate = DateTime.now().plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();
     }

    /**
     * 人员行为规则统计
     * @return
     */
    public String execute() {

        try {
            //初始化人员列表
            personList = allotcardService.findAllPersons(Sessions.createByAction().currentLogicGroup().getId(), true);

            //分页相关
            int userCount = userActionService.findCountByUserId(userId, startDate, endDate);
            pageCount = PagingUtil.pagesCount(userCount, Constants.SIZE_PER_PAGE);

            //人员规则列表
            staffruleList = userActionService.findUserActions(userId, startDate, endDate, order, currentPage, Constants.SIZE_PER_PAGE);

            ActionMessage.createByAction().consume();
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务端出错").consume();
            logFailed("人员行为规则统计", "人员行为规则统计失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public List<PersonBean> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonBean> personList) {
        this.personList = personList;
    }

    public List<? extends AbstractUserActionBean> getStaffruleList() {
        return staffruleList;
    }

    public void setStaffruleList(List<? extends AbstractUserActionBean> staffruleList) {
        this.staffruleList = staffruleList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public int getShowSub() {
        return showSub;
    }

    public void setShowSub(int showSub) {
        this.showSub = showSub;
    }
}
