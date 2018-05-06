package com.microwise.orion.action.relic;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 *       AJAX请求，验证总登记号是否在系统中已存在
 * </pre>
 *
 * @author Wang rensong
 * @time 13-5-21
 */
@Beans.Action
@Orion
public class RelicTotalCodeExistAction {

    @Autowired
    private RelicService relicService;

    //Input
    /**
     * 文物总登记号
     */
    private String totalCode;

    //Output
    /**
     * 总登记号存在与否
     */
    private boolean exist;

    /**
     * 验证总登记号是否在系统中已存在
     *
     *          exist :true  存在
     *                 false 不存在
     *
     * @return
     */
    public String execute() {

        try {
            // 获得siteId 当前站点编号
            String siteId = Sessions.createByAction().currentSiteId();
            // TODO
//            Relic relic = relicService.findRelicByRelicId(totalCode, siteId);
//            if (relic == null) {
//                exist = false;
//            } else {
//                exist = true;
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }


    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}

