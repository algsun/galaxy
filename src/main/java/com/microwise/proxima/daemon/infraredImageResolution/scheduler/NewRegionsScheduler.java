package com.microwise.proxima.daemon.infraredImageResolution.scheduler;

import com.microwise.proxima.bean.InfraredDVPlaceBean;
import org.quartz.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 负责红外图片解析任务计划添加、取消
 *
 * @author zhang.licong
 * @date 2012-9-10
 * @check guo.tian li.jianfei 2012-09-19
 * @deprecated
 */
public class NewRegionsScheduler implements ApplicationContextAware {



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }

    /**
     * 添加任务计划
     *
     * @param  dvPlace 摄像机点位Bean
     * @author zhang.licong
     * @date 2012-9-11
     */
    public void addInfraredAnalyzerTrigger(InfraredDVPlaceBean dvPlace){

    }


    /**
     * TODO 待实现
     * 添加job
     *
     * @throws org.quartz.SchedulerException
     * @author zhang.licong
     * @date 2012-9-11
     */
    public void addInfraredAnalyzerJob(String dvPlaceId) throws SchedulerException {
    }
    /**
     * 添加job
     *
     * @param dvPlaceId 摄像机点位Id
     * @throws org.quartz.SchedulerException
     * @author zhang.licong
     * @date 2012-9-11
     */
    public void addInfraredAnalyzerJob(int dvPlaceId) throws SchedulerException {
    }

    /**
     * 删除任务计划
     *
     * @param  dvPlaceId 摄像机点位ID
     * @author zhang.licong
     * @date 2012-9-12
     */
    public void removeInfJob(String dvPlaceId) throws SchedulerException {

    }

    /**
     * 获取job
     *
     * @param  dvPlaceId 摄像机点位ID
     * @return JobDetail
     * @author zhang.licong
     * @date 2012-9-12
     */
    private JobDetail getJob(int dvPlaceId) {
        return null;
    }

    /**
     * 获取jobkey
     *
     * @param dvPlaceId 摄像机点位ID
     * @return
     * @author zhang.licong
     * @date 2012-9-11
     */
    private JobKey getJobKey(int dvPlaceId) {
        return null;
    }

    /**
     * 取消某个红外摄像机点位的所有图片解析任务计划
     *
     * @param  dvPlaceId 摄像机点位ID
     * @throws org.quartz.SchedulerException
     * @author zhang.licong
     * @date 2012-9-11
     */
    private void cancelAllInfTrigger(int dvPlaceId) throws SchedulerException {
    }

    /**
     * 中断正在执行的job
     *
     * @param  dvPlaceId 摄像机点位ID
     * @throws org.quartz.UnableToInterruptJobException
     *
     * @author zhang.licong
     * @date 2012-9-12
     */
    private void interruptInfJob(int dvPlaceId) throws UnableToInterruptJobException {
    }

    /**
     * 返回 红外图片区域解析计划触发的 groupName
     *
     * @param  dvPlaceId 摄像机点位ID
     * @return String
     */
    private String triggerGroupName(int dvPlaceId) {
        return null;
    }

    /**
     * 返回 红外图片区域解析计划触发器的 name
     *
     * @param  dvPlaceId 摄像机点位ID
     * @return String
     */
    private String triggerName(int dvPlaceId) {
        return null;
    }

    /**
     * 返回 红外图片区域解析计划 trigger 的 key
     *
     * @param  dvPlaceId 摄像机点位ID
     * @return TriggerKey
     */
    private TriggerKey triggerKey(int dvPlaceId) {
        return null;
    }

}
