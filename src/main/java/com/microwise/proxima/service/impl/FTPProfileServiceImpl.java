package com.microwise.proxima.service.impl;

import com.microwise.proxima.bean.FTPProfile;
import com.microwise.proxima.dao.FTPProfileDao;
import com.microwise.proxima.service.FTPProfileService;
import com.microwise.proxima.sys.Proxima;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * ftp配置Service实现类
 * 
 * @author wnagyunlong
 * @date 2013-3-26
 */
@Service
@Transactional
@Proxima
public class FTPProfileServiceImpl implements FTPProfileService {

	/** ftp配置Dao层 */
	@Autowired
	private FTPProfileDao ftpProfileDao;

	@Override
	public void save(FTPProfile ftp) {
		ftpProfileDao.save(ftp);
	}

	@Override
	public void update(FTPProfile ftp) {
		ftpProfileDao.update(ftp);
	}

	@Override
	public FTPProfile findById(String ftpId) {
		return ftpProfileDao.findById(ftpId);
	}

	@Override
	public List<FTPProfile> findAll(String siteId) {
		return ftpProfileDao.findAll(siteId);
	}

	@Override
	public void delete(FTPProfile ftp) {
		ftpProfileDao.delete(ftp);
	}

	@Override
	public String testConnect(FTPProfile ftp) {
		FTPClient client = new FTPClient();
		String CONNECT_EXCEPTION = "FTP服务器无法访问";
		String LOGIN_EXCEPTION = "用户名或密码不正确";
		// 连接
		try {
			client.connect(ftp.getHost(), ftp.getPort());
		} catch (IllegalStateException e) {
			return CONNECT_EXCEPTION;
		} catch (IOException e) {
			return CONNECT_EXCEPTION;
		} catch (FTPIllegalReplyException e) {
			return CONNECT_EXCEPTION;
		} catch (FTPException e) {
			return CONNECT_EXCEPTION;
		}
		// 登陆
		try {
			client.login(ftp.getUsername(), ftp.getPassword());
		} catch (IllegalStateException e) {
			return LOGIN_EXCEPTION;
		} catch (IOException e) {
			return LOGIN_EXCEPTION;
		} catch (FTPIllegalReplyException e) {
			return LOGIN_EXCEPTION;
		} catch (FTPException e) {
			return LOGIN_EXCEPTION;
		} finally {
			try {
				client.disconnect(true);
                // 此处故意不处理, 退出失败不影响结果
			} catch (IllegalStateException e) {
			} catch (IOException e) {
			} catch (FTPIllegalReplyException e) {
			} catch (FTPException e) {
			}
		}
		return null;
	}

	@Override
	public boolean isUsing(String id) {
		return ftpProfileDao.isUsing(id);
	}
}
