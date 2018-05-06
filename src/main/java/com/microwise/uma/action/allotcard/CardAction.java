package com.microwise.uma.action.allotcard;

import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.service.AllotcardService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaDaemonApiClients;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 电子卡 action 用于 换卡，退卡，发卡
 *
 * @author xubaoji
 * @date 2013-4-22
 *
 * @check @wang yunlong 2013-04-28 #3076
 * @check @li.jianfei 2013-06-04 #3209
 */
@Beans.Action
@Uma
public class CardAction extends UmaLoggerAction {

    /**
     * 发卡service
     */
    @Autowired
    private AllotcardService allotcardService;

    // Input

    /**
     * 人员ID
     */
    private Integer personId;
    /**
     * 卡序号
     */
    private String cardSn;

    /**
     * 发卡
     */
    public String sendCard() {
        try {

            allotcardService.sendCardForPerson(personId, cardSn);

            boolean success = UmaDaemonApiClients.getClient().userCardChanged();
            if (success) {
                ActionMessage.createByAction().success("发卡成功!");
            } else {
                ActionMessage.createByAction().fail("发卡成功" + "，通知后台任务失败！");
            }
            log("电子卡管理", "为人员发卡");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务器异常，发卡失败！");
            logFailed("发放电子卡", "为人员发卡");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 换卡
     */
    public String changeCard() {
        try {

            allotcardService.changeCard(personId, cardSn);

            if (UmaDaemonApiClients.getClient().userCardChanged()) {
                ActionMessage.createByAction().success("换卡成功！");
            } else {
                ActionMessage.createByAction().fail("通知后台失败，但换卡成功！");
            }

            log("电子卡管理", "为人员换卡");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务器异常,换卡失败！");
            logFailed("发放电子卡", "为人员换卡");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 退卡
     */
    public String recedeCard() {
        try {

            allotcardService.recedeCard(personId);

            if (UmaDaemonApiClients.getClient().userCardChanged()) {
                ActionMessage.createByAction().success("退卡成功！");
            } else {
                ActionMessage.createByAction().fail("通知后台失败，但退卡成功！");
            }
            log("电子卡管理", "人员退卡");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务器异常，退卡失败！");
            logFailed("发放电子卡", "人员退卡");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getCardSn() {
        return cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }
}
