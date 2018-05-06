package com.microwise.phoenix.action.index;

import com.google.common.io.Resources;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.BeanMaps;
import com.microwise.phoenix.sys.Phoenix;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.struts2.views.freemarker.StrutsBeanWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author gaohui
 * @date 13-7-26 16:08
 */
@Beans.Bean
@Phoenix
public class EmailSummaryReport {
    public static final Logger log = LoggerFactory.getLogger(EmailSummaryReport.class);

    public static final String EMAIL_TEMPLATE = "common/email/summary-report-template.ftl";
    public static final String TEMPLATE_NAME = "summary-report-template.ftl";

    @Qualifier("phoenix:summaryReportHelper")
    @Autowired
    private SummaryReportHelper summaryReportHelper;

    public String emailContent(String siteId, int logicgroupId, int dateType, Date date, String basePath) {
        try {
            // 创建和调整配置
            Configuration cfg = new Configuration();
            String path = URLDecoder.decode(Resources.getResource(EMAIL_TEMPLATE).getFile(), "utf-8");
            cfg.setDirectoryForTemplateLoading(new File(path).getParentFile());
            cfg.setDefaultEncoding("utf-8");
            // 注意，这里使用 Struts 封装的 ObjectWrapper, 同时提供了 map 的支持
            cfg.setObjectWrapper(new StrutsBeanWrapper(true));

            // 获取或创建模板
            Template template = cfg.getTemplate(TEMPLATE_NAME);

            summaryReportHelper.getStatisticInfo(siteId, logicgroupId, dateType, date);

            Map<String, Object> root = BeanMaps.toMap(summaryReportHelper, new String[]{"zoneDataList",
                    "relicFrequencyByCondition",
                    "relicFrequencyOfAll",
                    "outEventStats",
                    "relicCount",
                    "relicBasicStats",
                    "userDistribution",
                    "userStopover",
                    "highFrequencyUser",
                    "lowFrequencyUser",
                    "highFrequencyAction"
            });
            String chartDate = "";
            if (dateType == 1) {
                chartDate = new SimpleDateFormat("yyyy年").format(date);
            } else if (dateType == 2) {
                chartDate = new SimpleDateFormat("yyyy年MM月").format(date);
            }
            root.put("chartDate", chartDate);
            root.put("basePath", basePath);


            // 将模板和数据模型合并
            StringWriter out = new StringWriter();
            template.process(root, out);

            return out.toString();
        } catch (IOException e) {
            log.error("生成综合报告邮件内容", e);
        } catch (TemplateException e) {
            log.error("生成综合报告邮件内容", e);
        }

        return null;
    }
}
