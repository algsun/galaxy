package com.microwise.phoenix.quartz;

import com.microwise.blackhole.bean.Subscribe;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.service.SubscribeService;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.common.service.email.EmailService;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.phoenix.action.index.EmailSummaryReport;
import com.microwise.uma.action.rule.AddOrUpdateRuleAction;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报表订阅 job
 *
 * @author xubaoji
 * @date 2013-07-26
 */
public class SubscribeReportJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(AddOrUpdateRuleAction.class);

    /**
     * 站点service
     */
    private SiteService siteService;

    /**
     * 订阅service
     */
    private SubscribeService subscribeService;

    private EmailSummaryReport emailSummaryReport;

    private LogicGroupService logicGroupService;

    /**
     * 系统日期
     */
    private Date date;

    @Override
    public void execute(JobExecutionContext jobContext) throws JobExecutionException {

        try {
            // 获得 初始化 参数
            date = new Date();

            siteService = (SiteService) jobContext.getTrigger().getJobDataMap()
                    .get(SubscribeReportQuartz.DATE_SITE_SERVICE);
            subscribeService = (SubscribeService) jobContext.getTrigger().getJobDataMap()
                    .get(SubscribeReportQuartz.DATE_SUBSCRIBE_SERVICE);
            emailSummaryReport = (EmailSummaryReport) jobContext.getTrigger().getJobDataMap()
                    .get(SubscribeReportQuartz.DATE_EMAIL_SUKMAR_REPORT);
            logicGroupService = (LogicGroupService) jobContext.getTrigger().getJobDataMap()
                    .get(SubscribeReportQuartz.DATE_LOGICGROUP_SERVICE);
            // 查询 所有有订阅用户的站点
            List<SiteVO> siteVOs = siteService.findSiteHasSubscribeUser();
            for (SiteVO siteVO : siteVOs) {
                Integer logicGroupId = logicGroupService.findLogicGroupIdBySiteId(siteVO
                        .getSiteId());
                if (logicGroupId != null) {
                    sentEmail(siteVO, logicGroupId);
                }
            }
        } catch (Exception e) {
            log.error("发送综合分析月报表订阅邮件出错！", e);
        }
    }

    /**
     * 为每个站点的订阅用户发送邮件
     *
     * @param siteVO 站点实体
     * @return
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @author 许保吉
     * @date 2013-07-26
     */
    private void sentEmail(SiteVO siteVO, int logicGroupId) throws UnsupportedEncodingException,
            MessagingException {
        // 查询站点 下 订阅 周报表 的用户列表
        List<Subscribe> subscribes = subscribeService.findSubscribeBySite(siteVO.getSiteId(),
                Subscribe.SUBSCRIBE_TYPE_PHOENIX_MONTH);

        // 组织邮件
        StringBuffer mailTitle = new StringBuffer();
        String mailText = getEmailText(siteVO, mailTitle, logicGroupId);
        // 获得要发送的用户 邮箱列表
        int i = 0;
        if (subscribes != null && !subscribes.isEmpty()) {
            List<String> recipient = new ArrayList<String>();
            for (Subscribe subscribe : subscribes) {
                recipient.add(subscribe.getUser().getEmail());
                i++;
                // 一百个用户发送一次邮件
                if (i % 100 == 0) {
                    EmailService.sendBatchEmail(mailTitle.toString(), mailText,
                            recipient.toArray(new String[100]));
                    i = 0;
                    recipient.clear();
                }
            }
            // 给 剩余用户发送邮件
            if (!recipient.isEmpty()) {
                EmailService.sendBatchEmail(mailTitle.toString(), mailText,
                        recipient.toArray(new String[i]));
            }
        }
    }

    /**
     * 获得 邮件 报表数据
     *
     * @param siteVO    站点实体
     * @param mailTitle 邮件标题
     * @return
     * @author 许保吉
     * @date 2013.07.26
     */
    private String getEmailText(SiteVO siteVO, StringBuffer mailTitle, int logicGroupId) {
        DateTime dateTime = new DateTime(date);
        DateTime paramDate = dateTime.minusMonths(1);
        // 组织邮件标题
        mailTitle.append(siteVO.getSiteName()).append(paramDate.getYear()).append("年").append(paramDate.getMonthOfYear()).append("月").append("综合报告");
        // 获得系统 根路径
        String galaxyBasePath = ConfigFactory.getInstance().getConfig("config.properties")
                .get("galaxy.basePath");
        // 将报表 数据 处理为 表格
        return emailSummaryReport.emailContent(siteVO.getSiteId(), logicGroupId,
                Constants.FIND_TYPE_MONTH, paramDate.toDate(), galaxyBasePath);
    }
}
