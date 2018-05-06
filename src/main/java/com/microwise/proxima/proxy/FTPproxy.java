package com.microwise.proxima.proxy;

import com.microwise.proxima.bean.FTPProfile;

import java.util.List;

/**
 * ftp 代理层
 * 
 * @author xu.baoji
 * @date 2013-7-25
 * 
 * @check @duan.qixin 2013年7月29日 #4680
 */
public interface FTPproxy {
	
	/**
	 * <pre>
	 * 查询所有ftp服务器配置对象
	 * 
	 * @return List<FTPProfile> ftp配置对象列表
	 * </pre>
	 */
	public List<FTPProfile> findAll(String siteId);
	
	/**
	 * <pre>
	 * 判断ftp服务器是否畅通
	 * 
	 * @param ftp ftp配置对象
	 * 
	 * @return String 
	 * 当返回值为null时，表示通畅
	 * 当返回值为具体字符串时，表示异常
	 * </pre>
	 */
	public String testConnect(FTPProfile ftp);

}
