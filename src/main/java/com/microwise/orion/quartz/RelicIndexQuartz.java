package com.microwise.orion.quartz;

import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.orion.service.RelicService;
import org.quartz.*;

import java.io.IOException;

/**
 * 重建索引任务类
 *
 * @author li.jianfei
 * @date 2014-04-24
 */
public class RelicIndexQuartz {

    /**
     * 重建索引触发器标识
     */
    private static final String RELIC_INDEX_TRIGGER = "relicIndexTrigger";

    /**
     * 重建索引任务标识
     */
    private static final String RELIC_INDEX_JOB = "relicIndexJob";

    /**
     * 触发器和任务标识
     */
    private static final String ORION_GROUP = "orionGroup";

    /**
     * 文物service标识
     */
    public static final String RELIC_SERVICE = "relicService";

    public static final String LOGICGROUP_SERVICE = "logicGroupService";


    /**
     * Quartz调度对象
     */
    private Scheduler scheduler;

    /**
     * 文物 Service
     */
    private RelicService relicService;

    private LogicGroupService logicGroupService;

    /**
     * 构造方法
     *
     * @param scheduler    任务调度器
     * @param relicService 文物 Service
     */
    public RelicIndexQuartz(Scheduler scheduler, RelicService relicService,
                            LogicGroupService logicGroupService) {
        this.scheduler = scheduler;
        this.relicService = relicService;
        this.logicGroupService = logicGroupService;
    }

    /**
     * 初始化任务计划
     */
    @SuppressWarnings("unused")
    private void initQuartz() throws SchedulerException, IOException {
        ConfigFactory.Configs appConfig = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        boolean fullTextSearchEnable = Boolean.parseBoolean(appConfig.get("orion.relic.fullTextSearch.enable"));
        if (!fullTextSearchEnable) {
            return;
        }
        JobDetail job = JobBuilder.newJob(RelicIndexJob.class)
                .withIdentity(RELIC_INDEX_JOB, ORION_GROUP).storeDurably().build();
        scheduler.addJob(job, false);

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(RELIC_SERVICE, relicService);
        jobDataMap.put(LOGICGROUP_SERVICE, logicGroupService);
        // 设置执行时间
        setTrigger(jobDataMap);
    }

    /**
     * 设置 计划任务
     *
     * @param jobDataMap 任务参数
     * @author li.jianfei
     * @date 2014-04-24
     */
    private void setTrigger(JobDataMap jobDataMap) throws SchedulerException {
        Trigger simpleTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(RELIC_INDEX_TRIGGER)
                .withSchedule(
                        // 每天 0点0分
                        CronScheduleBuilder.dailyAtHourAndMinute(0, 0)

                )
                .usingJobData(jobDataMap).forJob(RELIC_INDEX_JOB, ORION_GROUP).build();
        scheduler.scheduleJob(simpleTrigger);
    }
}
