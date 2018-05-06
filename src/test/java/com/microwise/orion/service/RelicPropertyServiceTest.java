package com.microwise.orion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Property;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.RelicProperty;

/**
 * 文物service 测试类
 * 
 * @author xubaoji
 * @date 2013-5-13
 * 
 * @check 2013-06-04 zhangpeng svn:4067
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RelicPropertyServiceTest extends CleanDBTest {

	/**
	 * 文物属性字典
	 */
	@Autowired
	private PropertyService propertyService;

	/**
	 * 文物属性信息 service
	 */
	@Autowired
	private RelicPropertyService relicPropertyService;

	/**
	 * 文物service
	 */
	@Autowired
	private RelicService relicService;

	@Before
	public void before() throws Exception {
		xmlInsert2("common/dbxml/orion/RelicPropertyServiceTest.xml");
	}

	/**
	 * 查询文物字典 信息 测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-22
	 */
	@Test
	public void testFindAllProperty() {
		List<Property> propertyList = propertyService.findAllProperty();
		Assert.assertNotNull(propertyList);
        Assert.assertEquals(30,propertyList.size());
	}
	
	/** 根据id删除文物属性测试 */
	@Test
	public void testDeleteRelicProperty() {
		// 删除文物
		relicPropertyService.deleteRelicProperty(2);
		RelicProperty relicProperty = relicPropertyService.findById(2);
		Assert.assertNull(relicProperty);
	}

	/**
	 * 添加或修改文物属性信息测试
	 * 
	 * @author 许保吉
	 * @date 2013-5-20
	 */
	@Test
	public void TestSaveOrUpdateRelicProperty() {
		List<RelicProperty> relicPropertyList = new ArrayList<RelicProperty>();
		Relic relic = new Relic();
		relic.setId(1);
		relic.setTotalCode("1230");
		// 修改文物重量、长度，添加文物来源
		relicPropertyList.add(test(relic, 1, 1, "120kg"));
		relicPropertyList.add(test(relic, null, 2, "120cm"));
		relicPropertyList.add(test(relic, null, 3, "aaa"));
		relicPropertyService.saveOrUpdateRelicProperty(relicPropertyList);
		Relic relic4 = relicService.findRelicByRelicId(1, "31010101");
		Assert.assertNotNull(relic4);
		Map<String, RelicProperty> map = relic4.getRelicPropertyMap();
		Assert.assertNotNull(map);
		Assert.assertEquals(4, map.size());
		Assert.assertEquals("120cm", map.get("sizes").getPropertyValue());
		Assert.assertEquals("aaa", map.get("source").getPropertyValue());
		Assert.assertEquals("120kg", map.get("weight").getPropertyValue());
	}

	/**
	 * 封装文物属性方法
	 * 
	 * @param relic
	 *            文物
	 * @param rProId
	 *            文物属性id
	 * @param propertyId
	 *            属性对应字典表id
	 * @param propertyValue
	 *            属性内容
	 * @return RelicProperty 封装后的文物属性
	 */
	public RelicProperty test(Relic relic, Integer rProId, Integer propertyId,
			String propertyValue) {
		RelicProperty rProperty = new RelicProperty();
		if (rProId != null) {
			rProperty.setId(rProId);
		}
		rProperty.setRelic(relic);
		Property property = new Property();
		property.setId(propertyId);
		rProperty.setProperty(property);
		rProperty.setPropertyValue(propertyValue);
		return rProperty;
	}


}
