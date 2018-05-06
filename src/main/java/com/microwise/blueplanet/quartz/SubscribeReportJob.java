package com.microwise.blueplanet.quartz;

import com.microwise.blackhole.bean.Subscribe;
import com.microwise.blackhole.service.SubscribeService;
import com.microwise.blueplanet.action.statistics.ReportAction;
import com.microwise.blueplanet.bean.vo.ReportVO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.dao.ReportDao;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.common.service.email.EmailService;
import com.microwise.common.service.email.EmailTemplateFactory;
import com.microwise.common.service.email.EmailTemplateFactory.EmailTemplate;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants.Emails;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.uma.action.rule.AddOrUpdateRuleAction;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 报表订阅 job
 *
 * @author xubaoji
 * @date 2013-6-14
 * @check li.jianfei 2013-06-20 #4268
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

    /**
     * 报表service
     */
    private ReportDao reportDao;

    /**
     * 系统日期
     */
    private Date date;

    @Override
    public void execute(JobExecutionContext jobContext)
            throws JobExecutionException {

        try {
            // 获得 初始化 参数
            date = new Date();

            siteService = (SiteService) jobContext.getTrigger().getJobDataMap()
                    .get(SubscribeReportQuartz.DATE_SITE_SERVICE);
            subscribeService = (SubscribeService) jobContext.getTrigger()
                    .getJobDataMap()
                    .get(SubscribeReportQuartz.DATE_SUBSCRIBE_SERVICE);
            reportDao = (ReportDao) jobContext.getTrigger().getJobDataMap()
                    .get(SubscribeReportQuartz.DATE_REPORT_DAO);

            // 查询 所有有订阅用户的站点
            List<SiteVO> siteVOs = siteService.findSiteHasSubscribeUser();
            if (siteVOs != null && !siteVOs.isEmpty()) {
                for (SiteVO siteVO : siteVOs) {
                    sentEmail(siteVO);
                }
            }
        } catch (Exception e) {
            log.error("发送周报表订阅邮件出错！", e);
        }
    }

    /**
     * 为每个站点的订阅用户发送邮件
     *
     * @param siteVO 站点实体
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @author 许保吉
     * @date 2013-6-18
     */
    private void sentEmail(SiteVO siteVO) throws UnsupportedEncodingException,
            MessagingException {
        // 查询站点 下 订阅 周报表 的用户列表
        List<Subscribe> subscribes = subscribeService.findSubscribeBySite(
                siteVO.getSiteId(), Subscribe.SUBSCRIBE_TYPE_WEEK);

        // 组织邮件
        StringBuffer mailTitle = new StringBuffer();
        String mailText = getEmailText(siteVO, mailTitle);
        // 处理 要发送的邮件
        EmailTemplate emailTemplate = EmailTemplateFactory.getInstance()
                .getEmailTemplate(Emails.EMAIL_SUBSCRIBE_PATH);
        String email = emailTemplate.mergeEmailTemplate(siteVO.getSiteName(),
                "周报表", mailText);
        // 获得要发送的用户 邮箱列表
        int i = 0;
        if (subscribes != null && !subscribes.isEmpty()) {
            List<String> recipient = new ArrayList<String>();
            for (Subscribe subscribe : subscribes) {
                recipient.add(subscribe.getUser().getEmail());
                i++;
                // 一百个用户发送一次邮件
                if (i % 100 == 0) {
                    EmailService.sendBatchEmail(mailTitle.toString(), email,
                            recipient.toArray(new String[100]));
                    i = 0;
                    recipient.clear();
                }
            }

            // 给 剩余用户发送邮件
            if (!recipient.isEmpty()) {
                EmailService.sendBatchEmail(mailTitle.toString(), email,
                        recipient.toArray(new String[i]));
            }
        }
    }

    /**
     * 获得 邮件 报表数据
     *
     * @param siteVO    站点实体
     * @param mailTitle 邮件标题
     * @return 邮件内容
     * @author 许保吉
     * @date 2013-6-18
     */
    public String getEmailText(SiteVO siteVO, StringBuffer mailTitle) {
        Calendar calendar = Calendar.getInstance();
        // 获得 发邮件 为 周几 标识 用当前系统时间 减去 这个int标识 获得到 上周的一个时间
        String s = ConfigFactory.getInstance()
                .getConfig(Emails.EMAIL_CONFIG_PATH)
                .get(Emails.SUBSCRIBE_REPORT_DAY_OF_WEEK);
        // 为 calendar 添加 上周 的一个日期 用来获得 上周 的开始 日期 和结束日期
        calendar.setTime(DateUtils.addDays(date, -Integer.parseInt(s)));
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        calendar.add(Calendar.DATE, -day_of_week);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 6);
        Date endDate = calendar.getTime();
        // 组织邮件标题
        mailTitle.append(siteVO.getSiteName()).append(":").append(DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, startDate)).append("--").append(DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, endDate)).append("周报表");
        // 获得 报表数据
        List<ReportVO> reportVOs = reportDao.findReportInfo(
                siteVO.getSiteId(),
                DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD,
                        DateTimeUtil.formatFull(startDate)),
                DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD,
                        DateTimeUtil.formatFull(endDate)));
        // 将报表 数据 处理为 表格
        return ReportAction.reportData(siteVO.getSiteName(),
                DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, startDate),
                DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, endDate), reportVOs);
    }
}
