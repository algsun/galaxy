/**
 * 
 */
package com.microwise.common.service.quartz;

import org.quartz.*;

import java.io.IOException;

/**
 * 
 * @author zhangpeng
 * @date 2012-10-30
 */
public class QuartzDemo {

	private static final String DEMO_JOB = "common-Job";
	private static final String DEMO_GROUP = "common-Group";

	private Scheduler scheduler;

	public QuartzDemo(Scheduler scheduler) throws SchedulerException {
		this.scheduler = scheduler;
	}

	@SuppressWarnings("unused")
	private void initQuartz() throws SchedulerException, IOException {
		JobDetail job = JobBuilder.newJob(JobDemo.class)
				.withIdentity(DEMO_JOB, DEMO_GROUP).storeDurably()
				.build();
		scheduler.addJob(job, false);

		JobDataMap jobDataMap = new JobDataMap();
		// 设置执行时间
		setTrigger(jobDataMap);
	}

	/**
	 * 设置任务计划
	 * 
	 * @author zhang.licong
	 * @date 2012-7-11
	 */
	private void setTrigger(JobDataMap jobDataMap) throws SchedulerException {
		Trigger simpleTrigger = TriggerBuilder
				.newTrigger()
				.withIdentity("dataSync")
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(5).repeatForever())
				.usingJobData(jobDataMap)
				.forJob(DEMO_JOB, DEMO_GROUP).build();

		scheduler.scheduleJob(simpleTrigger);
	}

}
