package com.microwise.proxima.service;

import com.microwise.blackhole.bean.Site;
import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.proxima.bean.FTPProfile;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 摄像机点位Service
 * 
 * @author zhangpeng
 * @date 2013-3-25
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FTPProfileServiceTest extends CleanDBTest {

	/** 摄像机Service */
	@Autowired
	private FTPProfileService ftpProfileService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/proxima/FTPProfileServiceTest.xml");
	}

	/**
	 * 测试根据id查询ftp配置
	 * 
	 * @author zhangpeng
	 * @date 2013-3-27
	 */
	@Test
	public void testFindByid() {
		FTPProfile ftp = ftpProfileService.findById("001");
		Assert.assertEquals("ftp配置测试", ftp.getName());
	}

	/**
	 * 测试查询所有ftp配置
	 * 
	 * @author zhangpeng
	 * @date 2013-3-27
	 */
	@Test
	public void testFindAll() {
		List<FTPProfile> ftpList = ftpProfileService.findAll("11010101");
		Assert.assertEquals(3, ftpList.size());
	}

	/**
	 * 测试添加ftp配置
	 * 
	 * @author zhangpeng
	 * @date 2013-3-27
	 */
	@Test
	public void testSave() {
		FTPProfile ftp = new FTPProfile();
		String id = GalaxyIdUtil.get64UUID();
		String name = "测试2ftp";
		ftp.setId(id);
		ftp.setName(name);
		Site site = new Site();
		site.setSiteId("11010101");
		ftp.setSite(site);
		ftpProfileService.save(ftp);
		FTPProfile ftp1 = ftpProfileService.findById(id);
		Assert.assertEquals(id, ftp1.getId());
		Assert.assertEquals(name, ftp1.getName());
	}

	/**
	 * 删除ftp配置
	 * 
	 * @author zhangpeng
	 * @date 2013-3-27
	 */
	@Test
	public void testDelete() {
		FTPProfile ftp = new FTPProfile();
		ftp.setId("002");
		ftpProfileService.delete(ftp);
		FTPProfile ftp1 = ftpProfileService.findById("002");
		Assert.assertNull(ftp1);
	}

	/**
	 * 更新ftp配置
	 * 
	 * @author zhangpeng
	 * @date 2013-3-27
	 */
	@Test
	public void testUpdate() {
		FTPProfile ftp = ftpProfileService.findById("003");
		String name = "新的冏啊";
		ftp.setName(name);
		Site site = new Site();
		site.setSiteId("11010102");
		ftp.setSite(site);
		ftpProfileService.update(ftp);
		FTPProfile ftp1 = ftpProfileService.findById("003");
		Assert.assertEquals(name, ftp1.getName());
	}

	/**
	 * 测试查询所有ftp配置
	 * 
	 * @author zhangpeng
	 * @date 2013-3-27
	 */
	@Test
	public void testIsUsing() {
		boolean bool = ftpProfileService.isUsing("003");
		Assert.assertTrue(bool);
	}

	/**
	 * 测试ftp畅通
	 * 
	 * @author zhangpeng
	 * @date 2013-3-27
	 */
	@Test
	public void testTestConnect() {
		FTPProfile ftp = new FTPProfile();
		ftp.setHost("192.168.0.100");
		ftp.setPort(8080);
		ftp.setUsername("admin");
		ftp.setPassword("admin");
		String result = ftpProfileService.testConnect(ftp);
		Assert.assertEquals("FTP服务器无法访问", result);
	}

}
