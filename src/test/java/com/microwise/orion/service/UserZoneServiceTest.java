package com.microwise.orion.service;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.BaseTest;
import com.microwise.orion.vo.UserZoneVo;

/**
 * 用户区域关系service 测试
 * 
 * @author xubaoji
 * @date 2013-6-17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserZoneServiceTest extends BaseTest {
	
	/**用户区域关系 service*/
	@Autowired
	private  UserZoneService userZoneService;
	
	@Test
	public  void  testFindUserZones(){
		List<UserZoneVo> userZoneVos = userZoneService.findUserZoneList("61011501");
		System.out.println(userZoneVos);
	}
	

}
