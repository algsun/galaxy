package com.microwise.blueplanet.quartz;

import com.microwise.blackhole.service.SubscribeService;
import com.microwise.blueplanet.dao.ReportDao;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.ConfigFactory.Configs;
import com.microwise.common.sys.Constants.Emails;
import org.quartz.*;

import java.io.IOException;

/**
 * 发送 报表 任务计划 类
 *
 * @author xubaoji
 * @date 2013-6-14
 * @check li.jianfei 2013-06-20 #4247
 */
public class SubscribeReportQuartz {

    /**
     * 报表触发器标识
     */
    private static final String SUBSCRIBE_REPORT_TRIGGER = "subscribeReportTrigger";

    /**
     * 报表任务标识
     */
    private static final String SUBSCRIBE_REPORT_JOB = "subscribeReportJob";

    /**
     * 触发器和任务标识
     */
    private static final String BLUEPLANET_GROUP = "blueplanetGroup";

    /**
     * 站点service标识
     */
    public static final String DATE_SITE_SERVICE = "siteService";

    /**
     * 订阅 Service 标识
     */
    public static final String DATE_SUBSCRIBE_SERVICE = "subscribeService";

    /**
     * 报表 Dao 标识
     */
    public static final String DATE_REPORT_DAO = "reportDao";

    /**
     * Quartz调度对象
     */
    private Scheduler scheduler;

    /**
     * 站点 Service
     */
    private SiteService siteService;

    /**
     * 订阅 Service
     */
    private SubscribeService subscribeService;

    /**
     * 报表 Dao
     */
    private ReportDao reportDao;

    /**
     * 构造方法
     *
     * @param scheduler        任务调度对象
     * @param siteService      站点 service
     * @param subscribeService 订阅 service
     * @param reportDao        报表 Dao
     * @throws SchedulerException
     */
    public SubscribeReportQuartz(Scheduler scheduler, SiteService siteService,
                                 SubscribeService subscribeService, ReportDao reportDao)
            throws SchedulerException {
        this.scheduler = scheduler;
        this.siteService = siteService;
        this.subscribeService = subscribeService;
        this.reportDao = reportDao;
    }

    /**
     * 初始化任务计划
     */
    @SuppressWarnings("unused")
    private void initQuartz() throws SchedulerException, IOException {
        JobDetail job = JobBuilder.newJob(SubscribeReportJob.class)
                .withIdentity(SUBSCRIBE_REPORT_JOB, BLUEPLANET_GROUP)
                .storeDurably().build();

        scheduler.addJob(job, false);

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(DATE_SITE_SERVICE, siteService);
        jobDataMap.put(DATE_SUBSCRIBE_SERVICE, subscribeService);
        jobDataMap.put(DATE_REPORT_DAO, reportDao);

        // 设置执行时间
        setTrigger(jobDataMap);
    }

    /**
     * 设置 计划任务
     *
     * @param jobDataMap 任务参数
     * @author 许保吉
     * @date 2013-6-14
     */
    private void setTrigger(JobDataMap jobDataMap) throws SchedulerException {
        Configs configs = ConfigFactory.getInstance().getConfig(
                Emails.EMAIL_CONFIG_PATH);
        Trigger simpleTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(SUBSCRIBE_REPORT_TRIGGER)
                .withSchedule(
                        // 发送 邮件的 时间 周一 八点 2 表示周一
                        CronScheduleBuilder.atHourAndMinuteOnGivenDaysOfWeek(
                                Integer.parseInt(configs.get(Emails.SUBSCRIBE_REPORT_HOUR)),
                                Integer.parseInt(configs.get(Emails.SUBSCRIBE_REPORT_MINUTE)),
                                Integer.parseInt(configs.get(Emails.SUBSCRIBE_REPORT_DAY_OF_WEEK)))
                        //SimpleScheduleBuilder.repeatMinutelyForTotalCount(10, 10)
                ).usingJobData(jobDataMap)
                .forJob(SUBSCRIBE_REPORT_JOB, BLUEPLANET_GROUP).build();
        scheduler.scheduleJob(simpleTrigger);
    }

}
