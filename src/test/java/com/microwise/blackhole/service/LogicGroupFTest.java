package com.microwise.blackhole.service;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.common.sys.test.BaseTest;

/**
 * 测试 logicGroupSerice
 * 
 * @author xubaoji
 * @date 2012-11-20
 * 
 * @check 2012-12-19 zhangpeng svn:885
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogicGroupFTest extends BaseTest {

	/** 注入 logicGroupService */
	@Autowired
	private LogicGroupService logicGroupService;

	/**
	 * 通过用户id获取用户归属站点及用户可查询的站点组包括子孙中所有基层站点，携带site信息
	 * 
	 * @author zhangpeng
	 * @date 2013-4-3
	 */
	@Test
	public void testFind() {
		List<LogicGroup> lList = logicGroupService.findLogicGroupForMap(4,9);
		for (LogicGroup l : lList) {
			System.out.println(l.getId() + " - " + l.getSite().getSiteName()
					+ " - " + l.getSite().getLngBaiDu() + " - "
					+ l.getSite().getLatBaiDu());
		}
	}

}
