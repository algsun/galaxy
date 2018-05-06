package com.microwise.blackhole.service;

import java.util.List;
import java.util.Set;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blackhole.bean.Task;
import com.microwise.blackhole.bean.TaskRecord;
import com.microwise.common.sys.test.CleanDBTest;

/**
 * 任务 添加修改测试
 * 
 * @author xu.baoji
 * @date 2013-7-23
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskFindTest extends CleanDBTest {

	/** 任务service */
	@Autowired
	private TaskService taskService;


	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/blackhole/TaskFindTest.xml");
	}

	/** 分页 查询任务列表 */
	@Test
	public void testFindTasks() {
		List<Task> tasks = taskService.findTasks(1, 1, 1, 3, -1);
		Assert.assertNotNull(tasks);
		Assert.assertEquals(3, tasks.size());
		tasks = taskService.findTasks(1, 1, 1, 5, 0);
		Assert.assertNotNull(tasks);
		Assert.assertEquals(3, tasks.size());
		tasks = taskService.findTasks(1, 2, 1, 3, -1);
		Assert.assertNotNull(tasks);
		Assert.assertEquals(1, tasks.size());
		tasks = taskService.findTasks(0, 2, 1, 3, -1);
		Assert.assertNotNull(tasks);
		Assert.assertEquals(0, tasks.size());
	}

	/** 查询任务总数量 */
	@Test
	public void testFindTaskCount() {
		int count = taskService.findTaskCount(1, 1, -1);
		Assert.assertEquals(4, count);
		count = taskService.findTaskCount(1, 1, 0);
		Assert.assertEquals(3, count);
		count = taskService.findTaskCount(1, 2, -1);
		Assert.assertEquals(1, count);
		count = taskService.findTaskCount(0, 2, -1);
		Assert.assertEquals(0, count);
	}

	/** 通过id 查询任务（携带有任务回复记录） */
	@Test
	public void testFindById() {
		Task task = taskService.findById(1);
		Assert.assertNotNull(task);
		Set<TaskRecord> taskRecords = task.getTaskRecords();
		Assert.assertNotNull(taskRecords);
		Assert.assertEquals(2, taskRecords.size());
		Assert.assertEquals(2, task.getDesignees().size());
		task = taskService.findById(5);
		Assert.assertNull(task);

		task = taskService.findById(4);
		Assert.assertNotNull(task);
		Assert.assertNotNull(task.getTaskRecords());
		Assert.assertEquals(0, task.getTaskRecords().size());
	}

}
