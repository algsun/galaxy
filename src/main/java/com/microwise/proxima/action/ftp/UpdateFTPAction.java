package com.microwise.proxima.action.ftp;

import com.microwise.blackhole.sys.MessageActionUtil;
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
 * 修改FTP
 *
 * @author Wang yunlong
 * @time 13-3-22 下午4:36
 * @check li.jianfei 2013-3-29 #2376
 */
@Beans.Action
@Proxima
public class UpdateFTPAction extends ProximaLoggerAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateFTPAction.class);

    @Autowired
    private FTPProfileService ftpProfileService;
    /**
     * 用于跳转
     */
    private String id;
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

    //output
    /**
     * 跳转ftp对象
     */
    private FTPProfile ftpProfile;

    public String view() {
        try {
            ftpProfile = ftpProfileService.findById(id);
        } catch (Exception e) {
            MessageActionUtil.fail("请求失败");
        }
        return Action.SUCCESS;
    }

    public String execute() {
        FTPProfile ftpProfile = ftpProfileService.findById(id);
        ftpProfile.setName(name);
        ftpProfile.setHost(ip);
        ftpProfile.setPort(port);
        ftpProfile.setUsername(userName);
        ftpProfile.setPassword(password);
        try {
            ftpProfileService.update(ftpProfile);
            MessageActionUtil.success("修改成功");
            log("FTP管理","修改FTP");
            return Action.SUCCESS;
        } catch (Exception e) {
            log.error("ftp修改", e);
            MessageActionUtil.fail("修改异常");
            return Action.INPUT;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public FTPProfile getFtpProfile() {
        return ftpProfile;
    }

    public void setFtpProfile(FTPProfile ftpProfile) {
        this.ftpProfile = ftpProfile;
    }
}
