package com.microwise.blueplanet.quartz;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.microwise.blackhole.bean.Subscribe;
import com.microwise.blackhole.service.SubscribeService;
import com.microwise.blueplanet.bean.po.Stock;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.service.StockService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.common.service.email.EmailService;
import com.microwise.common.service.email.EmailTemplateFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.DateTimeUtil;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * KDJ分析预警Job
 *
 * @author li.jianfei
 * @date 2015-02-28
 */
@Component
@Configurable
@EnableScheduling
public class KDJAlarmJob {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private StockService stockService;

    @Autowired
    private AppCacheHolder appCacheHolder;

    @Autowired
    private SiteService siteService;

    @Value("${blueplanet.kdj.alarm.sensors}")
    private int[] alarmSensors;


    /**
     * KDJ预警邮件模版路径
     */
    private static final String KDJ_ALARM_TEMPLATE = "common/email/kdj-alarm-template.ftl";


    //    @Scheduled(fixedDelay = 5000)
    @Scheduled(cron = "0 0 8 * * ?")
    public void alarm() {
        try {
            List<Subscribe> subscribes = subscribeService.findSubscribes(Subscribe.SUBSCRIBE_TYPE_KDJ_ALARM);
            if (subscribes == null || subscribes.isEmpty()) return;

            for (Subscribe subscribe : subscribes) {
                SiteVO site = siteService.findSiteById(subscribe.getSiteId());

                // 创建数据模型
                Map<String, Object> root = getConditionData(subscribe.getLocationId(), DateTimeUtil.startOfDay(DateTime.now().minusDays(1).toDate()));
//                getConditionData(subscribe.getLocationId(), DateTimeUtil.startOfDay(DateTime.now().minusDays(2).toDate())).isEmpty() &&
                if (!root.isEmpty()) {
                    // 创建和调整配置
                    Configuration cfg = new Configuration();
                    String path = URLDecoder.decode(Resources.getResource(KDJ_ALARM_TEMPLATE).getFile(), "utf-8");
                    cfg.setDirectoryForTemplateLoading(new File(path).getParentFile());
                    cfg.setDefaultEncoding("utf-8");
                    cfg.setObjectWrapper(new DefaultObjectWrapper());

                    // 获取或创建模板
                    Template template = cfg.getTemplate("kdj-alarm-template.ftl");

                    // 将模板和数据模型合并
                    StringWriter out = new StringWriter();
                    template.process(root, out);

                    String templateString = out.toString();

                    // 处理 要发送的邮件
                    EmailTemplateFactory.EmailTemplate emailTemplate = EmailTemplateFactory.getInstance()
                            .getEmailTemplate(Constants.Emails.EMAIL_SUBSCRIBE_PATH);
                    String email = emailTemplate.mergeEmailTemplate(site.getSiteName(), "元智微环境大数据分析预警", templateString);
//                EmailService.sendEmail("li.jianfei@microwise-system.com", "KDJ预警信息", email);
                    EmailService.sendEmail(subscribe.getUser().getEmail(), "元智微环境大数据分析提示", email);
                }

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> getConditionData(String locationId, Date date) throws ExecutionException {
        Map<String, Object> result = Maps.newHashMap();
        List<SensorinfoVO> ascendSensors = Lists.newArrayList();
        List<SensorinfoVO> descendSensors = Lists.newArrayList();
        List<Stock> stocks = stockService.findStocks(locationId, date);
        LocationVO location = locationService.findLocationById(locationId);
        for (Stock stock : stocks) {
            for (int alarmSensor : alarmSensors) {
                if (stock.getSensorId() != alarmSensor) continue;

                SensorinfoVO sensorInfo = appCacheHolder.loadSensorinfo(stock.getSensorId());
                // “黄金交叉”：当监测指标值经过一段时间的低位盘整后，K、D、J三线处于50线下，一旦J线和K线同时上穿D线时
                // “死亡交叉”：当监测指标值经过一段时间上升行情后，一旦J线、K线同时向下突破D线时
                if (stock.getK() > stock.getD() && stock.getJ() > stock.getD()) {
                    if (stock.getK() < 50 && stock.getD() < 50 && stock.getJ() < 50) {
                        // 黄金交叉
                        ascendSensors.add(sensorInfo);
                    }
                } else if (stock.getK() < stock.getD() && stock.getJ() < stock.getD()) {
                    // 死亡交叉
                    descendSensors.add(sensorInfo);
                }
            }
        }

        if (ascendSensors.isEmpty() && descendSensors.isEmpty()) return result;

        result.put("ascendSensors", ascendSensors);
        result.put("descendSensors", descendSensors);
        result.put("locationName", location.getLocationName());
        return result;
    }
}
