package com.microwise.biela.action;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.biela.service.MapIndexService;
import com.microwise.biela.sys.BielaLoggerAction;
import com.microwise.common.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 删除监测指标定制action
 *
 * @author liuzhu
 * @date 14-1-11
 * @check @wang.geng #7676 2014-1-17
 */
public class DeleteCustomizeAction {

    private static final Logger log = LoggerFactory.getLogger(DeleteCustomizeAction.class);

    /**
     * 站点地图service
     */
    @Autowired
    private MapIndexService mapIndexService;

    /**
     * 比拉日志操作类
     */
    @Autowired
    private BielaLoggerAction logger;

    /**
     * 定制id
     */
    private int customizeId;

    /**
     * 站点id
     */
    private String siteId;


    @Route("/biela/deleteCustomize/{customizeId}/{siteId}")
    public String deleteCustomize() {
        try {
            mapIndexService.deleteCustomizeById(customizeId);
            ActionMessage.createByAction().success("删除成功");
        } catch (Exception e) {
            log.error("删除定制失败", e);
            ActionMessage.createByAction().fail("删除失败");
        }
        logger.log("监测指标定制", "删除定制");
        return Results.redirect("/biela/customize/" + siteId);
    }

    public int getCustomizeId() {
        return customizeId;
    }

    public void setCustomizeId(int customizeId) {
        this.customizeId = customizeId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
