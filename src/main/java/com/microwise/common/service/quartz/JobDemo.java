/**
 * 
 */
package com.microwise.common.service.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * 调度任务示例
 *
 * @author zhangpeng
 * @date 2012-10-30
 */
public class JobDemo implements Job {
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("调度测试");
	}
    
}
