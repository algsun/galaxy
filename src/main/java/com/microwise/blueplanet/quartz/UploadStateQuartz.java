package com.microwise.blueplanet.quartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * 上传设备状态quartz
 *
 * @author liuzhu
 * @date 15-1-8
 */
@Deprecated
public class UploadStateQuartz {

    /**
     * 上传周期
     */
    private String interval_i;

    /**
     * Quartz调度对象
     */
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ApplicationContext appContext;

    /**
     * 初始化任务计划
     */
    @SuppressWarnings("unused")
    private void initQuartz() throws SchedulerException, IOException {
        JobDetail jobDetail = JobBuilder.newJob(UploadStateJob.class)
                .withIdentity("uploadStateJob", "uploadStateGroup")
                .storeDurably().build();
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("appContext", appContext);
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("uploadStateJob", "uploadStateGroup")
                .withSchedule(SimpleScheduleBuilder.repeatHourlyForever(Integer.parseInt(interval_i)))
                .usingJobData(jobDataMap)
                .startNow()
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public String getInterval_i() {
        return interval_i;
    }

    public void setInterval_i(String interval_i) {
        this.interval_i = interval_i;
    }
}
