package com.microwise.blackhole.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blackhole.bean.Department;
import com.microwise.common.sys.test.BaseTest;

/**
 * 测试 部门service
 * 
 * @author xubaoji
 * @date 2013.08.17
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentServiceTest extends BaseTest {

	@Autowired
   private  DepartmentService departmentService;
	
	@Test
	public void testFind() {
	  List<Department> departments=  departmentService.findAll(1);
	  Assert.assertNotNull(departments);
	}

}
