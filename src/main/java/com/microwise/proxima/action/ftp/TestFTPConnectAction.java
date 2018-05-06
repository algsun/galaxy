package com.microwise.proxima.action.ftp;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.FTPProfile;
import com.microwise.proxima.service.FTPProfileService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * 测试FTP连接状态
 * </pre>
 *
 * @author Wang yunlong
 * @time 13-3-27 下午2:09
 * @check li.jianfei 2013-3-29 #2357
 */
@Beans.Action
@Proxima
public class TestFTPConnectAction extends ProximaLoggerAction {

    private static final Logger log = LoggerFactory.getLogger(TestFTPConnectAction.class);
    @Autowired
    private FTPProfileService ftpProfileService;
    /**
     * ftp地址
     */
    private String ip;
    /**
     * 端口
     */
    private int port;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

    /**
     * 测试 反馈
     */
    private String msg;

    public String execute() {
        try {
            FTPProfile ftpProfile = new FTPProfile();
            ftpProfile.setHost(ip);
            ftpProfile.setPort(port);
            ftpProfile.setUsername(userName);
            ftpProfile.setPassword(password);
            msg = ftpProfileService.testConnect(ftpProfile);
            log("添加FTP", "测试FTP连接");
        } catch (Exception e) {
            log.error("测试FTP连接", e);
        }
        return Action.SUCCESS;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

