package com.microwise.uma.action.allotcard;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.uma.bean.PersonBean;
import com.microwise.uma.service.AllotcardService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 发放电子卡
 *
 * @author li.jianfei
 * @date 2013-4-11
 * @check @wang yunlong 2013-04-28 #2961
 * @check @li.jianfei 2013-06-04   #4019
 */
@Beans.Action
@Uma
public class QueryUserCardAction extends UmaLoggerAction {

    private static final int NO_CARD_USER = 0; // 未发卡人员
    private static final int HAVE_CARD_USER = 1; // 已发卡人员;


    /**
     * 发卡 service
     */
    @Autowired
    private AllotcardService allotcardService;

    // input
    /**
     * 姓名
     */
    private String userName;

    /**
     * 当前页码
     */
    private Integer index;

    /**
     * 查询低电量卡
     * true-只查询低电量卡；false-查询全部
     */
    private boolean lowPower;

    // output
    /**
     * 查询类型（0-未发卡人员；1-已发卡人员）
     */
    private int queryType;

    /**
     * 人员列表
     */
    private List<PersonBean> personList;

    /**
     * 人员总页数
     */
    private Integer pageCount;

    /**
     * 系统管理资源地址
     */
    private String blackholeResourcesUrl = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images";


    /**
     * 已经发卡人员
     */
    public String haveCardUser() {

        try {
            ActionMessage.createByAction().consume();

            queryType = HAVE_CARD_USER;
            initData(true);
            log("电子卡管理", "查询已发卡人员");
        } catch (Exception e) {
            logFailed("查询已发卡人员出错", "查询已发卡人员出错");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 未发卡人员
     */
    public String noCardUser() {

        try {
            ActionMessage.createByAction().consume();

            queryType = NO_CARD_USER;
            initData(false);
            log("电子卡管理", "未发卡人员");
        } catch (Exception e) {
            logFailed("查询未发卡人员出错", "查询未发卡人员出错");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 初始化人员列表
     *
     * @param isHasCard true-已发卡；false-未发卡
     */
    public void initData(boolean isHasCard) {
        try {
            // 获得siteId 当前站点编号
            String siteId = Sessions.createByAction().currentLogicGroup()
                    .getSite().getSiteId();

            index = (index == null ? 1 : index);
            personList = allotcardService.findPerson(userName, isHasCard, lowPower, Constants.SIZE_PER_PAGE, index, siteId);
            pageCount = PagingUtil.pagesCount(allotcardService.findPersonCount(userName, isHasCard, lowPower, siteId), Constants.SIZE_PER_PAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PersonBean> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonBean> personList) {
        this.personList = personList;
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

    public boolean isLowPower() {
        return lowPower;
    }

    public void setLowPower(int lowPower) {
        this.lowPower = lowPower == 1;
    }

    public String getBlackholeResourcesUrl() {
        return blackholeResourcesUrl;
    }
}
