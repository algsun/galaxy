package com.microwise.orion.bean;

import com.microwise.blackhole.bean.User;

import java.util.Date;

/**
 * 出库事件文档实体
 * 
 * @author xu.baoji
 * @date 2013-8-1
 */
public class OutEventAttachment {

	/*** 文档id */
	private Integer id;

	/** 出库事件实体 */
	private OutEvent outEvent;

	/** 上传该文档的用户 */
	private User user;

	/** 文档存储路径 */
	private String path;

	/** 文档上传日期 */
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OutEvent getOutEvent() {
		return outEvent;
	}

	public void setOutEvent(OutEvent outEvent) {
		this.outEvent = outEvent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
