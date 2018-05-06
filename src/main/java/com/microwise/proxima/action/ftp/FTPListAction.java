package com.microwise.proxima.action.ftp;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Site;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.FTPProfile;
import com.microwise.proxima.service.FTPProfileService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询FTP
 *
 * @author Wang yunlong
 * @time 13-3-22 下午3:53
 * @check li.jianfei 2013-3-29 #2349
 */
@Beans.Action("FTPListAction")
@Proxima
public class FTPListAction extends ProximaLoggerAction {

    private static final Logger log = LoggerFactory.getLogger(FTPListAction.class);

    @Autowired
    private FTPProfileService ftpProfileService;
    //output
    /**
     * ftp列表
     */
    private List<FTPProfile> ftpProfiles;

    public String execute() {
        try {
            ftpProfiles = new ArrayList<FTPProfile>();
            LogicGroup logicGroup = Sessions.createByAction().currentLogicGroup();
            if (logicGroup != null) {
                Site site = logicGroup.getSite();
                if (site != null) {
                    String siteId = site.getSiteId();
                    ftpProfiles = ftpProfileService.findAll(siteId);
                }
            }
            ActionMessage.createByAction().consume();
            log("FTP管理", "FTP列表");
        } catch (Exception e) {
            log.error("FTP列表", e);
        }
        return Action.SUCCESS;
    }

    public List<FTPProfile> getFtpProfiles() {
        return ftpProfiles;
    }

    public void setFtpProfiles(List<FTPProfile> ftpProfiles) {
        this.ftpProfiles = ftpProfiles;
    }
}
