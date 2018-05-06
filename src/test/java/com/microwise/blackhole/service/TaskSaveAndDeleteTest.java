package com.microwise.blackhole.service;

import java.util.Arrays;
import java.util.Date;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blackhole.bean.Task;
import com.microwise.blackhole.bean.TaskRecord;
import com.microwise.blackhole.bean.User;
import com.microwise.common.sys.test.CleanDBTest;

/**
 * 任务 添加修改测试
 * 
 * @author xu.baoji
 * @date 2013-7-23
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskSaveAndDeleteTest extends CleanDBTest {

	/**任务service*/
	@Autowired
	private  TaskService  taskService;
	
	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/blackhole/TaskSaveAndDeleteTest.xml");
	}
	
	/**添加任务测试*/
	@Test
	public void testSaveAndDeleteTask(){
		Task task = new Task();
		task.setTaskTitle("测试");
		task.setReleaseDate(new Date());
		User releaser = new User();
		releaser.setId(1);
		task.setReleaser(releaser);
		task.setState(false);
		task.setLogicGroupId(1);
		taskService.createTask(task,Arrays.asList(new Integer []{2,1}));
		Integer count = taskService.findTaskCount(1, 1,-1);
		Assert.assertEquals(3, count.intValue());
		taskService.deleteTask(1);
	    count = taskService.findTaskCount(1, 1,-1);
		Assert.assertEquals(2, count.intValue());
	}
	
	/** 添加任务回复测试*/
	@Test
	public void testSaveTaskRecrod(){
		TaskRecord taskRecord = new TaskRecord();
		taskRecord.setRecordDate(new Date());
		User user = new User();
		user.setId(2);
		taskRecord.setReplier(user);
		Task task = new Task();
		task.setId(2);
		taskRecord.setTask(task);
		taskRecord.setState(false);
		taskService.addTaskRecord(taskRecord);
		Task task1 = taskService.findById(2);
		Assert.assertNotNull(task1);
		Assert.assertEquals(2, task1.getId().intValue());
		Assert.assertEquals(2, task1.getTaskRecords().size());
	}
	
	/**任务更新测试*/
	@Test
	public void testUpdateTask(){
		taskService.updateTask(Arrays.asList(new Integer[]{2}), 32, 0, 2);
		Task task = taskService.findById(2);
		Assert.assertNotNull(task);
		Assert.assertEquals(1, task.getDesignees().size());
		
	}
	
	
	

}
