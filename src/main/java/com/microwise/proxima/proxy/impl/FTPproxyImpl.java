package com.microwise.proxima.proxy.impl;

import com.microwise.proxima.bean.FTPProfile;
import com.microwise.proxima.proxy.FTPproxy;
import com.microwise.proxima.service.FTPProfileService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ftp 服务代理实现
 * 
 * @author xu.baoji
 * @date 2013-7-25
 * 
 * @check @duan.qixin 2013年7月29日 #4680
 */
@Component
@Scope("prototype")
@Proxima
public class FTPproxyImpl implements FTPproxy {
	
	/**ftp 服务service*/
	@Autowired
	private  FTPProfileService ftpProfileService;
	
	@Override
	public List<FTPProfile> findAll(String siteId) {
		
		return ftpProfileService.findAll(siteId);
	}
	
	@Override
	public String testConnect(FTPProfile ftp) {
		return ftpProfileService.testConnect(ftp);
	}


}
