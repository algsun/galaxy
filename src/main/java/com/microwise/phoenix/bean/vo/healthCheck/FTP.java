package com.microwise.phoenix.bean.vo.healthCheck;

import com.microwise.proxima.bean.FTPProfile;

/**
 * 系统检测 异常ftp 实体
 *
 * @author xu.baoji
 * @date 2013-7-25
 */
public class FTP {

    /**
     * ftp配置id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 主机(IP)
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * ftp 异常信息
     */
    private String errorInfo;

    public FTP() {
        super();
    }

    public FTP(String errorInfo, FTPProfile ftp) {
        super();
        this.errorInfo = errorInfo;
        this.host = ftp.getHost();
        this.id = ftp.getId();
        this.name = ftp.getName();
        this.password = ftp.getPassword();
        this.port = ftp.getPort();
        this.username = ftp.getUsername();
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
