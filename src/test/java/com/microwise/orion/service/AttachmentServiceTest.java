package com.microwise.orion.service;

import java.util.Date;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Attachment;
import com.microwise.orion.bean.Relic;

/**
 * 文物 挂接文档service 测试
 * 
 * @author xubaoji
 * @date 2013-5-17
 * 
 * @check 2013-06-04 zhangpeng svn:3664
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AttachmentServiceTest extends CleanDBTest {

	/** 文物挂接文档service */
	@Autowired
	private AttachmentService attachmentService;

	@Before
	public void before() throws Exception {
		xmlInsert2("common/dbxml/orion/AttachmentServiceTest.xml");
	}

	/**
	 * 文物挂接文档 添加测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 */
	@Test
	public void testAdd() {
		Attachment attachment = new Attachment();
		attachment.setAttachmentDate(new Date());
		attachment.setPath("cc.txt");
		Relic relic = new Relic();
		relic.setId(1);
		attachment.setRelic(relic);
		attachmentService.addAttachment(attachment);
		Attachment attachment2 = attachmentService.findById(attachment.getId());
		Assert.assertNotNull(attachment2);
		Assert.assertEquals("cc.txt", attachment2.getPath());
	}

	/**
	 * 文物挂接文档 删除测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 */
	@Test
	public void testDelete() {
		attachmentService.deleteAttachment(1);
		Attachment attachment = attachmentService.findById(1);
		Assert.assertNull("测试删除挂接文档报错了", attachment);
	}
}
