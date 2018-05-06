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
 * <pre>
 * 删除FTP
 * </pre>
 * 
 * @author Wang yunlong
 * @time 13-3-22 下午4:36
 * @check li.jianfei 2013-3-29 #2330
 */
@Beans.Action
@Proxima
public class DeleteFTPAction extends ProximaLoggerAction {
	private static final Logger log = LoggerFactory
			.getLogger(DeleteFTPAction.class);
	@Autowired
	private FTPProfileService ftpProfileService;
	// input
	/**
	 * 将要删除ftp id
	 */
	private String id;

	public String execute() {
		try {
			FTPProfile ftpProfile = ftpProfileService.findById(id);
			if (ftpProfileService.isUsing(id)) {
				MessageActionUtil.fail("删除失败,该ftp应用中");
				return Action.SUCCESS;
			}
			ftpProfileService.delete(ftpProfile);
			MessageActionUtil.success("删除成功");
            log("FTP管理","删除FTP");
		} catch (Exception e) {
			log.error("删除FTP", e);
			MessageActionUtil.fail("删除失败");
		}
		return Action.SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
