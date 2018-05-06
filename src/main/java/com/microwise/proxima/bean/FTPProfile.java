package com.microwise.proxima.bean;

import com.microwise.blackhole.bean.Site;

/**
 * FTP 链接信息
 * 
 * @author gaohui
 * @date 13-3-19 15:50
 */
public class FTPProfile {

	/** ftp配置id */
	private String id;

	/** 名称 */
	private String name;

	/** 主机(IP) */
	private String host;

	/** 端口 */
	private int port;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** 所属站点 */
	private Site site;

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

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
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
