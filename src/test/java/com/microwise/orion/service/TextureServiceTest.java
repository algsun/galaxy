package com.microwise.orion.service;

import com.microwise.common.sys.test.BaseTest;
import com.microwise.orion.bean.Texture;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 质地service 测试
 * 
 * @author xubaoji
 * @date 2013-5-17
 *
 * @check 2013-06-04 zhangpeng svn:3510
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TextureServiceTest extends BaseTest {

	/** 质地service */
	@Autowired
	private TextureService textureService;

	/**
	 * 查询所有质地信息测试
	 * 
	 * @author 许保吉
	 * @date   2013-5-17 
	 */
	@Test
	public void TestFindAllTexture(){
		List<Texture> textures = textureService.findAllTexture();
		Assert.assertNotNull(textures);
		Assert.assertEquals(54, textures.size());
	}
}
