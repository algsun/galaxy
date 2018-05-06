package com.microwise.proxima.action.ftp;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Site;
import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.proxima.bean.FTPProfile;
import com.microwise.proxima.service.FTPProfileService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 添加ftp
 *
 * @author Wang yunlong
 * @time 13-3-22 下午4:35
 *
 * @check li.jianfei 2013-3-29 #2286
 */
@Beans.Action
@Proxima
public class AddFTPAction extends ProximaLoggerAction {
    private static final Logger log = LoggerFactory.getLogger(AddFTPAction.class);
    @Autowired
    private FTPProfileService ftpProfileService;
    //input
    /**
     * ftp名称
     */
    private String name;
    /**
     * ftp ip地址
     */
    private String ip;
    /**
     * 端口, 默认 21
     */
    private int port = 21;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

    public String view() {
        return Action.SUCCESS;
    }

    public String execute() {
        FTPProfile ftpProfile = new FTPProfile();
        ftpProfile.setId(GalaxyIdUtil.get64UUID());
        ftpProfile.setName(name);
        ftpProfile.setHost(ip);
        ftpProfile.setPort(port);
        ftpProfile.setUsername(userName);
        ftpProfile.setPassword(password);
        LogicGroup logicGroup = Sessions.createByAction().currentLogicGroup();
        if (logicGroup == null) {
            MessageActionUtil.fail("无站点");
            return Action.INPUT;
        } else {
            Site site = logicGroup.getSite();
            ftpProfile.setSite(site);
            if (site == null) {
                MessageActionUtil.fail("当前站点不能添加ftp");
                return Action.INPUT;
            }
        }
        try {
            ftpProfileService.save(ftpProfile);
            MessageActionUtil.success("添加成功");
            log("FTP管理","添加FTP");
            return Action.SUCCESS;
        } catch (Exception e) {
            log.error("ftp添加", e);
            MessageActionUtil.fail("添加异常");
            return Action.INPUT;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
