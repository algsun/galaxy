package com.microwise.blackhole.service;

import com.google.common.base.Objects;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Role;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试角色Service
 * 
 * @author 许保吉
 * @date 2012-11-19
 * 
 * @check 2012-12-19 zhangpeng svn:885
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleServiceTest extends CleanDBTest {

	/** 角色Service */
	@Autowired
	private RoleService roleService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/blackhole/RoleServiceTest.xml");
	}

    /**
     * 角色修改
     *
     * @author 许保吉
     * @date 2012-11-19
     */
    @Test
    public void testUpdate() {
        Role role = new Role();
        role.setId(1);
        role.setRoleName("修改角色测试");
        LogicGroup lg = new LogicGroup();
        lg.setId(1);
        role.setLogicGroup(lg);
        List<String> list = new ArrayList<String>();
        list.add("blackhole:log");
        roleService.updateRole(role, list);
        Role newRole = roleService.findRoleById(1);
        Assert.assertTrue(Objects.equal(newRole.getRoleName(), "修改角色测试"));
    }

	/**
	 * 添加角色
	 * 
	 * @author 许保吉
	 * @date 2012-11-19
	 */
	@Test
	public void testSaveRole() {
		roleService.saveRole(
				1,
				"添加角色测试3",
				Arrays.asList(new String[] { "blackhole:log",
						"blackhole:log:query" }));
		Integer count = roleService.findRoleCountByLogicGroupId(1, "添加角色测试3");
		Assert.assertTrue("添加角色失败", count == 1);
	}

	/**
	 * 通过id 删除角色
	 * 
	 * @author 许保吉
	 * @date 2012-11-19
	 */
	@Test
	public void testDeleteRoleById() {
		roleService.deleteRoleById(3);
		Assert.assertNull("根据角色编号删除角色失败", roleService.findRoleById(3));
	}

}
